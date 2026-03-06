package proyecto1;

import javax.swing.*;
import org.jfree.chart.ChartPanel;

public class DashboardVentas extends JFrame {

    private LaminaDashboardVentas lamina_ventas;

    public DashboardVentas(Object[][] elementoVenta, Object[][] elementoProducto,String[] producto_unico2, int mayor, double total_venta, double total_venta_mayor, int posicion_mayor2,String[][] productos_mayor_cantidad,String[][] solo_productos_mayores) {

        setTitle("Graficas Ventas");
        setBounds(150, 50, 1050, 670);

        lamina_ventas = new LaminaDashboardVentas(elementoVenta, elementoProducto, producto_unico2, mayor,total_venta,total_venta_mayor,posicion_mayor2,productos_mayor_cantidad,solo_productos_mayores);
        add(lamina_ventas);

    }
    
    /*
    public int[] getArregloCantidadProductos() {
        return lamina_ventas.getArregloCantidadProductos();
    }
    
    public String[][] getArregloProductos() {
        return lamina_ventas.getArregloProductos();
    }*/
}

class LaminaDashboardVentas extends JPanel {

    JScrollPane scroll;
    ChartPanel Pie;
    ChartPanel barras;

    public String[] columnas = {"Codigo Venta", "NIT cliente", "Producto", "Cantidad"}; // para usar en la tabla
    public String[] columnas2 = {"Producto", "Cantidad Total"}; // para usar en la tabla
  

    public LaminaDashboardVentas(Object[][] elementoVenta, Object[][] elementoProducto,String[] producto_unico2, int mayor, double total_venta, double total_venta_mayor, int posicion_mayor2,String[][] productos_mayor_cantidad,String[][] solo_productos_mayores) {
        
        
        
        setLayout(null);//para poder controlar la posicion de los elementos manualmente

        JLabel etiqueta = new JLabel("DATOS VENTAS");
        etiqueta.setBounds(400, 20, 200, 30);
        add(etiqueta);

        JTable table = new JTable(elementoVenta, columnas);

        table.setRowHeight(30);

        scroll = new JScrollPane(table);
        scroll.setBounds(200, 80, 500, 100);// modifica el tamano de la tabla junto con el scroll
        add(scroll);

        

        // -------------------------- FIN calculo del total del producto mas vendido  -----------------------------------------
        
        JLabel cantidad_mayor = new JLabel("Cantidad Vendida Mayor: ");
        cantidad_mayor.setBounds(100, 550, 200, 30);
        add(cantidad_mayor);

        JLabel valor_mayor = new JLabel(String.valueOf(mayor));
        valor_mayor.setBounds(300, 550, 50, 30);
        add(valor_mayor);

        JLabel etiqueta_total_venta = new JLabel("Total ventas: ");
        etiqueta_total_venta.setBounds(350, 550, 100, 30);
        add(etiqueta_total_venta);

        JLabel valor_total_venta = new JLabel(String.valueOf(total_venta));
        valor_total_venta.setBounds(450, 550, 200, 30);
        add(valor_total_venta);

        JLabel cantidad_venta_mayor = new JLabel("Total Venta Mayor: ");
        cantidad_venta_mayor.setBounds(550, 550, 200, 30);
        add(cantidad_venta_mayor);

        JLabel valor_venta_mayor = new JLabel(String.valueOf(total_venta_mayor));
        valor_venta_mayor.setBounds(670, 550, 50, 30);
        add(valor_venta_mayor);

        // ------ INICIO Total Producto Mayor Venta --------------------
        JLabel etiqueta_venta_mayor = new JLabel("Producto de Mayor Venta: ");
        etiqueta_venta_mayor.setBounds(770, 550, 200, 30);
        add(etiqueta_venta_mayor);

        JLabel total_producto_mayor = new JLabel(String.valueOf(producto_unico2[posicion_mayor2]));
        total_producto_mayor.setBounds(970, 550, 50, 30);
        add(total_producto_mayor);
        // ------ FIN Total Producto Mayor Venta --------------------

        JLabel titulo_table2 = new JLabel("DETALLE DE LA VENTA MAS GRANDE SEGUN CANTIDAD PRODUCTOS VENDIDOS");
        titulo_table2.setBounds(200, 200, 500, 30);
        add(titulo_table2);

        JTable table2 = new JTable(productos_mayor_cantidad, columnas);

        table2.setRowHeight(30);

        scroll = new JScrollPane(table2);
        scroll.setBounds(200, 250, 500, 100);// modifica el tamano de la tabla junto con el scroll
        add(scroll);

        JLabel titulo_table3 = new JLabel("PRODUCTOS MAS VENDIDOS");
        titulo_table3.setBounds(350, 400, 200, 30);
        add(titulo_table3);

        JTable table3 = new JTable(solo_productos_mayores, columnas2);

        table3.setRowHeight(30);

        scroll = new JScrollPane(table3);
        scroll.setBounds(200, 450, 500, 100);// modifica el tamano de la tabla junto con el scroll
        add(scroll);

    }// fin constructor LaminaDashboardVentas
   
    
    /*
    public String[] getArregloProductoUnico() {

        // ------------- ORDENANDO el arreglo producto_unico2 y el arreglo  cantidad_producto_unico --------------------------
        return producto_unico2;
    }

    public int[] getArregloCantidadProductos() {
        return cantidad_producto_unico;
    }
    
    public String[][] getArregloProductos() {
        return arreglo_productos;
    }*/
}
