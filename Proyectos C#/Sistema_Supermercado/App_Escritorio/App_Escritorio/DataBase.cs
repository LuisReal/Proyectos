using MySql.Data.MySqlClient;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Xml.Linq;
using static Mysqlx.Crud.Order.Types;
using static System.Runtime.InteropServices.JavaScript.JSType;

namespace App_Escritorio
{
    public class DataBase
    {
        public MySqlConnection connect;
        private string conection_content;
        public DataBase()
        {

            conection_content = "server = localhost; port = 3306;" +
                "database = supermercado; user = root; password= Mysql.123";

            connect = new MySqlConnection(conection_content);

            OpenConnection();
        }

        public MySqlConnection OpenConnection()
        {
            try
            {
                if (connect.State == System.Data.ConnectionState.Closed)
                {

                    connect.Open();
                    Console.WriteLine("Conexcion abierta a la base de datos");
                    //MessageBox.Show("Conexcion abierta a la base de datos");

                }
            }
            catch (Exception e)
            {
                Console.WriteLine("Error al conectar a la base de datos " + e);
                MessageBox.Show("Error al conectar a la base de datos " + e);
            }

            return connect;
        }

        public void createUser(string name, string username, string password)
        {
            try
            {
                

                string query = "INSERT INTO usuarios (nombre, usuario, contrasena) VALUES (@nombre, @usuario, @contrasena)";
                using (MySqlCommand cmd = new MySqlCommand(query, connect))
                {
                    cmd.Parameters.AddWithValue("@nombre", name);
                    cmd.Parameters.AddWithValue("@usuario", username);
                    cmd.Parameters.AddWithValue("@contrasena", password);

                    cmd.ExecuteNonQuery();
                }

                MessageBox.Show("Usuario registrado correctamente");
            }
            catch (Exception ex)
            {
                MessageBox.Show("Error al registrar usuario: " + ex.Message);
            }
           


        }

        public bool validateUser(string username, string password) {

            try
            {
                OpenConnection();

                string query = "SELECT COUNT(*) FROM usuarios WHERE usuario = @usuario AND contrasena = @contrasena";
                using (MySqlCommand cmd = new MySqlCommand(query, connect))
                {

                    cmd.Parameters.AddWithValue("@usuario", username);
                    cmd.Parameters.AddWithValue("@contrasena", password);

                    int count = Convert.ToInt32(cmd.ExecuteScalar());
                    connect.Close();

                    if (count > 0)
                    {
                        MessageBox.Show("Usuario validado correctamente");

                        return true;

                    }
                    else
                    {
                        MessageBox.Show("Usuario o contraseña incorrectos");
                        return false;
                    }


                }

            }
            catch (Exception ex)
            {
                MessageBox.Show("Error al validar el usuario: " + ex.Message);
                return false;
            }
        }

        public bool createClient(string name, string age, string gender, string nit, string image)
        {   
            try
            {
                

                string query1 = "SELECT COUNT(*) FROM clientes WHERE nit = @nit";
                using (MySqlCommand cmd = new MySqlCommand(query1, connect))
                {

                    cmd.Parameters.AddWithValue("@nit", int.Parse(nit));

                    int count = Convert.ToInt32(cmd.ExecuteScalar());
                    

                    if (count > 0)
                    {
                        

                        return false;
                    }
                    else
                    {

                        string query2 = "INSERT INTO clientes (nombre, edad, sexo, nit, imagen) VALUES (@nombre, @edad, @sexo, @nit, @imagen)";
                        using (MySqlCommand cmd2 = new MySqlCommand(query2, connect))
                        {
                            cmd2.Parameters.AddWithValue("@nombre", name);
                            cmd2.Parameters.AddWithValue("@edad", int.Parse(age));
                            cmd2.Parameters.AddWithValue("@sexo", gender);
                            cmd2.Parameters.AddWithValue("@nit", int.Parse(nit));
                            cmd2.Parameters.AddWithValue("@imagen", image);

                            cmd2.ExecuteNonQuery();
                            //connect.Close();

                            return true;
                        }
                    }


                }
                
            }
            catch (Exception ex)
            {
                MessageBox.Show("Error al registrar al Cliente: " + ex.Message);
                return false;
            }



        }

