package repositorios;

import java.util.List;
import modelos.Entrada;
import modelos.DetalleEntrada;
import modelos.Salida;
import modelos.DetalleSalida;

public interface IMovimientoRepository {
    boolean registrarEntrada(Entrada entrada, List<DetalleEntrada> detalles);
    boolean registrarSalida(Salida salida, List<DetalleSalida> detalles);
    List<Entrada> listarEntradas();
    List<Salida> listarSalidas();
    List<DetalleEntrada> listarDetallesEntrada(int idEntrada);
    List<DetalleSalida> listarDetallesSalida(int idSalida);
}
