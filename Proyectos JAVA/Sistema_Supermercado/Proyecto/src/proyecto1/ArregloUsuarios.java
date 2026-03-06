package proyecto1;

public class ArregloUsuarios {

    String nombre = "";
    String usuario = "";
    String contrasena = "";
    String confirmar_contrasena = "";

    public ArregloUsuarios(String nombre, String usuario, String contrasena, String confirmar_contrasena) {

        this.nombre = nombre;
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.confirmar_contrasena = confirmar_contrasena;
    }

    public String getNombre() {

        return nombre;

    }

    public String getUsuario() {

        return usuario;

    }

    public String getContrasena() {

        return contrasena;
    }
    
    public String getConfirmar(){
    
        return confirmar_contrasena;
    
    }

}
