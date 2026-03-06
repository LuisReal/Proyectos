using LiveCharts;
using LiveCharts.Defaults;
using LiveCharts.Wpf;
using LiveCharts.Wpf.Charts.Base;
using System;
using System.Collections.Generic;
using System.Data;
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


namespace App_Escritorio
{
    /*Para instalar la libreria LiveCharts ir a tools>Nugget package manager>package manager console y ejecutar lo siguiente:
     Install-Package LiveCharts.Wpf
    */

    public partial class Dashboard : UserControl
    {
        public List<KeyValuePair<string, int>> productosOrdenados;
        public int productos_vendidos;
        public decimal total_ventas;
        public SeriesCollection SeriesCollectionBars { get; set; }
        public SeriesCollection SeriesCollectionPie { get; set; }
        private DataBase db;


        public string[] Labels { get; set; }
        public Func<double, string> Formatter { get; set; }

        private Button botonOrigen;
        public Dashboard(String dashboard)
        {

            InitializeComponent();
            db = new DataBase();


            //botonOrigen = boton;

            //Console.WriteLine("El boton recivido es: "+boton);

            /*
            if (botonOrigen.Content?.ToString() == "Dashboard Clientes" || botonOrigen.Content?.ToString() == "Administracion de Clientes")
            {

                Console.WriteLine("Creando Dashboard Clientes\n");
                createDashboardClients();

            } else if(botonOrigen.Content?.ToString() == "Dashboard Productos" || botonOrigen.Content?.ToString() == "Administracion de Productos")
            {
                Console.WriteLine("Creando Dashboard Productos\n");
                createDashboardProducts();
            }
            else if (botonOrigen.Content?.ToString() == "Dashboard Ventas" || botonOrigen.Content?.ToString() == "Administracion de Ventas")
            {
                Console.WriteLine("Creando Dashboard Ventas\n");
                createDashboardSales();
            }
            else
            {

                Console.WriteLine("NO SE PUDO CARGAR EL DASHBOARD");

            }*/

            if (dashboard == "Dashboard Clientes" )
            {

                Console.WriteLine("Creando Dashboard Clientes\n");
                createDashboardClients();

            }
            else if (dashboard == "Dashboard Productos")
            {
                Console.WriteLine("Creando Dashboard Productos\n");
                createDashboardProducts();
            }
            else if (dashboard == "Dashboard Ventas")
            {
                Console.WriteLine("Creando Dashboard Ventas\n");
                createDashboardSales();
            }
            else
            {

                Console.WriteLine("NO SE PUDO CARGAR EL DASHBOARD");

            }
        }

