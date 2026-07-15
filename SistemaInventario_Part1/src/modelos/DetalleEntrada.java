package modelos;

public class DetalleEntrada {
    private int idDetalleEntrada;
    private int idEntrada;
    private int idProducto;
    private int cantidad;
    private double precioCompra;

    public DetalleEntrada() {}

    public DetalleEntrada(int idDetalleEntrada, int idEntrada, int idProducto, int cantidad, double precioCompra) {
        this.idDetalleEntrada = idDetalleEntrada;
        this.idEntrada = idEntrada;
        this.idProducto = idProducto;
        this.cantidad = cantidad;
        this.precioCompra = precioCompra;
    }

    public int getIdDetalleEntrada() {
        return idDetalleEntrada;
    }

    public void setIdDetalleEntrada(int idDetalleEntrada) {
        this.idDetalleEntrada = idDetalleEntrada;
    }

    public int getIdEntrada() {
        return idEntrada;
    }

    public void setIdEntrada(int idEntrada) {
        this.idEntrada = idEntrada;
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

    public double getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(double precioCompra) {
        this.precioCompra = precioCompra;
    }
}
