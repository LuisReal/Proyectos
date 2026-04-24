
package proyecto1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import org.jfree.chart.ChartPanel;


/**
 *
 * @author Fernando
 */
public class PanelVentas extends JPanel{
    
    JButton dashboard;
    JButton cargaVentas;
    JButton creacion_ventas;
    JButton consulta;
    
    public static Object[][] elementoVenta;
    public Object[][] elementoProducto;
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
    
    
    JScrollPane scrollTable1;
    JScrollPane scrollTable2;
    JScrollPane scrollTable3;
    ChartPanel Pie;
    ChartPanel barras;

    public String[] columnas = {"Codigo Venta", "NIT cliente", "Producto", "Cantidad"}; // para usar en la tabla
    public String[] columnas2 = {"Producto", 
        "Cantidad Total"}; // para usar en la tabla
    
    
    JLabel titulo;
    GridBagConstraints gbc_titulo;
    GridBagConstraints gbc; //esto se usa para poder posicionar los elementos dentro del panel en una posicion especifica
    
    private JPanel panelArriba;
    private JPanel panelAbajo;
    
    JLabel cantidad_mayor;
    JLabel valor_mayor;
    JLabel etiqueta_total_venta;
    JLabel valor_total_venta;
    JLabel cantidad_venta_mayor;
    JLabel valor_venta_mayor;
    JLabel etiqueta_venta_mayor;
    JLabel total_producto_mayor;
    
    public PanelVentas(){
        
        elementoVenta = new Object[200][4];
        
        
        setLayout(new GridBagLayout()); 
        
        panelArriba = new JPanel(new GridBagLayout());// se usa GridBagLayout para poder controlar los componentes y poder ocupar
        //cierta cantidad de columnas como el titulo que ocupa 5 columnas del layout (columnspan)
        
        panelAbajo = new JPanel(new GridBagLayout()); //se usa GridBagLayout para poder controlar los componentes
        
        
        GridBagConstraints gbcMain = new GridBagConstraints();
        gbcMain.fill = GridBagConstraints.BOTH;
        gbcMain.gridx = 0; // columna 0
        gbcMain.weightx = 1; // ocupa el 100% horizontalmente
        
        //10% de altura para el panel arriba
        gbcMain.gridy = 0; //fila 0
        gbcMain.weighty = 0.1; // ocupa el 10% verticalmente
        add(panelArriba, gbcMain);
        
        //90% de altura para el panel abajo
        gbcMain.gridy = 1;     // fila 1
        gbcMain.weighty = 0.9; // ocupal el 90% verticalmente
        add(panelAbajo, gbcMain);
        //********************************************PANEL ARRIBA****************************************************************
        
        // Fila 1: título (ocupa 5 columnas)
        titulo = new JLabel("ADMINISTRACION DE VENTAS");
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);//centra el titulo
        
        gbc_titulo = new GridBagConstraints(); //usado para configurar y posicionar solo el titulo
        gbc_titulo.fill = GridBagConstraints.HORIZONTAL;
        gbc_titulo.gridx = 0; //posicion en columna =0
        gbc_titulo.gridy = 0; //posicion en fila=0
        gbc_titulo.gridwidth = 4; //ocupa las 4 columnas (columnspan)
        /*weighty distribuye la altura del componente dentro del panel*/
        gbc_titulo.weightx = 1;
        gbc_titulo.weighty = 0.05; // este titulo ocupara el 5% de la altura del panel arriba
        panelArriba.add(titulo, gbc_titulo);
        
        
        gbc = new GridBagConstraints(); //usado para configurar y posicionar los botones
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1; //adapta el ancho al 100% del ancho del panel
        gbc.gridy = 1; //posicion en fila 1
        gbc.gridwidth = 1;
        gbc.weighty = 0.05; //los botones ocuparan el 5% de la altura del panel arriba
        
        
        cargaVentas = new JButton("Cargar Ventas");
        cargaVentas.setBackground(new Color(173, 216, 150));
        cargaVentas.setForeground(Color.WHITE);
        cargaVentas.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;  // posiciona el boton en columna 0
        panelArriba.add(cargaVentas, gbc);
        
        CargarVentas carga = new CargarVentas();
        cargaVentas.addActionListener(carga);

        dashboard = new JButton("Dashboard Ventas");
        dashboard.setBackground(new Color(173, 216, 150));
        dashboard.setForeground(Color.WHITE);
        dashboard.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 1; // posiciona el boton en columna 1
        panelArriba.add(dashboard, gbc);
        
        DashboardVentas dashventas = new DashboardVentas();
        dashboard.addActionListener(dashventas);
        

        creacion_ventas = new JButton("Creacion Ventas");
        creacion_ventas.setBackground(new Color(173, 216, 150));
        creacion_ventas.setForeground(Color.WHITE);
        creacion_ventas.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 2; // posiciona el boton en columna 2
        panelArriba.add(creacion_ventas, gbc);
        
