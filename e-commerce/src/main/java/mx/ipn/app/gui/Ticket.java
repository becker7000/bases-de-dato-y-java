package mx.ipn.app.gui;

import mx.ipn.app.model.Carrito;
import mx.ipn.app.model.Producto;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class Ticket extends JFrame {

    private JPanel panel;
    private JTextArea reporteTA;
    private JButton imprimirButton;
    private Carrito carrito;
    private List<Producto> productosAComprar;

    public Ticket(Carrito carrito){
        this.carrito = carrito;
        setTitle("Inicio");
        setSize(320, 400);
        setMinimumSize(new Dimension(260, 380));
        setVisible(true);
        setLocationRelativeTo(null);
        colocarComponentes();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void colocarComponentes() {

        getContentPane().add(panel);
        productosAComprar = carrito.getProductos();
        StringBuilder reporte = new StringBuilder();
        reporte.append("\n\t Tienda virtual");
        reporte.append("\n\t Compra y paga");
        reporte.append("\n\t Avenida Politécnico");
        reporte.append("\n\t *** IVA incluido ***\n\n");

        for(Producto producto : productosAComprar){
            reporte.append(String.format("\n\t %s \t $ %.2f",producto.getNombre(), producto.getPrecio()));
        }

        double total = carrito.calcularTotal();
        reporte.append(String.format("\n\n\n\t Total a pagar: \t $ %.2f",total));
        // Obtener la fecha actual
        LocalDate fechaActual = LocalDate.now();
        reporte.append("\n\n\t Fecha: "+fechaActual);
        reporte.append("\n\t Cajero: Luis Perez (Opcional)");

        // Mandamos el reporte al area de texto transformado a string
        reporteTA.setText(reporte.toString());
        reporteTA.setEditable(false);

    }


}
