
package proyecto1;

import java.awt.event.*;
import javax.swing.*;
import java.awt.*;


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
        panelCentral.setBorder(BorderFactory.createLineBorder(Color.GREEN)); //borde del panel de color negro
        
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

