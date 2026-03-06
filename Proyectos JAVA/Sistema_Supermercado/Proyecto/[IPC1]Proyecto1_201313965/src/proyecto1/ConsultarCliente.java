package proyecto1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.imageio.ImageIO;

public class ConsultarCliente extends JFrame {

    JLabel consulta;
    JTextField campo_consulta;
    JButton buscar;

    public static Object[][] elemento = new Object[100][5];

    public ConsultarCliente() {

        setLayout(null);
        setResizable(false);
        setTitle("Consulta Cliente");
        setBounds(300, 200, 350, 220);

        consulta = new JLabel("Consulta de Cliente (Ingrese NIT)");
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

    public void setElemento(Object[][] elemento) {

        this.elemento = elemento; // proviene de la clase VentanaClientes

    }

    private class Buscar implements ActionListener {

        public void actionPerformed(ActionEvent w) {
            int contador = 0;
            System.out.println("******************** ESTOY EN CONSULTAR CLIENTE **************************");
            for (int i = 0; i < elemento.length; i++) {

                if (elemento[i][3] != null && (campo_consulta.getText()).equals(elemento[i][3].toString())) {
                    System.out.println("******************** ESTOY EN CONSULTAR CLIENTE CICLO FOR **************************");
                    String nit = campo_consulta.getText();

                    DatosClientes mostrar = new DatosClientes(elemento, nit); //pasa a la ventana DatosClientes(ir hacia abajo)
                    mostrar.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    mostrar.setVisible(true);
                    System.out.println("******************** ESTOY EN CONSULTAR CLIENTE CICLO FOR 2 **************************");
                    contador++;
                }
            }

            if (contador == 0) {

                JOptionPane.showMessageDialog(null, "El NIT no es correcto");
            }

        } // find metodo actionPerformed

    }// fin private class Buscar

} // fin de la clase Consultar

class DatosClientes extends JFrame {

    public DatosClientes(Object[][] elemento, String nit) {

        setTitle("Datos de Cliente");
        setResizable(false);
        setBounds(300, 200, 400, 500);

        LaminaDatosClientes miLamina = new LaminaDatosClientes(elemento, nit);
        add(miLamina);

    }

}

class LaminaDatosClientes extends JPanel {

    JLabel etiqueta_nombre;
    JLabel etiqueta_edad;
    JLabel etiqueta_sexo;
    JLabel etiqueta_nit;
    JLabel etiqueta_avatar;

    JLabel mostrar_nombre;
    JLabel mostrar_edad;
    JLabel mostrar_sexo;
    JLabel mostrar_nit;

    JLabel avatar_foto;

    JButton eliminar;

    public static Object[][] elemento = new Object[100][5];

    String[] nits = new String[100];
    String[] edad = new String[100];
    String[] sexo = new String[100];
    String[] cadenaNombres = new String[100];
    String[] foto = new String[100];
    Icon[] iconos = new Icon[100];

    String nit = "";
    public Image imagen;
    public Icon icono;

    public LaminaDatosClientes(Object[][] elemento, String nit) {

        setLayout(null);

        this.elemento = elemento;
        this.nit = nit;

        etiqueta_nombre = new JLabel("Nombre ");
        etiqueta_nombre.setBounds(30, 20, 100, 30);
        add(etiqueta_nombre);

        etiqueta_edad = new JLabel("Edad ");
        etiqueta_edad.setBounds(30, 70, 100, 30);
        add(etiqueta_edad);

        etiqueta_sexo = new JLabel("Sexo ");
        etiqueta_sexo.setBounds(30, 120, 100, 30);
        add(etiqueta_sexo);

        etiqueta_nit = new JLabel("NIT ");
        etiqueta_nit.setBounds(30, 170, 100, 30);
        add(etiqueta_nit);

        etiqueta_avatar = new JLabel("Avatar ");
        etiqueta_avatar.setBounds(30, 220, 100, 30);
        add(etiqueta_avatar);

        eliminar = new JButton("Eliminar");
        eliminar.setBounds(150, 390, 100, 30);
        add(eliminar);

        Eliminar elimina = new Eliminar();
        eliminar.addActionListener(elimina);

        for (int j = 0; j < elemento.length; j++) {

            if (elemento[j][0] != null) {

                cadenaNombres[j] = String.valueOf(elemento[j][0]);
                edad[j] = String.valueOf(elemento[j][1]);
                sexo[j] = String.valueOf(elemento[j][2]);
                nits[j] = String.valueOf(elemento[j][3]);
                foto[j] = String.valueOf(elemento[j][4]);
                System.out.println("\nLa ruta de la imagen es: " + foto[j]);

                try {

                    imagen = ImageIO.read(getClass().getResource(foto[j])); // se maneja este tipo de ruta /clientes/C23.jpg

                    iconos[j] = new ImageIcon(new ImageIcon(imagen).getImage().getScaledInstance(200, 150, Image.SCALE_DEFAULT));

                    //icono1 = icono;
                } catch (IOException a) {

                    System.out.println("No se encontro la imagen");
                }

            }

        }

        for (int i = 0; i < elemento.length; i++) {

            if (nit.equals(elemento[i][3])) {

                mostrar_nombre = new JLabel(cadenaNombres[i]);
                mostrar_nombre.setBounds(150, 20, 100, 30);
                add(mostrar_nombre);

                mostrar_edad = new JLabel(edad[i]);
                mostrar_edad.setBounds(150, 70, 100, 30);
                add(mostrar_edad);

                mostrar_sexo = new JLabel(sexo[i]);
                mostrar_sexo.setBounds(150, 120, 100, 30);
                add(mostrar_sexo);

                mostrar_nit = new JLabel(nits[i]);
                mostrar_nit.setBounds(150, 170, 100, 30);
                add(mostrar_nit);

                avatar_foto = new JLabel(iconos[i]);
                avatar_foto.setBounds(150, 220, 200, 150);
                add(avatar_foto);

            }

        }

    } // fin del constructor LaminaDatosClientes

    private class Eliminar implements ActionListener {

        public void actionPerformed(ActionEvent w) {

            mostrar_nombre.setText("");
            mostrar_edad.setText("");
            mostrar_sexo.setText("");
            mostrar_nit.setText("");
            avatar_foto.setIcon(null); // elimina la imagen de la foto

            for (int i = 0; i < elemento.length; i++) {

                if (nit.equals(elemento[i][3])) { // busca el nit ingresado 

                    for (int j = 0; j < 5; j++) {

                        if ((elemento[i][j]) != null) {

                            elemento[i][j] = null;

                            //System.out.println("El elemento " + i + " " + j + " " + elemento[i][j]);
                        }

                    }// fin del for int j=0
                }
            } // fin del for int i=0

            for (int p = 0; p < elemento.length; p++) {

                for (int r = 0; r < 5; r++) {

                    System.out.println("El elemento " + p + " " + r + " = " + elemento[p][r]);

                }

            }
        } // fin del metodo actionPerformed

    }// fin clase Eliminar

}
