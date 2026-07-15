package repositorios.memoria;

import java.util.ArrayList;
import java.util.List;
import modelos.Usuario;
import repositorios.IUsuarioRepository;
import modelos.Administrador;
import modelos.Almacenero;

public class UsuarioRepositoryMemoria implements IUsuarioRepository {
    private static List<Usuario> usuarios = new ArrayList<>();
    private static UsuarioRepositoryMemoria instancia;

    static {
        usuarios.add(new Administrador(1, "admin", "123456", "Administrador Principal", true));
        usuarios.add(new Almacenero(2, "juan", "123", "Juan Vendedor", true));
    }

    private UsuarioRepositoryMemoria() {}

    public static synchronized UsuarioRepositoryMemoria getInstancia() {
        if (instancia == null) {
            instancia = new UsuarioRepositoryMemoria();
        }
        return instancia;
    }

    @Override
    public Usuario buscarPorUsuario(String usuario) {
        for (Usuario u : usuarios) {
            if (u.getUsuario().equalsIgnoreCase(usuario) && u.isEstado()) {
                return u;
            }
        }
        return null;
    }

    @Override
    public boolean validarCredenciales(String usuario, String password) {
        Usuario u = buscarPorUsuario(usuario);
        if (u != null) {
            return u.getPassword().equals(password);
        }
        return false;
    }

    @Override
    public void guardar(Usuario usuario) {
        usuarios.add(usuario);
    }

    @Override
    public List<Usuario> listar() {
        return new ArrayList<>(usuarios);
    }
}
