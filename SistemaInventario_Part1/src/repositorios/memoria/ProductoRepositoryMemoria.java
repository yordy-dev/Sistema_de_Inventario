package repositorios.memoria;

import java.util.ArrayList;
import java.util.List;
import modelos.Producto;
import repositorios.IProductoRepository;

public class ProductoRepositoryMemoria implements IProductoRepository {
    private static List<Producto> productos = new ArrayList<>();
    private static ProductoRepositoryMemoria instancia;
    private static int ultimoId = 5;

    static {
        productos.add(new Producto(1, "PRO001", "Refrigeradora No Frost", "LG", "GN-B202", 2400.00, 10, 3, true, 1));
        productos.add(new Producto(2, "PRO002", "Cocina 6 Hornillas", "Indurama", "Roma", 1800.00, 8, 2, true, 2));
        productos.add(new Producto(3, "PRO003", "Congeladora Vertical", "Mabe", "CV300", 2100.00, 6, 2, true, 3));
        productos.add(new Producto(4, "PRO004", "Smart TV 55", "Samsung", "AU7000", 2500.00, 5, 2, true, 4));
        productos.add(new Producto(5, "PRO005", "Aire Acondicionado", "Miray", "12000BTU", 1700.00, 12, 4, true, 5));
    }

    private ProductoRepositoryMemoria() {}

    public static synchronized ProductoRepositoryMemoria getInstancia() {
        if (instancia == null) {
            instancia = new ProductoRepositoryMemoria();
        }
        return instancia;
    }

    @Override
    public List<Producto> listar() {
        List<Producto> activos = new ArrayList<>();
        for (Producto p : productos) {
            if (p.isEstado()) {
                activos.add(p);
            }
        }
        return activos;
    }

    @Override
    public Producto buscarPorId(int id) {
        for (Producto p : productos) {
            if (p.getIdProducto() == id) {
                return p;
            }
        }
        return null;
    }

    @Override
    public Producto buscarPorCodigo(String codigo) {
        for (Producto p : productos) {
            if (p.getCodigo().equalsIgnoreCase(codigo) && p.isEstado()) {
                return p;
            }
        }
        return null;
    }

    @Override
    public boolean registrar(Producto producto) {
        if (buscarPorCodigo(producto.getCodigo()) != null) {
            return false;
        }
        ultimoId++;
        producto.setIdProducto(ultimoId);
        productos.add(producto);
        return true;
    }

    @Override
    public boolean editar(Producto producto) {
        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).getIdProducto() == producto.getIdProducto()) {
                Producto existente = buscarPorCodigo(producto.getCodigo());
                if (existente != null && existente.getIdProducto() != producto.getIdProducto()) {
                    return false;
                }
                productos.set(i, producto);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean eliminar(int id) {
        Producto p = buscarPorId(id);
        if (p != null) {
            p.setEstado(false);
            return true;
        }
        return false;
    }

    @Override
    public boolean actualizarStock(int idProducto, int cantidad) {
        Producto p = buscarPorId(idProducto);
        if (p != null) {
            int nuevoStock = p.getStock() + cantidad;
            if (nuevoStock < 0) {
                return false;
            }
            p.setStock(nuevoStock);
            return true;
        }
        return false;
    }
}
