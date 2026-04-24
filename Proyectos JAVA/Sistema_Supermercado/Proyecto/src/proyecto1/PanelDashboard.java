/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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
import java.text.DecimalFormat;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

/**
 *
 * @author Fernando
 */
class PanelDashboard extends JPanel{
    
    //public static Object[][] elementoCliente;
    public Object[][] elementoCliente;
    public Object[][] elementoProducto;
    private JPanel panelArriba;
    private JPanel panelAbajo;
    public String mujeres = "";
    public String hombres = "";
    
    
    JLabel titulo;
    GridBagConstraints gbc_titulo;
    
    JButton upload_button;
    JButton dashboard_button;
    JButton create_button;
    JButton search_button;
    JButton update_button;
    
    
    private ChartPanel Pie;
    private ChartPanel barras;
    private JScrollPane scrollTableClients;
    private JScrollPane scrollTableProducts;
    
    GridBagConstraints gbc; //esto se usa para poder posicionar los elementos dentro del panel en una posicion especifica
    
    public PanelDashboard(){
        elementoCliente = new Object[100][5];
        elementoProducto = new Object[100][4];
        
        setLayout(new GridLayout(2, 1)); // 2 filas 1 columna, divide en 2 partes iguales el panel
        
        //********************************************PANEL ARRIBA****************************************************************
        
        panelArriba = new JPanel(new GridBagLayout());// se usa GridBagLayout para poder controlar los componentes y poder ocupar
        //cierta cantidad de columnas como el titulo que ocupa 5 columnas del layout (columnspan)
        
        // Fila 1: título (ocupa 5 columnas)
        titulo = new JLabel("ADMINISTRACION DE CLIENTES");
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);//centra el titulo
        
        gbc_titulo = new GridBagConstraints();
        
        gbc_titulo.gridx = 0; //posicion en columna =0
        gbc_titulo.gridy = 0; //posicion en fila=0
        gbc_titulo.gridwidth = 5; //ocupa las 5 columna(columnspan)
        /*weighty distribuye la altura del componente dentro del panel*/
        gbc_titulo.weighty = 1; //ocupa el 10% de la altura del panel
        
        panelArriba.add(titulo, gbc_titulo);

        // Fila 2: botones 
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1; //adapta el ancho al 100% del ancho del panel
        
        gbc.gridy = 1; //posicion en fila=1
        gbc.gridwidth = 1;
        gbc.weighty = 1; //ocupa el 10% de la altura del panel
               
        upload_button = new JButton("CargarClientes");
        upload_button.setBackground(new Color(173, 216, 150));
        upload_button.setForeground(Color.WHITE);
        upload_button.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        panelArriba.add(upload_button, gbc);
        
        Upload upload_data = new Upload();
        upload_button.addActionListener(upload_data);
        
        dashboard_button = new JButton("DashboardClientes");
        dashboard_button.setBackground(new Color(173, 216, 150));
        dashboard_button.setForeground(Color.WHITE);
        dashboard_button.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 1;
        panelArriba.add(dashboard_button, gbc);
        
        Dashboard dashboard = new Dashboard();
        dashboard_button.addActionListener(dashboard);
        
        create_button = new JButton("CrearCliente");
        create_button.setBackground(new Color(173, 216, 150));
        create_button.setForeground(Color.WHITE);
        create_button.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 2;
        panelArriba.add(create_button, gbc);
        
        Create create = new Create();
        create_button.addActionListener(create);
        
        search_button = new JButton("ConsultarCliente");
        search_button.setBackground(new Color(173, 216, 150));
        search_button.setForeground(Color.WHITE);
        search_button.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 3;
        panelArriba.add(search_button, gbc);
        
        Search search = new Search();
        search_button.addActionListener(search);
        
        update_button = new JButton("ModificarCliente");
        update_button.setBackground(new Color(173, 216, 150));
        update_button.setForeground(Color.WHITE);
        update_button.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 4;
        
        panelArriba.add(update_button, gbc);
        
        Update update_data = new Update();
        update_button.addActionListener(update_data);
        
      
        
        //********************************************PANEL ABAJO****************************************************************
        
