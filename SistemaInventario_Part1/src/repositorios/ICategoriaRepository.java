package repositorios;

import java.util.List;
import modelos.Categoria;

public interface ICategoriaRepository {
    List<Categoria> listar();
    Categoria buscarPorId(int id);
    boolean registrar(Categoria categoria);
    boolean editar(Categoria categoria);
    boolean eliminar(int id);
}
