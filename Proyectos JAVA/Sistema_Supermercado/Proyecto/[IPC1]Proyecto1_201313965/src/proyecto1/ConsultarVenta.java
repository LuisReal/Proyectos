package proyecto1;

import javax.swing.*;
import java.awt.event.*;

public class ConsultarVenta extends JFrame {

    JLabel consulta;
    JTextField campo_consulta;
    JButton buscar;

    public static Object[][] elementoVenta = new Object[200][4];
    public static Object[][] elementoProducto = new Object[200][4];

    public ConsultarVenta() {

        setLayout(null);
        setResizable(false);
        setTitle("Consulta Venta");
        setBounds(300, 200, 350, 220);

        consulta = new JLabel("Ingrese ID venta");
        consulta.setBounds(60, 20, 200, 30);
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

    public void setElementos(Object[][] elementoVenta, Object[][] elementoProducto) {

        ConsultarVenta.elementoVenta = elementoVenta; // proviene de la clase VentanaVentas
        ConsultarVenta.elementoProducto = elementoProducto; // proviene de la clase VentanaVentas

    }

    private class Buscar implements ActionListener {

        public void actionPerformed(ActionEvent w) {

            int contador = 0;
            String idVenta = "";
            for (int i = 0; i < elementoVenta.length; i++) {

                if (elementoVenta[i][0] != null && (campo_consulta.getText()).equals(elementoVenta[i][0].toString())) {
                    System.out.println("El ID del elementoVenta es: " + elementoVenta[i][0] + " Producto: " + elementoVenta[i][2] + " Cantidad: " + elementoVenta[i][3]);
                    idVenta = campo_consulta.getText();

                    contador++;
                }
            }

            if (contador == 0) {

                JOptionPane.showMessageDialog(null, "El ID no existe");
            } else {
                DatosVentas mostrar = new DatosVentas(elementoVenta, elementoProducto, idVenta);
                mostrar.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                mostrar.setVisible(true);
            }

        } // find metodo actionPerformed

    }// fin private class Buscar

}

class DatosVentas extends JFrame {

    public DatosVentas(Object[][] elementoVenta, Object[][] elementoProducto, String idVenta) {

        setTitle("Datos de la Venta");
        setResizable(false);
        setBounds(300, 200, 400, 500);

        LaminaDatosVentas laminaDatos = new LaminaDatosVentas(elementoVenta, elementoProducto, idVenta);
        add(laminaDatos);

    }

}

class LaminaDatosVentas extends JPanel {

    JLabel etiqueta_nit_cliente;
    JLabel etiqueta_productos_venta;
    JLabel etiqueta_total_venta;

    JLabel mostrar_nombre_producto;
    JLabel mostrar_nit_cliente;
    JLabel mostrar_cantidad_producto;
    JLabel mostrar_total_venta;

    JButton eliminar;

    public static Object[][] elementoVenta = new Object[200][4];
    public static Object[][] elementoProducto = new Object[200][4];

    String[] cadenaNombresProductos = new String[200];
    String nitCliente = "";

    String idVenta = "";

