package patrones.factory;

import modelos.Producto;

public class ProductoFactory {
    
    public static Producto crearProducto() {
        return new Producto();
    }

    public static Producto crearProducto(String codigo, String nombre, String marca, String modelo, 
                                         double precio, int stockMinimo, int idCategoria) {
        Producto p = new Producto();
        p.setCodigo(codigo);
        p.setNombre(nombre);
        p.setMarca(marca);
        p.setModelo(modelo);
        p.setPrecio(precio);
        p.setStock(0);
        p.setStockMinimo(stockMinimo);
        p.setEstado(true);
        p.setIdCategoria(idCategoria);
        return p;
    }
}
