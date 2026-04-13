
package proyecto1;

import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

public class Administracion2 extends JFrame{
    
    public Administracion2(){
    
        setTitle("ADMINISTRACION");
        
        MainPanel main_panel = new MainPanel();
        add(main_panel);
        
        pack();//al minimizar o restaurar al tamaño normal, esta funcion adapta los elementos al tamaño de la ventana
        //setMinimumSize(getPreferredSize());
        setExtendedState(JFrame.MAXIMIZED_BOTH); // pantalla completa (maximizada)
        
        setLocationRelativeTo(null); // centra la ventana
        
        
        
    }
    
}

class MainPanel extends JPanel{

    public MainPanel(){
        
        setLayout(new BorderLayout());
        
        //panel izquierdo
        JPanel panelIzquierdo = new JPanel(); //panel que contendra el menu principal del lado izquierdo
        
        panelIzquierdo.setBorder(BorderFactory.createLineBorder(Color.BLACK));//borde del panel de color negro
        
        panelIzquierdo.setLayout(new GridBagLayout());//centra vertical y horizontal el panel contenedorBotones
        panelIzquierdo.setBackground(Color.BLACK);
                
        JPanel contenedorBotones = new JPanel();
        contenedorBotones.setLayout(new GridLayout(3, 1)); // 3 filas y 1 columna
        
        
        JButton b1 = new JButton("Clientes");
        ImageIcon icon = new ImageIcon(getClass().getResource("/MenuIcons/cliente.png"));
        Image img = icon.getImage().getScaledInstance(80, 60, Image.SCALE_SMOOTH);
        b1.setIcon(new ImageIcon(img));
        b1.setHorizontalTextPosition(SwingConstants.CENTER);//centra el texto clientes
        b1.setVerticalTextPosition(SwingConstants.BOTTOM); // coloca el texto debajo de la imagen

        JButton b2 = new JButton("Productos");
        ImageIcon icon2 = new ImageIcon(getClass().getResource("/MenuIcons/productos.png"));
        Image img2 = icon2.getImage().getScaledInstance(80, 60, Image.SCALE_SMOOTH);
        b2.setIcon(new ImageIcon(img2));
        b2.setHorizontalTextPosition(SwingConstants.CENTER);//centra el texto productos
        b2.setVerticalTextPosition(SwingConstants.BOTTOM); // coloca el texto debajo de la imagen
        
        JButton b3 = new JButton("Ventas");
        ImageIcon icon3 = new ImageIcon(getClass().getResource("/MenuIcons/ventas.png"));
        Image img3 = icon3.getImage().getScaledInstance(80, 60, Image.SCALE_SMOOTH);
        b3.setIcon(new ImageIcon(img3));
        b3.setHorizontalTextPosition(SwingConstants.CENTER); //centra el texto ventas
        b3.setVerticalTextPosition(SwingConstants.BOTTOM); // coloca el texto debajo de la imagen
        
        
        b1.setFont(new Font("Arial", Font.BOLD, 16));
        b2.setFont(new Font("Arial", Font.BOLD, 16));
        b3.setFont(new Font("Arial", Font.BOLD, 16));
        
        b1.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, Color.WHITE)); //arriba, izquierda, abajo derecha
        b2.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.WHITE));
        b3.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.WHITE));

        b1.setBackground(Color.BLACK);
        b1.setForeground(Color.WHITE);
        b1.setOpaque(true);
        
        
        //configura botones para que al hacer click al boton el color de fondo cambia a blanco
        
        final JButton[] seleccionado = {null};
        
        
        b1.addActionListener(e -> {
            if (seleccionado[0] != null) {
                seleccionado[0].setBackground(Color.BLACK);
                seleccionado[0].setForeground(Color.WHITE);
            }

            b1.setBackground(Color.WHITE);
            b1.setForeground(Color.BLACK);
            seleccionado[0] = b1;
        });



        b2.setBackground(Color.BLACK);
        b2.setForeground(Color.WHITE);
        b2.setOpaque(true);
        
        b2.addActionListener(e -> {
            if (seleccionado[0] != null) {
                seleccionado[0].setBackground(Color.BLACK);
                seleccionado[0].setForeground(Color.WHITE);
            }

            b2.setBackground(Color.WHITE);
            b2.setForeground(Color.BLACK);
            seleccionado[0] = b2;
        });
        
        
        b3.setBackground(Color.BLACK);
        b3.setForeground(Color.WHITE);
        b3.setOpaque(true);
        
        b3.addActionListener(e -> {
            if (seleccionado[0] != null) {
                seleccionado[0].setBackground(Color.BLACK);
                seleccionado[0].setForeground(Color.WHITE);
            }

            b3.setBackground(Color.WHITE);
            b3.setForeground(Color.BLACK);
            seleccionado[0] = b3;
        });
        contenedorBotones.add(b1);
        contenedorBotones.add(b2);
        contenedorBotones.add(b3);
        
        
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL; // hace que el contenedorBotones ocupe el ancho horizontalmente
        gbc.weightx = 1; // permite expandirse horizontalmente y adaptar los botones al panel
        
        panelIzquierdo.add(contenedorBotones, gbc);
        
        
        //panel Central
        JPanel panelCentral = new JPanel(new CardLayout()); //CardLayout se usa para mostrar los paneles segun el boton presionado
        panelCentral.setBorder(BorderFactory.createLineBorder(Color.BLACK)); //borde del panel de color negro
        
        PanelClientes panelClientes = new PanelClientes();
        PanelProductos panelProductos = new PanelProductos();
        PanelVentas panelVentas = new PanelVentas();
        
        
        //agregando paneles al panel central
        panelCentral.add(panelClientes, "CLIENTES"); //CLIENTES es un identificador unico
        panelCentral.add(panelProductos, "PRODUCTOS");
        panelCentral.add(panelVentas, "VENTAS");
        
        CardLayout cl = (CardLayout) panelCentral.getLayout();

        b1.addActionListener(e -> cl.show(panelCentral, "CLIENTES")); //si presiono el boton1 se muestra el panel Clientes
        b2.addActionListener(e -> cl.show(panelCentral, "PRODUCTOS"));
        b3.addActionListener(e -> cl.show(panelCentral, "VENTAS"));

        //agregando los paneles al panel principal
        add(panelIzquierdo, BorderLayout.WEST);
        add(panelCentral, BorderLayout.CENTER);
        
        //la siguiente linea tiene que ir aca al final, despues de agregar los botones para que calcule el tamaño y se adapte
        panelIzquierdo.setPreferredSize(new Dimension(200, panelIzquierdo.getPreferredSize().height)); //ancho, alto adaptable a botones
        
    }
}

