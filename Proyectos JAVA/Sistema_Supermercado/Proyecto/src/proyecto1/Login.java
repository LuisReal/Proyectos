package proyecto1;

import java.awt.event.*;
import javax.swing.*;
import java.awt.*;

public class Login extends JFrame {

    //ArregloUsuarios[] usuarios_nuevos = new ArregloUsuarios[10];
    public Login() {

        setTitle("Login");
        setBounds(300, 200, 600, 340);
        setResizable(false);
        Panel panel = new Panel(this);
        add(panel);
    }
}

class Panel extends JPanel {

    /*Declarar obligatoriamente public y static el ArregloUsuarios para poder acceder a este desde la clase
       privada interna private class Ingresar */
    public static ArregloUsuarios[] usuarios1 = new ArregloUsuarios[10];
    
    private JTextField campo1; // contiene el nombre del usuario y NO se declara como static 
    private JPasswordField campo2;// contiene la contrasena y NO se declara como static
    JLabel usuario;
    JLabel contrasena;
    JButton registrar;
    JButton ingresar;
    JLabel recuperar1;
    JButton recuperar2;

    int m = 1;
    
    private Login loginRef;
    private Image fondo; 
    
    public Panel(Login login) {
        
        this.loginRef = login;
        fondo = new ImageIcon(getClass().getResource("/images/fondo.jpg")).getImage();

        
        usuarios1[0] = new ArregloUsuarios("", "admin", "admin123", "");
        usuarios1[1] = new ArregloUsuarios("", "", "", "");
        usuarios1[2] = new ArregloUsuarios("", "", "", "");
        usuarios1[3] = new ArregloUsuarios("", "", "", "");
        usuarios1[4] = new ArregloUsuarios("", "", "", "");
        usuarios1[5] = new ArregloUsuarios("", "", "", "");
        usuarios1[6] = new ArregloUsuarios("", "", "", "");
        usuarios1[7] = new ArregloUsuarios("", "", "", "");
        usuarios1[8] = new ArregloUsuarios("", "", "", "");
        usuarios1[9] = new ArregloUsuarios("", "", "", "");

        //usuarios1 = Proyecto1.usuarios_nuevos;
        setLayout(null);// Para poder colocar los componentes en la lamina donde yo quiera

        usuario = new JLabel("Usuario");
        usuario.setBounds(150, 60, 100, 30);
        usuario.setForeground(Color.BLACK);
        usuario.setFont(new Font("Arial", Font.BOLD, 16));
        add(usuario);

        campo1 = new JTextField("");// contiene el nombre de usuario
        campo1.setBounds(250, 60, 150, 25);
        add(campo1);

        contrasena = new JLabel("Contrasena");
        contrasena.setBounds(150, 100, 100, 30);
        contrasena.setForeground(Color.BLACK);
        contrasena.setFont(new Font("Arial", Font.BOLD, 16));
        add(contrasena);

        campo2 = new JPasswordField(""); // contiene la contrasena del usuario y la oculta
        campo2.setBounds(250, 100, 150, 25);
        campo2.setEchoChar('•');
        add(campo2);

        ingresar = new JButton("Ingresar");
        ingresar.setBounds(150, 200, 100, 25);
        add(ingresar);

        registrar = new JButton("Crear Usuario");
        registrar.setBounds(280, 200, 150, 25);
        add(registrar);
        
        

        RegistrarUsuarios oyente = new RegistrarUsuarios();
        registrar.addActionListener(oyente);

        Ingresar oyente_ingresar = new Ingresar();
        ingresar.addActionListener(oyente_ingresar);

    }
    
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // 🔹 Dibujar la imagen del fondo ajustada al tamaño del panel
        g.drawImage(fondo, 0, 0, getWidth(), getHeight(), this);
    }

    public void setArreglo(ArregloUsuarios[] arreglo) {

        usuarios1[1] = arreglo[1];
        usuarios1[2] = arreglo[2];
        usuarios1[3] = arreglo[3];
        usuarios1[4] = arreglo[4];
        usuarios1[5] = arreglo[5];
        usuarios1[6] = arreglo[6];
        usuarios1[7] = arreglo[7];
        usuarios1[8] = arreglo[8];
        usuarios1[9] = arreglo[9];

    }

    private class Ingresar implements ActionListener {

        public void actionPerformed(ActionEvent e) {

            for (int i = 0; i < 10; i++) {

                if ((campo1.getText().equals(usuarios1[i].getUsuario()) == true)
                        & (campo2.getText().equals(usuarios1[i].getContrasena())) == true) {

                   /* System.out.println("La posicion " + i + " El campo1 " + campo1.getText()
                            + " El usuario " + usuarios1[i].getUsuario());*/
                    
                    campo1.setText("");
                    campo2.setText("");
                    
                    Administracion administracion = new Administracion(loginRef);

                    administracion.setVisible(true);
                    administracion.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                    
                    loginRef.setVisible(false);
                    
                    break;
                }

            }// fin del for
            
            
            
            int contador = 0;
            boolean boo = false;

            for (int i = 0; i < 10; i++) {
                /*evalua el usuario y la contrasena ingresados con el arreglo
                si no encuentra el usuario y la contrasena imprime el mensaje usuario o contrasena incorrectos*/

                boo = ((campo1.getText().equals(usuarios1[i].getUsuario()))
                        & campo2.getText().equals(usuarios1[i].getContrasena()));

                // System.out.println(boo);
                if (boo == false) {

                    if (contador == 9) { // muestra el mensaje hasta que contador sea igual a 2 (boo = false todos son falsos)

                      int obj = JOptionPane.showConfirmDialog(null, "Usuario o contrasena Incorrectos \n Desea recuperar"
                                + " la contrasena? \n\n");
                      
                      if( obj == 0){
                      
                          for( int j=0; j<10; j++){
                              
                             if((campo1.getText().equals(usuarios1[j].getUsuario()))){
                                 
                                 JOptionPane.showMessageDialog(null, "Contrasena: "+usuarios1[j].getContrasena());
                             
                             } 
                          
                          }
                          
                      }

                        campo1.setText(null);
                        campo2.setText(null);

                    }

                    contador++; // suma 1 al contador si boo es false
                }

            }// FIN DEL FOR

            m++;

        }// FIN DEL METODO ACTION PERFORMED

    }
    
    

    private class RegistrarUsuarios implements ActionListener {

        public void actionPerformed(ActionEvent e) {

            Usuarios usuarios = new Usuarios(Panel.this);

            usuarios.setVisible(true);

        }
    }

}

