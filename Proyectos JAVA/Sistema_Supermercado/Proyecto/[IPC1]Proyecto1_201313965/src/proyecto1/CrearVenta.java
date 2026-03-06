package proyecto1;

import javax.swing.*;

import java.awt.event.*;

public class CrearVenta extends JFrame {

    public CrearVenta(Object[][] elementoVenta, Object[][] elementoProducto, String[] producto_unico, int[] cantidad_producto_unico) {

        setTitle("Crear Venta");
        setResizable(false);
        setBounds(400, 100, 400, 500);

        LaminaCrearVenta crearV = new LaminaCrearVenta(elementoVenta, elementoProducto, producto_unico, cantidad_producto_unico);
        add(crearV);
    }
}

class LaminaCrearVenta extends JPanel {

    //public static Object[][] elementoVenta = new Object[500][4];
    public int x = 0;

    JLabel nit_cliente;
    JLabel agregar_producto;
    JLabel producto;
    JLabel cantidad;
    JLabel ingresados_venta;

    JTextField campo_nit_cliente;
    JComboBox elegir_producto;
    String[] nombre_producto;

    JTextField campo_cantidad_producto;
    JButton agregar;

    String ruta_imagen;

    int cantidad_maxima = 0;
    int cantidad_sumada = 0;

    private Object[][] elementoVenta;
    private Object[][] elementoProducto;
    private int[] cantidad_producto_unico;
    private String[] producto_unico;
    private int mayor = 0;

    

    public LaminaCrearVenta(Object[][] elementoVenta, Object[][] elementoProducto, String[] producto_unico, int[] cantidad_producto_unico) {

        this.elementoVenta = elementoVenta;
        this.elementoProducto = elementoProducto;
        this.cantidad_producto_unico = cantidad_producto_unico;
        this.producto_unico = producto_unico;

        setLayout(null);

        nit_cliente = new JLabel("NIT del Cliente");
        nit_cliente.setBounds(30, 30, 100, 30);
        add(nit_cliente);

        campo_nit_cliente = new JTextField();
        campo_nit_cliente.setBounds(200, 30, 150, 30);
        add(campo_nit_cliente);

        agregar_producto = new JLabel("Agregar Productos");
        agregar_producto.setBounds(130, 80, 200, 30);
        add(agregar_producto);

        producto = new JLabel("Producto");
        producto.setBounds(30, 130, 100, 30);
        add(producto);

        elegir_producto = new JComboBox();
        elegir_producto.setBounds(200, 130, 150, 30);

        nombre_producto = new String[200];

        System.out.println("\n\n");
        for (int i = 0; i < producto_unico.length; i++) {

            if (producto_unico[i] != null) {

                //System.out.println("**********************El elemento Venta es: " + elementoVenta[i]);
                nombre_producto[i] = producto_unico[i];
            }
        }

        for (int j = 0; j < nombre_producto.length; j++) {

            if (nombre_producto[j] != null) {
                elegir_producto.addItem(nombre_producto[j]);
            }

        }

        add(elegir_producto);

        cantidad = new JLabel("Cantidad");
        cantidad.setBounds(30, 180, 100, 30);
        add(cantidad);

        campo_cantidad_producto = new JTextField();
        campo_cantidad_producto.setBounds(200, 180, 150, 30);
        add(campo_cantidad_producto);

        ingresados_venta = new JLabel("Ingresados en la venta");
        ingresados_venta.setBounds(130, 280, 150, 30);
        add(ingresados_venta);

        agregar = new JButton("Agregar");
        agregar.setBounds(250, 230, 100, 30);
        add(agregar);

        Agregar agrega = new Agregar();
        agregar.addActionListener(agrega);

        

        int posicion_mayor = 0;
        int contador_mayor = 0;

        for (int i = 0; i < elementoVenta.length; i++) {

            if (elementoVenta[i][0] != null) {

                if (contador_mayor == 0) {
                    mayor = Integer.parseInt(elementoVenta[i][0].toString());
                    contador_mayor++;
                }

                if (Integer.parseInt(elementoVenta[i][0].toString()) > mayor) {

                    mayor = Integer.parseInt(elementoVenta[i][0].toString());
                    posicion_mayor = i;
                }

            }
        }
        
        mayor++; //Importante aumentar el ID de la venta aca

        System.out.println("\nEl ID mayor es " + mayor + " En la posicion: " + posicion_mayor);

    }

