package proyecto1;

import java.awt.*;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;

public class CargaMasivaClientes {

    public String[] campos;
    public Object[][] elemento;

    // public String texto = "";
    public CargaMasivaClientes() {
        int i = 0;
        elemento = new Object[100][5];

        File archivo;
        JFileChooser seleccionar = new JFileChooser(); ////automaticamente se abre en Documents
        seleccionar.showOpenDialog(null);

        archivo = seleccionar.getSelectedFile();

        try {
            // Abrir el .csv en buffer de lectura
            FileReader lector = new FileReader(archivo);
            BufferedReader bufferLectura = new BufferedReader(lector);

            String linea;

            while ((linea = bufferLectura.readLine()) != null) {

                //texto = texto + linea;
                campos = linea.split(","); // guarda la informacion en un arreglo de tipo String

                for (int j = 0; j < campos.length; j++) {

                    elemento[i][j] = campos[j];

                    // System.out.println("El elemento "+elemento[i][j]);
                }
                i++;// para poder llevar el conteo de las filas
            }
        } catch (IOException e) {
            System.out.println("El archivo no se ha encontrado");
        }

        /* Imagenes paso = new Imagenes();

        paso.rutaImagen(elemento);
        paso.setImage();*/
        //elemento[0][4] = paso.getIcono();
        /*Lamina2 lamina1 = new Lamina2();// ubicada en DashboardClientes
        lamina1.setArreglos(elemento);*/
        int m = 1;
        String[] cadena = new String[100];
        int[] nit = new int[100];

        //System.out.println("El tamano del elemento es "+elemento.length);
        for (int j = 0; j < elemento.length; j++) {

            if ((elemento[j][3]) != null) {
                cadena[j] = String.valueOf(elemento[j][3]);
            }

            if ((cadena[j]) != null) {

                nit[j] = Integer.parseInt(cadena[j]);

                //System.out.println("El nit " + j + " = " + nit[j]);
            }
            /*if((cadena[j])!= null){
            System.out.println("La cadena "+j+" = "+cadena[j]);
            }*/
        }

        int[] contador = new int[99999999];

        for (int k = 0; k < (nit.length); k++) {

            if ((nit[k]) != 0) {

                contador[nit[k]] += 1;// guarda el # veces que se repite un numero en el arreglo contador

                //System.out.println("El contador " + k + " = " + contador[k]);
            }

        }

        //int[] datos = new int[100];
        int q = 0;
        for (int j = 0; j < contador.length; j++) {

            if ((contador[j] != 0) && (contador[j] != 1)) {

                JOptionPane.showMessageDialog(null, "El nit " + j + " se repite: " + contador[j] + " veces");

                q++;
                System.out.println("el valor de q " + q);
            }

        }

        if (q == 0) {

            Lamina lamina = new Lamina();
            lamina.setDatos(elemento);// pasa los datos cargados a la clase class Lamina de la clase VentanaClientes

        }else{
        
            JOptionPane.showMessageDialog(null, "No se puede cargar el archivo, Hay NITs que se repiten");
        
        }

    }

    class Imagenes extends JPanel {

        private Image imagen;

        public Icon icono;
        // public Icon icono1;

        public String[] ruta;

        public void rutaImagen(Object[][] elemento) {
            ruta = new String[13];

            for (int i = 0; i < 13; i++) {

                ruta[i] = String.valueOf(elemento[i][4]); // convierte un objeto a un String

                System.out.println("La ruta de la imagen " + i + " " + ruta[i]);

            }

        }

        public void setImage() {

            try {

                // String prueba = ruta[0];
                File ruta = new File("Luis.jpg");

                imagen = ImageIO.read(ruta);

                icono = new ImageIcon(imagen);

                //icono1 = icono;
            } catch (IOException a) {

                System.out.println("No se encontro la imagen");
            }

            // g.drawImage(imagen, 5, 5, null);
        }

        public Icon getIcono() {

            return icono;
        }
    }

}
