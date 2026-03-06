using Microsoft.Win32;
using System;
using System.Collections.Generic;
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
using static System.Net.Mime.MediaTypeNames;
using System.IO;


namespace App_Escritorio
{
    /// <summary>
    /// Interaction logic for VentanaNuevo_Cliente.xaml
    /// </summary>
    public partial class VentanaNuevo_Cliente : Window
    {
        string fileName = "";
        string rutaArchivo = "";
        private string nombre_boton;
        private DataBase db;
        
        public VentanaNuevo_Cliente(string nombre_boton, string nit)
        {
            this.nombre_boton = nombre_boton;

            db = new DataBase();

            InitializeComponent();

            if (nit != "")
            {   //si se presiona el boton Modificar Clientes, lo siguiente mostrara automaticamente
                //la informacion del cliente(antes de ser modificado).
                List<Clients> clients = db.getAllClients();

                foreach (Clients client in clients)
                {

                    if (client.Nit == int.Parse(nit))
                    {

                        txtbox_name.Text = client.Name;
                        txtbox_age.Text = client.Age.ToString();
                        txt_gender.Text = client.Gender;
                        txtbox_nit.Text = client.Nit.ToString();

                    }
                }
            }
        }

        private void button_save(object sender, RoutedEventArgs e)
        {
            



            string name = txtbox_name.Text;
            string age = txtbox_age.Text;
            string gender = txt_gender.Text;
            string nit = txtbox_nit.Text;
            string image= rutaArchivo;

            if (string.IsNullOrWhiteSpace(name) ||
                string.IsNullOrWhiteSpace(age) ||
                string.IsNullOrWhiteSpace(gender) ||
                string.IsNullOrWhiteSpace(nit) ||
                string.IsNullOrWhiteSpace(image))
            {
                MessageBox.Show("Por favor, complete todos los campos antes de continuar.");
            }
            else
            {
                if (nombre_boton == "Creacion Clientes")
                {

                    //createClient(string name, string age, string gender, string nit, string image
                    bool creado = db.createClient(name, age, gender, nit, image);

                    if (creado == true)
                    {
                        MessageBox.Show("Cliente creado exitosamente");
                    }
                    else {
                        MessageBox.Show($"El cliente con nit: {nit}, ya existe");
                        MessageBox.Show("ERROR: No se pudo crear el cliente");
                    }
                    
                    
                }
                else if (nombre_boton == "Modificar Clientes")
                {

                    if (int.Parse(age) >= 18 && int.Parse(age) <= 100)
                    {
                   

                        //string name, string age, string gender, string nit, string image
                        db.modifyClient(name, age, gender, nit, image);

                    }
                    else {
                        MessageBox.Show("La edad no es valida");
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

                fileName = openFileDialog.SafeFileName; //obtiene el nombre del archivo seleccionado
                rutaArchivo = openFileDialog.FileName;
                MessageBox.Show("Archivo seleccionado:\n" + rutaArchivo);

                
                
            }
            else {
                MessageBox.Show("No ha seleccionado un archivo");
            }

        }
    }
}