class Usuarios extends JFrame {

    JLabel usuario;
    JTextField campo_usuario;
    JLabel nombre;
    JTextField campo_nombre;
    JLabel contrasena;
    JPasswordField campo_contrasena;
    JLabel confirmar_contrasena;
    
    JPasswordField campo_confirmar_contrasena;
    JButton registrar;
    

    int k = 1;
    public static ArregloUsuarios[] usuarios2 = new ArregloUsuarios[10];
    private Panel pasar;

    public Usuarios(Panel panel) {
        
        this.pasar = panel;
        
        usuarios2[0] = new ArregloUsuarios("", "admin", "admin123", "");
        usuarios2[1] = new ArregloUsuarios("", "", "", "");
        usuarios2[2] = new ArregloUsuarios("", "", "", "");
        usuarios2[3] = new ArregloUsuarios("", "", "", "");
        usuarios2[4] = new ArregloUsuarios("", "", "", "");
        usuarios2[5] = new ArregloUsuarios("", "", "", "");
        usuarios2[6] = new ArregloUsuarios("", "", "", "");
        usuarios2[7] = new ArregloUsuarios("", "", "", "");
        usuarios2[8] = new ArregloUsuarios("", "", "", "");
        usuarios2[9] = new ArregloUsuarios("", "", "", "");

        //usuarios2 = Proyecto1.usuarios_nuevos;
        setTitle("Registro de Usuarios");
        setBounds(750, 200, 400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLayout(null);

        usuario = new JLabel("Usuario");
        usuario.setBounds(40, 60, 100, 30);
        add(usuario);

        campo_usuario = new JTextField();
        campo_usuario.setBounds(200, 60, 120, 25);
        add(campo_usuario);

        nombre = new JLabel("Nombre");
        nombre.setBounds(40, 100, 100, 30);
        add(nombre);

        campo_nombre = new JTextField();
        campo_nombre.setBounds(200, 100, 120, 25);
        add(campo_nombre);

        contrasena = new JLabel("Contrasena");
        contrasena.setBounds(40, 140, 100, 30);
        add(contrasena);

        campo_contrasena = new JPasswordField();
        campo_contrasena.setBounds(200, 140, 120, 25);
        campo_contrasena.setEchoChar('•');
        add(campo_contrasena);

        confirmar_contrasena = new JLabel("Confirmar Contrasena");
        confirmar_contrasena.setBounds(40, 180, 150, 30);
        add(confirmar_contrasena);

        campo_confirmar_contrasena = new JPasswordField();
        campo_confirmar_contrasena.setBounds(200, 180, 120, 25);
        campo_confirmar_contrasena.setEchoChar('•');//alt+7 = •
        add(campo_confirmar_contrasena);

        registrar = new JButton("Registrar");
        registrar.setBounds(80, 280, 100, 30);
        add(registrar);

        
        //System.out.println("usuario " + usuarios_nuevos[0].getUsuario());
        Registrar registro = new Registrar();
        registrar.addActionListener(registro);

    }

    private class Registrar implements ActionListener {

        public void actionPerformed(ActionEvent e) {

            boolean bool = false;
            int contador = 0;
            for (int i = 0; i < 10; i++) {
                // el if evalua si el usuario ingresado ya existe en el arreglo
                if ((campo_usuario.getText()).equals(usuarios2[i].getUsuario())) {

                    JOptionPane.showMessageDialog(null, "El usuario ingresado ya existe");

                } else if (bool == false) {

                   if (contador == 9) {// evalua si las 3 veces es falso y entra

                        if ((campo_contrasena.getText()).equals(campo_confirmar_contrasena.getText())) {

                            usuarios2[k] = new ArregloUsuarios((campo_nombre.getText()), (campo_usuario.getText()),
                                    (campo_contrasena.getText()), (campo_confirmar_contrasena.getText()));

                            System.out.println("usuario " + k + " " + usuarios2[k].getUsuario()
                                    + " nombre " + k + " " + usuarios2[k].getNombre()
                                    + " contrasena " + k + " " + usuarios2[k].getContrasena());

                            pasar.setArreglo(usuarios2);
                            /* pasa el arreglo de usuarios a la ventana LOGIN clase panel*/

                            k++;

                            if (k == 10) {

                                dispose(); // cierra la ventana

                            }

                        } // Fin del if que evalua los campos de contrasenas ingresadas
                        else {

                            JOptionPane.showMessageDialog(null, "Las contrasenas no coinciden");

                        } // FIN DEL ELSE

                        campo_usuario.setText(null);
                        campo_nombre.setText(null);
                        campo_contrasena.setText(null);
                        campo_confirmar_contrasena.setText(null);

                }

                    contador++;

                }// fin del else if (bool == false)
            } // fin del for

        }// FIN DEL METODO actionPerformed

    }

    
}


