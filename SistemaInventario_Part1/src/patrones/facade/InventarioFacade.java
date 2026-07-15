package patrones.facade;

import java.util.Date;
import java.util.List;
import modelos.Categoria;
import modelos.DetalleEntrada;
import modelos.DetalleSalida;
import modelos.Entrada;
import modelos.Inventario;
import modelos.Producto;
import modelos.Salida;
import modelos.Usuario;
import patrones.command.Command;
import patrones.command.RegistrarEntradaCommand;
import patrones.command.RegistrarProductoCommand;
import patrones.command.RegistrarSalidaCommand;

public class InventarioFacade {
    private static InventarioFacade instancia;
    private Usuario usuarioLogueado;

    private InventarioFacade() {}

    public static synchronized InventarioFacade getInstancia() {
        if (instancia == null) {
            instancia = new InventarioFacade();
        }
        return instancia;
    }

    public boolean login(String usuario, String password) {
        boolean valido = Inventario.getInstancia().getUsuarioRepo().validarCredenciales(usuario, password);
        if (valido) {
            this.usuarioLogueado = Inventario.getInstancia().getUsuarioRepo().buscarPorUsuario(usuario);
        }
        return valido;
    }

    public Usuario getUsuarioLogueado() {
        return usuarioLogueado;
    }

    public void logout() {
        this.usuarioLogueado = null;
    }

    public List<Producto> listarProductos() {
        return Inventario.getInstancia().getProductoRepo().listar();
    }

    public Producto buscarProductoPorId(int id) {
        return Inventario.getInstancia().getProductoRepo().buscarPorId(id);
    }

    public Producto buscarProductoPorCodigo(String codigo) {
        return Inventario.getInstancia().getProductoRepo().buscarPorCodigo(codigo);
    }

    public List<Categoria> listarCategorias() {
        return Inventario.getInstancia().getCategoriaRepo().listar();
    }

    public Categoria buscarCategoriaPorId(int id) {
        return Inventario.getInstancia().getCategoriaRepo().buscarPorId(id);
    }

    public boolean registrarCategoria(Categoria c) {
        return Inventario.getInstancia().getCategoriaRepo().registrar(c);
    }

    public boolean editarCategoria(Categoria c) {
        return Inventario.getInstancia().getCategoriaRepo().editar(c);
    }

    public boolean eliminarCategoria(int id) {
        return Inventario.getInstancia().getCategoriaRepo().eliminar(id);
    }

    public boolean registrarProducto(Producto p) {
        Command cmd = new RegistrarProductoCommand(p);
        return cmd.ejecutar();
    }

    public boolean editarProducto(Producto p) {
        return Inventario.getInstancia().getProductoRepo().editar(p);
    }

    public boolean eliminarProducto(int id) {
        return Inventario.getInstancia().getProductoRepo().eliminar(id);
    }

    public boolean registrarEntrada(String observacion, List<DetalleEntrada> detalles) {
        if (usuarioLogueado == null) return false;

        Entrada entrada = new Entrada();
        entrada.setFecha(new Date());
        entrada.setIdUsuario(usuarioLogueado.getIdUsuario());
        entrada.setObservacion(observacion);

        Command cmd = new RegistrarEntradaCommand(entrada, detalles);
        return cmd.ejecutar();
    }

    public boolean registrarSalida(String observacion, List<DetalleSalida> detalles) {
        if (usuarioLogueado == null) return false;

        Salida salida = new Salida();
        salida.setFecha(new Date());
        salida.setIdUsuario(usuarioLogueado.getIdUsuario());
        salida.setObservacion(observacion);

        Command cmd = new RegistrarSalidaCommand(salida, detalles);
        return cmd.ejecutar();
    }

    public List<Entrada> listarEntradas() {
        return Inventario.getInstancia().getMovimientoRepo().listarEntradas();
    }

    public List<Salida> listarSalidas() {
        return Inventario.getInstancia().getMovimientoRepo().listarSalidas();
    }

    public List<DetalleEntrada> listarDetallesEntrada(int idEntrada) {
        return Inventario.getInstancia().getMovimientoRepo().listarDetallesEntrada(idEntrada);
    }

    public List<DetalleSalida> listarDetallesSalida(int idSalida) {
        return Inventario.getInstancia().getMovimientoRepo().listarDetallesSalida(idSalida);
    }
}
