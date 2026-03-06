using System;
using System.Collections.Generic;
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
    /// <summary>
    /// Interaction logic for VentanaConsultaProducto.xaml
    /// </summary>
    public partial class VentanaConsultaProducto : Window
    {
        private string nombre_boton;
        public VentanaConsultaProducto(string nombre_boton)
        {
            this.nombre_boton = nombre_boton;
            InitializeComponent();

        }

        private void button_productInquiry(object sender, RoutedEventArgs e)
        {
            string name = txtbox_consultarProducto.Text;

            if (name != "") 
            {

                DataBase db = new DataBase();

                List<Products> products = db.getAllProducts();

                bool existe = false;

                
                double price = 0;
                string gender = "";
                int quantity = 0;
                string ruta = "";

                foreach (Products p in products)
                {

                    if (p.Name == name)
                    {
                        name = p.Name;
                        price = p.Price;
                        quantity = p.Cantidad;
                        ruta = p.Ruta_Imagen;
                        existe = true;

                    }

                }

                if (existe == true)
                {
                    MessageBox.Show("EL PRODUCTO SI EXISTE");

                    if (nombre_boton == "Consulta Productos")
                    {
                        
                        VentanaMostrarProducto ventana = new VentanaMostrarProducto(name, price, quantity, ruta);
                        ventana.Show(); // muestra la ventana con la informacion del producto
                    }
                    else if (nombre_boton == "Modificar Productos")
                    {
                        Console.WriteLine("Haz presionado el boton Modificar Productos");

                        VentanaNuevo_Producto ventana = new VentanaNuevo_Producto(nombre_boton, name);
                        ventana.Show();
                    }


                }
                else
                {
                    MessageBox.Show("EL PRODUCTO NO EXISTE");
                }


            }
            else
            {

                MessageBox.Show("No ingreso el nombre del producto");
            }


        }
    }
}
