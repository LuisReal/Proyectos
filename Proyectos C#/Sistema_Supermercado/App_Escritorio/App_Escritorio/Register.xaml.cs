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
    /// Interaction logic for Window1.xaml
    /// </summary>
    public partial class Register : Window
    {
        private DataBase db;
        private Window login;
        public Register(Window login)
        {
            InitializeComponent();
            db = new DataBase();
            this.login = login;
            
        }

        /*el siguiente metodo asegura que al cerrar esta ventana se cierre toda la aplicacion*/
        protected override void OnClosing(System.ComponentModel.CancelEventArgs e)
        {
            base.OnClosing(e);
            Application.Current.Shutdown(); // Fuerza el cierre total
        }

        private void Button_Registrar(object sender, RoutedEventArgs e)
        {
            string name = txt_name.Text;
            string username = txt_username.Text;
            string password = txt_password.Password;
            string confirm_password = txt_confirm_password.Password;

            //MessageBox.Show($"{username}:{password}");

            if (password == confirm_password)
            {
                //db.OpenConnection();
                db.createUser(name, username, password);
            }
            else {
                MessageBox.Show("Las contrasenas no coinciden");
            }

            txt_name.Text = "";
            txt_username.Text = "";
            txt_password.Password = "";
            txt_confirm_password.Password = "";

        }

        private void Button_Login(object sender, RoutedEventArgs e)
        {
            this.login.Show();
            this.Hide();
        }
    }
}