        public void createDashboardClients()
        {

            List<int> edades = db.getClientsAge();

            if (edades != null)//necesario para que se abra la ventana.
            {

                double mayor, menor;

                // Inicializamos mayor y menor con el primer valor del List
                mayor = menor = edades[0];

                int numero_edades = 0;

                // Recorrer el List de edades
                for (int i = 0; i < edades.Count; i++)
                {
                    if (edades[i] != 0)
                    {
                        numero_edades = i + 1; // Incrementamos el número de edades

                        if (edades[i] > mayor)
                        {
                            mayor = edades[i]; // Actualizamos el mayor
                        }

                        if (edades[i] < menor)
                        {
                            menor = edades[i]; // Actualizamos el menor
                        }
                    }
                }

                // Mostrar el mayor, menor y el número de edades
                //Console.WriteLine("El número mayor es " + mayor);
                //Console.WriteLine("El número menor es " + menor);
                //Console.WriteLine("El número de edades es " + numero_edades);

                // Calcular el intervalo usando la fórmula de Sturges
                double intervalo = 1 + 3.3 * Math.Log10(numero_edades);
                int intervalo_entero = (int)(intervalo); // Redondear a entero
                                                         //Console.WriteLine("El intervalo es " + intervalo);
                                                         //Console.WriteLine("El intervalo entero es " + intervalo_entero);

                // Redondear el intervalo al valor entero más cercano
                int redondear3 = (int)Math.Round(intervalo); // Redondear
                                                             //Console.WriteLine("El intervalo redondeado es " + redondear3);

                // Calcular la amplitud
                double amplitud = (mayor - menor) / intervalo_entero;
                //Console.WriteLine("La amplitud es " + amplitud);

                int[] rangos = new int[redondear3];// tamano7
                int contador4 = 0;
                for (int z = 0; z < redondear3; z++)
                {//de 0 a 6 son 7 

                    rangos[z] = (int)Math.Round(menor);

                    menor = menor + amplitud;

                    contador4++;

                    if (contador4 == redondear3)
                    {

                        rangos[z] = rangos[z] + 1;

                    }
                }

                /*
                for (int h = 0; h < rangos.Length; h++)
                {

                    Console.WriteLine("Los rangos " + h + " = " + rangos[h]);
                }*/

                int[] frecuencia_edades = new int[redondear3]; //7
                int contador1 = 0;
                int contador2 = 0;
                int d = 0;

                for (int z = 0; z < (rangos.Length); z++)
                {

                    if (edades[z] != 0)
                    {
                        //            (7-1 = 6)
                        if (contador1 < (rangos.Length - 1))
                        { // hace las siguientes instrucciones 6 veces

                            //Console.WriteLine("El rango z " + z + " = " + rangos[z]);
                            //Console.WriteLine("El rango z+1 " + (z + 1) + " = " + rangos[z + 1]);
                            contador1++;

                            for (int g = 0; g < numero_edades; g++)
                            {

                                // if ((rangos[z] <= edades[g]) && (edades[g] < rangos[z + 1])) {
                                if (rangos[z] <= edades[g])
                                {

                                    if (edades[g] < rangos[z + 1])
                                    {

                                        contador2++;
                                        //Console.WriteLine("El valor del contador2 es "+contador2);
                                        //Console.WriteLine("Hola mundo");
                                    }
                                }
                            }

                            //Console.WriteLine("El valor del contador2 antes de resetearlo es "+contador2);
                            frecuencia_edades[d] = contador2;
                            //Console.WriteLine("El valor del contador2 antes de ser reseteado es " + contador2);
                            //Console.WriteLine("El valor de la frecuencia_edades " + d + " = " + frecuencia_edades[d]);
                            d++;

                            contador2 = 0; // resetea el contador

                        }
                    }
                }

                String[] rangos_strings = new String[rangos.Length];

                for (int t = 0; t < rangos.Length; t++)
                {

                    rangos_strings[t] = rangos[t].ToString();
                    //Console.WriteLine("El valor de rangos_strings " + t + " = " + rangos_strings[t]);

                }

                String[] rangos_finales = new String[rangos.Length];
                int contador3 = 0;

                for (int c = 0; c < rangos_finales.Length; c++)
                {

                    if (contador3 < (rangos_finales.Length - 1))
                    {

                        rangos_finales[c] = rangos_strings[c] + "-" + rangos_strings[c + 1];
                        //Console.WriteLine("Los rangos finales " + rangos_finales[c]);
                        contador3++;
                    }
                }

                //Console.WriteLine("Frecuencia de edades:");
                /*foreach (var frecuencia in frecuencia_edades)
                {
                    Console.WriteLine(frecuencia);  // Mostrar cada valor de frecuencia
                }*/

                /*Console.WriteLine("Rangos finales:");
                foreach (var rango in rangos_finales)
                {
                    Console.WriteLine($"El rango es: {rango}");  // Mostrar cada rango
                }*/


                //********************************* Inicializa La Tabla de Clientes ********************************
                /*id_cliente, nombre, edad, sexo, nit*/

                List<Clients> listClients = db.getAllClients(); //obtiene la lista de todos los clientes de la base de datos

                DataTable dt = new DataTable();
                dt.Columns.Add("Id", typeof(int));
                dt.Columns.Add("Nombre", typeof(string));
                dt.Columns.Add("Edad", typeof(int));
                dt.Columns.Add("Sexo", typeof(string));
                dt.Columns.Add("Nit", typeof(int));

                foreach (Clients c in listClients)
                {
                    dt.Rows.Add(c.Id_client, c.Name, c.Age, c.Gender, c.Nit);
                }



                // Asignar al DataGrid
                Tabla.ItemsSource = dt.DefaultView; //se obtiene del archivo DashboardClientes.xaml



                //********************************* Inicializa Grafica de Barras ********************************


                SeriesCollectionBars = new SeriesCollection
                {
                    new ColumnSeries // Usar ColumnSeries para gráfico de barras vertical
                    {
                        Title = "Clientes",
                        Values = new ChartValues<int> (frecuencia_edades) //EJE Y
                    }
                };



                // Definir las etiquetas del eje X
                Labels = rangos_finales;

                // Definir un formateador para las etiquetas del eje Y (ejemplo: mostrar como números enteros)
                Formatter = value => value.ToString("N0");

                GraficaBarras.AxisX[0].Title = "Rangos de edades";
                GraficaBarras.AxisY[0].Title = "Frecuencia de edades";

                GraficaBarras.Visibility = Visibility.Visible;


                // *************************** Inicializa Grafica de Pie ****************************************

                List<string> genero = db.getClientsGender();



                double contadorF = 0.0;
                double contadorM = 0.0;

                for (int j = 0; j < genero.Count; j++)
                {

                    if (genero[j] == "F")
                    {
                        contadorF++;
                    }
                    else
                    {
                        contadorM++;
                    }

                }

                //Console.WriteLine("La cantidad de mujeres es " + contadorF);
                //Console.WriteLine("La cantidad de hombres es " + contadorM);

                double totalPersonas = contadorM + contadorF;

                double porcentajeF = (100 * contadorF) / totalPersonas;
                //Console.WriteLine("El porcentaje femenino es "+porcentajeF);

                double porcentajeM = (100 * contadorM) / totalPersonas;
                //Console.WriteLine("El porcentaje masculino es "+porcentajeM);



                // Configurar el gráfico de pastel con dos segmentos: masculino y femenino
                SeriesCollectionPie = new SeriesCollection
                {
                    new PieSeries
                    {
                        Title = "Masculino",
                        Values = new ChartValues<int> { (int)contadorM },  // Solo un valor: el total de hombres
                        DataLabels = true,  // Mostrar etiquetas en cada segmento
                        Fill = new SolidColorBrush(Colors.Blue),  // Color Azul para masculino
                        //LabelPoint = point => $"{totalMasculino} ({(totalMasculino / (double)(totalMasculino + totalFemenino)) * 100:0.0}%)"
                        LabelPoint = point => $"{(int)contadorM } ({porcentajeM:0.0}%)"
                    },
                    new PieSeries
                    {
                        Title = "Femenino",
                        Values = new ChartValues<int> { (int)contadorF },  // Solo un valor: el total de mujeres
                        DataLabels = true,  // Mostrar etiquetas en cada segmento
                        Fill = new SolidColorBrush(Colors.Red),  // Color Rojo para femenino
                        //LabelPoint = point => $"{totalFemenino} ({(totalFemenino / (double)(totalMasculino + totalFemenino)) * 100:0.0}%)"
                        LabelPoint = point => $"{(int)contadorF} ({porcentajeF:0.0}%)"
                    }
                };

                // Establecer el contexto de datos de la ventana a esta instancia de MainWindow
                DataContext = this;

            }
        }

