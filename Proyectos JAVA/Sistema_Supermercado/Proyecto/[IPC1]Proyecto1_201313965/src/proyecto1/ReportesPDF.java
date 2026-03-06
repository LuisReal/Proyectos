package proyecto1;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

public class ReportesPDF extends JFrame {

    private LaminaReportesPDF lamina_reportes;

    public ReportesPDF(Object[][] elementoVenta, Object[][] elementoProducto, String[][] arregloProductosMayores) {

        setTitle("Reportes");
        setBounds(200, 200, 400, 400);
        setResizable(false);

        lamina_reportes = new LaminaReportesPDF(elementoVenta, elementoProducto, arregloProductosMayores);
        add(lamina_reportes);

    }

    public Object[][] getElementoProducto() {
        return lamina_reportes.getElementoProducto();
    }

}


class LaminaReportesPDF extends JPanel {

    JButton mas_vendidos;
    JButton factura;
    JButton ventas_mayor;
    JButton consulta;
    JButton modificar;

    public static Object[][] elementoProducto;
    public static Object[][] elementoVenta;
    public static String[][] arregloProductosMayores; //se obtiene de VentanaVentas y de dashboardVentas

    public static ImageIcon icono;

    public LaminaReportesPDF(Object[][] elementoVenta, Object[][] elementoProducto, String[][] arregloProductosMayores) {

        LaminaReportesPDF.elementoVenta = elementoVenta;
        LaminaReportesPDF.elementoProducto = elementoProducto;
        LaminaReportesPDF.arregloProductosMayores = arregloProductosMayores;

        setLayout(null); //para poder controlar la posicion de los elementos manualmente

        factura = new JButton("Generar Factura");
        factura.setBounds(100, 50, 200, 30);
        add(factura);

        mas_vendidos = new JButton("Productos Mas Vendidos");
        mas_vendidos.setBounds(100, 100, 200, 30);
        add(mas_vendidos);

        ventas_mayor = new JButton("Ventas con Mayor Total");
        ventas_mayor.setBounds(100, 150, 200, 30);
        add(ventas_mayor);

        GenerarFactura factura1 = new GenerarFactura();
        factura.addActionListener(factura1);

        ProductosMasVendidos masVendidos = new ProductosMasVendidos();
        mas_vendidos.addActionListener(masVendidos);

        VentasMayorTotal miVenta = new VentasMayorTotal();
        ventas_mayor.addActionListener(miVenta);

    }

    public Object[][] getElementoProducto() {
        return elementoProducto;
    }

    private class GenerarFactura implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            ConsultarIDVenta consulta = new ConsultarIDVenta();
            consulta.setElementos(elementoVenta, elementoProducto);
            consulta.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            consulta.setVisible(true);

        }

    }

    private class ProductosMasVendidos implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent w) {

            PDDocument document = new PDDocument();
            PDPage page = new PDPage();

            document.addPage(page);
            try {
                PDPageContentStream cs = new PDPageContentStream(document, page);
                cs.setFont(PDType1Font.COURIER_BOLD_OBLIQUE, 14);
                cs.beginText(); //abre el documento para empezar a trabajar
                cs.newLineAtOffset(100, 700); //indica las coordenadas de donde debe empezar a escribir en la pagina

                cs.showText("Producto");

                cs.newLineAtOffset(150, 0);
                cs.showText("Cantidad");

                //String texto = "Productos: " + productos;
                for (int i = 0; i < 10; i++) {

                    if (arregloProductosMayores[i][0] != null) {

                        cs.newLineAtOffset(-150, -20);
                        cs.showText(arregloProductosMayores[i][0]);

                        cs.newLineAtOffset(150, 0);
                        cs.showText(arregloProductosMayores[i][1]);

                    }

                } // fin del for

                //cs.showText("Id Venta: " + idVenta + " NIT cliente: " + nitCliente + " Productos: " + productos + " Total Venta: " + total_venta);
                cs.endText();
                cs.close();

                File dir = new File("reportes"); //crea una carpeta donde se encuentre el jar
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                document.save("reportes/productos_mas_vendidos.pdf");
                document.close();
                JOptionPane.showMessageDialog(null, "PDF generado con exito en carpeta reportes");
            } catch (Exception e) {
                System.out.println("Error: " + e);
            }
        }
    }

    private class VentasMayorTotal implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent w) {

        }

    }

}

class ConsultarIDVenta extends JFrame {

    JLabel consulta;
    JTextField campo_consulta;
    JButton buscar;

    public static Object[][] elementoVenta;
    public static Object[][] elementoProducto;

    public ConsultarIDVenta() {

        setLayout(null);

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

        ConsultarIDVenta.elementoVenta = elementoVenta;
        ConsultarIDVenta.elementoProducto = elementoProducto;

    }

    private class Buscar implements ActionListener {

        String[] cadenaNombresProductos = new String[200];
        String nitCliente = "";
        double total_venta = 0;
        String productos = "";

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
                //JOptionPane.showMessageDialog(null, "El ID de la venta es: " + idVenta);

                setData(idVenta);

