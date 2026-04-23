/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
class PanelClientes extends JPanel{
    
    //public static Object[][] elementoCliente;
    public Object[][] elementoCliente;
    private JPanel panelArriba;
    private JPanel panelAbajo;
    public String mujeres = "";
    public String hombres = "";
    //JScrollPane scroll;
    private ChartPanel Pie;
    private ChartPanel barras;
    private JScrollPane scrollTabla;
    
    GridBagConstraints gbc; //esto se usa para poder posicionar los elementos dentro del panel en una posicion especifica
    
    public PanelClientes(){
        elementoCliente = new Object[100][5];
        
        setLayout(new GridLayout(2, 1)); // 2 filas 1 columna, divide en 2 partes iguales el panel
        
        //********************************************PANEL ARRIBA****************************************************************
        
        panelArriba = new JPanel(new GridBagLayout());// se usa GridBagLayout para poder controlar los componentes y poder ocupar
        //cierta cantidad de columnas como el titulo que ocupa 5 columnas del layout (columnspan)
        
        // Fila 1: título (ocupa 5 columnas)
        JLabel titulo = new JLabel("ADMINISTRACION DE CLIENTES");
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);//centra el titulo
        
        GridBagConstraints gbc_titulo = new GridBagConstraints();
        
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
               
        JButton cargar_clientes = new JButton("Cargar Clientes");
        cargar_clientes.setBackground(new Color(173, 216, 150));
        cargar_clientes.setForeground(Color.WHITE);
        cargar_clientes.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        panelArriba.add(cargar_clientes, gbc);
        
        CargarClientes clientes = new CargarClientes();
        cargar_clientes.addActionListener(clientes);
        
        JButton dashboard_clientes = new JButton("Dashboard Clientes");
        dashboard_clientes.setBackground(new Color(173, 216, 150));
        dashboard_clientes.setForeground(Color.WHITE);
        dashboard_clientes.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 1;
        panelArriba.add(dashboard_clientes, gbc);
        
        Dashboard dashboard = new Dashboard();
        dashboard_clientes.addActionListener(dashboard);
        
        JButton creacion_clientes = new JButton("Crear Clientes");
        creacion_clientes.setBackground(new Color(173, 216, 150));
        creacion_clientes.setForeground(Color.WHITE);
        creacion_clientes.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 2;
        panelArriba.add(creacion_clientes, gbc);
        
        CrearClientes crear_clientes = new CrearClientes();
        creacion_clientes.addActionListener(crear_clientes);
        
        JButton consultar_clientes = new JButton("Consultar Clientes");
        consultar_clientes.setBackground(new Color(173, 216, 150));
        consultar_clientes.setForeground(Color.WHITE);
        consultar_clientes.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 3;
        panelArriba.add(consultar_clientes, gbc);
        
        ConsultarClientes consulta_clientes = new ConsultarClientes();
        consultar_clientes.addActionListener(consulta_clientes);
        
        JButton modificar_clientes = new JButton("Modificar Clientes");
        modificar_clientes.setBackground(new Color(173, 216, 150));
        modificar_clientes.setForeground(Color.WHITE);
        modificar_clientes.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 4;
        panelArriba.add(consultar_clientes, gbc);
        
        ModificarClientes modifica_clientes = new ModificarClientes();
        modificar_clientes.addActionListener(modifica_clientes);
        
        //********************************************PANEL ABAJO****************************************************************
        
        //el segundo panel se coloca automaticamente en la fila de abajo
        
        panelAbajo = new JPanel(new GridLayout(1, 2, 10, 0)); // 1 fila, 2 columnas
        
        panelAbajo.setBorder(BorderFactory.createLineBorder(Color.BLACK));//borde del panel de color negro
        
        //************************************************************************************************************************
        
        uploadData();//carga los datos de los clientes automaticamente desde el archivo clientes.csv para guardarlos en el array elementoCliente
        
        updateData(); //actualiza los datos de la tabla y las graficas de pie y barras cada vez que se ingresa un nuevo cliente o dato
        

        add(panelAbajo, BorderLayout.CENTER);
        add(panelArriba);
        add(panelAbajo);
    }
    
    public void updateData(){//este metodo actualiza los datos de la tabla y las graficas cada vez que se crea un nuevo cliente o dato
        
        
        setTableData(); // crea la tabla y tambien la actualiza cuando se agrega un nuevo cliente
        
        
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
        
        setPieChart(porcentajeF, porcentajeM);//llama al metodo para crear la grafica de pie
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

        setBarsChart(datos); //crea y actualiza la grafica de barras
    
    }
    
    public void setTableData(){
        
        //el siguiente if es usado por buena practica ya que cada vez que se presiona el boton dashboard se crea una nueva tabla
        // y lo que hace el siguiente if es borrar la tabla anterior para poder limpiar las tablas de la memoria
        if (scrollTabla != null) {
            panelArriba.remove(scrollTabla);
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

        JTable tabla = new JTable(elementoCliente, columnas); //10 filas, 3 columnas
        tabla.setRowHeight(25); //altura de cada fila
        scrollTabla = new JScrollPane(tabla);

        gbc.gridx = 0; //posicion 0 equivalente a columna 1
        gbc.gridy = 2; //posicion 2 equivalente a fila 3
        gbc.gridwidth = 5; //ocupa las 5 columna(columnspan) 
        gbc.weighty = 8; //ocupa el 80% de la altura del panel

        panelArriba.add(scrollTabla,gbc);
        
        panelArriba.revalidate(); //  actualiza el panelArriba
        panelArriba.repaint();
    }
    
    public void setPieChart(Double porcentajeF, Double porcentajeM){
        
        // 🔥 eliminar gráfica anterior
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
    
    public void setBarsChart(DefaultCategoryDataset datos){
        
        // 🔥 eliminar gráfica anterior
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
    
    public void uploadData(){ // metodo que carga automaticamente los datos del archivo csv automaticamente y guardarlos en el array elementoCliente
        
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
    public void setDatos(Object[][] elemento) {

        this.elementoCliente = elemento; // proviene de la clase CargaMasiva

    }

    /*public void setNuevoElemento() {

        Lamina3 lamina = new Lamina3();

        this.elementoCliente = lamina.getElemento(); // proviene de la clase NuevoCliente (Lamina3)

    }*/
    
    private class CargarClientes implements ActionListener {

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
                
                
                updateData(); //cada vez que se presiona el boton Dashboard se recalculan los datos para las graficas y la tabla
            }
            

        }

    }
    
    private class CrearClientes implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            CrearCliente nuevo = new CrearCliente(elementoCliente);
            nuevo.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            nuevo.setVisible(true);

           /* Lamina3 elementos = new Lamina3();
            elementos.setElemento(elementoCliente);*/
            // pasa los valores ingresados por carga masiva (elemento) a NuevoCliente en clase Lamina3

        }

    }

    private class ConsultarClientes implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent w) {

            ConsultarCliente consulta = new ConsultarCliente();
            consulta.setElemento(elementoCliente); // envia los elementos de la carga masiva + nuevos creados a la clase CONSULTAR
            consulta.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            consulta.setVisible(true);
        }

    }

    private class ModificarClientes implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent w) {

            ModificarCliente modifica = new ModificarCliente(); // envia todos los elementos CargaMasiva y NuevoCliente
            modifica.setElemento(elementoCliente);
            modifica.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            modifica.setVisible(true);

        }

    }
}