    private class Agregar implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            int contador = 0;

            for (int d = 0; d < elementoVenta.length; d++) {

                if (elementoVenta[d][1] != null && campo_nit_cliente.getText().equals(elementoVenta[d][1].toString())) {

                    contador++; // suma 1 al contador si existe el NIT

                }

            }

            if (contador == 0) {

                JOptionPane.showMessageDialog(null, "El NIT ingresado no existe");

            } else { //aunque el valor de contador sea mayor > 0 el metodo productosExistencia() se llamara una sola vez
                productosExistencia();
            }

        }

        public void productosExistencia() {

            String producto_seleccionado = (String) elegir_producto.getSelectedItem();
            //producto_unico
            int contador = 0;
            int cantidad_ingresada = Integer.parseInt(campo_cantidad_producto.getText());

            for (int d = 0; d < elementoProducto.length; d++) {

                if (elementoProducto[d][0] != null && producto_seleccionado.equals(elementoProducto[d][0].toString())) {

                    if (elementoProducto[d][2] != null && cantidad_ingresada <= Integer.parseInt(elementoProducto[d][2].toString())) {

                        JOptionPane.showMessageDialog(null, "SI hay suficientes productos");

                        elementoProducto[d][2] = Integer.parseInt(elementoProducto[d][2].toString()) - cantidad_ingresada;

                        contador++; // suma 1 al contador para verificar que hayan productos en existencia

                        crearNuevaVenta(mayor, campo_nit_cliente.getText(), producto_seleccionado, cantidad_ingresada);
                    }

                }

            }

            if (contador == 0) {

                JOptionPane.showMessageDialog(null, "No hay suficientes productos");

            }

            /*
            for (int i = 0; i < elementoVenta.length; i++) {

                if (elementoVenta[i][0] != null) {
                    System.out.println("El id de la venta es: " + elementoVenta[i][0].toString() + " y la posicion es: " + i);
                } else {
                    System.out.println("El elemento esta vacio en la posicion: " + i);
                }

            }*/
        }

        public void crearNuevaVenta(int idMayor, String nit_cliente, String producto_seleccionado, int cantidad_ingresada) {

            for (int i = 0; i < elementoVenta.length; i++) {

                if (elementoVenta[i][0] == null) {

                    elementoVenta[i][0] = idMayor;
                    elementoVenta[i][1] = nit_cliente;
                    elementoVenta[i][2] = producto_seleccionado;
                    elementoVenta[i][3] = cantidad_ingresada;

                    break;

                }
            }

            System.out.println("El array elementoVenta con la nueva venta es: ");

            for (int i = 0; i < elementoVenta.length; i++) {

                if (elementoVenta[i][0] != null) {
                    System.out.println("El id de la venta es: " + elementoVenta[i][0].toString() + " NIT cliente: " + elementoVenta[i][1]
                            + " Producto: " + elementoVenta[i][2] + " Cantidad: " + elementoVenta[i][3]);
                }

            }

            //----------- Restando la cantidad de productos Disponibles -------------------------
            for (int i = 0; i < elementoProducto.length; i++) {

                if (elementoProducto[i][0] != null && (elementoProducto[i][0].toString()).equals(producto_seleccionado)) {

                    //elementoProducto[i][2] = Integer.parseInt(elementoProducto[i][2].toString()) - cantidad_ingresada;
                    System.out.println("\nProducto: " + elementoProducto[i][0] + " Cantidad: " + elementoProducto[i][2]);

                    JOptionPane.showMessageDialog(null, "productos en existencia " + elementoProducto[i][2]);
                }

            }

        }

    }

}
