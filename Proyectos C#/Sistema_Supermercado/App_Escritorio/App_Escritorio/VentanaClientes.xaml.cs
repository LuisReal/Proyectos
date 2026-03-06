using iTextSharp.text;
using iTextSharp.text.pdf;
using iTextSharp.tool.xml;
using Microsoft.Win32;
using MySqlX.XDevAPI;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Shapes;
using static iTextSharp.text.pdf.AcroFields;
using PdfImage = iTextSharp.text.Image; //alias para no tener errores con el compilador ya que Image tambien se usa para crear iconos.

namespace App_Escritorio
{
    /// <summary>
    /// Interaction logic for VentanaClientes.xaml
    /// </summary>
    public partial class VentanaClientes : Window
    {
        private List<KeyValuePair<string, int>> productosOrdenados;
        private int productos_vendidos;
        private decimal total_ventas;
        private DataBase db;
        private string nombre_boton;
        private Window login; //se usara para cerrar sesion
        public VentanaClientes(Window login)
        {
            InitializeComponent();
            this.login = login;
            db = new DataBase();

            ClearTopButtons();

            AddTopButton("Cargar Clientes", "Icons/cargar.png", Button_UploadClients);
            AddTopButton("Dashboard Clientes", "Icons/barras.png", button_dashboardClientes);
            AddTopButton("Creación Clientes", "Icons/cliente.png", button_createClients);
            AddTopButton("Consulta Clientes", "Icons/consulta.png", button_customerInquiry);
            AddTopButton("Modificar Clientes", "Icons/modificar.png", button_ModifyClient);

            AddInfoButton("PDF", "Icons/pdf.png", (s, e) => button_createPDFreport(s, e, "Clientes"));

            MainContent.Content = new Dashboard("Dashboard Clientes");

        }

        protected override void OnClosing(System.ComponentModel.CancelEventArgs e)
        {
            base.OnClosing(e);
            Application.Current.Shutdown(); // Fuerza el cierre total
        }
        private void Button_Clients(object sender, RoutedEventArgs e)
        {
            MarcarBotonActivo((Button)sender);
            ClearTopButtons();
            ClearInfoTopButtons();

            AddTopButton("Cargar Clientes", "Icons/cargar.png", Button_UploadClients);
            AddTopButton("Dashboard Clientes", "Icons/barras.png", button_dashboardClientes);
            AddTopButton("Creación Clientes", "Icons/cliente.png", button_createClients);
            AddTopButton("Consulta Clientes", "Icons/consulta.png", button_customerInquiry);
            AddTopButton("Modificar Clientes", "Icons/modificar.png", button_ModifyClient);

            AddInfoButton("PDF", "Icons/pdf.png", (s, e) => button_createPDFreport(s, e, "Clientes"));
            //Button? botonPresionado = sender as Button;
            MainContent.Content = new Dashboard("Dashboard Clientes");
        }

