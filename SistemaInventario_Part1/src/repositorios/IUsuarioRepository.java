package repositorios;

import java.util.List;
import modelos.Usuario;

public interface IUsuarioRepository {
    Usuario buscarPorUsuario(String usuario);
    boolean validarCredenciales(String usuario, String password);
    void guardar(Usuario usuario);
    List<Usuario> listar();
}