    public LaminaDatosVentas(Object[][] elementoVenta, Object[][] elementoProducto, String idVenta) {

        setLayout(null);

        this.elementoVenta = elementoVenta;
        this.elementoProducto = elementoProducto;

        this.idVenta = idVenta;

        etiqueta_nit_cliente = new JLabel("NIT cliente ");
        etiqueta_nit_cliente.setBounds(30, 20, 100, 30);
        add(etiqueta_nit_cliente);

        etiqueta_productos_venta = new JLabel("Productos de la venta ");
        etiqueta_productos_venta.setBounds(30, 70, 150, 30);
        add(etiqueta_productos_venta);

        etiqueta_total_venta = new JLabel("Total de la venta: ");
        etiqueta_total_venta.setBounds(30, 300, 150, 30);
        add(etiqueta_total_venta);

        eliminar = new JButton("Eliminar");
        eliminar.setBounds(150, 390, 100, 30);
        add(eliminar);

        Eliminar elimina = new Eliminar();
        eliminar.addActionListener(elimina);

        for (int j = 0; j < elementoVenta.length; j++) {

            if (elementoVenta[j][0] != null && idVenta.equals(elementoVenta[j][0].toString())) {

                nitCliente = String.valueOf(elementoVenta[j][1]);
                cadenaNombresProductos[j] = String.valueOf(elementoVenta[j][2]);
                //cadenaCantidad[j] = String.valueOf(elementoVenta[j][2]);

            }

        } // fin del for

        mostrar_nit_cliente = new JLabel(nitCliente);
        mostrar_nit_cliente.setBounds(180, 20, 100, 30);
        add(mostrar_nit_cliente);

        mostrar_nombre_producto = new JLabel("Producto");
        mostrar_nombre_producto.setBounds(50, 100, 100, 30);
        add(mostrar_nombre_producto);
        
        mostrar_nombre_producto = new JLabel("Cantidad");
        mostrar_nombre_producto.setBounds(150, 100, 100, 30);
        add(mostrar_nombre_producto);
        
        mostrar_nombre_producto = new JLabel("Precio");
        mostrar_nombre_producto.setBounds(250, 100, 100, 30);
        add(mostrar_nombre_producto);
        
        int distanciaX = 50;
        int distanciaY = 120;

        double total_venta = 0;

        for (int i = 0; i < elementoVenta.length; i++) {

            if (elementoVenta[i][0] != null && idVenta.equals(elementoVenta[i][0].toString())) {
                System.out.println("El ID de la venta es: " + idVenta);

                for (int j = 0; j < elementoProducto.length; j++) {

                    if (elementoProducto[j][0] != null && (elementoVenta[i][2]).equals(elementoProducto[j][0])) { //si el nombre del producto coincide
                        System.out.println("El Producto es: " + elementoVenta[i][2]);
                        
                        mostrar_nombre_producto = new JLabel(elementoVenta[i][2].toString());
                        mostrar_nombre_producto.setBounds(distanciaX, distanciaY, 100, 30);
                        add(mostrar_nombre_producto);
                        distanciaX += 100;
                         
                        double cantidad_productos = Double.parseDouble(elementoVenta[i][3].toString()); // obtiene cantidad de productos vendidos
                        System.out.println("La cantidad de productos es: " + cantidad_productos);

                        mostrar_nombre_producto = new JLabel(elementoVenta[i][3].toString());
                        mostrar_nombre_producto.setBounds(distanciaX, distanciaY, 100, 30);
                        add(mostrar_nombre_producto);
                        distanciaX += 100;
                        
                        double precio = Double.parseDouble(elementoProducto[j][1].toString()); // obtiene el precio del producto
                        System.out.println("El precio es: " + precio);
                        
                        mostrar_nombre_producto = new JLabel(elementoProducto[j][1].toString());
                        mostrar_nombre_producto.setBounds(distanciaX, distanciaY, 100, 30);
                        add(mostrar_nombre_producto);
                        

                        distanciaX = 50;
                        distanciaY +=20;

                        double venta = precio * cantidad_productos;
                        total_venta += venta;

                        System.out.println("El total venta es: " + total_venta);
                    }
                }

            }

        } // fin del for
        mostrar_total_venta = new JLabel(String.valueOf(total_venta));
        mostrar_total_venta.setBounds(200, 300, 50, 30);
        add(mostrar_total_venta);

    }// fin constructor LaminaDatosProductos

    private class Eliminar implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent w) {

            mostrar_nombre_producto.setText("");
            mostrar_nit_cliente.setText("");
            //mostrar_cantidad_producto.setText("");

            int contador = 0;

            for (int i = 0; i < elementoVenta.length; i++) {

                if (elementoVenta[i][0] != null && idVenta.equals(elementoVenta[i][0])) { // busca el ID de la venta ingresado 

                    for (int j = 0; j < 4; j++) {

                        if ((elementoVenta[i][j]) != null) {

                            elementoVenta[i][j] = null;

                            //System.out.println("Eliminando dato de la venta " + i + " " + j + " " + elementoVenta[i][j]);
                            contador++;
                        }

                    }// fin del for int j=0
                }
            } // fin del for int i=0

            if (contador == 0) {
                JOptionPane.showMessageDialog(null, "El ID de la venta no existe");
            } else {
                JOptionPane.showMessageDialog(null, "Venta eliminada con exito");
            }
            /*
            for (int p = 0; p < elementoVenta.length; p++) {

                for (int r = 0; r < 4; r++) {

                    System.out.println("El dato de la venta es en la posicion " + p + " " + r + " = " + elementoVenta[p][r]);

                }

            }*/

        } // fin del metodo actionPerformed

    }// fin clase Eliminar

}
