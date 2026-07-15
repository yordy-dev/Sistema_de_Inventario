package modelos;

public class DetalleSalida {
    private int idDetalleSalida;
    private int idSalida;
    private int idProducto;
    private int cantidad;
    private double precioVenta;

    public DetalleSalida() {}

    public DetalleSalida(int idDetalleSalida, int idSalida, int idProducto, int cantidad, double precioVenta) {
        this.idDetalleSalida = idDetalleSalida;
        this.idSalida = idSalida;
        this.idProducto = idProducto;
        this.cantidad = cantidad;
        this.precioVenta = precioVenta;
    }

    public int getIdDetalleSalida() {
        return idDetalleSalida;
    }

    public void setIdDetalleSalida(int idDetalleSalida) {
        this.idDetalleSalida = idDetalleSalida;
    }

    public int getIdSalida() {
        return idSalida;
    }

    public void setIdSalida(int idSalida) {
        this.idSalida = idSalida;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(double precioVenta) {
        this.precioVenta = precioVenta;
    }
}
