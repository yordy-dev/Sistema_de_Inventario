package modelos;

import java.util.List;
import repositorios.IProductoRepository;
import repositorios.ICategoriaRepository;
import repositorios.IUsuarioRepository;
import repositorios.IMovimientoRepository;
import repositorios.memoria.ProductoRepositoryMemoria;
import repositorios.memoria.CategoriaRepositoryMemoria;
import repositorios.memoria.UsuarioRepositoryMemoria;
import repositorios.memoria.MovimientoRepositoryMemoria;
import patrones.observer.INotificacion;
import patrones.observer.NotificacionService;

public class Inventario {
    private static Inventario instancia;

    private IProductoRepository productoRepo;
    private ICategoriaRepository categoriaRepo;
    private IUsuarioRepository usuarioRepo;
    private IMovimientoRepository movimientoRepo;

    private Inventario() {
        this.usuarioRepo = UsuarioRepositoryMemoria.getInstancia();
        this.productoRepo = ProductoRepositoryMemoria.getInstancia();
        this.categoriaRepo = CategoriaRepositoryMemoria.getInstancia();
        this.movimientoRepo = MovimientoRepositoryMemoria.getInstancia();
    }

    public static synchronized Inventario getInstancia() {
        if (instancia == null) {
            instancia = new Inventario();
        }
        return instancia;
    }

    public IProductoRepository getProductoRepo() {
        return productoRepo;
    }

    public ICategoriaRepository getCategoriaRepo() {
        return categoriaRepo;
    }

    public IUsuarioRepository getUsuarioRepo() {
        return usuarioRepo;
    }

    public IMovimientoRepository getMovimientoRepo() {
        return movimientoRepo;
    }

    public boolean registrarProducto(Producto p) {
        return productoRepo.registrar(p);
    }

    public boolean editarProducto(Producto p) {
        return productoRepo.editar(p);
    }

    public boolean eliminarProducto(int id) {
        return productoRepo.eliminar(id);
    }

    public boolean registrarEntrada(Entrada entrada, List<DetalleEntrada> detalles) {
        boolean exito = movimientoRepo.registrarEntrada(entrada, detalles);
        if (exito) {
            for (DetalleEntrada det : detalles) {
                Producto p = productoRepo.buscarPorId(det.getIdProducto());
                if (p != null) {
                    NotificacionService.getInstancia().notificar(p, det.getCantidad());
                }
            }
        }
        return exito;
    }

    public boolean registrarSalida(Salida salida, List<DetalleSalida> detalles) {
        boolean exito = movimientoRepo.registrarSalida(salida, detalles);
        if (exito) {
            for (DetalleSalida det : detalles) {
                Producto p = productoRepo.buscarPorId(det.getIdProducto());
                if (p != null) {
                    NotificacionService.getInstancia().notificar(p, -det.getCantidad());
                    if (verificarStock(p)) {
                        NotificacionService.getInstancia().enviarAlerta(p);
                    }
                }
            }
        }
        return exito;
    }

    public boolean verificarStock(Producto p) {
        return p.getStock() <= p.getStockMinimo();
    }
}
