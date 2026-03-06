package proyecto1;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class ModificarProducto extends JFrame {

    JLabel consulta;
    JTextField campo_consulta;
    JButton buscar;

    public static Object[][] elementoProducto = new Object[100][4];

    public ModificarProducto() {

        setTitle("Modificar Producto");
        setResizable(false);
        setBounds(300, 200, 350, 220);

        setLayout(null);

        consulta = new JLabel("Modificar Producto (Ingrese Nombre)");
        consulta.setBounds(60, 20, 250, 30);
        add(consulta);

        campo_consulta = new JTextField();
        campo_consulta.setBounds(60, 70, 200, 30);
        add(campo_consulta);

        buscar = new JButton("Buscar");
        buscar.setBounds(160, 120, 100, 30);
        add(buscar);

        Buscar busquedaP = new Buscar();
        buscar.addActionListener(busquedaP);

    }

    public void setElemento(Object[][] elementoProducto) {

        this.elementoProducto = elementoProducto; // proviene de la clase VentanaClientes

    }

    private class Buscar implements ActionListener {

        public void actionPerformed(ActionEvent w) {

            boolean existe = false;

            for (int i = 0; i < elementoProducto.length; i++) {

                if ((campo_consulta.getText()).equals(elementoProducto[i][0])) {

                    String nombre = campo_consulta.getText();

                    ModificaProducto modifica = new ModificaProducto(elementoProducto, nombre);
                    modifica.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    modifica.setVisible(true);

                    existe = true;
                }
            }

            if (existe == false) { //El producto no existe
                JOptionPane.showMessageDialog(null, "El Producto no existe");
            }

        }// fin metodo actionPerformed
    } // fin clase Buscar 
}

class ModificaProducto extends JFrame {

    public ModificaProducto(Object[][] elementoProducto, String nombre) {

        setTitle("Modificar Producto");
        setResizable(false);
        setBounds(300, 200, 440, 370);

        LaminaModificaProducto mi_lamina = new LaminaModificaProducto(elementoProducto, nombre);
        add(mi_lamina);

    }

}

class LaminaModificaProducto extends JPanel {

    JLabel etiqueta_nombre_producto;
    JLabel etiqueta_precio_producto;
    JLabel etiqueta_cantidad_producto;

    JLabel etiqueta_avatar;

    JTextField campo_nombre_producto;
    JTextField campo_precio_producto;
    JTextField campo_cantidad_producto;

    JButton seleccionar;
    JButton guardar;

    JLabel avatar_foto;

    String[] nombres = new String[100];
    String[] precios = new String[100];
    String[] cantidades = new String[100];

    String[] foto = new String[100];
    Icon[] iconos = new Icon[100];

    String nombre = "";
    String foto_seleccionada = "";

    public static Object[][] elementoProducto = new Object[100][4];

    public Image imagen;
    public Icon icono;

    public LaminaModificaProducto(Object[][] elementoProducto, String nombre) {

        setLayout(null);

        this.nombre = nombre;

        this.elementoProducto = elementoProducto;

        etiqueta_nombre_producto = new JLabel("Ingrese Nombre ");
        etiqueta_nombre_producto.setBounds(30, 20, 100, 30);
        add(etiqueta_nombre_producto);

        campo_nombre_producto = new JTextField(nombre);
        campo_nombre_producto.setBounds(200, 20, 150, 30);
        add(campo_nombre_producto);

        etiqueta_precio_producto = new JLabel("Ingrese Precio ");
        etiqueta_precio_producto.setBounds(30, 70, 100, 30);
        add(etiqueta_precio_producto);

        campo_precio_producto = new JTextField();
        campo_precio_producto.setBounds(200, 70, 150, 30);
        add(campo_precio_producto);

        etiqueta_cantidad_producto = new JLabel("Ingrese Cantidad ");
        etiqueta_cantidad_producto.setBounds(30, 120, 100, 30);
        add(etiqueta_cantidad_producto);

        campo_cantidad_producto = new JTextField();
        campo_cantidad_producto.setBounds(200, 120, 150, 30);
        add(campo_cantidad_producto);

        etiqueta_avatar = new JLabel("Seleccione Avatar ");
        etiqueta_avatar.setBounds(30, 180, 150, 30);
        add(etiqueta_avatar);

        seleccionar = new JButton("Seleccionar");
        seleccionar.setBounds(200, 180, 150, 30);
        add(seleccionar);

        guardar = new JButton("Guardar");
        guardar.setBounds(150, 270, 100, 30);
        add(guardar);

        Guardar guarda = new Guardar();
        guardar.addActionListener(guarda);

        Seleccionar seleccion = new Seleccionar();
        seleccionar.addActionListener(seleccion);

    }

    private class Guardar implements ActionListener {

        public void actionPerformed(ActionEvent e) {

            if (nombre.equals(campo_nombre_producto.getText())) {

                for (int i = 0; i < elementoProducto.length; i++) {

                    if (elementoProducto[i][0] != null && nombre.equals(elementoProducto[i][0])) {

                        elementoProducto[i][0] = campo_nombre_producto.getText();
                        elementoProducto[i][1] = campo_precio_producto.getText();
                        elementoProducto[i][2] = campo_cantidad_producto.getText();
                        elementoProducto[i][3] = foto_seleccionada;

                    }

                } // fin for
            } else {

                boolean existe = false;

                for (int d = 0; d < elementoProducto.length; d++) {

                    if (campo_nombre_producto.getText().equals(elementoProducto[d][0])) {

                        System.out.println("El Producto ingresado ya existe en la posicion " + d + " " + elementoProducto[d][0]);
                        JOptionPane.showMessageDialog(null, "El Producto ingresado ya existe");

                        existe = true;
                    }

                }

                if (existe == false) { // si el producto no existe

                    for (int i = 0; i < elementoProducto.length; i++) {

                        if (elementoProducto[i][0] != null && nombre.equals(elementoProducto[i][0])) {

                            elementoProducto[i][0] = campo_nombre_producto.getText();
                            elementoProducto[i][1] = campo_precio_producto.getText();
                            elementoProducto[i][2] = campo_cantidad_producto.getText();
                            elementoProducto[i][3] = foto_seleccionada;

                        }

                    } // fin for
                }

            }

            campo_nombre_producto.setText(null);
            campo_precio_producto.setText(null);
            campo_cantidad_producto.setText(null);

            for (int j = 0; j < elementoProducto.length; j++) {

                for (int m = 0; m < 4; m++) {

                    if ((elementoProducto[j][m]) != null) {

                        System.out.println("El elemento en la posicion: " + j + "," + m + " es: " + elementoProducto[j][m]);
                    }
                }
            }

        }// fin del metodo actionPerformed

    }

    private class Seleccionar implements ActionListener {

        public void actionPerformed(ActionEvent e) {

            JFileChooser chooser = new JFileChooser();//automaticamente se abre en Documents
            chooser.showOpenDialog(null);
            foto_seleccionada = chooser.getSelectedFile().toString();

            String rutaCorregida = foto_seleccionada.replace("\\", "/");
            int posicion = rutaCorregida.indexOf("src/");

            if (posicion != -1) {
                foto_seleccionada = rutaCorregida.substring(posicion + 3); // incluye la barra inicial de esta forma /producto/P0.jpg
            }

            System.out.println("la ruta de la foto seleccionada " + foto_seleccionada);
        }

    }

}// fin clase LaminaModificaProducto
