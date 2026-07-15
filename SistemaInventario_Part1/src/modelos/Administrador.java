package modelos;

public class Administrador extends Usuario {
    public Administrador() {
        super();
    }

    public Administrador(int idUsuario, String usuario, String password, String nombre, boolean estado) {
        super(idUsuario, usuario, password, nombre, estado);
    }
}
