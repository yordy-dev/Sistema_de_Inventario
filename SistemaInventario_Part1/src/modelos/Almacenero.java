package modelos;

public class Almacenero extends Usuario {
    public Almacenero() {
        super();
    }

    public Almacenero(int idUsuario, String usuario, String password, String nombre, boolean estado) {
        super(idUsuario, usuario, password, nombre, estado);
    }
}