                JOptionPane.showMessageDialog(null, "Factura Generada con exito en carpeta reportes");

            }

        } // find metodo actionPerformed

        public void setData(String idVenta) {

            for (int j = 0; j < elementoVenta.length; j++) {

                if (elementoVenta[j][0] != null && idVenta.equals(elementoVenta[j][0].toString())) {

                    nitCliente = String.valueOf(elementoVenta[j][1]);
                    cadenaNombresProductos[j] = String.valueOf(elementoVenta[j][2]);
                    //cadenaCantidad[j] = String.valueOf(elementoVenta[j][2]);

                }

            } // fin del for

            //Obteniendo los nombres de los productos
            for (int i = 0; i < elementoVenta.length; i++) {

                if (elementoVenta[i][0] != null && idVenta.equals(elementoVenta[i][0].toString())) {

                    productos += cadenaNombresProductos[i] + " ; ";

                }

            } // fin del for

            PDDocument document = new PDDocument();
            PDPage page = new PDPage();

            document.addPage(page);
            try {
                PDPageContentStream cs = new PDPageContentStream(document, page);
                cs.setFont(PDType1Font.COURIER_BOLD_OBLIQUE, 14);
                cs.beginText(); //abre el documento para empezar a trabajar
                cs.newLineAtOffset(100, 700); //indica las coordenadas de donde debe empezar a escribir en la pagina

                cs.showText("Id Venta: " + idVenta);

                cs.newLineAtOffset(0, -20); //en donde se quedo el cursor(en X=100) le suma 0, X=100 y (en Y=680)
                cs.showText("NIT cliente: " + nitCliente);

                cs.newLineAtOffset(0, -20); //en donde se quedo el cursor(en X=100) le suma 0, X=100 y (en Y=660)
                cs.showText("Producto");

                cs.newLineAtOffset(150, 0); //en donde se quedo el cursor(en X=100) le suma 150, X=250 y (en Y=660)
                cs.showText("Cantidad");

                cs.newLineAtOffset(150, 0); //en donde se quedo el cursor(en 100) le suma 150, X=400 y (en Y=660)
                cs.showText("Precio");

                //String texto = "Productos: " + productos;
                for (int i = 0; i < elementoVenta.length; i++) {

                    if (elementoVenta[i][0] != null && idVenta.equals(elementoVenta[i][0].toString())) {
                        System.out.println("El ID de la venta es: " + idVenta);

                        for (int j = 0; j < elementoProducto.length; j++) {

                            if (elementoProducto[j][0] != null && (elementoVenta[i][2]).equals(elementoProducto[j][0])) { //si el nombre del producto coincide
                                System.out.println("El Producto es: " + elementoVenta[i][2]);

                                cs.newLineAtOffset(-300, -20);  //en donde se quedo el cursor(en 100) le resto 300, X=100 y (en Y=640 y en cada iteracion del for se le resta 20)
                                cs.showText(elementoVenta[i][2].toString());

                                double precio = Double.parseDouble(elementoProducto[j][1].toString()); // obtiene el precio del producto
                                System.out.println("El precio es: " + precio);

                                double cantidad_productos = Double.parseDouble(elementoVenta[i][3].toString()); // obtiene cantidad de productos vendidos
                                System.out.println("La cantidad de productos es: " + cantidad_productos);

                                cs.newLineAtOffset(150, 0); //en donde se quedo el cursor(en 100) le sumo 150, X=250 y (en Y=640)
                                cs.showText(String.valueOf((int) cantidad_productos));

                                cs.newLineAtOffset(150, 0); //en donde se quedo el cursor(en 250) le sumo 150, X=400 y (en Y=640)
                                cs.showText(String.valueOf(precio));

                                double venta = precio * cantidad_productos;
                                total_venta += venta;

                                System.out.println("El total venta es: " + total_venta);
                            }
                        }

                    }

                } // fin del for
                /*
                PDRectangle mediaBox = page.getMediaBox();
                float anchoPagina = mediaBox.getWidth();

                // define márgenes y ancho máximo de texto
                float margen = 100;
                float maxWidth = anchoPagina - 2 * margen;
                float fontSize = 14;
                float leading = 18; // espacio entre líneas

                String[] palabras = texto.split(" ");
                StringBuilder linea = new StringBuilder();

                for (String palabra : palabras) {
                    String temp = linea + palabra + " ";

                    // mide el ancho del texto actual
                    float anchoTexto = PDType1Font.COURIER_BOLD_OBLIQUE.getStringWidth(temp) / 1000 * fontSize;

                    // si se pasa del ancho máximo, imprime la línea y salta
                    if (anchoTexto > maxWidth) {
                        cs.showText(linea.toString());
                        cs.newLineAtOffset(0, -leading); // baja una línea
                        linea = new StringBuilder(palabra + " "); // nueva línea con la palabra que excedió
                    } else {
                        linea.append(palabra).append(" ");
                    }
                }

                cs.showText(linea.toString());
                cs.newLineAtOffset(0, -20);*/

                total_venta = Math.round(total_venta * 100.0) / 100.0;

                cs.newLineAtOffset(-300, -20); //en donde se quedo el cursor(en 400) le resto 300, X=100 y (en Y=depende de las iteraciones del ciclo for y se le resta 20)
                cs.showText("Total Venta");

                cs.newLineAtOffset(300, 0); //en donde se quedo el cursor(en 100) le sumo 300, X=300 y (en en Y=depende de las iteraciones del ciclo for y se le resta 20)
                cs.showText(String.valueOf(total_venta));

                //cs.showText("Id Venta: " + idVenta + " NIT cliente: " + nitCliente + " Productos: " + productos + " Total Venta: " + total_venta);
                cs.endText();
                cs.close();

                File dir = new File("reportes"); //crea una carpeta donde se encuentre el jar
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                document.save("reportes/factura" + idVenta + ".pdf");
                document.close();

            } catch (Exception e) {
                System.out.println("Error: " + e);
            }
        }

    }// fin private class Buscar

}
