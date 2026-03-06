package proyecto1;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.*;

public class Administracion extends JFrame {

    JButton clientes;
    JButton productos;
    JButton ventas;
    JButton reportes;
    JButton boton_login;
    private Login loginRef;
    private VentanaClientes ventana_clientes;
    private VentanaProductos ventana_productos;
    private VentanaVentas ventana_ventas;
    private ReportesPDF ventana_reportes;
    private String[][] arregloProductosMayores;

    private Object[][] elementoProducto;
    private Object[][] elementoVentas;

    public Administracion(Login login) {
        this.loginRef = login;

        getContentPane().setBackground(new Color(173, 216, 230));

        setLayout(null); //para poder controlar la posicion de los elementos manualmente con setBounds
        setBounds(250, 300, 500, 400);
        setTitle("ADMINISTRACION"); 
        setResizable(false);

        clientes = new JButton("Administracion de Clientes");
        clientes.setBounds(100, 50, 300, 30);
        clientes.setFocusPainted(false);
        clientes.setBackground(new Color(173, 216, 150));
        clientes.setForeground(Color.WHITE);
        clientes.setFont(new Font("Arial", Font.BOLD, 16));
        add(clientes);

        productos = new JButton("Administracion de Productos");
        productos.setBounds(100, 100, 300, 30);
        productos.setBackground(new Color(173, 216, 150));
        productos.setForeground(Color.WHITE);
        productos.setFont(new Font("Arial", Font.BOLD, 16));

        productos.setFocusPainted(false);
        add(productos);

        ventas = new JButton("Administracion de Ventas");
        ventas.setBounds(100, 150, 300, 30);
        ventas.setBackground(new Color(173, 216, 150));
        ventas.setForeground(Color.WHITE);
        ventas.setFont(new Font("Arial", Font.BOLD, 16));
        add(ventas);

        reportes = new JButton("Reportes");
        reportes.setBounds(100, 200, 300, 30);
        reportes.setBackground(new Color(0, 153, 0));
        reportes.setForeground(Color.WHITE);
        reportes.setFont(new Font("Arial", Font.BOLD, 16));
        add(reportes);

        boton_login = new JButton("Login");
        boton_login.setBounds(150, 250, 200, 30);
        boton_login.setBackground(new Color(0, 153, 255));
        boton_login.setForeground(Color.WHITE);
        boton_login.setFont(new Font("Arial", Font.BOLD, 16));
        add(boton_login);

        Clientes clientes1 = new Clientes();
        clientes.addActionListener(clientes1);

        Productos productos1 = new Productos();
        productos.addActionListener(productos1);

        Ventas ventas1 = new Ventas();
        ventas.addActionListener(ventas1);

        Reportes reportes1 = new Reportes();
        reportes.addActionListener(reportes1);

        BotonLogin ventana_login = new BotonLogin();
        boton_login.addActionListener(ventana_login);

    }

    private class BotonLogin implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            loginRef.setVisible(true);
            dispose();

        }

    }

    private class Clientes implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            ventana_clientes = new VentanaClientes();

            ventana_clientes.setVisible(true);

        }

    }

    private class Productos implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            ventana_productos = new VentanaProductos();
            ventana_productos.setVisible(true);
            //System.out.println("Obteniendo elementoProducto");

        }

    }

    private class Ventas implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent v) {

            if (ventana_productos != null) {
                elementoProducto = ventana_productos.getElementoProducto();

                if (elementoProducto != null) {
                    ventana_ventas = new VentanaVentas(elementoProducto);

                    ventana_ventas.setVisible(true);
                } else {
                    System.out.println("Todavia no existen productos");
                }
            } else {
                JOptionPane.showMessageDialog(null, "No ha cargado los productos todavia");
            }

        }

    }

    private class Reportes implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent v) {

            elementoVentas = ventana_ventas.getElementoVentas();
            arregloProductosMayores = ventana_ventas.getArregloProductos();

            if (ventana_productos != null && ventana_ventas != null) {

                ventana_reportes = new ReportesPDF(elementoVentas, elementoProducto, arregloProductosMayores);

                ventana_reportes.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "No ha cargado los productos o las ventas todavia");
            }

        }

    }

}
