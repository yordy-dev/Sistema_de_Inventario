package patrones.proxy;

import java.util.List;
import modelos.Producto;
import modelos.Usuario;
import patrones.facade.InventarioFacade;

public class SecurityProxy {
    private InventarioFacade facade;

    public SecurityProxy() {
        this.facade = InventarioFacade.getInstancia();
    }

    private boolean esAdmin() {
        Usuario u = facade.getUsuarioLogueado();
        return u != null && u.getUsuario().equalsIgnoreCase("admin");
    }

    public boolean registrarProducto(Producto p) {
        if (!esAdmin()) {
            throw new SecurityException("Acceso denegado: Solo el Administrador puede registrar productos.");
        }
        return facade.registrarProducto(p);
    }

    public boolean editarProducto(Producto p) {
        if (!esAdmin()) {
            throw new SecurityException("Acceso denegado: Solo el Administrador puede editar productos.");
        }
        return facade.editarProducto(p);
    }

    public boolean eliminarProducto(int id) {
        if (!esAdmin()) {
            throw new SecurityException("Acceso denegado: Solo el Administrador puede eliminar productos.");
        }
        return facade.eliminarProducto(id);
    }

    public boolean registrarCategoria(modelos.Categoria c) {
        if (!esAdmin()) {
            throw new SecurityException("Acceso denegado: Solo el Administrador puede registrar categorías.");
        }
        return facade.registrarCategoria(c);
    }

    public boolean editarCategoria(modelos.Categoria c) {
        if (!esAdmin()) {
            throw new SecurityException("Acceso denegado: Solo el Administrador puede editar categorías.");
        }
        return facade.editarCategoria(c);
    }

    public boolean eliminarCategoria(int id) {
        if (!esAdmin()) {
            throw new SecurityException("Acceso denegado: Solo el Administrador puede eliminar categorías.");
        }
        return facade.eliminarCategoria(id);
    }

    public List<Producto> listarProductos() {
        return facade.listarProductos();
    }

    public Producto buscarProductoPorId(int id) {
        return facade.buscarProductoPorId(id);
    }

    public List<modelos.Categoria> listarCategorias() {
        return facade.listarCategorias();
    }
}
