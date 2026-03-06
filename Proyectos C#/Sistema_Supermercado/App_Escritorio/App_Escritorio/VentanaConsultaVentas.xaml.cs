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
using System.Windows.Navigation;
using System.Windows.Shapes;

namespace App_Escritorio
{
    /// <summary>
    /// Interaction logic for VentanaConsultaVentas.xaml
    /// </summary>
    public partial class VentanaConsultaVentas : Window
    {
        public VentanaConsultaVentas()
        {
            InitializeComponent();
        }

        private void button_saleInquiry(object sender, RoutedEventArgs e)
        {
            string codigo_venta = txtbox_consultarVenta.Text;

            if (codigo_venta.All(char.IsDigit) && codigo_venta != "") //verifica si el texto codigo_venta contiene solo numeros
            {

                DataBase db = new DataBase();

                List<Sales> sales = db.getAllSales();

                bool existe = false;

               
                
               

                foreach (Sales c in sales)
                {

                    if (c.Sale_code == int.Parse(codigo_venta))
                    {
                        
                        
                        existe = true;

                    }

                }

                if (existe == true)
                {
                    MessageBox.Show("LA VENTA SI EXISTE");

                    
                        VentanaMostrarVenta ventana = new VentanaMostrarVenta(int.Parse(codigo_venta));
                        ventana.Show(); // muestra la ventana con la informacion de la venta
                    
                    


                }
                else
                {
                    MessageBox.Show("LA VENTA NO EXISTE");
                }


            }
            else
            {

                MessageBox.Show("El codigo venta no puede estar vacio y solo puede tener numeros");
            }
        }
    }
}
