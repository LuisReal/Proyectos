using System.Text;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;
using System.Xml.Linq;
using System.Runtime.InteropServices;
namespace App_Escritorio
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
  
    public partial class MainWindow : Window
    {
        /*[DllImport("kernel32.dll", SetLastError = true)] //para poder ver la consola junto con la interfaz grafica
        private static extern bool AllocConsole();  //para poder ver la consola junto con la interfaz grafica
        */
        private Register ventana;
        
        private DataBase db;
        public MainWindow()
        {
            InitializeComponent();
            //AllocConsole();  //para poder ver la consola junto con la interfaz grafica
            db = new DataBase();
        }

        /*el siguiente metodo asegura que al cerrar esta ventana se cierre toda la aplicacion*/
        protected override void OnClosing(System.ComponentModel.CancelEventArgs e)
        {
            base.OnClosing(e);
            Application.Current.Shutdown(); // Fuerza el cierre total
        }

        private void Button_Login(object sender, RoutedEventArgs e)
        {
            
            string username = txt_username.Text;
            string password = txt_password.Password;
            

            //db.OpenConnection();
            bool validate = db.validateUser(username, password );

            if (validate == true ) {
                txt_username.Text = "";
                txt_password.Password = "";

                VentanaClientes ventana_administracion = new VentanaClientes(this);
                ventana_administracion.Show();
                this.Hide();
            }
            
        }


        private void Button_Register(object sender, RoutedEventArgs e)
        {
            
            

            ventana = new Register(this);
            ventana.Show();

            this.Hide();
        }
    }
}