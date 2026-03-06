using Microsoft.Win32;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;

namespace App_Escritorio
{
    public class CargarDatos
    {
        private DataBase db;
        public CargarDatos(String buton) {

            db = new DataBase(); //abre la conexion de la base de datos

            OpenFileDialog openFileDialog = new OpenFileDialog();


            openFileDialog.Title = "Selecciona un archivo";
            

            string fileName = "";
            string rutaArchivo = "";
            

            

            if (openFileDialog.ShowDialog() == true)
            {

                fileName = openFileDialog.SafeFileName;
                rutaArchivo = openFileDialog.FileName;
                MessageBox.Show("Archivo seleccionado:\n" + rutaArchivo);



                string[] lineas = File.ReadAllLines(rutaArchivo);

                if (buton  == "Cargar Clientes") {

                    string name, age, gender, nit, image;
                    string[] columnas;
                    bool validar = false;

                    foreach (string linea in lineas)
                    {
                        // Separar por coma 
                        columnas = linea.Split(',');

                        // Verificar que tenga las 5 columnas esperadas
                        if (columnas.Length == 5)
                        {
                            name = columnas[0].Trim();  // quitar espacios extra
                            age = columnas[1].Trim();
                            gender = columnas[2].Trim();
                            nit = columnas[3].Trim();
                            image = columnas[4].Trim();

                            // Ejemplo: mostrar los valores
                            //Console.WriteLine($"Col1: {name}, Col2: {age}, Col3: {gender}, Col4: {nit}, Col5: {image}");

                            //createClient(string name, string age, string gender, string nit, string image)

                            validar = db.createClient(name, age, gender, nit, image);

                        }
                        else
                        {
                            Console.WriteLine("El archivo no tiene 5 columnas");
                        }


                    }

                    db.connect.Close(); //cierra la conexion de la base de datos

                    if (validar == true)
                    {
                        MessageBox.Show("Clientes registrados correctamente");
                    }
                    else
                    {
                        MessageBox.Show("No se pudo registrar a los clientes");
                    }

                } else if (buton  == "Cargar Productos") {


                    string name, price, quantity, image;
                    string[] columnas;
                    bool validar = false;

                    foreach (string linea in lineas)
                    {
                        // Separar por coma 
                        columnas = linea.Split(',');

                        // Verificar que tenga las 4 columnas esperadas
                        if (columnas.Length == 4)
                        {
                            name = columnas[0].Trim();  // quitar espacios extra
                            price = columnas[1].Trim();
                            quantity = columnas[2].Trim();
                            image = columnas[3].Trim();


                            //Console.WriteLine($"Col1: {name}, Col2: {age}, Col3: {gender}, Col4: {nit}, Col5: {image}");



                            validar = db.createProduct(name, price, quantity, image);

                        }
                        else
                        {
                            Console.WriteLine("El archivo no tiene 4 columnas");
                        }
                    }

                    db.connect.Close(); //cierra la conexion de la base de datos

                    if (validar == true)
                    {
                        MessageBox.Show("Productos registrados correctamente");
                    }
                    else
                    {
                        MessageBox.Show("No se pudo registrar a los productos");
                    }
                }
                else if (buton  == "Cargar Ventas")
                {


                    string nombre_producto;
                    string[] columnas;
                    bool validar = false;

                    int codigo_venta, nit, cantidad_comprada;

                    foreach (string linea in lineas)
                    {
                        // Separar por coma 
                        columnas = linea.Split(',');

                        // Verificar que tenga las 4 columnas esperadas
                        if (columnas.Length == 4)
                        {
                            codigo_venta = int.Parse(columnas[0].Trim());  // quitar espacios extra
                            nit = int.Parse(columnas[1].Trim());
                            nombre_producto = columnas[2].Trim();
                            cantidad_comprada = int.Parse(columnas[3].Trim());


                            //Console.WriteLine($"Col1: {name}, Col2: {age}, Col3: {gender}, Col4: {nit}, Col5: {image}");



                            validar = db.createSale(codigo_venta, nit, nombre_producto, cantidad_comprada);

                        }
                        else
                        {
                            Console.WriteLine("El archivo no tiene 4 columnas");
                        }
                    }

                    db.connect.Close(); //cierra la conexion de la base de datos

                    if (validar == true)
                    {
                        MessageBox.Show("Ventas registradas correctamente");
                    }
                    else
                    {
                        MessageBox.Show("No se pudo registrar las ventas");
                    }
                }

            }
            else
            {
                MessageBox.Show("No ha seleccionado ningun Archivo");
            }
        }
    
    }
}