        public List<Clients> getAllClients()
        {
            try
            {
                

                List<Clients> listClients = new List<Clients>();

                string query1 = "SELECT * FROM clientes";
                using (MySqlCommand cmd = new MySqlCommand(query1, connect))
                {   

                    using (MySqlDataReader reader = cmd.ExecuteReader())
                    {
                        while (reader.Read()) // si no hay registros en la tabla clientes este while nunca se ejecuta
                        {
                            //Console.WriteLine($"ID: {reader["id_cliente"]}, Nombre: {reader["nombre"]}, Edad: {reader["edad"]}, Sexo:{reader["sexo"]},NIT: {reader["nit"]}");

                            listClients.Add(new Clients() 
                            { Id_client = (int)reader["id_cliente"], 
                              Name = (string)reader["nombre"], 
                              Age = (int)reader["edad"], 
                              Gender = (string)reader["sexo"], 
                              Nit = (int)reader["nit"],
                              Ruta_Imagen = (string)reader["imagen"]
                            });
                        
                        }
                    }



                }

                if (listClients.Count == 0)
                {
                    MessageBox.Show("No hay clientes registrados en la base de datos.");
                    return null;
                }
                else {
                    return listClients;
                }

                    

            }
            catch (Exception ex)
            {
                MessageBox.Show("Error al obtener los Clientes: " + ex.Message);
                return null;
            }



        }

        public List<int> getClientsAge()
        {
            try
            {
                

                List<int> edades = new List<int>();

                string query = "SELECT edad FROM clientes";

                
                // Ejecutar la consulta
                using (MySqlCommand cmd = new MySqlCommand(query, connect))
                {
                    // Leer los datos con un DataReader
                    using (MySqlDataReader reader = cmd.ExecuteReader())
                    {
                        while (reader.Read())
                        {
                            // Leer cada edad y agregarla a la lista
                            edades.Add(reader.GetInt32("edad"));
                        }
                    }
                }

                if (edades.Count == 0)
                {
                    MessageBox.Show("No hay edades registradas en la base de datos.");
                    return null;
                }
                else
                {
                    return edades;
                }

                /*
                foreach (int edad in edades)
                {
                    Console.Write($"{edad}, ");
                }*/

                
            }
            catch (Exception ex)
            {
                MessageBox.Show("Error al obtener los datos de los Clientes: " + ex.Message);

                return null;
            }



        }

        public List<string> getClientsGender()
        {
            try
            {
                

                List<string> genero = new List<string>();

                string query = "SELECT sexo FROM clientes";


                // Ejecutar la consulta
                using (MySqlCommand cmd = new MySqlCommand(query, connect))
                {
                    // Leer los datos con un DataReader
                    using (MySqlDataReader reader = cmd.ExecuteReader())
                    {
                        while (reader.Read())
                        {
                            // Leer cada edad y agregarla a la lista
                            genero.Add(reader.GetString("sexo"));
                        }
                    }
                }

                if (genero.Count == 0)
                {
                    MessageBox.Show("No hay generos registradas en la base de datos.");
                    return null;
                }
                else
                {
                    return genero;
                }

                /*
                foreach (string sexo in genero)
                {
                    Console.Write($"{sexo}, ");
                }*/

                
            }
            catch (Exception ex)
            {
                MessageBox.Show("Error al obtener los datos de los Clientes: " + ex.Message);

                return null;
            }



        }

        public void deleteClient(int nit) {

            try
            {
                string query = "DELETE FROM clientes WHERE nit = @nit";


                // Ejecutar la consulta
                using (MySqlCommand cmd = new MySqlCommand(query, connect))
                {

                    cmd.Parameters.AddWithValue("@nit", nit);

                    int filasAfectadas = cmd.ExecuteNonQuery();

                    if (filasAfectadas > 0)
                    {
                        MessageBox.Show("Cliente eliminado correctamente.");
                    }
                    else
                    {
                        MessageBox.Show("No se encontró un cliente con ese NIT.");
                    }
                }


            }
            catch (Exception ex)
            {
                MessageBox.Show("Error al eliminar el cliente: " + ex.Message);

                
            }

        }

