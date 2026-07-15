package patrones.observer;

import java.util.ArrayList;
import java.util.List;
import modelos.Producto;

public class NotificacionService implements INotificacion {
    private static NotificacionService instancia;
    private List<IStockObserver> observers = new ArrayList<>();

    private NotificacionService() {}

    public static synchronized NotificacionService getInstancia() {
        if (instancia == null) {
            instancia = new NotificacionService();
        }
        return instancia;
    }

    public void agregarObserver(IStockObserver observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    public void removerObserver(IStockObserver observer) {
        observers.remove(observer);
    }

    public void notificar(Producto producto, int cantidadCambio) {
        for (IStockObserver obs : observers) {
            obs.stockActualizado(producto, cantidadCambio);
        }
    }

    @Override
    public void enviarAlerta(Producto p) {
        System.out.println("[ALERTA DE SISTEMA] ¡STOCK BAJO en el producto: " + p.getNombre() + 
                           " (Stock actual: " + p.getStock() + ", Stock mínimo: " + p.getStockMinimo() + ")!");
        notificar(p, 0);
    }
}
