package patrones.command;

import modelos.Inventario;
import modelos.Producto;

public class RegistrarProductoCommand extends Command {
    private Producto producto;

    public RegistrarProductoCommand(Producto producto) {
        this.producto = producto;
    }

    @Override
    public boolean ejecutar() {
        return Inventario.getInstancia().registrarProducto(producto);
    }
}
