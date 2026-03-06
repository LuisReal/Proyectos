package proyecto1;

import javax.swing.*;
import java.awt.event.*;

public class VentanaVentas extends JFrame {

    private LaminaVentanaVentas lamina_ventas;

    public VentanaVentas(Object[][] elementoProducto) {

        setTitle("Administracion de Ventas");
        setBounds(200, 200, 400, 400);
        setResizable(false);

        lamina_ventas = new LaminaVentanaVentas(elementoProducto);
        add(lamina_ventas);
    }

    public Object[][] getElementoVentas() {
        return lamina_ventas.getElementoVentas();
    }

    public String[][] getArregloProductos() {
        return lamina_ventas.getArregloProductos();
    }

}

class LaminaVentanaVentas extends JPanel {

    JButton dashboard;
    JButton cargaVentas;
    JButton creacion_ventas;
    JButton consulta;

    public static Object[][] elementoVenta;
    public static Object[][] elementoProducto;
    private DashboardVentas dash2;
    public static ImageIcon icono;
    //public String[][] arregloProductos;
    
    private static String[] producto_unico2 = new String[200]; //contiene un arreglo de los productos SIN repetirse
    private static int[] cantidad_producto_unico = new int[200]; //contiene un arreglo de los productos SIN repetirse
    public static String[][] arreglo_productos = new String[200][2]; // contiene los productos y las cantidades SIN repetirse de mayor a menor
    
    private static int mayor, menor;
    private static double total_venta;
    private static double[] array_total_ventas;
    private static double total_venta_mayor;
    private static int posicion_mayor2;
    private static String[][] productos_mayor_cantidad; //es importante usar static (si se sale de la ventana(para ver otra ventana como reportes) 
    //los valores siempre quedan guardados y si se vuelve a regresar podran usarse y no sera necesario volver a usar el boton CargarVentas)
    private static String[][] solo_productos_mayores; // es importante usar static (si se sale de la ventana(para ver otra ventana como reportes) 
    //los valores siempre quedan guardados y si se vuelve a regresar podran usarse y no sera necesario volver a usar el boton CargarVentas)

    public LaminaVentanaVentas(Object[][] elementoProducto) {
        LaminaVentanaVentas.elementoProducto = elementoProducto;

        setLayout(null);

        cargaVentas = new JButton("Cargar Ventas");
        cargaVentas.setBounds(100, 50, 200, 30);
        add(cargaVentas);

        dashboard = new JButton("Dashboard Ventas");
        dashboard.setBounds(100, 100, 200, 30);
        add(dashboard);

        creacion_ventas = new JButton("Creacion Ventas");
        creacion_ventas.setBounds(100, 150, 200, 30);
        add(creacion_ventas);

        consulta = new JButton("Consulta Ventas");
        consulta.setBounds(100, 200, 200, 30);
        add(consulta);

        CargarVentas carga = new CargarVentas();
        cargaVentas.addActionListener(carga);

        DashboardVentasOyente dashventas = new DashboardVentasOyente();
        dashboard.addActionListener(dashventas);

        CrearVentas nuevav = new CrearVentas();
        creacion_ventas.addActionListener(nuevav);

        ConsultarVentas consultar = new ConsultarVentas();
        consulta.addActionListener(consultar);

    }

    public Object[][] getElementoVentas() {
        return elementoVenta;
    }

    public String[][] getArregloProductos() {
        return arreglo_productos;
    }

    private class CargarVentas implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            CargaMasivaVentas carga = new CargaMasivaVentas();
            elementoVenta = carga.elementoVenta;

            int k = 0;

            int cantidad_producto1;
            int cantidad_producto2;

            
            //System.out.println("El tamano de elementoVenta es: "+ elementoVenta.length);

            String[] producto_unico = new String[200];

            int producto_repetido = 0;

