using Microsoft.Win32;
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
    
    public partial class VentanaNuevo_Producto : Window
    {
        string rutaArchivo = "";
        private string nombre_boton;
        private DataBase db;
        public VentanaNuevo_Producto(string nombre_boton, string name)
        {
            this.nombre_boton = nombre_boton;

            db = new DataBase();

            InitializeComponent();

            if (name != "")
            {   //si se presiona el boton Modificar Productos, lo siguiente mostrara automaticamente
                //la informacion del producto(antes de ser modificado).
                List<Products> products = db.getAllProducts();

                foreach (Products product in products)
                {

                    if (product.Name == name)
                    {

                        txtbox_name.Text = product.Name;
                        txtbox_price.Text = product.Price.ToString();
                        txtbox_quantity.Text = product.Cantidad.ToString();
                        

                    }
                }
            }
        }

        private void button_save(object sender, RoutedEventArgs e)
        {
            string name = txtbox_name.Text;
            string price = txtbox_price.Text;
            string quantity = txtbox_quantity.Text;
            
            string image = rutaArchivo;

            if (string.IsNullOrWhiteSpace(name) ||
                string.IsNullOrWhiteSpace(price) ||
                string.IsNullOrWhiteSpace(quantity) ||
                
                string.IsNullOrWhiteSpace(image))
            {
                MessageBox.Show("Por favor, complete todos los campos antes de continuar.");
            }
            else
            {
                if (nombre_boton == "Creacion Productos")
                {

                    //createProduct(string name, string price, string quantity, string image)
                    bool creado = db.createProduct(name, price, quantity, image);

                    if (creado == true)
                    {
                        MessageBox.Show("Producto creado exitosamente");
                    }
                    else
                    {
                        MessageBox.Show($"El producto con nombre: {name}, ya existe");
                        MessageBox.Show("ERROR: No se pudo crear el producto");
                    }


                }
                else if (nombre_boton == "Modificar Productos")
                {

                    if (int.Parse(quantity) >= 0 )
                    {


                        //string name, string price, string quantity, string image
                        db.modifyProduct(name, price, quantity, image);

                    }
                    else
                    {
                        MessageBox.Show("La cantidad no es valida");
                    }



                }
            }
        }

        private void Button_Select(object sender, RoutedEventArgs e)
        {
            OpenFileDialog openFileDialog = new OpenFileDialog();


            openFileDialog.Title = "Selecciona un archivo";



            if (openFileDialog.ShowDialog() == true)
            {

                string fileName = openFileDialog.SafeFileName; //obtiene el nombre del archivo seleccionado
                rutaArchivo = openFileDialog.FileName;
                MessageBox.Show("Archivo seleccionado:\n" + rutaArchivo);



            }
            else
            {
                MessageBox.Show("No ha seleccionado un archivo");
            }

        }
    }
}
