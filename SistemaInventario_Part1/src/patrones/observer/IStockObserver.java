package patrones.observer;

import modelos.Producto;

public interface IStockObserver {
    void stockActualizado(Producto producto, int cantidadCambio);
}
