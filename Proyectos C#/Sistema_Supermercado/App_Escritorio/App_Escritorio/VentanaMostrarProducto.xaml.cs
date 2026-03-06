using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Reflection;
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
using System.Xml.Linq;

namespace App_Escritorio
{
    /// <summary>
    /// Interaction logic for VentanaMostrarProducto.xaml
    /// </summary>
    public partial class VentanaMostrarProducto : Window
    {
        private string name;
        public VentanaMostrarProducto(string name, double price, int quantity, string ruta)
        {
            InitializeComponent();

            this.name = name;

            label_name.Content = name;
            label_price.Content = price.ToString();
            label_quantity.Content = quantity.ToString();
            


            // Asignar la imagen al control


            try
            {
                if (!File.Exists(ruta))
                {
                    MessageBox.Show("La imagen no existe o la ruta no es válida:\n" + ruta,
                                    "Error de imagen", MessageBoxButton.OK, MessageBoxImage.Warning);
                    return;
                }

                image_icon.Source = new BitmapImage(new Uri(ruta, UriKind.Absolute));
            }
            catch (UriFormatException)
            {
                MessageBox.Show("La ruta de la imagen tiene un formato incorrecto.",
                                "Error de formato", MessageBoxButton.OK, MessageBoxImage.Error);
            }
        }

        private void button_deleteProduct(object sender, RoutedEventArgs e)
        {
            DataBase db = new DataBase();

            MessageBoxResult result = MessageBox.Show(
                "¿Deseas eliminar el producto?",          // Mensaje
                "Confirmación",                // Título
                MessageBoxButton.YesNo,        // Botones: Sí / No
                MessageBoxImage.Question       // Icono (opcional)
            );

            if (result == MessageBoxResult.Yes)
            {
                db.deleteProduct(name);
            }
            else if (result == MessageBoxResult.No)
            {
                MessageBox.Show("Presionaste No");
            }
            
        }
    }
}