        public void modifyClient(string name, string age, string gender, string nit, string image)
        {

            try
            {
                string query = @"UPDATE clientes 
                     SET nombre = @nombre, 
                         edad = @edad, 
                         sexo = @genero, 
                         nit = @nit,
                         imagen = @image
                     WHERE nit = @nit";

                using (MySqlCommand cmd = new MySqlCommand(query, connect))
                {
                    // Asignar valores a los parámetros
                    cmd.Parameters.AddWithValue("@nombre", name);
                    cmd.Parameters.AddWithValue("@edad", age);
                    cmd.Parameters.AddWithValue("@genero", gender);
                    cmd.Parameters.AddWithValue("@nit", nit);
                    cmd.Parameters.AddWithValue("@image", image); 

                    int filasAfectadas = cmd.ExecuteNonQuery();

                    if (filasAfectadas > 0)
                    {
                        MessageBox.Show("Cliente actualizado correctamente.");
                    }
                    else
                    {
                        MessageBox.Show("No se encontró un cliente con ese NIT.");
                    }
                }


            }
            catch (Exception ex)
            {
                MessageBox.Show("Error al modificar los datos del cliente: " + ex.Message);


            }

        }


        public bool createProduct(string name, string price, string quantity, string image)
        {
            try
            {
                

                string query1 = "SELECT COUNT(*) FROM productos WHERE nombre = @nombre";
                using (MySqlCommand cmd = new MySqlCommand(query1, connect))
                {

                    cmd.Parameters.AddWithValue("@nombre", name);

                    int count = Convert.ToInt32(cmd.ExecuteScalar());


                    if (count > 0)
                    {
                        //MessageBox.Show($"El cliente con nit: {nit}, ya existe");

                        return false;
                    }
                    else
                    {

                        string query2 = "INSERT INTO productos (nombre, precio, cantidad, imagen) VALUES (@nombre, @precio, @cantidad, @imagen)";
                        using (MySqlCommand cmd2 = new MySqlCommand(query2, connect))
                        {
                            cmd2.Parameters.AddWithValue("@nombre", name);
                            cmd2.Parameters.AddWithValue("@precio", double.Parse(price));
                            cmd2.Parameters.AddWithValue("@cantidad", int.Parse(quantity));
                            cmd2.Parameters.AddWithValue("@imagen", image);

                            cmd2.ExecuteNonQuery();
                            //connect.Close();

                            return true;
                        }
                    }


                }

            }
            catch (Exception ex)
            {
                MessageBox.Show("Error al registrar el Producto: " + ex.Message);
                return false;
            }



        }

        public List<Products> getAllProducts()
        {
            try
            {


                List<Products> listProducts = new List<Products>();

                string query1 = "SELECT * FROM productos";
                using (MySqlCommand cmd = new MySqlCommand(query1, connect))
                {

                    using (MySqlDataReader reader = cmd.ExecuteReader())
                    {
                        while (reader.Read()) // si no hay registros en la tabla productos este while nunca se ejecuta
                        {


                            listProducts.Add(new Products()
                            {
                                Id_product = (int)reader["id_producto"],
                                Name = (string)reader["nombre"],
                                Price = Convert.ToDouble(reader["precio"]),
                                Cantidad = Convert.ToInt32(reader["cantidad"]),
                                Ruta_Imagen = (string)reader["imagen"]
                                //Nit = (int)reader["nit"]
                            });

                        }
                    }



                }

                if (listProducts.Count == 0)
                {
                    MessageBox.Show("No hay productos registrados en la base de datos.");
                    return null;
                }
                else
                {
                    return listProducts;
                }



            }
            catch (Exception ex)
            {
                MessageBox.Show("Error al obtener los Productos: " + ex.Message);
                return null;
            }



        }

