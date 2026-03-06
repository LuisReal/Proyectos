package proyecto1;

import javax.swing.*;

import java.awt.event.*;

public class CrearProducto extends JFrame {

    public CrearProducto() {

        setTitle("Crear Producto");
        setResizable(false);
        setBounds(400, 100, 350, 350);

        LaminaCrearProducto lamina_crear = new LaminaCrearProducto();
        add(lamina_crear);

    }

}

class LaminaCrearProducto extends JPanel {

    public static Object[][] elementoProducto = new Object[100][4]; // declarar static para que pueda recivir los elementos de la carga masiva

    //public int z = 0;
    public int x = 0;

    JLabel label_nombre_producto;
    JLabel label_precio_producto;
    JLabel label_cantidad_producto;
    JLabel label_imagen_producto;

    JTextField campo_nombre_producto;
    JTextField campo_precio_producto;
    JTextField campo_cantidad_producto;

    JButton seleccionar_imagen;
    JButton guardar;

    String ruta_imagen;

    JFileChooser chooser;

    public LaminaCrearProducto() {

        setLayout(null);

        label_nombre_producto = new JLabel("Nombre");
        label_nombre_producto.setBounds(50, 20, 100, 30);
        add(label_nombre_producto);

        campo_nombre_producto = new JTextField();
        campo_nombre_producto.setBounds(120, 20, 150, 30);
        add(campo_nombre_producto);

        label_precio_producto = new JLabel("Precio");
        label_precio_producto.setBounds(50, 70, 100, 30);
        add(label_precio_producto);

        campo_precio_producto = new JTextField();
        campo_precio_producto.setBounds(120, 70, 150, 30);
        add(campo_precio_producto);

        label_cantidad_producto = new JLabel("Cantidad");
        label_cantidad_producto.setBounds(50, 120, 100, 30);
        add(label_cantidad_producto);

        campo_cantidad_producto = new JTextField();
        campo_cantidad_producto.setBounds(120, 120, 150, 30);
        add(campo_cantidad_producto);

        label_imagen_producto = new JLabel("Imagen");
        label_imagen_producto.setBounds(50, 170, 100, 30);
        add(label_imagen_producto);

        seleccionar_imagen = new JButton("Seleccionar");
        seleccionar_imagen.setBounds(120, 170, 150, 30);
        add(seleccionar_imagen);

        guardar = new JButton("Guardar");
        guardar.setBounds(100, 230, 100, 30);
        add(guardar);

        Seleccionar selecciona = new Seleccionar();
        seleccionar_imagen.addActionListener(selecciona);

        Guardar guarda = new Guardar();
        guardar.addActionListener(guarda);

    }

    public void setElemento(Object[][] elementoProducto) {

        this.elementoProducto = elementoProducto; // proviene de la clase VentanaProductos

    }

    private class Seleccionar implements ActionListener {

        public void actionPerformed(ActionEvent e) {

            chooser = new JFileChooser(); //automaticamente se abre en Documents
            chooser.showOpenDialog(null);
            
            ruta_imagen = chooser.getSelectedFile().toString();

            String rutaCorregida = ruta_imagen.replace("\\", "/");
            int posicion = rutaCorregida.indexOf("src/");

            if (posicion != -1) {
                ruta_imagen = rutaCorregida.substring(posicion + 3); // incluye la barra inicial de esta forma /producto/P0.jpg
            }

            
            System.out.println("La ruta de la imagen del producto es: " + ruta_imagen);

        }

    }

    private class Guardar implements ActionListener {

        public void actionPerformed(ActionEvent e) {

            int contador = 0;

            for (int d = 0; d < elementoProducto.length; d++) {

                if (campo_nombre_producto.getText().equals(elementoProducto[d][0])) {

                    System.out.println("El Producto ingresado ya existe en la posicion " + d + " " + elementoProducto[d][0]);
                    JOptionPane.showMessageDialog(null, "El Producto ingresado ya existe");

                    contador++; // suma 1 al contador si ya existe el producto
                }

            }

            int z = 0;

            // ------------- Almacenando la informacion ingresada en elementoProducto -----------------------
            for (int j = 0; j < elementoProducto.length; j++) {

                if (contador != 1) { // si ya existe el producto el contador ==1 y nunca entra aca 

                    if (elementoProducto[j][0] == null) { // evalua si el arreglo esta vacio para almacenar los datos

                        z++;// incrementa 1 para poder entrar a la siguiente instruccion y solo guarda una vez los datos

                        if (z == 1) {

                            elementoProducto[j][x] = campo_nombre_producto.getText();
                            System.out.println("La posicion elemento " + j + "," + x + " = " + elementoProducto[j][x]);

                            elementoProducto[j][x + 1] = campo_precio_producto.getText();
                            System.out.println("La posicion elemento " + j + "," + (x + 1) + " = " + elementoProducto[j][x + 1]);

                            elementoProducto[j][x + 2] = campo_cantidad_producto.getText();
                            System.out.println("La posicion elemento " + j + "," + (x + 2) + " = " + elementoProducto[j][x + 2]);

                            elementoProducto[j][x + 3] = ruta_imagen; // se almacen la ruta de la imagen
                            System.out.println("La posicion elemento " + j + "," + (x + 3) + " = " + elementoProducto[j][x + 3]);

                        }
                    }// if (elemento[j][3] == null)

                }

            }//fin del for

            System.out.println("\nImprimiendo los valores de elementoProducto");
            for (int i = 0; i < elementoProducto.length; i++) {
                
                if (elementoProducto[i][0] != null) {

                    System.out.println("Producto: " + elementoProducto[i][0] + " Precio: " + elementoProducto[i][1] + " Cantidad: " + elementoProducto[i][2] + " Ruta imagen: " + elementoProducto[i][3]);
                }
            }

            campo_nombre_producto.setText(null);
            campo_precio_producto.setText(null);
            campo_cantidad_producto.setText(null);

        }// fin del metodo actionPerformed

    }

    public Object[][] getElemento() {

        return elementoProducto; // envia el elemento a la clase VentanaProductos

    }
}
