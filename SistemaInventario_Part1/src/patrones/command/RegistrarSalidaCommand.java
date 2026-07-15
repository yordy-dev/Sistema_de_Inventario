package patrones.command;

import java.util.List;
import modelos.DetalleSalida;
import modelos.Inventario;
import modelos.Salida;

public class RegistrarSalidaCommand extends Command {
    private Salida salida;
    private List<DetalleSalida> detalles;

    public RegistrarSalidaCommand(Salida salida, List<DetalleSalida> detalles) {
        this.salida = salida;
        this.detalles = detalles;
    }

    @Override
    public boolean ejecutar() {
        return Inventario.getInstancia().registrarSalida(salida, detalles);
    }
}