        public void createDashboardProducts()
        {

            //********************************* Inicializa La Tabla de Productos ********************************
            /*id_cliente, nombre, edad, sexo, nit*/

            List<Products> listProducts = db.getAllProducts(); //obtiene la lista de todos los productos de la base de datos

            if (listProducts != null)
            {


                DataTable dt = new DataTable();
                dt.Columns.Add("Id", typeof(int));
                dt.Columns.Add("Nombre del Producto", typeof(string));
                dt.Columns.Add("Precio", typeof(double));
                dt.Columns.Add("Cantidad", typeof(string));


                foreach (Products c in listProducts)
                {
                    dt.Rows.Add(c.Id_product, c.Name, c.Price, c.Cantidad);
                }



                // Asignar al DataGrid
                Tabla.ItemsSource = dt.DefaultView; //se obtiene del archivo Dashboard.xaml


                //********************************* Inicializa Grafica de Barras ********************************

                List<Products> productos = db.getAllProducts();



                var productosOrdenados = productos.OrderBy(p => p.Price).ToList(); //ordena los productos de menor a mayor

                /*
                Console.WriteLine("\nLista con los productos ordenados de menor a mayor:");
                foreach (Products producto in productosOrdenados) {
                    Console.WriteLine(producto.Price);
                }*/


                double mayor, menor;

                // Inicializamos mayor y menor con el primer valor del List
                mayor = menor = productos[0].Price;

                int numero_precios = 0;

                // Recorrer el List de productos
                for (int i = 0; i < productos.Count; i++)
                {
                    if (productos[i].Price != 0)
                    {
                        numero_precios = i + 1; // Incrementamos el número de veces de precios

                        if (productos[i].Price > mayor)
                        {
                            mayor = productos[i].Price; // Actualizamos el mayor
                        }

                        if (productos[i].Price < menor)
                        {
                            menor = productos[i].Price; // Actualizamos el menor
                        }
                    }
                }


                Console.WriteLine("El número mayor es " + mayor);
                Console.WriteLine("El número menor es " + menor);
                //Console.WriteLine("El número de edades es " + numero_edades);

                // Calcular el intervalo usando la fórmula de Sturges
                double intervalo = 1 + 3.3 * Math.Log10(numero_precios);
                int intervalo_entero = (int)(intervalo); // Redondear a entero
                                                         //Console.WriteLine("El intervalo es " + intervalo);

                Console.WriteLine("El intervalo entero es " + intervalo_entero);

                // Redondear el intervalo al valor entero más cercano
                int redondear3 = (int)Math.Round(intervalo); // Redondear
                                                             //Console.WriteLine("El intervalo redondeado es " + redondear3);

                // Calcular la amplitud
                double amplitud = (mayor - menor) / intervalo_entero;
                amplitud = Math.Round(amplitud, 2);

                Console.WriteLine("La amplitud es " + amplitud);

                double[] rangos = new double[redondear3];// tamano7
                int contador4 = 0;
                for (int z = 0; z < redondear3; z++)
                {//de 0 a 6 son 7 

                    rangos[z] = Math.Round(menor, 2);

                    menor = menor + amplitud;

                    contador4++;

                    if (contador4 == redondear3)
                    {

                        rangos[z] = rangos[z] + 1;

                    }
                }


                for (int h = 0; h < rangos.Length; h++)
                {

                    Console.WriteLine("Los rangos " + h + " = " + rangos[h]);
                }

                int[] frecuencia_precios = new int[redondear3]; //7
                int contador1 = 0;
                int contador2 = 0;
                int d = 0;

                for (int z = 0; z < (rangos.Length); z++)
                {

                    if (productos[z].Price != 0)
                    {
                        //            (7-1 = 6)
                        if (contador1 < (rangos.Length - 1))
                        { // hace las siguientes instrucciones 6 veces

                            //Console.WriteLine("El rango z " + z + " = " + rangos[z]);
                            //Console.WriteLine("El rango z+1 " + (z + 1) + " = " + rangos[z + 1]);
                            contador1++;

                            for (int g = 0; g < numero_precios; g++)
                            {

                                // if ((rangos[z] <= edades[g]) && (edades[g] < rangos[z + 1])) {
                                if (rangos[z] <= productos[g].Price)
                                {

                                    if (productos[g].Price < rangos[z + 1])
                                    {

                                        contador2++;
                                        //Console.WriteLine("El valor del contador2 es "+contador2);
                                        //Console.WriteLine("Hola mundo");
                                    }
                                }
                            }

                            //Console.WriteLine("El valor del contador2 antes de resetearlo es "+contador2);
                            frecuencia_precios[d] = contador2;
                            //Console.WriteLine("El valor del contador2 antes de ser reseteado es " + contador2);
                            //Console.WriteLine("El valor de la frecuencia_edades " + d + " = " + frecuencia_edades[d]);
                            d++;

                            contador2 = 0; // resetea el contador

                        }
                    }
                }

                String[] rangos_strings = new String[rangos.Length];

                for (int t = 0; t < rangos.Length; t++)
                {

                    rangos_strings[t] = rangos[t].ToString();
                    //Console.WriteLine("El valor de rangos_strings " + t + " = " + rangos_strings[t]);

                }

                String[] rangos_finales = new String[rangos.Length];
                int contador3 = 0;

                for (int c = 0; c < rangos_finales.Length; c++)
                {

                    if (contador3 < (rangos_finales.Length - 1))
                    {

                        rangos_finales[c] = rangos_strings[c] + "-" + rangos_strings[c + 1];
                        Console.WriteLine("Los rangos finales " + rangos_finales[c]);
                        contador3++;
                    }
                }

                SeriesCollectionBars = new SeriesCollection
                {
                    new ColumnSeries // Usar ColumnSeries para gráfico de barras vertical
                    {
                        Title = "Productos",
                        Values = new ChartValues<int> (frecuencia_precios)
                    }
                };



                // Definir las etiquetas del eje X
                Labels = rangos_finales;

                // Definir un formateador para las etiquetas del eje Y (ejemplo: mostrar como números enteros)
                Formatter = value => value.ToString("N0");

                GraficaBarras.AxisX[0].Title = "Rangos de Precios";
                GraficaBarras.AxisY[0].Title = "Frecuencia de Precios";

                GraficaBarras.Visibility = Visibility.Visible;

                // *************************** Inicializa Grafica de Pie ****************************************

                var productosOrdenados2 = productos.OrderByDescending(p => p.Price).ToList(); //ordena de mayor a menor.

                /*
                Console.WriteLine("\nLista con los productos ordenados mayor a menor:");
                foreach (Products producto in productosOrdenados2)
                {
                    Console.WriteLine(producto.Price);
                }*/



                SeriesCollectionPie = new SeriesCollection();

                for (int i = 0; i < 5; i++)
                {
                    SeriesCollectionPie.Add(new PieSeries
                    {
                        Title = productosOrdenados2[i].Name,
                        Values = new ChartValues<int> { productosOrdenados2[i].Cantidad },
                        DataLabels = true,

                        LabelPoint = point => $"{point.Y}"
                    });
                }

                DataContext = this;

            }
        }

