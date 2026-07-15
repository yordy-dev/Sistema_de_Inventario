package patrones.command;

import java.util.List;
import modelos.DetalleEntrada;
import modelos.Entrada;
import modelos.Inventario;

public class RegistrarEntradaCommand extends Command {
    private Entrada entrada;
    private List<DetalleEntrada> detalles;

    public RegistrarEntradaCommand(Entrada entrada, List<DetalleEntrada> detalles) {
        this.entrada = entrada;
        this.detalles = detalles;
    }

    @Override
    public boolean ejecutar() {
        return Inventario.getInstancia().registrarEntrada(entrada, detalles);
    }
}