        private void button_createPDFreport(object sender, RoutedEventArgs e, string reporte)
        {
            SaveFileDialog savefile = new SaveFileDialog();
            var fecha = DateTime.Now.ToString("yyyy/MM/dd/HH:mm:ss");
            savefile.FileName = $"Reporte{reporte}-{DateTime.Now:yyyyMMddHHmmss}.pdf";


            if (savefile.ShowDialog() == true)
            {
                string html = @"
                <!DOCTYPE html>
                <html xmlns=""http://www.w3.org/1999/xhtml"">
                <head>
                    <title>Title of the document</title>
                    <style>
                        table.border {
                            border-collapse: collapse;
                        }

                            table.border th {
                                padding: 5px;
                                border: 1px solid black;
                            }

                            table.border td {
                                padding: 5px;
                                border: 1px solid black;
                            }
                    </style>
                </head>
                <body>
                    <table style=""width:100%"">
                        <tr>
                            <td style=""width:20%""></td>
                            <td style=""width:60%"" align=""center"" valign=""top"">
                                <table>
                                    <tr><td align=""center""><h4 style=""margin:0px"">@REPORTE</h4></td></tr>
                                    <tr><td align=""center"">Direccion: Avenida la Castellana, zona 9 </td></tr>
                                    <tr><td align=""center"">Telefono: 555-555-55</td></tr>
                                </table>
                            </td>
                            <td style=""width:20%"">
                                <table class=""border"" style=""width:100%"">
                                    <tr><td align=""center"">RUC: </td></tr>
                                    <tr><td align=""center"" style=""background-color:#D8D8D8"">BOLETA</td></tr>
                                    <tr><td align=""center"">Nro: 00000111</td></tr>
                                </table>
                            </td>
                        </tr>

                        <tr><td colspan=""3"" height=""20""></td></tr>

                        <tr>
                            <td colspan=""2"">
                                <table style=""width:100%"">
                                    <tr>
                                        <td colspan=""1"" style=""width:20%"">Supermercado:</td>
                                        <td colspan=""3"" style=""width:80%;border-bottom:1px solid black"">@SUPERMERCADO</td>
                                    </tr>
                                </table>
                                <table style=""width:100%"">
                                    <tr>
                                        <td style=""width:20%"">Documento:</td>
                                        <td style=""width:30%;border-bottom:1px solid black"">@TIPO-REPORTE</td>
                                        <td style=""width:10%"">Fecha: </td>
                                        <td style=""width:40%;border-bottom:1px solid black"">@FECHA</td>
                                    </tr>
                                </table>
                            </td>
                            <td></td>
                        </tr>

                        <tr><td colspan=""3"" height=""30""></td></tr>
                        
                        <tr>
                            <td colspan=""3"">
                                @TABLAVENTAS
                            </td>
                        </tr>
                        
                        <tr><td colspan=""3"" height=""30""></td></tr>

                        <tr>
                            <td colspan=""3"">
                                <table class=""border"" style=""width:100%;"">
                                        @HEAD
                                    <tbody>
                                        
                                        @FILAS
                                        
                                    </tbody>
                                </table>
                            </td>
                        </tr>
                         <tr><td colspan=""3"" height=""30""></td></tr>

                        
                    </table>
                </body>
                </html>";

                /*
                 <tr>
                            <td colspan=""3"">
                                <table class=""border"" style=""width:100%;"">
                                        @CABECERA
                                    <tbody>
                                        
                                        @ROWS
                                        
                                    </tbody>
                                </table>
                            </td>
                        </tr>*/


                if (reporte == "Clientes") {

                    string titulo_reporte = $@"REPORTE DE CLIENTES";
                    string tipo_reporte = $@"Reporte de Clientes";

                    string head = $@"<thead>
                            <tr style=""background-color:#D8D8D8"">
                                <th>Id Cliente</th>
                                <th>Nombre</th>
                                <th>Edad</th>
                                <th>Sexo</th>
                                <th>Nit</th>
                            </tr>
                           </thead>";

                    List<Clients> listClients = db.getAllClients();

                    string filas = "";

                    foreach (var client in listClients)
                    {
                        filas += $@"
                    <tr>
                        <td>{client.Id_client}</td>
                        <td>{client.Name}</td>
                        <td>{client.Age}</td>
                        <td>{client.Gender}</td>
                        <td>{client.Nit}</td>
                    </tr>";
                    }

                    html = html.Replace("@FECHA", fecha);
                    html = html.Replace("@FILAS", filas);
                    html = html.Replace("@HEAD", head);
                    html = html.Replace("@TIPO-REPORTE", tipo_reporte);
                    html = html.Replace("@REPORTE", titulo_reporte);
                    html = html.Replace("@TABLAVENTAS", ""); //evita que se imprima en el pdf esta palabra: @TABLAVENTAS


                }
                else if (reporte == "Productos") {

                    string titulo_reporte = $@"REPORTE DE PRODUCTOS";
                    string tipo_reporte = $@"Reporte de Productos";

                    string head = $@"<thead>
                            <tr style=""background-color:#D8D8D8"">
                                <th>Id Producto</th>
                                <th>Nombre</th>
                                <th>Precio</th>
                                <th>Cantidad</th>
                                
                            </tr>
                           </thead>";

                    List<Products> listProducts = db.getAllProducts();

                    string filas = "";

                    foreach (var product in listProducts)
                    {
                        filas += $@"
                    <tr>
                        <td>{product.Id_product}</td>
                        <td>{product.Name}</td>
                        <td>{product.Price}</td>
                        <td>{product.Cantidad}</td>
                        
                    </tr>";
                    }

                    html = html.Replace("@FECHA", fecha);
                    html = html.Replace("@FILAS", filas);
                    html = html.Replace("@HEAD", head);
                    html = html.Replace("@TIPO-REPORTE", tipo_reporte);
                    html = html.Replace("@REPORTE", titulo_reporte);
                    html = html.Replace("@TABLAVENTAS", ""); //evita que se imprima en el pdf esta palabra: @TABLAVENTAS
                } else if(reporte == "Ventas"){

                    List<Products> listProducts = db.getAllProducts();

                    int cantidad_vendida_mayor = 0;
                    int productos_vendidos = 0;
                    decimal total_acumulado = 0; //se usa decimal para poder representar con 2 decimales porque con double no es posible
                    decimal total = 0;


                    string filas = "";

                    

                    for (int i = 0; i < this.productosOrdenados.Count; i++) //obteniendo todos los productos ordenados ascendentemente
                    {
                        
                        var item = this.productosOrdenados[i];

                        if (i == 0)
                        { // obtiene la cantidad del primer producto mas vendido
                            cantidad_vendida_mayor = item.Value;
                        }

                        foreach (Products producto in listProducts)
                        {
                            
                            if (item.Key == producto.Name)
                            {
                                productos_vendidos += item.Value;
                                total_acumulado += Math.Round((decimal)producto.Price * item.Value, 2);
                                total = Math.Round((decimal)producto.Price * item.Value, 2);
                                //dt.Rows.Add(item.Key, item.Value, producto.Price, total, total_acumulado);
                                
                                filas += $@"
                                <tr>
                                    <td>{item.Key}</td>
                                    <td>{item.Value}</td>
                                    <td>{producto.Price}</td>
                                    <td>{total}</td>
                                    <td>{total_acumulado}</td>
                                </tr>";
                            }
                        }

                        //Console.WriteLine($"Producto: {item.Key}, Cantidad: {item.Value}");

                    }

                    string titulo_reporte = $@"REPORTE DE VENTAS";
                    string tipo_reporte = $@"Reporte de Ventas";

                    string head = $@"<thead>
                            <tr style=""background-color:#D8D8D8"">
                                <th>Producto</th>
                                <th>Cantidad</th>
                                <th>Precio</th>
                                <th>Total</th>
                                <th>Total Acumulado</th>
                            </tr>
                           </thead>";

                    string tabla = "";
                    tabla += $@"<table class=""border"" style=""width:100%;"">
                                <thead>
                                    <tr style=""background-color:#D8D8D8"">
                                        <th>Productos Vendidos</th>
                                        <th>Total Ventas</th>
                                
                                    </tr>
                                 </thead>
                                <tbody>
                                    <tr>
                                        <td>{this.productos_vendidos}</td>
                                        <td>{this.total_ventas}</td>
                                    
                                    </tr>
                                </tbody>
                            </table>";
                    string filas2 = "";
                    filas2 += $@"";
                    
                    string head2 = $@"";

                    html = html.Replace("@FECHA", fecha);
                    html = html.Replace("@FILAS", filas);
                    html = html.Replace("@HEAD", head);
                    html = html.Replace("@TIPO-REPORTE", tipo_reporte);
                    html = html.Replace("@REPORTE", titulo_reporte);
                    //html = html.Replace("@ROWS", filas2);
                    //html = html.Replace("@CABECERA", head2);
                    html = html.Replace("@TABLAVENTAS", tabla);

                }



                    string rutaPdf = savefile.FileName;

                using (FileStream stream = new FileStream(rutaPdf, FileMode.Create))
                {
                    Document pdfDoc = new Document(PageSize.A4, 25, 25, 25, 25);
                    PdfWriter writer = PdfWriter.GetInstance(pdfDoc, stream);
                    pdfDoc.Open();

                    using (StringReader sr = new StringReader(html))
                    {
                        XMLWorkerHelper.GetInstance().ParseXHtml(writer, pdfDoc, sr);
                    }

                    pdfDoc.Close();
                    stream.Close();
                }
            }



        }