class PanelClientes extends JPanel{
    
    public static Object[][] elementoCliente;
    private JPanel panelArriba;
    public String mujeres = "";
    public String hombres = "";
    //JScrollPane scroll;
    ChartPanel Pie;
    ChartPanel barras;
    
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
        GridBagConstraints gbc = new GridBagConstraints();
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
        
        uploadData();//carga los datos de los clientes para luego ser mostrados en la tabla
        
        // Fila 3: tabla (ocupa 5 columnas)
        String[] columnas = {"ID", "Nombre", "Edad", "Genero", "NIT"};
        Object[][] dataTable = new Object[10][5];

        JTable tabla = new JTable(elementoCliente, columnas); //10 filas, 3 columnas
        tabla.setRowHeight(25); //altura de cada fila
        JScrollPane scroll = new JScrollPane(tabla);

        gbc.gridx = 0; //posicion en columna =0
        gbc.gridy = 2; //posicion en fila =2
        gbc.gridwidth = 5; //ocupa las 5 columna(columnspan) 
        gbc.weighty = 8; //ocupa el 80% de la altura del panel

        panelArriba.add(scroll,gbc);
        
        //********************************************PANEL ABAJO****************************************************************
        
        //el segundo panel se coloca automaticamente en la fila de abajo
        
        JPanel panelAbajo = new JPanel(new GridLayout(1, 2, 10, 0)); // 1 fila, 2 columnas
        
        panelAbajo.setBorder(BorderFactory.createLineBorder(Color.BLACK));//borde del panel de color negro
        
        // AQUI INICIA EL CALCULO DE PORCENTAJES PARA LA GRAFICA DE PIE
        
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
        
        
        
        //AQUI INICIA EL CODIGO DE LA GRAFIA DE PIE *********************************************
        DefaultPieDataset pie = new DefaultPieDataset();

        pie.setValue("F", porcentajeF);
        pie.setValue("M", porcentajeM);

        JFreeChart graficaPie = ChartFactory.createPieChart("Grafica de Pie", pie);

        Pie = new ChartPanel(graficaPie);
        panelAbajo.add(Pie);
        
        // AQUI INICIA EL CODIGO PARA LA GRAFICA DE BARRAS ***********************************************
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

        JFreeChart chart = ChartFactory.createBarChart("Grafica de Barras", "Edad", "Clientes", datos,
                PlotOrientation.VERTICAL, true, true, false);

        barras = new ChartPanel(chart);
        
        panelAbajo.add(barras);

        add(panelAbajo, BorderLayout.CENTER);
        
        add(panelArriba);
        add(panelAbajo);
    }
    
    public void uploadData(){
        
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

    public void setNuevoElemento() {

        Lamina3 lamina = new Lamina3();

        this.elementoCliente = lamina.getElemento(); // proviene de la clase NuevoCliente (Lamina3)

    }
    
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
                
                
                panelArriba.revalidate();
                panelArriba.repaint();
            }
            

        }

    }
    
    private class CrearClientes implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            CrearCliente nuevo = new CrearCliente();
            nuevo.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            nuevo.setVisible(true);

            Lamina3 elementos = new Lamina3();
            elementos.setElemento(elementoCliente);
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




class PanelProductos extends JPanel{

    public PanelProductos(){
        
        JLabel etiqueta = new JLabel("Vista Productos");
        add(etiqueta);
    }
}

class PanelVentas extends JPanel{

    public PanelVentas(){
        
        JLabel etiqueta = new JLabel("Vista Ventas");
        add(etiqueta);
    }
}