        public void modifyProduct(string name, string price, string quantity, string image)
        {

            try
            {
                string query = @"UPDATE productos 
                     SET nombre = @nombre, 
                         precio = @price, 
                         cantidad = @quantity, 
                         
                         imagen = @image
                     WHERE nombre = @nombre";

                using (MySqlCommand cmd = new MySqlCommand(query, connect))
                {
                    // Asignar valores a los parámetros
                    cmd.Parameters.AddWithValue("@nombre", name);
                    cmd.Parameters.AddWithValue("@price", price);
                    cmd.Parameters.AddWithValue("@quantity", quantity);
                    
                    cmd.Parameters.AddWithValue("@image", image);

                    int filasAfectadas = cmd.ExecuteNonQuery();

                    if (filasAfectadas > 0)
                    {
                        MessageBox.Show("Producto actualizado correctamente.");
                    }
                    else
                    {
                        MessageBox.Show("No se encontró un producto con ese Nombre.");
                    }
                }


            }
            catch (Exception ex)
            {
                MessageBox.Show("Error al modificar los datos del producto: " + ex.Message);


            }

        }

        public void deleteProduct(string name)
        {

            try
            {
                string query = "DELETE FROM productos WHERE nombre = @name";


                // Ejecutar la consulta
                using (MySqlCommand cmd = new MySqlCommand(query, connect))
                {

                    cmd.Parameters.AddWithValue("@name", name);

                    int filasAfectadas = cmd.ExecuteNonQuery();

                    if (filasAfectadas > 0)
                    {
                        MessageBox.Show("Producto eliminado correctamente.");
                    }
                    else
                    {
                        MessageBox.Show("No se encontró un producto con ese Nombre.");
                    }
                }


            }
            catch (Exception ex)
            {
                MessageBox.Show("Error al eliminar el producto: " + ex.Message);


            }

        }

        //codigo_venta, nit, nombre_producto, cantidad_comprad
        public bool createSale(int codigo_venta, int nit, string nombre_producto, int cantidad_comprada)
        {
            try
            {
                List<Products> listProducts = getAllProducts();
                
                int id_producto = 0;

                if (listProducts != null)
                {

                    foreach (Products p in listProducts)
                    {

                        if (nombre_producto == p.Name)
                        {

                            id_producto = p.Id_product;

                        }
                    }

                }
                else {
                    MessageBox.Show("Todavia no ha registrado los productos");
                    return false;
                }


                List<Clients> listClients = getAllClients();

                int id_cliente = 0;

                if (listClients != null)
                {
                    foreach (Clients c in listClients)
                    {

                        if (nit == c.Nit)
                        {

                            id_cliente = c.Id_client;

                        }
                    }
                }
                else {
                    MessageBox.Show("Todavia no ha registrado los clientes");
                    return false;
                }



                    string query = "INSERT INTO ventas (codigo_venta, id_cliente, id_producto, nit, nombre_producto ,cantidad_comprada) " +
                        "VALUES (@codigo_venta, @id_cliente, @id_producto, @nit, @nombre_producto, @cantidad_comprada)";
                using (MySqlCommand cmd = new MySqlCommand(query, connect))
                { 
                    cmd.Parameters.AddWithValue("@codigo_venta", codigo_venta);
                    cmd.Parameters.AddWithValue("@id_cliente", id_cliente);
                    cmd.Parameters.AddWithValue("@id_producto", id_producto);
                    cmd.Parameters.AddWithValue("@nit", nit);
                    cmd.Parameters.AddWithValue("@nombre_producto", nombre_producto);
                    cmd.Parameters.AddWithValue("@cantidad_comprada", cantidad_comprada);

                    

                    cmd.ExecuteNonQuery();
                    //connect.Close();

                    return true;

                }

            }
            catch (Exception ex)
            {
                MessageBox.Show("Error al registrar la Venta: " + ex.Message);
                return false;
            }



        }