        private void MarcarBotonActivo(Button boton)
        {
            // Resetear todos los botones primero
            ButtonClientes.Background = (Brush)new BrushConverter().ConvertFromString("#FF2E2A2A");
            ButtonProductos.Background = (Brush)new BrushConverter().ConvertFromString("#FF2E2A2A");
            ButtonVentas.Background = (Brush)new BrushConverter().ConvertFromString("#FF2E2A2A");

            // Cambiar el fondo del botón activo
            boton.Background = (Brush)new BrushConverter().ConvertFromString("#FF007ACC"); // color de seleccionado
        }
        private void button_dashboardClientes(object sender, RoutedEventArgs e)
        {
            //Button? botonPresionado = sender as Button;
            MainContent.Content = new Dashboard("Dashboard Clientes"); // Dashboard es de tipo UserControl (ya no es Window)
                                                                       //Dashboard dashboard = new Dashboard(botonPresionado);
                                                                       //dashboard.Show();

        }
        private void Button_UploadClients(object sender, RoutedEventArgs e)
        {
            Button? botonPresionado = sender as Button;

            CargarDatos cargar_archivo = new CargarDatos("Cargar Clientes"); //carga la informacion en la base de datos
        }



        private void button_createClients(object sender, RoutedEventArgs e)
        {

            VentanaNuevo_Cliente ventana = new VentanaNuevo_Cliente("Creacion Clientes", "");
            ventana.Show();
        }

