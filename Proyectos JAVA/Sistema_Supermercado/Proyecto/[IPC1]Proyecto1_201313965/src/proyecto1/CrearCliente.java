package proyecto1;

import javax.swing.*;

import java.awt.event.*;
import java.io.File;


public class CrearCliente extends JFrame {

    public CrearCliente() {

        setTitle("Nuevo Cliente");
        setResizable(false);
        setBounds(400, 100, 450, 400);

        Lamina3 lamina = new Lamina3();
        add(lamina);

    }

}

class Lamina3 extends JPanel {

    // public static Object[][] nuevos = new Object[100][5];
    public static Object[][] elemento = new Object[100][5]; // declarar static para que pueda recivir los elementos de la carga masiva

    //public int z = 0;
    public int x = 0;
    String avatar = "";

    JLabel label_nombre;
    JLabel label_edad;
    JLabel label_sexo;
    JLabel label_nit;
    JLabel label_avatar;

    JTextField campo_nombre;
    JTextField campo_edad;
    JTextField campo_nit;
    JComboBox combo_sexo;
    JButton seleccionar;
    JButton guardar;

    JFileChooser chooser;
    

    public Lamina3() {

        setLayout(null);

        
        label_nombre = new JLabel("Nombre");
        label_nombre.setBounds(100, 50, 150, 30);
        add(label_nombre);

        campo_nombre = new JTextField();
        campo_nombre.setBounds(200, 50, 150, 30);
        add(campo_nombre);

        label_edad = new JLabel("Edad");
        label_edad.setBounds(100, 100, 150, 30);
        add(label_edad);

        campo_edad = new JTextField();
        campo_edad.setBounds(200, 100, 150, 30);
        add(campo_edad);

        label_sexo = new JLabel("Sexo");
        label_sexo.setBounds(100, 150, 150, 30);
        add(label_sexo);

        combo_sexo = new JComboBox();
        combo_sexo.setBounds(200, 150, 150, 30);
        combo_sexo.addItem("M");
        combo_sexo.addItem("F");
        add(combo_sexo);

        label_nit = new JLabel("Nit");
        label_nit.setBounds(100, 200, 150, 30);
        add(label_nit);

        campo_nit = new JTextField();
        campo_nit.setBounds(200, 200, 150, 30);
        add(campo_nit);

        label_avatar = new JLabel("Avatar");
        label_avatar.setBounds(100, 250, 150, 30);
        add(label_avatar);

        seleccionar = new JButton("Seleccionar");
        seleccionar.setBounds(200, 250, 150, 30);
        add(seleccionar);

        guardar = new JButton("Guardar");
        guardar.setBounds(150, 310, 100, 30);
        add(guardar);

        Guardar guardarDatos = new Guardar();
        guardar.addActionListener(guardarDatos);

        Seleccionar chooser = new Seleccionar(); //automaticamente se abre en Documents
        seleccionar.addActionListener(chooser);

    }

    public void setElemento(Object[][] elemento) {

        this.elemento = elemento; // proviene de la clase VentanaClientes

    }

    private class Seleccionar implements ActionListener {

        public void actionPerformed(ActionEvent e) {

            chooser = new JFileChooser(); //automaticamente se abre en Documents
            chooser.showOpenDialog(null);

            avatar = chooser.getSelectedFile().toString();
            
            String rutaCorregida = avatar.replace("\\", "/");
            int posicion = rutaCorregida.indexOf("src/");

            if (posicion != -1) {
                avatar = rutaCorregida.substring(posicion + 3); // incluye la barra inicial de esta forma /clientes/C0.jpg
            }
            
            System.out.println("La ruta de la imagen del cliente es: " + avatar);
        }

    }// FIN DE LA CLASE Seleccionar

    private class Guardar implements ActionListener {

        public void actionPerformed(ActionEvent w) {

            int contador = 0;

            for (int d = 0; d < elemento.length; d++) {

                if (elemento[d][3] != null && campo_nit.getText().equals(elemento[d][3])) {

                    System.out.println("El NIT ingresado ya existe en la posicion " + d + " " + elemento[d][3]);
                    JOptionPane.showMessageDialog(null, "El NIT ingresado ya existe");

                    contador++; // suma 1 al contador si ya existe el NIT
                }

            }

            int z = 0;

            for (int j = 0; j < elemento.length; j++) {

                if (contador != 1) { // si ya existe el NIT el contador ==1 y nunca entra aca 

                    if (elemento[j][3] == null) { // evalua si el arreglo esta vacio para almacenar los datos

                        z++;// incrementa 1 para poder entrar a la siguiente instruccion y solo guarda una vez los datos

                        if (z == 1) {

                            elemento[j][x] = campo_nombre.getText();
                            System.out.println("La posicion elemento " + j + "," + x + " = " + elemento[j][x]);

                            elemento[j][x + 1] = campo_edad.getText();
                            System.out.println("La posicion elemento " + j + "," + (x + 1) + " = " + elemento[j][x + 1]);

                            elemento[j][x + 2] = combo_sexo.getSelectedItem();
                            System.out.println("La posicion elemento " + j + "," + (x + 2) + " = " + elemento[j][x + 2]);

                            elemento[j][x + 3] = campo_nit.getText();
                            System.out.println("La posicion elemento " + j + "," + (x + 3) + " = " + elemento[j][x + 3]);

                            elemento[j][x + 4] = avatar; // se almacena la ruta de la foto seleccionada
                            System.out.println("\n*****************Estoy mostrando la ruta en CrearCliente");
                            JOptionPane.showMessageDialog(null, "La ruta de la imagen es: " + avatar);

                            System.out.println("La posicion elemento " + j + "," + (x + 4) + " = " + elemento[j][x + 4]);

                        }
                    }// if (elemento[j][3] == null)

                }

            }

            campo_nombre.setText(null);
            campo_edad.setText(null);
            campo_nit.setText(null);

        }// fin del metodo actionPerformed

    }

    public Object[][] getElemento() {

        return elemento; // envia el elemento a la clase VentanaClientes

    }

}// FIN DE LA CLASE Lamina3
