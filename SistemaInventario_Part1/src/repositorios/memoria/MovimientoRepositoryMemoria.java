package repositorios.memoria;

import java.util.ArrayList;
import java.util.List;
import modelos.Entrada;
import modelos.DetalleEntrada;
import modelos.Salida;
import modelos.DetalleSalida;
import repositorios.IMovimientoRepository;
import repositorios.IProductoRepository;

public class MovimientoRepositoryMemoria implements IMovimientoRepository {
    private static List<Entrada> entradas = new ArrayList<>();
    private static List<DetalleEntrada> detallesEntrada = new ArrayList<>();
    private static List<Salida> salidas = new ArrayList<>();
    private static List<DetalleSalida> detallesSalida = new ArrayList<>();

    private static MovimientoRepositoryMemoria instancia;
    private static int ultimoIdEntrada = 0;
    private static int ultimoIdDetalleEntrada = 0;
    private static int ultimoIdSalida = 0;
    private static int ultimoIdDetalleSalida = 0;

    private MovimientoRepositoryMemoria() {}

    public static synchronized MovimientoRepositoryMemoria getInstancia() {
        if (instancia == null) {
            instancia = new MovimientoRepositoryMemoria();
        }
        return instancia;
    }

    @Override
    public boolean registrarEntrada(Entrada entrada, List<DetalleEntrada> detalles) {
        ultimoIdEntrada++;
        entrada.setIdEntrada(ultimoIdEntrada);
        entradas.add(entrada);

        IProductoRepository prodRepo = ProductoRepositoryMemoria.getInstancia();

        for (DetalleEntrada det : detalles) {
            ultimoIdDetalleEntrada++;
            det.setIdDetalleEntrada(ultimoIdDetalleEntrada);
            det.setIdEntrada(ultimoIdEntrada);
            detallesEntrada.add(det);

            prodRepo.actualizarStock(det.getIdProducto(), det.getCantidad());
        }
        return true;
    }

    @Override
    public boolean registrarSalida(Salida salida, List<DetalleSalida> detalles) {
        IProductoRepository prodRepo = ProductoRepositoryMemoria.getInstancia();
        for (DetalleSalida det : detalles) {
            modelos.Producto p = prodRepo.buscarPorId(det.getIdProducto());
            if (p == null || p.getStock() < det.getCantidad()) {
                return false;
            }
        }

        ultimoIdSalida++;
        salida.setIdSalida(ultimoIdSalida);
        salidas.add(salida);

        for (DetalleSalida det : detalles) {
            ultimoIdDetalleSalida++;
            det.setIdDetalleSalida(ultimoIdDetalleSalida);
            det.setIdSalida(ultimoIdSalida);
            detallesSalida.add(det);

            prodRepo.actualizarStock(det.getIdProducto(), -det.getCantidad());
        }
        return true;
    }

    @Override
    public List<Entrada> listarEntradas() {
        return new ArrayList<>(entradas);
    }

    @Override
    public List<Salida> listarSalidas() {
        return new ArrayList<>(salidas);
    }

    @Override
    public List<DetalleEntrada> listarDetallesEntrada(int idEntrada) {
        List<DetalleEntrada> filtered = new ArrayList<>();
        for (DetalleEntrada det : detallesEntrada) {
            if (det.getIdEntrada() == idEntrada) {
                filtered.add(det);
            }
        }
        return filtered;
    }

    @Override
    public List<DetalleSalida> listarDetallesSalida(int idSalida) {
        List<DetalleSalida> filtered = new ArrayList<>();
        for (DetalleSalida det : detallesSalida) {
            if (det.getIdSalida() == idSalida) {
                filtered.add(det);
            }
        }
        return filtered;
    }
}
