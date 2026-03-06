package proyecto1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.imageio.ImageIO;

public class ConsultarProducto extends JFrame {

    JLabel consulta;
    JTextField campo_consulta;
    JButton buscar;

    public static Object[][] elementoProducto = new Object[100][4];

    public ConsultarProducto() {

        setLayout(null);
        
        setTitle("Consulta Producto");
        setResizable(false);
        setBounds(300, 200, 350, 220);

        consulta = new JLabel("Consulta de Producto (Ingrese NOMBRE)");
        consulta.setBounds(60, 20, 250, 30);
        add(consulta);

        campo_consulta = new JTextField();
        campo_consulta.setBounds(60, 70, 200, 30);
        add(campo_consulta);

        buscar = new JButton("Buscar");
        buscar.setBounds(160, 120, 100, 30);
        add(buscar);

        Buscar busqueda = new Buscar();
        buscar.addActionListener(busqueda);

    }

    public void setElemento(Object[][] elementoProducto) {

        this.elementoProducto = elementoProducto; // proviene de la clase VentanaProductos

    }

    private class Buscar implements ActionListener {

        public void actionPerformed(ActionEvent w) {
            
            int contador = 0;
            
            for (int i = 0; i < elementoProducto.length; i++) {

                if ((campo_consulta.getText()).equals(elementoProducto[i][0])) {

                    String nombre = campo_consulta.getText();

                    DatosProductos mostrar = new DatosProductos(elementoProducto, nombre);
                    mostrar.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    mostrar.setVisible(true);
                    
                    contador++;
                }
            }
            
            if (contador == 0) {

                JOptionPane.showMessageDialog(null, "El Nombre no existe");
            }

        } // find metodo actionPerformed

    }// fin private class Buscar

}

class DatosProductos extends JFrame {

    public DatosProductos(Object[][] elementoProducto, String nombre) {

        setTitle("Datos de Producto");
        setResizable(false);
        setBounds(300, 200, 400, 500);

        LaminaDatosProductos laminaDatos = new LaminaDatosProductos(elementoProducto, nombre);
        add(laminaDatos);

    }

}

class LaminaDatosProductos extends JPanel {

    JLabel etiqueta_nombre_producto;
    JLabel etiqueta_precio_producto;
    JLabel etiqueta_cantidad_producto;
    JLabel imagen_producto;

    JLabel mostrar_nombre_producto;
    JLabel mostrar_precio_producto;
    JLabel mostrar_cantidad_producto;

    JLabel avatar_foto;

    JButton eliminar;

    public static Object[][] elementoProducto = new Object[100][4];

    String[] cadenaNombres = new String[100];
    String[] cadenaPrecios = new String[100];
    String[] cadenaCantidad = new String[100];

    String[] foto = new String[200];
    Icon[] iconos = new Icon[200];

    String nombre = "";
    public Image imagen;
    public Icon icono;

    public LaminaDatosProductos(Object[][] elementoProducto, String nombre) {

        setLayout(null);

        this.elementoProducto = elementoProducto;
        this.nombre = nombre;

        etiqueta_nombre_producto = new JLabel("Nombre ");
        etiqueta_nombre_producto.setBounds(30, 20, 100, 30);
        add(etiqueta_nombre_producto);

        etiqueta_precio_producto = new JLabel("Precio ");
        etiqueta_precio_producto.setBounds(30, 70, 100, 30);
        add(etiqueta_precio_producto);

        etiqueta_cantidad_producto = new JLabel("Cantidad ");
        etiqueta_cantidad_producto.setBounds(30, 120, 100, 30);
        add(etiqueta_cantidad_producto);

        imagen_producto = new JLabel("Imagen ");
        imagen_producto.setBounds(30, 170, 100, 30);
        add(imagen_producto);

        eliminar = new JButton("Eliminar");
        eliminar.setBounds(150, 390, 100, 30);
        add(eliminar);

        Eliminar elimina = new Eliminar();
        eliminar.addActionListener(elimina);

        for (int j = 0; j < elementoProducto.length; j++) {

            if (elementoProducto[j][0] != null) {

                cadenaNombres[j] = String.valueOf(elementoProducto[j][0]);
                cadenaPrecios[j] = String.valueOf(elementoProducto[j][1]);
                cadenaCantidad[j] = String.valueOf(elementoProducto[j][2]);

                foto[j] = String.valueOf(elementoProducto[j][3]); //esto contiene la ruta de la imagen tipo: /productos/P27.jpg

                try {

                    imagen = ImageIO.read(getClass().getResource(foto[j])); // se maneja este tipo de ruta /productos/P27.jpg

                    iconos[j] = new ImageIcon(new ImageIcon(imagen).getImage().getScaledInstance(200, 150, Image.SCALE_DEFAULT));

                    //icono1 = icono;
                } catch (IOException a) {

                    System.out.println("No se encontro la imagen");
                }

            }

        } // fin del for

        for (int i = 0; i < elementoProducto.length; i++) {

            if (nombre.equals(elementoProducto[i][0])) {

                mostrar_nombre_producto = new JLabel(cadenaNombres[i]);
                mostrar_nombre_producto.setBounds(150, 20, 100, 30);
                add(mostrar_nombre_producto);

                mostrar_precio_producto = new JLabel(cadenaPrecios[i]);
                mostrar_precio_producto.setBounds(150, 70, 100, 30);
                add(mostrar_precio_producto);

                mostrar_cantidad_producto = new JLabel(cadenaCantidad[i]);
                mostrar_cantidad_producto.setBounds(150, 120, 100, 30);
                add(mostrar_cantidad_producto);

                avatar_foto = new JLabel(iconos[i]);
                avatar_foto.setBounds(100, 220, 200, 150);
                add(avatar_foto);

            }

        } // fin del for
    }// fin constructor LaminaDatosProductos

    private class Eliminar implements ActionListener {

        public void actionPerformed(ActionEvent w) {

            mostrar_nombre_producto.setText("");
            mostrar_precio_producto.setText("");
            mostrar_cantidad_producto.setText("");

            avatar_foto.setIcon(null); // elimina la imagen de la foto

            for (int i = 0; i < elementoProducto.length; i++) {

                if (nombre.equals(elementoProducto[i][0])) { // busca el nombre ingresado 

                    for (int j = 0; j < 4; j++) {

                        if ((elementoProducto[i][j]) != null) {

                            elementoProducto[i][j] = null;

                            //System.out.println("El elemento " + i + " " + j + " " + elemento[i][j]);
                        }

                    }// fin del for int j=0
                }
            } // fin del for int i=0

            for (int p = 0; p < elementoProducto.length; p++) {

                for (int r = 0; r < 4; r++) {

                    System.out.println("El elemento " + p + " " + r + " = " + elementoProducto[p][r]);

                }

            }
        } // fin del metodo actionPerformed

    }// fin clase Eliminar

}
