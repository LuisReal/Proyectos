package proyecto1;

import javax.swing.*;
import java.awt.event.*;


public class VentanaProductos extends JFrame {
    
    private LaminaVentanaProductos lamina_productos;
    
    public VentanaProductos() {

        setTitle("Administracion de Productos");
        setBounds(200, 200, 400, 400);
        setResizable(false);

        lamina_productos = new LaminaVentanaProductos();
        add(lamina_productos);

    }
    
    public Object[][] getElementoProducto() {
        return lamina_productos.getElementoProducto();
    }

}

class LaminaVentanaProductos extends JPanel {

    JButton dashboard;
    JButton carga;
    JButton creacion_productos;
    JButton consulta;
    JButton modificar;

    public static Object[][] elementoProducto;
    public static ImageIcon icono;

    public LaminaVentanaProductos() {

        setLayout(null); //para poder controlar la posicion de los elementos manualmente
        
        carga = new JButton("Cargar Productos");
        carga.setBounds(100, 50, 200, 30);
        add(carga);
        
        dashboard = new JButton("Dashboard Productos");
        dashboard.setBounds(100, 100, 200, 30);
        add(dashboard);

        creacion_productos = new JButton("Creacion Productos");
        creacion_productos.setBounds(100, 150, 200, 30);
        add(creacion_productos);

        consulta = new JButton("Consulta Productos");
        consulta.setBounds(100, 200, 200, 30);
        add(consulta);

        modificar = new JButton("Modificar Productos");
        modificar.setBounds(100, 250, 200, 30);
        add(modificar);

        CargarProductos carga1 = new CargarProductos();
        carga.addActionListener(carga1);

        DashboardProductosOyente dash1 = new DashboardProductosOyente();
        dashboard.addActionListener(dash1);

        CreacionProductos miProducto = new CreacionProductos();
        creacion_productos.addActionListener(miProducto);

        ConsultarProductos consultarP = new ConsultarProductos();
        consulta.addActionListener(consultarP);

        Modificar modificaP = new Modificar();
        modificar.addActionListener(modificaP);

    }

    public Object[][] getElementoProducto() {
        return elementoProducto;
    }

    private class CargarProductos implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            CargaMasivaProductos carga = new CargaMasivaProductos();
            elementoProducto = carga.elementoProducto;
        }

    }

    private class DashboardProductosOyente implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            if (elementoProducto == null) {

                JOptionPane.showMessageDialog(null, "No ha cargado los datos todavia");

            } else {
                DashboardProductos dash2 = new DashboardProductos(elementoProducto);
                dash2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                dash2.setVisible(true);

            }

        }
    }

    private class CreacionProductos implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent w) {

            CrearProducto nuevo_producto = new CrearProducto();
            nuevo_producto.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            nuevo_producto.setVisible(true);

            LaminaCrearProducto crear1 = new LaminaCrearProducto();
            crear1.setElemento(elementoProducto); // envia a la clase CrearProducto (a la Lamina)
        }

    }

    private class ConsultarProductos implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent w) {

            ConsultarProducto miConsulta = new ConsultarProducto();
            miConsulta.setElemento(elementoProducto);
            miConsulta.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            miConsulta.setVisible(true);

        }

    }

    private class Modificar implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            ModificarProducto modifica5 = new ModificarProducto(); // envia todos los elementos CargaMasiva y NuevoCliente
            modifica5.setElemento(elementoProducto);
            modifica5.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            modifica5.setVisible(true);
        }

    }
}
