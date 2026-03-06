package proyecto1;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.text.DecimalFormat;
import javax.swing.*;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

public class DashboardProductos extends JFrame {

    public DashboardProductos(Object[][] elementoProducto) {

        setTitle("Graficas Productos");
        setBounds(150, 50, 1050, 670);

        LaminaDashboardProductos laminadash = new LaminaDashboardProductos(elementoProducto);
        add(laminadash);

    }

} // fin clase DashboardProductos

class LaminaDashboardProductos extends JPanel {

    JScrollPane scroll;
    ChartPanel Pie;
    ChartPanel barras;
    //public Object[][] elementoProducto;

    public String[] columnas = {"nombre", "precio", "cantidad"}; // para usar en la tabla

    public LaminaDashboardProductos(Object[][] elementoProducto) {

        setLayout(new BorderLayout(10, 10));

        /*JLabel etiqueta = new JLabel("GRAFICAS PRODUCTOS");
        etiqueta.setBounds(400, 20, 200, 30);
        add(etiqueta);*/

        JTable table = new JTable(elementoProducto, columnas);

        table.setRowHeight(30);

        scroll = new JScrollPane(table);
        JPanel panelTabla = new JPanel(new BorderLayout());
        panelTabla.add(new JLabel("GRAFICAS PRODUCTOS", SwingConstants.CENTER), BorderLayout.NORTH);
        panelTabla.add(scroll, BorderLayout.CENTER);
        panelTabla.setPreferredSize(new Dimension(100, 200));
        add(panelTabla, BorderLayout.NORTH);
        
        //scroll.setBounds(200, 80, 500, 100);// modifica el tamano de la tabla junto con el scroll
        //add(scroll);

        DefaultCategoryDataset datos;

        
//------------------------codigo anterior----------------------------------------------------------------        
        
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

        //------------------------*****FINALIZA CODIGO NUEVO*********--------------------------------------------
        datos = new DefaultCategoryDataset();

        for (int i = 0; i < (rangos.length - 1); i++) {// k es el contador que lleva el conteo de las veces que se repite los numeros

            datos.addValue(frecuencia_precios[i], "1", rangos_finales[i]);

        }
        
        JPanel panelGraficas = new JPanel(new GridLayout(1, 2, 10, 10)); // 1 fila, 2 columnas
        
        JFreeChart chart = ChartFactory.createBarChart("Grafica de Barras", "Cantidad Productos", "Precio", datos,
                PlotOrientation.VERTICAL, true, true, false);

        barras = new ChartPanel(chart);
        panelGraficas.add(barras);

        add(panelGraficas, BorderLayout.CENTER); 
        //barras.setBounds(450, 200, 550, 400);
        //add(barras);

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
//--------------*********-----CONTIENE SOLUCION PARA GRAFICA DE PIE------------***************------------
        for (int e = 0; e < cantidad.length; e++) {// solo imprime los valores de manera descendente (pero repetidos)

            if ((cantidad[e] != 0) && (nombre_productos[e] != null)) {

                /*  System.out.println("La cantidad " + d + " = " + cantidad[d] +        // es int 
                        " y el nombre productos " + d + " = "+ nombre_productos[d]); // es String*/
            }
        }

//--------------*********-----CONTIENE SOLUCION PARA GRAFICA DE PIE------------***************-------------
        

        
        
        DefaultPieDataset pie = new DefaultPieDataset();

        for (int c = 0; c < 5; c++) {   // solo muestra 5 productos en la grafica
            // int
            pie.setValue(nombre_productos[c], cantidad[c]);

        }
        JFreeChart graficaPie = ChartFactory.createPieChart("Grafica de Pie", pie);

        Pie = new ChartPanel(graficaPie);
        panelGraficas.add(Pie);
        //Pie.setBounds(20, 200, 400, 400);
        //add(Pie);

    }// fin constructor LaminaDashboardProductos

}
