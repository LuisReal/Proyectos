package proyecto1;

import java.io.*;
import javax.swing.*;

public class CargaMasivaProductos extends JFrame {

    public String[] camposProducto;
    public Object[][] elementoProducto;

    String[] cadena;

    public CargaMasivaProductos() {

        int i = 0;
        elementoProducto = new Object[100][4];

        File archivo;
        JFileChooser seleccionar = new JFileChooser(); //automaticamente se abre en Documents
        seleccionar.showOpenDialog(null);

        archivo = seleccionar.getSelectedFile();

        try {
            // Abrir el .csv en buffer de lectura
            FileReader lector = new FileReader(archivo);
            BufferedReader bufferLectura = new BufferedReader(lector);

            String linea;

            while ((linea = bufferLectura.readLine()) != null) {

                //texto = texto + linea;
                camposProducto = linea.split(","); // guarda la informacion en un arreglo de tipo String

                for (int j = 0; j < camposProducto.length; j++) {

                    elementoProducto[i][j] = camposProducto[j];

                    // System.out.println("El elemento "+elemento[i][j]);
                }
                i++;// para poder llevar el conteo de las filas
            }
        } catch (IOException e) {
            System.out.println("El archivo no se ha encontrado");
        }

        int m = 1;
        cadena = new String[100];
        int[] nit = new int[100];

        //System.out.println("El tamano del elemento es "+elemento.length);
        for (int j = 0; j < elementoProducto.length; j++) {

            if ((elementoProducto[j][0]) != null) {

                cadena[j] = String.valueOf(elementoProducto[j][0]);

                // System.out.println("La cadena Nombre de Producto en la posicion " + j + " = " + cadena[j]);
            }

        }

        int contador = 0;

        for (int k = 0; k < (cadena.length); k++) { // tamano cadena 9

            if (cadena[k] != null) {

                for(int b=k+1; b<cadena.length; b++ ) {

                    //System.out.println("La cadena k "+cadena[k]);
                    //System.out.println("La cadena b "+cadena[b]);
                    
                    if (cadena[k].equals(cadena[b])) {

                        contador++;

                        System.out.println("El producto repetido es " + cadena[k]);
                        
                        JOptionPane.showMessageDialog(null, "El producto que se repite es "+cadena[k]);
                        //System.out.println("El contador es "+contador);
                    }
                }
            }
        }// fin del for
        
        if (contador == 0) {

            System.out.println("Si se puede cargar el archivo");
            
            /*LaminaVentanaProductos lamina_producto1 = new LaminaVentanaProductos();
            lamina_producto1.setDatos(elementoProducto); // envia elementoProducto a VentanaProductos*/
            
            

        }else{
        
            JOptionPane.showMessageDialog(null, "No se puede cargar el archivo, Hay Productos que se repiten");
        
        }
        
        
    }// fin constructor CargaMasivaProductos

}
