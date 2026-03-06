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

namespace App_Escritorio
{
    /// <summary>
    /// Interaction logic for VentanaMostrarVenta.xaml
    /// </summary>
    public partial class VentanaMostrarVenta : Window
    {
        private DataBase db;
        private int codigo_venta;
        public VentanaMostrarVenta(int codigo_venta)
        {
            this.codigo_venta = codigo_venta;

            InitializeComponent();

            db = new DataBase();

            List<Sales> listSales = db.getAllSales(); //obtiene la lista de todos las ventas de la base de datos
            List<Products> listProducts = db.getAllProducts();
            int nit = 0;

            double total_venta = 0;

            DataTable dt = new DataTable();
            
            dt.Columns.Add("Producto", typeof(string));
            dt.Columns.Add("Cantidad", typeof(int));
            dt.Columns.Add("Precio", typeof(double));

            foreach (Sales s in listSales)
            {
                if (codigo_venta == s.Sale_code) {
                    

                    foreach (Products p in listProducts) {

                        if (s.Id_product == p.Id_product) {

                            dt.Rows.Add(s.Name_product, s.Saled_amount, p.Price);

                            total_venta += s.Saled_amount * p.Price;
                        }
                    }

                    nit = s.Nit;
                }
                
            }

            label_mostrar_nit.Content = nit;
            label_mostrar_total.Content = total_venta*100/100;

            // Asignar al DataGrid
            Tabla.ItemsSource = dt.DefaultView; 
        }

        private void deleteSale(object sender, RoutedEventArgs e)
        {
            MessageBoxResult result = MessageBox.Show(
                "¿Deseas eliminar la venta?",          // Mensaje
                "Confirmación",                // Título
                MessageBoxButton.YesNo,        // Botones: Sí / No
                MessageBoxImage.Question       // Icono (opcional)
            );

            if (result == MessageBoxResult.Yes)
            {
                db.deleteSale(codigo_venta);
            }
            else if (result == MessageBoxResult.No)
            {
                MessageBox.Show("Presionaste No");
            }

        }
    }
}