        //el segundo panel se coloca automaticamente en la fila de abajo
        
        panelAbajo = new JPanel(new GridLayout(1, 2, 10, 0)); // 1 fila, 2 columnas
        panelAbajo.setBorder(BorderFactory.createLineBorder(Color.BLACK));//borde del panel de color negro
        
        //************************************************************************************************************************
        
        uploadClientsData();//carga los datos de los clientes automaticamente desde el archivo clientes.csv para guardarlos en el array elementoCliente
        updateClientsData(); //actualiza los datos de la tabla y las graficas de pie y barras cada vez que se ingresa un nuevo cliente o dato
        

        add(panelAbajo, BorderLayout.CENTER);
        add(panelArriba);
        add(panelAbajo);
    }
    
    public void setButtons(String tipo){
    
        if(tipo.equals("clientes")){
            titulo.setText("ADMINISTRACION DE CLIENTES");
            upload_button.setText("CargarClientes");
            dashboard_button.setText("DashboardClientes");
            create_button.setText("CrearCliente");
            search_button.setText("ConsultarCliente");
            update_button.setText("ModificarCliente");
            
        }else if(tipo.equals("productos")){
            titulo.setText("ADMINISTRACION DE PRODUCTOS");
            upload_button.setText("CargarProductos");
            dashboard_button.setText("DashboardProductos");
            create_button.setText("CrearProducto");
            search_button.setText("ConsultarProducto");
            update_button.setText("ModificarProducto");
        }
    
    }
    
    public void updateClientsData(){//este metodo actualiza los datos de la tabla y las graficas cada vez que se crea un nuevo cliente o dato
        
        
        setTableClientsData(); // crea la tabla y tambien la actualiza cuando se agrega un nuevo cliente
        
        
        //******************************AQUI INICIA EL CODIGO PARA LA GRAFIA DE PIE *********************************************
        
        Double contadorF = 0.0;
        Double contadorM = 0.0;
        //*********************MUJERES************************
        for (int j = 0; j < this.elementoCliente.length; j++) { // ANALIZAR Y CORREGIR DESPUES EL TAMANO DEL ARREGLO

            mujeres = String.valueOf(this.elementoCliente[j][2]);
            //System.out.println("El elemento en la posicion " + j + " " + mujeres);

            if ((mujeres).equals("F")) {

                contadorF++;

            }

        }

        System.out.println("La cantidad de mujeres es " + contadorF);
        //********************************HOMBRES****************************
        for (int j = 0; j < this.elementoCliente.length; j++) { // ANALIZAR Y CORREGIR DESPUES EL TAMANO DEL ARREGLO

            hombres = String.valueOf(this.elementoCliente[j][2]);
            // System.out.println("El elemento en la posicion " + j + " " + hombres);

            if ((hombres).equals("M")) {

                contadorM++;

            }

        }
        
        double totalPersonas = contadorM + contadorF;
        
        System.out.println("La cantidad de hombres es " + contadorM);
        Double porcentajeF = (100 * contadorF) / (totalPersonas);
        System.out.println("El porcentaje femenino es "+porcentajeF);

        Double porcentajeM = (100 * contadorM) / (totalPersonas);
        System.out.println("El porcentaje masculino es "+porcentajeM);
        
        setClientsPieChart(porcentajeF, porcentajeM);//llama al metodo para crear la grafica de pie
        //panelAbajo.add(Pie);
        
        // *****************************AQUI INICIA EL CODIGO PARA LA GRAFICA DE BARRAS ***********************************************
        
        String[] cadena = new String[100];
        int[] enteros = new int[100];
        DefaultCategoryDataset datos = null;

        for (int i = 0; i < this.elementoCliente.length; i++) {

            if ((this.elementoCliente[i][1]) != null) {

                cadena[i] = String.valueOf(this.elementoCliente[i][1]); // se guardan las edades en un arreglo String

                // System.out.println("La cadena en la posicion "+i+" "+cadena[i]);
            }

            if ((cadena[i]) != null) {

                enteros[i] = Integer.parseInt(cadena[i]); // se guarda las edades String en un arreglo int

            }
        }

        //------------ENCUENTRE EL NUMERO MAYOR Y EL MENOR DEL ARREGLO enteros[] QUE CONTIENE LAS EDADES EN INT   
        double mayor, menor;

        mayor = menor = enteros[0];
        int numero_edades = 0;
        for (int i = 0; i < enteros.length; i++) {

            if (enteros[i] != 0) {

                numero_edades = i + 1;

                if (enteros[i] > mayor) {

                    mayor = enteros[i];
                }

                if (enteros[i] < menor) {

                    menor = enteros[i];
                }

            }
        }

        System.out.println("El numero mayor es " + mayor);
        System.out.println("El numero menor es " + menor);
        System.out.println("El numero de edades es " + numero_edades);

        //------------FINALIZA EL NUMERO MAYOR Y EL MENOR DEL ARREGLO enteros[] QUE CONTIENE LAS EDADES EN INT 
        //---------SE DEFINE LA AMPLITUD Y EL INTERVALO-----------------------------
        double intervalo = 1 + 3.3 * Math.log10(numero_edades);
        int intervalo_entero = (int) (intervalo);
        // double ejemplo = 1 + 3.3 * Math.log10(20);
        // System.out.println("El ejemplo log " + ejemplo);
        System.out.println("El intervalo es " + intervalo);
        System.out.println("El intervalo entero es " + intervalo_entero);

        int redondear3 = (int) Math.round(intervalo);//7

        double amplitud = (mayor - menor) / (intervalo_entero);
        System.out.println("La amplitud es " + amplitud);

        int[] rangos = new int[redondear3];// tamano7
        int contador4 = 0;
        for (int z = 0; z < redondear3; z++) {//de 0 a 6 son 7 

            rangos[z] = (int) Math.round(menor);

            menor = menor + amplitud;

            contador4++;

            if (contador4 == redondear3) {

                rangos[z] = rangos[z] + 1;

            }
        }

        for (int h = 0; h < rangos.length; h++) {

            System.out.println("Los rangos " + h + " = " + rangos[h]);
        }

        int[] frecuencia_edades = new int[redondear3]; //7
        int contador1 = 0;
        int contador2 = 0;
        int d = 0;

        for (int z = 0; z < (rangos.length); z++) {

            if (enteros[z] != 0) {
                //            (7-1 = 6)
                if (contador1 < (rangos.length - 1)) { // hace las siguientes instrucciones 6 veces

                    System.out.println("El rango z " + z + " = " + rangos[z]);
                    System.out.println("El rango z+1 " + (z + 1) + " = " + rangos[z + 1]);
                    contador1++;

                    for (int g = 0; g < numero_edades; g++) {

                        // if ((rangos[z] <= enteros[g]) && (enteros[g] < rangos[z + 1])) {
                        if (rangos[z] <= enteros[g]) {

                            if (enteros[g] < rangos[z + 1]) {

                                contador2++;
                                //System.out.println("El valor del contador2 es "+contador2);
                                //System.out.println("Hola mundo");
                            }
                        }
                    }

                    //System.out.println("El valor del contador2 antes de resetearlo es "+contador2);
                    frecuencia_edades[d] = contador2;
                    System.out.println("El valor del contador2 antes de ser reseteado es " + contador2);
                    System.out.println("El valor de la frecuencia_edades " + d + " = " + frecuencia_edades[d]);
                    d++;

                    contador2 = 0; // resetea el contador

                }
            }
        }

        //---------FINALIZA DEFINE LA AMPLITUD Y EL INTERVALO-----------------------------
        

        //-----------Convertir los rangos (int) a String--------------------------------
        String[] rangos_strings = new String[rangos.length];

        for (int t = 0; t < rangos.length; t++) {

            rangos_strings[t] = String.valueOf(rangos[t]);
            System.out.println("El valor de rangos_strings " + t + " = " + rangos_strings[t]);

        }

        String[] rangos_finales = new String[rangos.length];
        int contador3 = 0;

        for (int c = 0; c < rangos_finales.length; c++) {

            if (contador3 < (rangos_finales.length - 1)) {

                contador3++;

                rangos_finales[c] = rangos_strings[c] + "-" + rangos_strings[c + 1];
                System.out.println("Los rangos finales " + rangos_finales[c]);

            }
        }

        //-----------Finaliza Convertir los rangos (int) a String--------------------------------
        datos = new DefaultCategoryDataset();

        for (int i = 0; i < (rangos.length - 1); i++) {// k es el contador que lleva el conteo de las veces que se repite los numeros
            //int                  string
            datos.addValue(frecuencia_edades[i], "1", rangos_finales[i]);

        }

        setClientsBarsChart(datos); //crea y actualiza la grafica de barras
    
    }
    
    public void setTableClientsData(){
        
        //el siguiente if es usado por buena practica ya que cada vez que se presiona el boton dashboard se crea una nueva tabla
        // y lo que hace el siguiente if es borrar la tabla anterior para poder limpiar las tablas de la memoria
        if (scrollTableClients != null) {
            panelArriba.remove(scrollTableClients);
        }
        
        if (scrollTableProducts != null) {
            panelArriba.remove(scrollTableProducts);
        }
        
        
        // Fila 3: tabla (ocupa 5 columnas)
        String[] columnas = {"ID", "Nombre", "Edad", "Genero", "NIT"};
        
        //nuevo arreglo para poder incluir el ID, y mostrar los datos de elementoCliente en orden en las columnas de la tabla  
        Object[][] datosTabla = new Object[elementoCliente.length][5]; 

        for (int i = 0; i < elementoCliente.length; i++) {

            if (elementoCliente[i][0] != null) {

                datosTabla[i][0] = i + 1; // ID
                datosTabla[i][1] = elementoCliente[i][0]; // Nombre
                datosTabla[i][2] = elementoCliente[i][1]; // Edad
                datosTabla[i][3] = elementoCliente[i][2]; // Género
                datosTabla[i][4] = elementoCliente[i][3]; // NIT
            }
        }

        JTable tabla = new JTable(datosTabla, columnas); //10 filas, 3 columnas
        tabla.setRowHeight(25); //altura de cada fila
        scrollTableClients = new JScrollPane(tabla);

        gbc.gridx = 0; //posicion 0 equivalente a columna 1
        gbc.gridy = 2; //posicion 2 equivalente a fila 3
        gbc.gridwidth = 5; //ocupa las 5 columna(columnspan) 
        gbc.weighty = 8; //ocupa el 80% de la altura del panel

        panelArriba.add(scrollTableClients,gbc);
        
        panelArriba.revalidate(); //  actualiza el panelArriba
        panelArriba.repaint();
    }
    
    public void setClientsPieChart(Double porcentajeF, Double porcentajeM){
        
        // eliminar gráfica anterior
        if (Pie != null) {
            panelAbajo.remove(Pie);
        }
        
        DefaultPieDataset pie = new DefaultPieDataset();

        pie.setValue("F", porcentajeF);
        pie.setValue("M", porcentajeM);

        JFreeChart graficaPie = ChartFactory.createPieChart("Grafica de Pie", pie);

        Pie = new ChartPanel(graficaPie);
        panelAbajo.add(Pie);
        panelAbajo.revalidate();
        panelAbajo.repaint();
    }
    
    public void setClientsBarsChart(DefaultCategoryDataset datos){
        
        //  eliminar gráfica anterior
        if (barras != null) {
            panelAbajo.remove(barras);
        }
    
        JFreeChart chart = ChartFactory.createBarChart("Grafica de Barras", "Edad", "Clientes", datos,
                PlotOrientation.VERTICAL, true, true, false);

        barras = new ChartPanel(chart);
        
        panelAbajo.add(barras);
        panelAbajo.revalidate();
        panelAbajo.repaint();
    }
    
    public void uploadClientsData(){ // metodo que carga automaticamente los datos del archivo csv y los guarda en el array elementoCliente
        
        String[] campos;
        int i = 0;
        try {
            // Abrir el .csv en buffer de lectura
            InputStream is = getClass().getResourceAsStream("/ArchivosCarga/clientes.csv");
            BufferedReader bufferLectura = new BufferedReader(new InputStreamReader(is));

            

            String linea;

            while ((linea = bufferLectura.readLine()) != null) {

                //texto = texto + linea;
                campos = linea.split(","); // guarda la informacion en un arreglo de tipo String

                for (int j = 0; j < campos.length; j++) {

                    this.elementoCliente[i][j] = campos[j];

                    // System.out.println("El elemento "+elemento[i][j]);
                }
                i++;// para poder llevar el conteo de las filas
            }
        } catch (IOException e) {
            System.out.println("El archivo no se ha encontrado");
        }

       
        int m = 1;
        String[] cadena = new String[100];
        int[] nit = new int[100];

        //System.out.println("El tamano del elemento es "+elemento.length);
        for (int j = 0; j < this.elementoCliente.length; j++) {

            if ((this.elementoCliente[j][3]) != null) {
                cadena[j] = String.valueOf(this.elementoCliente[j][3]);
            }

            if ((cadena[j]) != null) {

                nit[j] = Integer.parseInt(cadena[j]);

                //System.out.println("El nit " + j + " = " + nit[j]);
            }
            
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

        if (q != 0) {

            JOptionPane.showMessageDialog(null, "No se puede cargar el archivo, Hay NITs que se repiten");
        
        }
    }
    
    
    public void updateProductsData(){
        
        setTableProductsData(); // crea la tabla y tambien la actualiza cuando se agrega un nuevo producto
        
        
        int k = 0;
        String[] cantidad_productos = new String[100];
        String[] nombre_productos = new String[100];

        for (int j = 0; j < elementoProducto.length; j++) {

            if (elementoProducto[j][2] != null) {

                cantidad_productos[j] = String.valueOf(elementoProducto[j][2]);
                nombre_productos[j] = String.valueOf(elementoProducto[j][0]);
                //System.out.println("Productos "+cantidad_productos[j]);
                k++;

            }
        }

            
        //------------------------*****INICIA CODIGO NUEVO*********--------------------------------------------
        String[] cadena_precios = new String[100];
        double[] precios = new double[100];
        // DefaultCategoryDataset datos = null;

        for (int i = 0; i < elementoProducto.length; i++) {

            if ((elementoProducto[i][1]) != null) {

                cadena_precios[i] = String.valueOf(elementoProducto[i][1]); // se guardan las edades en un arreglo String

                // System.out.println("La cadena en la posicion "+i+" "+cadena[i]);
            }

            if ((cadena_precios[i]) != null) {

                precios[i] = Double.parseDouble(cadena_precios[i]); // se guarda las edades String en un arreglo int

            }
        }

        double mayor, menor;

        mayor = menor = precios[0];
        int numero_precios = 0;
        for (int i = 0; i < precios.length; i++) {

            if (precios[i] != 0) {

                numero_precios = i + 1;

                if (precios[i] > mayor) {

                    mayor = precios[i];
                }

                if (precios[i] < menor) {

                    menor = precios[i];
                }

            }
        }

        System.out.println("El precio mayor es " + mayor);
        System.out.println("El precio menor es " + menor);
        System.out.println("El numero de precios es " + numero_precios);

        double intervalo = 1 + 3.3 * Math.log10(numero_precios);
        int intervalo_entero = (int) (intervalo);
        // double ejemplo = 1 + 3.3 * Math.log10(20);
        // System.out.println("El ejemplo log " + ejemplo);
        System.out.println("El intervalo es " + intervalo);
        System.out.println("El intervalo entero es " + intervalo_entero);

        int redondear3 = (int) Math.round(intervalo);//7

        DecimalFormat df = new DecimalFormat("#.00");
        double amplitud = (mayor - menor) / (intervalo_entero);
        double amplitud2 = Double.parseDouble(df.format(amplitud));

        System.out.println("La amplitud es " + amplitud2);

        double[] rangos = new double[redondear3];// tamano7

        int contador4 = 0;
        for (int z = 0; z < redondear3; z++) {//de 0 a 6 son 7 

            rangos[z] = Double.parseDouble(df.format(menor));

            menor = menor + amplitud2;

            contador4++;

            if (contador4 == redondear3) {

                rangos[z] = rangos[z] + 1;

            }
        }

        for (int h = 0; h < rangos.length; h++) {

            System.out.println("Los rangos " + h + " = " + rangos[h]);
        }

        int[] frecuencia_precios = new int[redondear3]; //7
        double contador1 = 0;
        int contador2 = 0;
        int d = 0;

        for (int z = 0; z < (rangos.length); z++) {

            if (precios[z] != 0) {
                //            (7-1 = 6)
                if (contador1 < (rangos.length - 1)) { // hace las siguientes instrucciones 6 veces

                    System.out.println("El rango z " + z + " = " + rangos[z]);
                    System.out.println("El rango z+1 " + (z + 1) + " = " + rangos[z + 1]);
                    contador1++;

                    for (int g = 0; g < numero_precios; g++) {

                        // if ((rangos[z] <= enteros[g]) && (enteros[g] < rangos[z + 1])) {
                        if (rangos[z] <= precios[g]) {

                            if (precios[g] < rangos[z + 1]) {

                                contador2++;
                                //System.out.println("El valor del contador2 es "+contador2);
                                //System.out.println("Hola mundo");
                            }
                        }
                    }

                    //System.out.println("El valor del contador2 antes de resetearlo es "+contador2);
                    frecuencia_precios[d] = contador2;
                    System.out.println("El valor del contador2 antes de ser reseteado es " + contador2);
                    System.out.println("El valor de la frecuencia_edades " + d + " = " + frecuencia_precios[d]);
                    d++;

                    contador2 = 0; // resetea el contador

                }
            }
        }

        String[] rangos_strings = new String[rangos.length];

        for (int t = 0; t < rangos.length; t++) {

            rangos_strings[t] = String.valueOf(rangos[t]);
            System.out.println("El valor de rangos_strings " + t + " = " + rangos_strings[t]);

        }

        String[] rangos_finales = new String[rangos.length];
        int contador3 = 0;

        for (int c = 0; c < rangos_finales.length; c++) {

            if (contador3 < (rangos_finales.length - 1)) {

                contador3++;

                rangos_finales[c] = rangos_strings[c] + "-" + rangos_strings[c + 1];
                System.out.println("Los rangos finales " + rangos_finales[c]);

            }
        }
        
        DefaultCategoryDataset datos = new DefaultCategoryDataset();

        for (int i = 0; i < (rangos.length - 1); i++) {// k es el contador que lleva el conteo de las veces que se repite los numeros

            datos.addValue(frecuencia_precios[i], "1", rangos_finales[i]);

        }
        
        setProductsBarsChart(datos);
        
        
        //--------- INICIA GRAFICA DE PIE-----------------------------------------------------------------
        
        int[] cantidad = new int[100];
        for (int j = 0; j < cantidad_productos.length; j++) {  //cantidad_productos es string

            if (elementoProducto[j][2] != null) {

                if ((cantidad_productos[j]) != null) {

                    //System.out.println("La cantidad productos " + j + " = " + cantidad_productos[j]);
                    //System.out.println("El nombre productos " + j + " = " + nombre_productos[j]);
                    cantidad[j] = Integer.parseInt(cantidad_productos[j]); // de String a Integer
                    //System.out.println("Cantidad "+cantidad[j]);
                    k++;
                }
            }
        }

        int aux = 0;
        String aux1 = "";
        for (int a = 0; a < (100 - 1); a++) {

            for (int b = (a + 1); b < 100; b++) {
                /* ORDENA las cantidades de productos pero se repiten
                                                    y ORDENA los nombres de los productos*/
                if (cantidad[a] < cantidad[b]) { // Descendente<   ascendente>

                    aux = cantidad[a];
                    aux1 = nombre_productos[a];

                    cantidad[a] = cantidad[b]; // cantidad int
                    nombre_productos[a] = nombre_productos[b];

                    cantidad[b] = aux;
                    nombre_productos[b] = aux1;
                }
            }
        }
        
        
        setProductsPieChart(nombre_productos, cantidad);
    }
    
    public void setTableProductsData(){
        
        System.out.println("******************CREANDO LA TABLA DE PRODUCTOS*******************************");
        
        //el siguiente if es usado por buena practica ya que cada vez que se presiona el boton dashboard se crea una nueva tabla
        // y lo que hace el siguiente if es borrar la tabla anterior para poder limpiar las tablas de la memoria
        if (scrollTableProducts != null) {
            panelArriba.remove(scrollTableProducts);
        }
        
        if (scrollTableClients != null) {
            panelArriba.remove(scrollTableClients);
        }
        
        
        
        // Fila 3: tabla (ocupa 5 columnas)
        String[] columnas = {"ID", "Nombre", "Precio", "Cantidad"};
        
        Object[][] datosTabla = new Object[elementoProducto.length][4]; 

        for (int i = 0; i < elementoProducto.length; i++) {

            if (elementoProducto[i][0] != null) {

                datosTabla[i][0] = i + 1; // ID
                datosTabla[i][1] = elementoProducto[i][0]; // Nombre
                datosTabla[i][2] = elementoProducto[i][1]; // Precio
                datosTabla[i][3] = elementoProducto[i][2]; // Cantidad
                
            }
        }
        JTable table = new JTable(datosTabla, columnas);

        table.setRowHeight(30);

        scrollTableProducts = new JScrollPane(table);
        
        gbc.gridx = 0; //posicion 0 equivalente a columna 1
        gbc.gridy = 2; //posicion 2 equivalente a fila 3
        gbc.gridwidth = 5; //ocupa las 5 columna(columnspan) 
        gbc.weighty = 8; //ocupa el 80% de la altura del panel

        panelArriba.add(scrollTableProducts,gbc);
        
        panelArriba.revalidate(); //  actualiza el panelArriba
        panelArriba.repaint();
        
    }
    
    public void setProductsPieChart(String[] nombre_productos, int[] cantidad ){
        
        // eliminar gráfica anterior
        if (Pie != null) {
            panelAbajo.remove(Pie);
        }
        
        DefaultPieDataset pie = new DefaultPieDataset();

        for (int c = 0; c < 5; c++) {   // solo muestra 5 productos en la grafica
            // int
            pie.setValue(nombre_productos[c], cantidad[c]);

        }
        JFreeChart graficaPie = ChartFactory.createPieChart("Grafica de Pie", pie);

        Pie = new ChartPanel(graficaPie);
        panelAbajo.add(Pie);
        panelAbajo.revalidate();
        panelAbajo.repaint();
        
    }
    
    public void setProductsBarsChart(DefaultCategoryDataset datos){
        
        // eliminar gráfica anterior
        if (barras != null) {
            panelAbajo.remove(barras);
        }
        
        JFreeChart chart = ChartFactory.createBarChart("Grafica de Barras", "Cantidad Productos", "Precio", datos,
                PlotOrientation.VERTICAL, true, true, false);

        barras = new ChartPanel(chart);
        
        panelAbajo.add(barras);
        panelAbajo.revalidate();
        panelAbajo.repaint();
    }
    public void uploadProductsData(){
        System.out.println("*****************CARGANDO LOS DATOS DE LOS PRODUCTOS*******************************");
        String[] campos;
        int i = 0;
        try {
            // Abrir el .csv en buffer de lectura
            InputStream is = getClass().getResourceAsStream("/ArchivosCarga/productos.csv");
            BufferedReader bufferLectura = new BufferedReader(new InputStreamReader(is));

            

            String linea;

            while ((linea = bufferLectura.readLine()) != null) {

                //texto = texto + linea;
                campos = linea.split(","); // guarda la informacion en un arreglo de tipo String

                for (int j = 0; j < campos.length; j++) {

                    this.elementoProducto[i][j] = campos[j];

                    System.out.println("El producto "+elementoProducto[i][j]);
                }
                i++;// para poder llevar el conteo de las filas
            }
        } catch (IOException e) {
            System.out.println("El archivo no se ha encontrado");
        }

       
        int m = 1;
        String[] cadena = new String[100];
        int[] nit = new int[100];

        //System.out.println("El tamano del elemento es "+elemento.length);
        for (int j = 0; j < elementoProducto.length; j++) {

            if ((elementoProducto[j][0]) != null) {

                cadena[j] = String.valueOf(elementoProducto[j][0]);

                // System.out.println("La cadena Nombre de Producto en la posicion " + j + " = " + cadena[j]);
            }

        }

        int contador = 0;

        for (int k = 0; k < (cadena.length); k++) { // tamano cadena 9

            if (cadena[k] != null) {

                for(int b=k+1; b<cadena.length; b++ ) {

                    //System.out.println("La cadena k "+cadena[k]);
                    //System.out.println("La cadena b "+cadena[b]);
                    
                    if (cadena[k].equals(cadena[b])) {

                        contador++;

                        System.out.println("El producto repetido es " + cadena[k]);
                        
                        JOptionPane.showMessageDialog(null, "El producto que se repite es "+cadena[k]);
                        //System.out.println("El contador es "+contador);
                    }
                }
            }
        }// fin del for
        
        if (contador != 0) {

            JOptionPane.showMessageDialog(null, "No se puede cargar el archivo, Hay Productos que se repiten");
            
        }
    }
    
    
    public void setDatos(Object[][] elemento) {

        this.elementoCliente = elemento; // proviene de la clase CargaMasiva

    }
    
    public Object[][] getProductos() {
        return this.elementoProducto;
    }
    private class Upload implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            CargaMasivaClientes carga = new CargaMasivaClientes();
            

        }

    }
    
    private class Dashboard implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            if (elementoCliente == null) {

                JOptionPane.showMessageDialog(null, "No ha cargado los datos todavia");

            } else {
                
                if(dashboard_button.getText().equals("DashboardClientes")){
                    
                    updateClientsData(); //cada vez que se presiona el boton Dashboard se recalculan los datos para las graficas y la tabla
                
                }else if(dashboard_button.getText().equals("DashboardProductos")){
                    
                    updateProductsData(); //cada vez que se presiona el boton Dashboard se recalculan los datos para las graficas y la tabla
                    
                }
                
            }
            

        }

    }
    
    private class Create implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            
            if(dashboard_button.getText().equals("DashboardClientes")){
                    
                CrearCliente nuevo_cliente = new CrearCliente(elementoCliente);
                nuevo_cliente.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                nuevo_cliente.setVisible(true);
                    
            }else if(dashboard_button.getText().equals("DashboardProductos")){
                
                CrearProducto nuevo_producto = new CrearProducto(elementoProducto);
                nuevo_producto.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                nuevo_producto.setVisible(true);
                
            }
          
        }

    }

    private class Search implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent w) {
            
            if(dashboard_button.getText().equals("DashboardClientes")){
                    
                ConsultarCliente consulta = new ConsultarCliente();
                consulta.setElemento(elementoCliente); // envia los elementos de la carga masiva + nuevos creados a la clase CONSULTAR
                consulta.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                consulta.setVisible(true);
                    
            }else if(dashboard_button.getText().equals("DashboardProductos")){
                
                ConsultarProducto consulta = new ConsultarProducto();
                consulta.setElemento(elementoProducto); // envia los elementos de la carga masiva + nuevos creados a la clase CONSULTAR
                consulta.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                consulta.setVisible(true);
                
            }
            
            
        }

    }

    private class Update implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent w) {
            
            if(dashboard_button.getText().equals("DashboardClientes")){
                    
                ModificarCliente modifica = new ModificarCliente(); // envia todos los elementos CargaMasiva y NuevoCliente
                modifica.setElemento(elementoCliente);
                modifica.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                modifica.setVisible(true);
                    
            }else if(dashboard_button.getText().equals("DashboardProductos")){
                
                ModificarProducto modifica = new ModificarProducto(); // envia todos los elementos CargaMasiva y NuevoCliente
                modifica.setElemento(elementoProducto);
                modifica.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                modifica.setVisible(true);
                
            }
            
            

        }

    }
}