        CrearVentas nuevav = new CrearVentas();
        creacion_ventas.addActionListener(nuevav);
        

        consulta = new JButton("Consulta Ventas");
        consulta.setBackground(new Color(173, 216, 150));
        consulta.setForeground(Color.WHITE);
        consulta.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 3; // posiciona el boton en columna 3
        panelArriba.add(consulta, gbc); 

        ConsultarVentas consultar = new ConsultarVentas();
        consulta.addActionListener(consultar);
        
        
        //********************************************PANEL ABAJO****************************************************************
        
        gbc.gridy = 1; //posicion en fila 1 para los siguientes componentes
        gbc.gridwidth = 1; // ocupa una sola columna a la vez
        gbc.weighty = 0.1; //ocupa el 10% de la altura del panel abajo
        
        cantidad_mayor = new JLabel("Cantidad Vendida Mayor: ");
        cantidad_mayor.setOpaque(true); // esto habilita el color de fondo de setBackground de la siguiente linea
        cantidad_mayor.setBackground(new Color(173, 216, 230));
        cantidad_mayor.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0; //posicion en columna 1
        panelAbajo.add(cantidad_mayor,gbc);

        valor_mayor = new JLabel("00");
        valor_mayor.setOpaque(true); // esto habilita el color de fondo de setBackground de la siguiente linea
        valor_mayor.setBackground(new Color(173, 216, 230));
        valor_mayor.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 1; //posicion en columna 2
        panelAbajo.add(valor_mayor,gbc);

        etiqueta_total_venta = new JLabel("Total ventas: ");
        etiqueta_total_venta.setOpaque(true); // esto habilita el color de fondo de setBackground de la siguiente linea
        etiqueta_total_venta.setBackground(new Color(173, 216, 230));
        etiqueta_total_venta.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 2;
        panelAbajo.add(etiqueta_total_venta,gbc);

        valor_total_venta = new JLabel("00");
        valor_total_venta.setOpaque(true); // esto habilita el color de fondo de setBackground de la siguiente linea
        valor_total_venta.setBackground(new Color(173, 216, 230));
        valor_total_venta.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 3;
        panelAbajo.add(valor_total_venta,gbc);
        
        gbc.gridy = 2; //posicion en fila 2 para las etiquetas cantidad_venta_mayor, valor_venta_mayor
        
        
        cantidad_venta_mayor = new JLabel("Total Venta Mayor: ");
        cantidad_venta_mayor.setOpaque(true); // esto habilita el color de fondo de setBackground de la siguiente linea
        cantidad_venta_mayor.setBackground(new Color(173, 216, 230));
        cantidad_venta_mayor.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        panelAbajo.add(cantidad_venta_mayor,gbc);

        valor_venta_mayor = new JLabel("00");
        valor_venta_mayor.setOpaque(true); // esto habilita el color de fondo de setBackground de la siguiente linea
        valor_venta_mayor.setBackground(new Color(173, 216, 230));
        valor_venta_mayor.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 1;
        panelAbajo.add(valor_venta_mayor,gbc);

        // ------ INICIO Total Producto Mayor Venta --------------------
        etiqueta_venta_mayor = new JLabel("Producto de Mayor Venta: ");
        etiqueta_venta_mayor.setOpaque(true); // esto habilita el color de fondo de setBackground de la siguiente linea
        etiqueta_venta_mayor.setBackground(new Color(173, 216, 230));
        etiqueta_venta_mayor.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 2;
        panelAbajo.add(etiqueta_venta_mayor,gbc);

