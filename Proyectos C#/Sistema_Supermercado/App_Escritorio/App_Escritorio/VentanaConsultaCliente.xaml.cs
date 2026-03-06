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
using System.Linq;

namespace App_Escritorio
{
    /// <summary>
    /// Interaction logic for VentanaConsultaCliente.xaml
    /// </summary>
    public partial class VentanaConsultaCliente : Window
    {
        private string nombre_boton;
        public VentanaConsultaCliente(string nombre_boton)
        {
            this.nombre_boton = nombre_boton;
            InitializeComponent();
        }

        private void button_customerInquiry(object sender, RoutedEventArgs e)
        {
            string nit = txtbox_consultarNIT.Text;

            if (nit.All(char.IsDigit) && nit != "") //verifica si el texto nit contiene solo numeros
            {

                DataBase db = new DataBase();

                List<Clients> clientes = db.getAllClients();

                bool existe = false;

                string name = "";
                int age = 0;
                string gender = "";
                
                string ruta = "";

                foreach (Clients c in clientes)
                {

                    if (c.Nit == int.Parse(nit))
                    {
                        name = c.Name;
                        age = c.Age;
                        gender = c.Gender;
                        ruta = c.Ruta_Imagen;
                        existe = true;

                    }

                }

                if (existe == true)
                {
                    MessageBox.Show("EL NIT SI EXISTE");

                    if (nombre_boton == "Consulta Clientes")
                    {
                        VentanaMostrarCliente ventana = new VentanaMostrarCliente(name, age, gender, int.Parse(nit), ruta);
                        ventana.Show(); // muestra la ventana con la informacion del cliente
                    }
                    else if (nombre_boton == "Modificar Clientes")
                    {
                        Console.WriteLine("Haz presionado el boton Modificar Clientes");

                        VentanaNuevo_Cliente ventana = new VentanaNuevo_Cliente(nombre_boton, nit);
                        ventana.Show();
                    }
                    
                    
                }
                else
                {
                    MessageBox.Show("EL NIT NO EXISTE");
                }


            }
            else {

                MessageBox.Show("El NIT solo puede tener numeros");
            }


        }
    }
}
