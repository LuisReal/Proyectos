using Microsoft.Win32;
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

namespace App_Escritorio
{
    /// <summary>
    /// Interaction logic for VentanaMostrarCliente.xaml
    /// </summary>
    public partial class VentanaMostrarCliente : Window
    {
        private int nit;
        public VentanaMostrarCliente(string name, int age, string gender, int nit, string ruta)
        {
            InitializeComponent();

            this.nit = nit;

            label_name.Content = name;
            label_age.Content = age;
            label_gender.Content = gender;
            label_nit.Content = nit;

            
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

        private void button_deleteClient(object sender, RoutedEventArgs e)
        {
            DataBase db = new DataBase();

            MessageBoxResult result = MessageBox.Show(
                "¿Deseas eliminar el cliente?",          // Mensaje
                "Confirmación",                // Título
                MessageBoxButton.YesNo,        // Botones: Sí / No
                MessageBoxImage.Question       // Icono (opcional)
            );

            if (result == MessageBoxResult.Yes)
            {
                db.deleteClient(nit);
            }
            else if (result == MessageBoxResult.No)
            {
                MessageBox.Show("Presionaste No");
            }

            
        }
    }
}
