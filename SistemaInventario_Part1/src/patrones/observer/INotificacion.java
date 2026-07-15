package patrones.observer;

import modelos.Producto;

public interface INotificacion {
    void enviarAlerta(Producto p);
}