            for (int j = 0; j < elementoVenta.length; j++) { //el tamaño de elementoVenta es 200

                if (elementoVenta[j][2] != null) { //el archivo de prueba csv contiene 142 valores, por eso se hace esta validacion

                    String producto1 = elementoVenta[j][2].toString();

                    for (int a = 0; a < producto_unico.length; a++) { //valida si ya se analizo el producto para que no se repita y no se vuelva a sumar

                        if (producto_unico[a] != null && producto_unico[a].equals(producto1)) {
                            producto_repetido++;
                        }
                    }

                    if (producto_repetido > 0) {
                        producto_repetido = 0;
                        continue;
                    }

                    cantidad_producto1 = Integer.parseInt(elementoVenta[j][3].toString());

                    for (int m = j + 1; m < elementoVenta.length; m++) {

                        if (elementoVenta[m][2] != null) {

                            String producto2 = elementoVenta[m][2].toString();

                            if (producto1.equals(producto2)) {

                                producto_unico[j] = producto1;

                                cantidad_producto2 = Integer.parseInt(elementoVenta[m][3].toString());

                                //System.out.println("El nombre de producto1 es: "+producto1+" En la posicion: "+j+ " y el de producto2 es: "+producto2+" En la posicion: "+m);
                                cantidad_producto1 += cantidad_producto2;

                                //System.out.println("La cantidad_producto1 es: "+cantidad_producto1);
                            }

                        }
                    }

                    cantidad_producto_unico[j] = cantidad_producto1; //contiene un arreglo de la cantidad de los productos SIN repetirse
                    producto_unico2[j] = producto1;  //contiene un arreglo de los productos SIN repetirse

                    k++;

                }
            }

            int cont = 0;
            //IMPRIMIENDO EL ARREGLO CANTIDAD_PRODUCTO_UNICO
            for (int i = 0; i < cantidad_producto_unico.length; i++) {

                if (cantidad_producto_unico[i] != 0) {

                    arreglo_productos[cont][0] = producto_unico2[i];
                    arreglo_productos[cont][1] = String.valueOf(cantidad_producto_unico[i]);
                    cont++;

                    //System.out.println("\nEl productos es: " + producto_unico2[i]);
                    //System.out.println("La cantidad es: " + cantidad_producto_unico[i]);
                }

            }

            for (int i = 0; i < arreglo_productos.length - 1; i++) {

                for (int j = 0; j < arreglo_productos.length - 1 - i; j++) {

                    if (arreglo_productos[j + 1][1] != null) {

                        if (Integer.parseInt(arreglo_productos[j][1]) < Integer.parseInt(arreglo_productos[j + 1][1])) {

                            int temp = Integer.parseInt(arreglo_productos[j][1]);
                            String temp2 = arreglo_productos[j][0];
                            arreglo_productos[j][1] = arreglo_productos[j + 1][1];
                            arreglo_productos[j][0] = arreglo_productos[j + 1][0];
                            arreglo_productos[j + 1][1] = String.valueOf(temp);
                            arreglo_productos[j + 1][0] = temp2;

                        }
                    }

                }
            }

            //IMPRIMIENDO EL ARREGLO CANTIDAD_PRODUCTO_UNICO
            for (int i = 0; i < arreglo_productos.length; i++) {

                if (arreglo_productos[i][0] != null) {

                    System.out.println("\nEl productos es: " + arreglo_productos[i][0]);
                    System.out.println("La cantidad es: " + arreglo_productos[i][1]);
                }

            }

            

            mayor = menor = cantidad_producto_unico[0];
            int total_cantidades = 0;
            int posicion_mayor = 0;
            int posicion_menor = 0;

            for (int i = 0; i < cantidad_producto_unico.length; i++) {

                if (cantidad_producto_unico[i] != 0) {

                    total_cantidades += cantidad_producto_unico[i];

                    if (cantidad_producto_unico[i] > mayor) {

                        mayor = cantidad_producto_unico[i];
                        posicion_mayor = i;
                    }

                    if (cantidad_producto_unico[i] < menor) {

                        menor = cantidad_producto_unico[i];
                        posicion_menor = i;
                    }

                }
            }

            System.out.println("\nLa cantidad mayor es " + mayor + " y el producto es: " + producto_unico2[posicion_mayor]);   //P13 Y P7 son los que mayor cantidad tienen con 16 cada uno
            System.out.println("La cantidad menor es " + menor + " y el producto es: " + producto_unico2[posicion_menor]);
            System.out.println("El total de cantidades es " + total_cantidades);

            
            productos_mayor_cantidad = new String[200][4];
            solo_productos_mayores = new String[200][2];
            
