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
using static iTextSharp.text.pdf.AcroFields;

namespace App_Escritorio
{
    /// <summary>
    /// Interaction logic for VentanaNueva_Venta.xaml
    /// </summary>
    public partial class VentanaNueva_Venta : Window
    {
        private DataTable dt;
        private decimal total_venta;
        private string nombre_boton;
        private DataBase db;
        private List<Sales> listSales;
        Sales lastSale;
        private int id_venta;
        public VentanaNueva_Venta(string nombre_boton, string name)
        {
            this.nombre_boton = nombre_boton;
            db = new DataBase();
            dt = new DataTable();
            dt.Columns.Add("Producto", typeof(string));
            dt.Columns.Add("Precio", typeof(double));
            dt.Columns.Add("Cantidad", typeof(string));

            listSales = db.getAllSales();

            lastSale = null;

            if (listSales != null) //si la lista no esta vacia
            {
                lastSale = listSales[listSales.Count - 1];//obtiene el ultimo elemento de la listaj

                id_venta = lastSale.Sale_code + 1;

            }
            else {
                return; //si la lista esta vacia detiene la apertura de la ventana Nueva_venta
            }

            InitializeComponent();

            List<Products> products = db.getAllProducts();

            if (products != null)
            {
                foreach (Products p in products)
                {
                    if (p.Name != "")
                    {
                        Console.WriteLine("El nombre del producto es: " + p.Name);
                        txt_select_product.Items.Add(p.Name);
                    }


                }
            }
            else
            {
                Console.WriteLine("La lista de productos esta vacia");
            }
        }

        private void button_add(object sender, RoutedEventArgs e)
        {
            string nit = txtbox_nit.Text;
            string product = txt_select_product.Text;
            string amount = txtbox_amount.Text;

            

            if (string.IsNullOrWhiteSpace(nit) ||
                string.IsNullOrWhiteSpace(product) ||
                string.IsNullOrWhiteSpace(amount) )

                
            {
                MessageBox.Show("Por favor, complete todos los campos antes de continuar.");
            }
            else
            {
                if (nombre_boton == "Crear Venta")
                {

                    
                    
                    bool creado = false;

                    if (listSales.Any()) //si la lista no esta vacia
                    {
                        

                        creado = db.createSale(id_venta, int.Parse(nit), product, int.Parse(amount));

                         
                    }

                    

                    if (creado == true)
                    {
                        
                        List<Products> listProducts = db.getAllProducts();
                        
                        foreach (Products p in listProducts) {
                            
                            if (product == p.Name) {
                                
                                dt.Rows.Add(product, p.Price, amount);
                                
                                total_venta +=  Math.Round((decimal)(p.Price * double.Parse(amount)), 2);
                            }
                            
                        }

                        
                        // Asignar al DataGrid
                        TablaAgregados.ItemsSource = dt.DefaultView; //se obtiene del archivo DashboardClientes.xaml


                        label_mostrar_total.Text = total_venta.ToString();
                    }
                    else
                    {
                        
                        MessageBox.Show("ERROR: No se pudo crear la venta");
                    }


                }
                
            }
        }

        private void button_print(object sender, RoutedEventArgs e)
        {

        }
    }
}
