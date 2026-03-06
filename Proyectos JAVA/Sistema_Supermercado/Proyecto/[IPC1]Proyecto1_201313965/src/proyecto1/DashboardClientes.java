package proyecto1;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.*;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;

import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

public class DashboardClientes extends JFrame {

    //public Object[][] elemento;

    /*public String[] columnas = {"nombre", "edad", "sexo", "nit", "foto"};
    public Object[][] elemento;
    public Image imagen;
    public ImageIcon icono;*/
    public DashboardClientes(Object[][] elemento) { // el elemento proviene de la clase VentanaClientes

        setTitle("Graficas");
        setBounds(150, 50, 900, 670);

        Lamina2 lamina = new Lamina2(elemento);// pasar el objeto solo por el constructo (por metodo no funciona)
        //lamina.setArreglos(elemento);
        add(lamina);

    }

}

class Lamina2 extends JPanel {

    JScrollPane scroll;
    ChartPanel Pie;
    ChartPanel barras;
    public Object[][] elemento;
    public String mujeres = "";
    public String hombres = "";
    public String[] columnas = {"nombre", "edad", "sexo", "nit"};

    public Lamina2(Object[][] elemento) {

        setLayout(new BorderLayout(10, 10));

        //JLabel etiqueta = new JLabel("GRAFICAS");
        //etiqueta.setBounds(400, 20, 100, 30);
        //add(etiqueta);

        JTable table = new JTable(elemento, columnas);

        table.setRowHeight(30);

        scroll = new JScrollPane(table);
        JPanel panelTabla = new JPanel(new BorderLayout());
        panelTabla.add(new JLabel("GRAFICAS", SwingConstants.CENTER), BorderLayout.NORTH);
        panelTabla.add(scroll, BorderLayout.CENTER);
        panelTabla.setPreferredSize(new Dimension(100, 200));
        add(panelTabla, BorderLayout.NORTH);
        
        //scroll.setBounds(200, 80, 500, 100);// modifica el tamano de la tabla junto con el scroll
        //add(scroll);

        // AQUI INICIA EL CALCULO DE PORCENTAJES PARA LA GRAFICA DE PIE
        System.out.println("El tamano del arreglo elemento es: " + elemento.length);

        Double contadorF = 0.0;
        Double contadorM = 0.0;
        //*********************MUJERES************************
        for (int j = 0; j < elemento.length; j++) { // ANALIZAR Y CORREGIR DESPUES EL TAMANO DEL ARREGLO

            mujeres = String.valueOf(elemento[j][2]);
            //System.out.println("El elemento en la posicion " + j + " " + mujeres);

            if ((mujeres).equals("F")) {

                contadorF++;

            }

        }

        System.out.println("La cantidad de mujeres es " + contadorF);
        //********************************HOMBRES****************************
        for (int j = 0; j < elemento.length; j++) { // ANALIZAR Y CORREGIR DESPUES EL TAMANO DEL ARREGLO

            hombres = String.valueOf(elemento[j][2]);
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
        
        JPanel panelGraficas = new JPanel(new GridLayout(1, 2, 10, 0)); // 1 fila, 2 columnas
        
        //AQUI INICIA EL CODIGO DE LA GRAFIA DE PIE *********************************************
        DefaultPieDataset pie = new DefaultPieDataset();

        pie.setValue("F", porcentajeF);
        pie.setValue("M", porcentajeM);

        JFreeChart graficaPie = ChartFactory.createPieChart("Grafica de Pie", pie);

        Pie = new ChartPanel(graficaPie);
        panelGraficas.add(Pie);
        //Pie.setBounds(20, 200, 400, 400);
        //add(Pie);

        // AQUI INICIA EL CODIGO PARA LA GRAFICA DE BARRAS ***********************************************
        String[] cadena = new String[100];
        int[] enteros = new int[100];
        DefaultCategoryDataset datos = null;

        for (int i = 0; i < elemento.length; i++) {

            if ((elemento[i][1]) != null) {

                cadena[i] = String.valueOf(elemento[i][1]); // se guardan las edades en un arreglo String

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
        
        panelGraficas.add(barras);

        add(panelGraficas, BorderLayout.CENTER); 
        //barras.setBounds(450, 200, 400, 400);
        //add(barras);

    }// FIN DEL CONSTRUCTOR LAMINA2

    public void setArreglos(Object[][] elemento) {

        this.elemento = elemento;

    }

}