            int rows = 0;
            int rows2 = 0;

            for (int i = 0; i < producto_unico2.length; i++) {   //producto_unico2 contiene un arreglo de los productos SIN repetirse igual que cantidad_producto_unico

                if (producto_unico2[i] != null && cantidad_producto_unico[i] == mayor) { //cantidad_producto_unico contiene las cantidades de productos SIN repetirse

                    solo_productos_mayores[rows2][0] = producto_unico2[i];
                    solo_productos_mayores[rows2][1] = String.valueOf(cantidad_producto_unico[i]);
                    //System.out.println("\nOBTENIENDO Y ANALIZANDO SEGUNDO RESULTAD0");
                    //System.out.println("El producto es: " + solo_productos_mayores[i][0] + " y la cantidad total es: " + solo_productos_mayores[i][1]);
                    rows2++;

                    for (int j = 0; j < elementoVenta.length; j++) {

                        if (elementoVenta[j][2] != null) {

                            if (producto_unico2[i].equals(elementoVenta[j][2].toString())) {

                                for (int m = 0; m < 4; m++) {
                                    productos_mayor_cantidad[rows][m] = elementoVenta[j][m].toString();
                                }

                                rows++;
                            }
                        }

                    }

                }

            }

            // -------------------------  Calculando el valor total de las ventas ------------------------------------------
            
            array_total_ventas = new double[200];

            for (int i = 0; i < producto_unico2.length; i++) {   //producto_unico2 contiene un arreglo de los productos SIN repetirse igual que cantidad_producto_unico

                if (producto_unico2[i] != null) {

                    for (int j = 0; j < elementoProducto.length; j++) {

                        if (elementoProducto[j][0] != null) {

                            if (producto_unico2[i].equals(elementoProducto[j][0].toString())) {

                                double precio = Double.parseDouble(elementoProducto[j][1].toString());
                                double total = Double.valueOf(cantidad_producto_unico[i]) * precio;
                                array_total_ventas[i] = Double.valueOf(cantidad_producto_unico[i]) * precio;

                                total_venta += total;
                            }

                        }

                    }

                }

            }

            total_venta = Math.round(total_venta * 100.0) / 100.0;

            System.out.println("\n -------------------EL TOTAL DE VENTAS ES: " + total_venta + " ----------------------");

            // -------------------------- FIN calculo del valor total de las ventas -----------------------------------------
// -------------------------- INICIO calculo del total del producto mas vendido  -----------------------------------------
            total_venta_mayor = array_total_ventas[0];

            

            for (int i = 0; i < array_total_ventas.length; i++) {

                if (array_total_ventas[i] != 0) {

                    if (array_total_ventas[i] > total_venta_mayor) {

                        total_venta_mayor = array_total_ventas[i];
                        posicion_mayor2 = i;
                    }

                }
            }

            System.out.println("\nLa cantidad VENTA mayor es " + total_venta_mayor + " y el producto es: " + producto_unico2[posicion_mayor2]);
        }

    }

    private class DashboardVentasOyente implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            if (elementoVenta == null) {

                JOptionPane.showMessageDialog(null, "No ha cargado los datos todavia");

            } else {
                dash2 = new DashboardVentas(elementoVenta, elementoProducto, producto_unico2, mayor,total_venta,total_venta_mayor,posicion_mayor2,productos_mayor_cantidad,solo_productos_mayores);    
                dash2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                dash2.setVisible(true);

            }

        }

    }

    private class CrearVentas implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            //Object[][] elementoVenta, String[] producto_unico
            if (dash2 != null) {
                CrearVenta nueva = new CrearVenta(elementoVenta, elementoProducto, producto_unico2, cantidad_producto_unico);
                nueva.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                nueva.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Tiene que dar click en dashboard primero");
            }

        }
    }

    private class ConsultarVentas implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent w) {

            if (dash2 != null) {
                ConsultarVenta miConsulta = new ConsultarVenta();
                miConsulta.setElementos(elementoVenta, elementoProducto);
                miConsulta.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                miConsulta.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Tiene que dar click en dashboard primero");
            }
        }

    }
}
