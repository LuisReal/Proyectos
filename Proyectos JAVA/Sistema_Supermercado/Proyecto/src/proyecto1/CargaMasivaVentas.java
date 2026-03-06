package proyecto1;


import java.io.*;
import javax.swing.*;

public class CargaMasivaVentas {

    public String[] camposVenta;
    public Object[][] elementoVenta;

    String[] cadena;

    public CargaMasivaVentas() {

        int i = 0;
        elementoVenta = new Object[200][4];

        File archivo;
        JFileChooser seleccionar = new JFileChooser();//automaticamente se abre en Documents
        seleccionar.showOpenDialog(null);

        archivo = seleccionar.getSelectedFile();

        try {
            // Abrir el .csv en buffer de lectura
            FileReader lector = new FileReader(archivo);
            BufferedReader bufferLectura = new BufferedReader(lector);

            String linea;

            while ((linea = bufferLectura.readLine()) != null) {

                //texto = texto + linea;
                camposVenta = linea.split(","); // guarda la informacion en un arreglo de tipo String
                //System.out.println("El tamano de camposVenta es: " + camposVenta.length);
                for (int j = 0; j < camposVenta.length; j++) {

                    elementoVenta[i][j] = camposVenta[j];

                    //System.out.println("El elemento Venta es: "+elementoVenta[i][j]);
                }
                i++;// para poder llevar el conteo de las filas
            }
        } catch (IOException e) {
            System.out.println("El archivo no se ha encontrado");
        }

        int m = 1;
        cadena = new String[500];

        for (int j = 0; j < elementoVenta.length; j++) {

            if ((elementoVenta[j][2]) != null) {

                cadena[j] = String.valueOf(elementoVenta[j][2]); // guarda los nombres de los productos

                // System.out.println("La cadena Nombre de Venta en la posicion " + j + " = " + cadena[j]);
            }

        }

        
        
         
        System.out.println("Si se puede cargar el archivo");

        /*LaminaVentanaVentas lamina_venta1 = new LaminaVentanaVentas();
        lamina_venta1.setDatos1(elementoVenta); // envia elementoVenta a VentanaVentas*/

    }// fin del constructor CargaMasivaVentas
}