        private void button_customerInquiry(object sender, RoutedEventArgs e)
        {


            VentanaConsultaCliente ventana = new VentanaConsultaCliente("Consulta Clientes");
            ventana.Show();
        }

        private void button_ModifyClient(object sender, RoutedEventArgs e)
        {

            VentanaConsultaCliente ventana2 = new VentanaConsultaCliente("Modificar Clientes");
            ventana2.Show();
        }

        //***************************** PRODUCTOS ***************************************************

        private void Button_Products(object sender, RoutedEventArgs e)
        {
            MarcarBotonActivo((Button)sender);
            ClearTopButtons();
            ClearInfoTopButtons();

            AddTopButton("Cargar Productos", "Icons/cargar.png", Button_UploadClients);
            AddTopButton("Dashboard Productos", "Icons/barras.png", button_dashboardProductos);
            AddTopButton("Creación Productos", "Icons/productos.png", button_createProducts);
            AddTopButton("Consulta Productos", "Icons/consulta.png", button_productInquiry);
            AddTopButton("Modificar Productos", "Icons/modificar.png", button_ModifyProduct);

            AddInfoButton("PDF", "Icons/pdf.png", (s, e) => button_createPDFreport(s, e, "Productos"));
            //Button? botonPresionado = sender as Button;
            MainContent.Content = new Dashboard("Dashboard Productos");
        }

        private void button_dashboardProductos(object sender, RoutedEventArgs e)
        {
            //Button? botonPresionado = sender as Button;
            MainContent.Content = new Dashboard("Dashboard Productos"); // Dashboard es de tipo UserControl (ya no es Window)
                                                                        //Dashboard dashboard = new Dashboard(botonPresionado);
                                                                        //dashboard.Show();

        }

        private void button_createProducts(object sender, RoutedEventArgs e)
        {

            VentanaNuevo_Producto ventana = new VentanaNuevo_Producto("Creacion Productos", "");
            ventana.Show();
        }

        private void button_productInquiry(object sender, RoutedEventArgs e)
        {


            VentanaConsultaProducto ventana = new VentanaConsultaProducto("Consulta Productos");
            ventana.Show();
        }

        private void button_ModifyProduct(object sender, RoutedEventArgs e)
        {

            VentanaConsultaProducto ventana2 = new VentanaConsultaProducto("Modificar Productos");
            ventana2.Show();
        }

        //***************************** VENTAS ***************************************************

        private void Button_Sales(object sender, RoutedEventArgs e)
        {
            MarcarBotonActivo((Button)sender);
            ClearTopButtons();
            ClearInfoTopButtons();

            AddTopButton("Cargar Ventas", "Icons/cargar.png", Button_UploadSales);
            AddTopButton("Dashboard Ventas", "Icons/barras.png", button_dashboardSales);
            AddTopButton("Crear Venta", "Icons/ventas.png", button_createSale);
            AddTopButton("Consulta Ventas", "Icons/consulta.png", button_saleInquiry);

            AddInfoButton("PDF", "Icons/pdf.png", (s, e) => button_createPDFreport(s, e, "Ventas"));
            //Button? botonPresionado = sender as Button;

            Dashboard ventas = new Dashboard("Dashboard Ventas");
            MainContent.Content = ventas;

            this.productosOrdenados = ventas.productosOrdenados;
            this.productos_vendidos = ventas.productos_vendidos;
            this.total_ventas = ventas.total_ventas;

            
        }

        private void button_dashboardSales(object sender, RoutedEventArgs e)
        {
            //Button? botonPresionado = sender as Button;
            
            MainContent.Content = new Dashboard("Dashboard Ventas"); // Dashboard es de tipo UserControl (ya no es Window)
                                                                     //Dashboard dashboard = new Dashboard(botonPresionado);
                                                                     //dashboard.Show();

           
        }
        private void Button_UploadSales(object sender, RoutedEventArgs e)
        {
            Button? botonPresionado = sender as Button;

            CargarDatos cargar_archivo = new CargarDatos("Cargar Ventas"); //carga la informacion en la base de datos
        }

        private void button_createSale(object sender, RoutedEventArgs e)
        {

            VentanaNueva_Venta ventana = new VentanaNueva_Venta("Crear Venta", "");
            ventana.Show();
        }

        private void button_saleInquiry(object sender, RoutedEventArgs e)
        {


            VentanaConsultaVentas ventana = new VentanaConsultaVentas();
            ventana.Show();
        }