        public void createDashboardSales()
        {

            //********************************* Inicializa La Tabla de Ventas ********************************
            

            List<Sales> listSales = db.getAllSales(); //obtiene la lista de todos las ventas de la base de datos

            if (listSales != null)
            {


                DataTable dt = new DataTable();
                dt.Columns.Add("Id_Venta", typeof(int));
                dt.Columns.Add("Id_Cliente", typeof(int));
                dt.Columns.Add("Id_Producto", typeof(int));
                dt.Columns.Add("Codigo_Venta", typeof(int));
                dt.Columns.Add("Nit", typeof(int));
                dt.Columns.Add("Nombre del Producto", typeof(string));
                dt.Columns.Add("Cantidad Comprada", typeof(int));
                


                foreach (Sales s in listSales)
                {
                    dt.Rows.Add(s.Id_sale, s.Id_client, s.Id_product, s.Sale_code ,s.Nit, s.Name_product, s.Saled_amount);
                }



                // Asignar al DataGrid
                Tabla.ItemsSource = dt.DefaultView; //se obtiene del archivo Dashboard.xaml


               

            }

            
            Dictionary<string, int> productos2 = new Dictionary<string, int>();

            if (listSales != null) {


                foreach (Sales s in listSales)
                {


                        if (productos2.ContainsKey(s.Name_product)) //si el valor existe en el diccionario
                        {
                            productos2[s.Name_product] += s.Saled_amount;   // suma el valor
                        }
                        else  
                        {
                            productos2[s.Name_product] = s.Saled_amount;    //si el valor NO existe en el diccionario lo crea
                        }

                        
                }
            }
            /*
            foreach (var item in productos2)
            {
                Console.WriteLine($"Clave: {item.Key}, Valor: {item.Value}");
            }*/

            var productosOrdenados = productos2
            .OrderByDescending(x => x.Value)
            .ToList(); //al final productosOrdenados es una Lista ordenada de pares clave–valor


            

            //******************* Mostrando segunda tabla de Productos Mas Vendidos*********************************
            List<Products> listProducts = db.getAllProducts();

            int cantidad_vendida_mayor = 0;
            int productos_vendidos = 0;

            if (productosOrdenados != null)
            {


                DataTable dt = new DataTable();
                dt.Columns.Add("Producto", typeof(string));
                dt.Columns.Add("Cantidad", typeof(int));
                dt.Columns.Add("Precio", typeof(double));
                dt.Columns.Add("Total", typeof(decimal));
                dt.Columns.Add("Total Acumulado", typeof(decimal));

                decimal total_acumulado = 0; //se usa decimal para poder representar con 2 decimales porque con double no es posible
                decimal total = 0;

                for (int i = 0; i < productosOrdenados.Count; i++) //obteniendo todos los productos ordenados ascendentemente
                {
                    var item = productosOrdenados[i];

                    if ( i == 0) { // obtiene la cantidad del primer producto mas vendido
                        cantidad_vendida_mayor = item.Value;
                    }

                    foreach (Products producto in listProducts)
                    {
                        if (item.Key == producto.Name)
                        {
                            productos_vendidos += item.Value;
                            total_acumulado += Math.Round((decimal)producto.Price * item.Value,2);
                            total = Math.Round((decimal)producto.Price * item.Value, 2);
                            dt.Rows.Add(item.Key, item.Value, producto.Price, total, total_acumulado);
                        }
                    }
                    
                    //Console.WriteLine($"Producto: {item.Key}, Cantidad: {item.Value}");
                    
                }



                // Asignar al DataGrid
                Tabla2.ItemsSource = dt.DefaultView; //se obtiene del archivo Dashboard.xaml


                

            }

            // Cantidad vendida mayor = 16, total ventas = 134462.34, total venta mayor=12271.84

            Console.WriteLine("Llamando a la funcion getTotalSales");
            decimal total_ventas = db.getTotalSales();

            DataTable dt2 = new DataTable();
            dt2.Columns.Add("Producto vendidos", typeof(int));
            dt2.Columns.Add("Total ventas", typeof(string));

            dt2.Rows.Add (productos_vendidos, total_ventas);
            Tabla3.ItemsSource = dt2.DefaultView; //se obtiene del archivo Dashboard.xaml

            PanelTabla2.Visibility = Visibility.Visible; //muestra el stack panel que contiene la tabla 2 y 3

            this.productosOrdenados = productosOrdenados;
            this.productos_vendidos = productos_vendidos;
            this.total_ventas = total_ventas;

        }

    }
}