        public List<Sales> getAllSales()
        {
            try
            {


                List<Sales> listSales = new List<Sales>();

                string query1 = "SELECT * FROM ventas";
                using (MySqlCommand cmd = new MySqlCommand(query1, connect))
                {

                    using (MySqlDataReader reader = cmd.ExecuteReader())
                    {
                        while (reader.Read()) // si no hay registros en la tabla ventas este while nunca se ejecuta
                        {


                            listSales.Add(new Sales()
                            {
                                Id_sale = (int)reader["id_venta"],
                                Sale_code = (int)reader["codigo_venta"],
                                Id_client = (int)reader["id_cliente"],
                                Id_product = (int)(reader["id_producto"]),
                                Nit = (int)(reader["nit"]),
                                Name_product = (string)reader["nombre_producto"],
                                Saled_amount = (int)reader["cantidad_comprada"]
                                //Nit = (int)reader["nit"]
                            });

                        }
                    }



                }

                if (listSales.Count == 0)
                {
                    MessageBox.Show("No hay ventas registradas en la base de datos.");
                    return null;
                }
                else
                {
                    return listSales;
                }



            }
            catch (Exception ex)
            {
                MessageBox.Show("Error al obtener las Ventas: " + ex.Message);
                return null;
            }



        }

        public void deleteSale(int codigo_venta)
        {

            try
            {
                string query = "DELETE FROM ventas WHERE codigo_venta = @codigo_venta";


                // Ejecutar la consulta
                using (MySqlCommand cmd = new MySqlCommand(query, connect))
                {

                    cmd.Parameters.AddWithValue("@codigo_venta", codigo_venta);

                    int filasAfectadas = cmd.ExecuteNonQuery();

                    if (filasAfectadas > 0)
                    {
                        MessageBox.Show("Venta eliminada correctamente.");
                    }
                    else
                    {
                        MessageBox.Show("No se encontró una venta con el codigo venta.");
                    }
                }


            }
            catch (Exception ex)
            {
                MessageBox.Show("Error al eliminar la venta: " + ex.Message);


            }

        }

        public decimal getTotalSales() {

            string query = @"
            SELECT v.cantidad_comprada, v.id_producto, p.precio, p.nombre
            FROM ventas v
            JOIN productos p ON v.id_producto = p.id_producto
            ORDER BY v.codigo_venta ASC;
            ";

            decimal total_ventas = 0;

            using (MySqlCommand cmd = new MySqlCommand(query, connect))
            {
                using (MySqlDataReader reader = cmd.ExecuteReader())
                {
                    while (reader.Read())
                    {
                        int cantidad = Convert.ToInt32(reader["cantidad_comprada"]);
                        string nombre_producto = (string)(reader["nombre"]);
                        decimal precio = Convert.ToDecimal(reader["precio"]);

                        total_ventas += cantidad * precio;

                        //Console.WriteLine("Nombre producto: "+nombre_producto+", la cantidad es: "+ cantidad + ", el precio es: "+precio+" y el total es: "+total);
                        
                    }
                }
            }

            Console.WriteLine("El total de las ventas es: "+ total_ventas);

            return total_ventas;
        }
    }

    public class Clients
    {
        /*id_cliente, nombre, edad, sexo, nit*/
        public int Id_client { get; set; }

        public string Name { get; set; }

        public int Age { get; set; }

        public string Gender { get; set; }

        public int Nit { get; set; }

        public string Ruta_Imagen { get; set; }
    }

    public class Products
    {
        /*id_cliente, nombre, edad, sexo, nit*/
        public int Id_product { get; set; }

        public string Name { get; set; }

        public double Price { get; set; }

        public int Cantidad { get; set; }

        public string Ruta_Imagen { get; set; }

    }

    public class Sales
    {
        /*id_venta, codigo_venta, id_cliente, id_producto, nit, nombre_producto, cantidad_comprada*/
        public int Id_sale { get; set; }

        public int Sale_code { get; set; }

        public int Id_client { get; set; }

        public int Id_product { get; set; }

        public int Nit { get; set; }

        public string Name_product { get; set; }
        public int Saled_amount { get; set; }
    }

}