        private void ClearInfoTopButtons()
        {
            TopInfoGrid.Children.Clear();
            TopInfoGrid.ColumnDefinitions.Clear();
        }

        private void ClearTopButtons()
        {
            TopButtonsGrid.Children.Clear();
            TopButtonsGrid.ColumnDefinitions.Clear();
        }

        private void AddTopButton(string text, string ruta, RoutedEventHandler clickHandler)
        {
            int col = TopButtonsGrid.ColumnDefinitions.Count;

            // Agrega una nueva columna y Cada botón ocupa una columna
            TopButtonsGrid.ColumnDefinitions.Add(new ColumnDefinition { Width = new GridLength(1, GridUnitType.Star) });

            System.Windows.Controls.Image icon = new System.Windows.Controls.Image
            {   /*Para poder usar la siguiente ruta relativa, primero configurar (para cada imagen) Build Action = content 
                 en el solution explorer de la derecha,
                ir a carpeta icons, click derecho en la imagen y seleccionar propiedades tambien en copy to output 
                seleccionar Copy to Output Directory = Copy if newer*/
                Source = new BitmapImage(new Uri(ruta, UriKind.Relative)),
                Width = 24,
                Height = 24,
                Margin = new Thickness(0, 0, 10, 0) // margen a la derecha para separar del texto
            };

            // Crear el texto
            TextBlock txt = new TextBlock
            {
                Text = text,
                FontSize = 16,
                FontFamily = new FontFamily("Bahnschrift SemiBold"),
                VerticalAlignment = VerticalAlignment.Center
            };

            // Contenedor horizontal
            StackPanel panel = new StackPanel
            {
                Orientation = Orientation.Horizontal,
                Children = { icon, txt }
            };


            Button btn = new Button
            {
                Content = panel,
                FontSize = 16,
                FontFamily = new FontFamily("Bahnschrift SemiBold"),
                BorderBrush = null,
                Background = new LinearGradientBrush
                {
                    StartPoint = new Point(0.5, 0),
                    EndPoint = new Point(0.5, 1),
                    GradientStops =
            {
                new GradientStop((Color)ColorConverter.ConvertFromString("#FF54D6D6"), 0),
                new GradientStop((Color)ColorConverter.ConvertFromString("#FF6FFFFF"), 1),
                new GradientStop(Colors.White, 0.513)
            }
                }
            };

            btn.Click += clickHandler;

            Grid.SetColumn(btn, col);
            TopButtonsGrid.Children.Add(btn);
        }


        private void AddInfoButton(string text, string ruta, RoutedEventHandler clickHandler)
        {
            int col = TopInfoGrid.ColumnDefinitions.Count;

            // Agrega una nueva columna y Cada botón ocupa una columna
            TopInfoGrid.ColumnDefinitions.Add(new ColumnDefinition { Width = new GridLength(1, GridUnitType.Auto) });

            System.Windows.Controls.Image icon = new System.Windows.Controls.Image
            {   /*Para poder usar la siguiente ruta relativa, primero configurar (para cada imagen) Build Action = content 
                 en el solution explorer de la derecha,
                ir a carpeta icons, click derecho en la imagen y seleccionar propiedades tambien en copy to output 
                seleccionar Copy to Output Directory = Copy if newer*/
                Source = new BitmapImage(new Uri(ruta, UriKind.Relative)),
                Width = 24,
                Height = 24,
                Margin = new Thickness(0, 0, 10, 0) // margen a la derecha para separar del texto
            };

            // Crear el texto
            TextBlock txt = new TextBlock
            {
                Text = text,
                FontSize = 16,
                FontFamily = new FontFamily("Bahnschrift SemiBold"),
                VerticalAlignment = VerticalAlignment.Center,
                Foreground = Brushes.White
            };

            // Contenedor horizontal
            StackPanel panel = new StackPanel
            {
                Orientation = Orientation.Horizontal,
                Children = { icon, txt }
            };


            Button btn = new Button
            {
                Content = panel,
                FontSize = 16,
                FontFamily = new FontFamily("Bahnschrift SemiBold"),
                BorderBrush = null,
                Background = new SolidColorBrush(Color.FromRgb(255, 0, 0)), // rojo claro

            };

            btn.Click += clickHandler;
            btn.Width = 100;
            btn.HorizontalAlignment = HorizontalAlignment.Right;


            //Grid.SetColumn(btn, col);
            TopInfoGrid.Children.Add(btn);
        }

        private void Button_Session(object sender, RoutedEventArgs e)
        {
            this.login.Show();
            this.Hide();
        }

        //siguiente metodo
    }
}