        total_producto_mayor = new JLabel("00");
        total_producto_mayor.setOpaque(true); // esto habilita el color de fondo de setBackground de la siguiente linea
        total_producto_mayor.setBackground(new Color(173, 216, 230));
        total_producto_mayor.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 3;
        panelAbajo.add(total_producto_mayor,gbc);
        // ------ FIN Total Producto Mayor Venta --------------------
        
        
        
        
        
    }
    
    public void setLabelsData(){
        
        valor_mayor.setText(String.valueOf(mayor));
        valor_total_venta.setText(String.valueOf(total_venta));
        valor_venta_mayor.setText(String.valueOf(total_venta_mayor));
        total_producto_mayor.setText(String.valueOf(producto_unico2[posicion_mayor2]));
    }
    
    public void setTableSalesData(){
        
        JTable table = new JTable(elementoVenta, columnas);
        table.setRowHeight(30);

        scrollTable1 = new JScrollPane(table);
        gbc.gridx = 0; //posicion en columna 0
        gbc.gridy = 0; //posicion en fila 0
        gbc.gridwidth = 5; //ocupa las 5 columna(columnspan) 
        gbc.weighty = 0.4; //ocupa el 40% de la altura del panel abajo

        panelAbajo.add(scrollTable1,gbc);
        
        gbc.gridy = 3; //posicion en fila 3 para los siguientes componentes
        
        JLabel titulo_table2 = new JLabel("DETALLE DE LA VENTA MAS GRANDE SEGUN CANTIDAD PRODUCTOS VENDIDOS");
        titulo_table2.setHorizontalAlignment(SwingConstants.CENTER);//centra el titulo
        titulo_table2.setOpaque(true); // esto habilita el color de fondo de setBackground de la siguiente linea
        titulo_table2.setBackground(new Color(255, 102, 102));
        gbc.gridx = 0;
        gbc.weighty = 0.1; //ocupa el 10% de la altura del panel abajo
        panelAbajo.add(titulo_table2,gbc);
        
        
        JTable table2 = new JTable(productos_mayor_cantidad, columnas);
        table2.setRowHeight(30);
        scrollTable2 = new JScrollPane(table2);
        gbc.gridx = 0; //posicion en columna 0
        gbc.gridy = 4; //posicion en fila 4 para esta tabla
        gbc.gridwidth = 5; //ocupa las 5 columna(columnspan) 
        gbc.weighty = 0.2; //ocupa el 20% de la altura del panel abajo
        panelAbajo.add(scrollTable2,gbc);

        JLabel titulo_table3 = new JLabel("PRODUCTOS MAS VENDIDOS");
        

        JTable table3 = new JTable(solo_productos_mayores, columnas2);
        table3.setRowHeight(30);
        scrollTable3 = new JScrollPane(table3);
        gbc.gridx = 0; //posicion en columna 0
        gbc.gridy = 5; //posicion en fila 5 para esta tabla
        gbc.gridwidth = 5; //ocupa las 5 columna(columnspan) 
        gbc.weighty = 0.2; //ocupa el 20% de la altura del panel abajo
        panelAbajo.add(scrollTable3,gbc);
    }
    
    public void updateSalesData(){
        
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
        
        if(this.elementoProducto[0][0] == null){
            System.out.println("***********************EL ARRAY elementoProducto ESTA VACIO********************************");
        }else{
            System.out.println("***********************EL ARRAY elementoProducto NOOO ESTA VACIO********************************");
        }
        
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

        setTableSalesData(); // Metodo que crea las tablas
    }
    
    public void uploadSalesData(){
        
        String[] campos;
        int filas = 0;
        try {
            // Abrir el .csv en buffer de lectura
            InputStream is = getClass().getResourceAsStream("/ArchivosCarga/ventas.csv");
            BufferedReader bufferLectura = new BufferedReader(new InputStreamReader(is));

            String linea;

            while ((linea = bufferLectura.readLine()) != null) {

                //texto = texto + linea;
                campos = linea.split(","); // guarda la informacion en un arreglo de tipo String
                //System.out.println("El tamano de camposVenta es: " + camposVenta.length);
                for (int j = 0; j < campos.length; j++) {

                    elementoVenta[filas][j] = campos[j];

                    //System.out.println("El elemento Venta es: "+elementoVenta[i][j]);
                }
                filas++;// para poder llevar el conteo de las filas
            }
            
        } catch (IOException e) {
            System.out.println("El archivo no se ha encontrado");
        }
        
        
        
        String[] cadena = new String[500];

        for (int j = 0; j < elementoVenta.length; j++) {

            if ((elementoVenta[j][2]) != null) {

                cadena[j] = String.valueOf(elementoVenta[j][2]); // guarda los nombres de los productos

                // System.out.println("La cadena Nombre de Venta en la posicion " + j + " = " + cadena[j]);
            }

        }

        
        
         
        System.out.println("Si se puede cargar el archivo");
        
        //**************************************************************************************************************************
                
        
    }
    
    public void setProductos(Object[][] productos) {
        this.elementoProducto = productos;
    }
    
    private class CargarVentas implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            CargaMasivaVentas carga = new CargaMasivaVentas();
            elementoVenta = carga.elementoVenta;

        }

    }
    
    private class DashboardVentas implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            if (elementoVenta == null) {

                JOptionPane.showMessageDialog(null, "No ha cargado los datos todavia");

            } else {
                
                updateSalesData();
            }

        }

    }
    
    private class CrearVentas implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            //Object[][] elementoVenta, String[] producto_unico
            
            CrearVenta nueva = new CrearVenta(elementoVenta, elementoProducto, producto_unico2, cantidad_producto_unico);
            nueva.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            nueva.setVisible(true);
            

        }
    }

    private class ConsultarVentas implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent w) {

            
            ConsultarVenta miConsulta = new ConsultarVenta();
            miConsulta.setElementos(elementoVenta, elementoProducto);
            miConsulta.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            miConsulta.setVisible(true);

        }

    }
}
