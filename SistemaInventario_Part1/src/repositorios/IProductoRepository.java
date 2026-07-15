package repositorios;

import java.util.List;
import modelos.Producto;

public interface IProductoRepository {
    List<Producto> listar();
    Producto buscarPorId(int id);
    Producto buscarPorCodigo(String codigo);
    boolean registrar(Producto producto);
    boolean editar(Producto producto);
    boolean eliminar(int id);
    boolean actualizarStock(int idProducto, int cantidad);
}
