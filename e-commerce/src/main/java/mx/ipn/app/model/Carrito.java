package mx.ipn.app.model;

import java.util.ArrayList;
import java.util.List;

public class Carrito {

    private List<Producto> productos = new ArrayList<>();

    public void agregarProducto(Producto producto) {
        productos.add(producto);
    }

    public void eliminarProducto(int indice) {
        productos.remove(indice);
    }

    public double calcularTotal() {
        double total = 0;
        for (Producto p : productos) {
            total += p.getPrecio();
        }
        return total;
    }

    public List<Producto> getProductos() {
        return productos;
    }
}
