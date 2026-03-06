package proyecto1;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class ModificarCliente extends JFrame {

    JLabel consulta;
    JTextField campo_consulta;
    JButton buscar;

    public static Object[][] elemento = new Object[100][5];

    public ModificarCliente() { // proviene de la clase VentanaCliente

        setTitle("Modificar Cliente");
        setResizable(false);
        setBounds(300, 200, 350, 220);

        setLayout(null);

        consulta = new JLabel("Modificar Cliente (Ingrese NIT)");
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

            for (int i = 0; i < elemento.length; i++) {

                if ((campo_consulta.getText()).equals(elemento[i][3])) {

                    String nit = campo_consulta.getText();

                    ModificaClientes modifica = new ModificaClientes(elemento, nit);
                    modifica.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    modifica.setVisible(true);

                    contador++;
                }
            }

            if (contador == 0) { //El nit no existe
                JOptionPane.showMessageDialog(null, "El NIT no existe");
            }

        }// fin metodo actionPerformed
    } // fin clase Buscar 

}// fin clase ModificarCliente

class ModificaClientes extends JFrame {

    public ModificaClientes(Object[][] elemento, String nit) {

        setTitle("Modificar Cliente");
        setResizable(false);
        setBounds(300, 200, 440, 370);

        LaminaModificaCliente mi_lamina = new LaminaModificaCliente(elemento, nit);
        add(mi_lamina);

    }

}

class LaminaModificaCliente extends JPanel {

    JLabel etiqueta_nombre;
    JLabel etiqueta_edad;
    JLabel etiqueta_sexo;
    JLabel etiqueta_nit;
    JLabel etiqueta_avatar;

    JTextField campo_nombre;
    JTextField campo_edad;
    JTextField campo_nit;
    JComboBox combo_sexo;
    JButton seleccionar;
    JButton guardar;

    JLabel avatar_foto;

    String[] nits = new String[100];
    String[] edad = new String[100];
    String[] sexo = new String[100];
    String[] cadenaNombres = new String[100];
    String[] foto = new String[100];
    Icon[] iconos = new Icon[100];

    String nit = "";
    String foto_seleccionada = "";

    public static Object[][] elemento = new Object[100][5];

    public Image imagen;
    public Icon icono;

    public LaminaModificaCliente(Object[][] elemento, String nit) {

        setLayout(null);

        this.nit = nit;

        this.elemento = elemento;

        etiqueta_nombre = new JLabel("Ingrese Nombre ");
        etiqueta_nombre.setBounds(30, 20, 100, 30);
        add(etiqueta_nombre);

        campo_nombre = new JTextField();
        campo_nombre.setBounds(200, 20, 150, 30);
        add(campo_nombre);

        etiqueta_edad = new JLabel("Ingrese Edad ");
        etiqueta_edad.setBounds(30, 70, 100, 30);
        add(etiqueta_edad);

        campo_edad = new JTextField();
        campo_edad.setBounds(200, 70, 150, 30);
        add(campo_edad);

        etiqueta_sexo = new JLabel("Ingrese Sexo ");
        etiqueta_sexo.setBounds(30, 120, 100, 30);
        add(etiqueta_sexo);

        combo_sexo = new JComboBox();
        combo_sexo.addItem("M");
        combo_sexo.addItem("F");
        combo_sexo.setBounds(200, 120, 150, 30);
        add(combo_sexo);

        etiqueta_nit = new JLabel("Ingrese NIT ");
        etiqueta_nit.setBounds(30, 170, 100, 30);
        add(etiqueta_nit);

        campo_nit = new JTextField(nit);
        campo_nit.setBounds(200, 170, 150, 30);
        add(campo_nit);

        etiqueta_avatar = new JLabel("Seleccione Avatar ");
        etiqueta_avatar.setBounds(30, 220, 150, 30);
        add(etiqueta_avatar);

        seleccionar = new JButton("Seleccionar");
        seleccionar.setBounds(200, 220, 150, 30);
        add(seleccionar);

        guardar = new JButton("Guardar");
        guardar.setBounds(150, 270, 100, 30);
        add(guardar);

        Guardar guarda = new Guardar();
        guardar.addActionListener(guarda);

        Seleccionar seleccion = new Seleccionar();
        seleccionar.addActionListener(seleccion);

    }

    private class Guardar implements ActionListener {

        public void actionPerformed(ActionEvent w) {

            if (nit.equals(campo_nit.getText())) {

                for (int i = 0; i < elemento.length; i++) {

                    if (elemento[i][3] != null && nit.equals(elemento[i][3])) {

                        elemento[i][0] = campo_nombre.getText();
                        elemento[i][1] = campo_edad.getText();
                        elemento[i][2] = combo_sexo.getSelectedItem();
                        elemento[i][3] = campo_nit.getText();
                        elemento[i][4] = foto_seleccionada;

                    }

                } // fin for

            } else {

                boolean existe = false;

                for (int d = 0; d < elemento.length; d++) {

                    if (campo_nit.getText().equals(elemento[d][3])) {

                        System.out.println("El NIT ingresado ya existe en la posicion " + d + " " + elemento[d][3]);
                        JOptionPane.showMessageDialog(null, "El NIT ingresado ya existe");

                        existe = true;
                    }

                }

                if (existe == false) { // si el producto no existe
                    
                    for (int i = 0; i < elemento.length; i++) {

                        if (elemento[i][3] != null && nit.equals(elemento[i][3])) {

                            elemento[i][0] = campo_nombre.getText();
                            elemento[i][1] = campo_edad.getText();
                            elemento[i][2] = combo_sexo.getSelectedItem();
                            elemento[i][3] = campo_nit.getText();
                            elemento[i][4] = foto_seleccionada;

                        }

                    } // fin for
                }

            }

           

            campo_nombre.setText(null);
            campo_edad.setText(null);
            campo_nit.setText(null);

            for (int j = 0; j < elemento.length; j++) {

                for (int m = 0; m < 5; m++) {

                    if ((elemento[j][m]) != null) {

                        System.out.println("El elemento " + j + " " + m + " = " + elemento[j][m]);
                    }
                }
            }

        } // fin del metodo actionPerformed

    } // fin clase Guardar

    private class Seleccionar implements ActionListener {

        public void actionPerformed(ActionEvent e) {

            JFileChooser chooser = new JFileChooser(); //automaticamente se abre en Documents
            chooser.showOpenDialog(null);
            foto_seleccionada = chooser.getSelectedFile().toString();

            String rutaCorregida = foto_seleccionada.replace("\\", "/");
            int posicion = rutaCorregida.indexOf("src/");

            if (posicion != -1) {
                foto_seleccionada = rutaCorregida.substring(posicion + 3); // incluye la barra inicial de esta forma /clientes/C0.jpg
            }

            System.out.println("la ruta de la foto seleccionada " + foto_seleccionada);
        }

    }

}// fin LaminaModificaCliiente
