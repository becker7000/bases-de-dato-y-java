package mx.ipn.app.gui;

import mx.ipn.app.model.Carrito;
import mx.ipn.app.model.Producto;
import mx.ipn.app.model.Usuario;
import mx.ipn.app.repository.ProductoRepositorioImp;
import mx.ipn.app.repository.Repositorio;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CarritoCompras extends JFrame {

    private JLabel bienvenidaJLabel;
    private Usuario usuario;
    private Carrito carrito;
    private DefaultListModel<String> carritoListModel;
    private JList<String> carritoList;
    private JLabel totalLabel;

    public CarritoCompras(Usuario usuario) {
        setTitle("Carrito de compras");
        this.usuario = usuario;
        bienvenidaJLabel = new JLabel();
        setSize(800, 600);
        setMinimumSize(new Dimension(780, 580));
        setVisible(true);
        setLocationRelativeTo(null);
        colocarComponentes();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void colocarComponentes() {

        bienvenidaJLabel.setText("Bienvend@ a la tienda virtual " + this.usuario.getNombre());
        bienvenidaJLabel.setSize(760,28);
        bienvenidaJLabel.setLocation(8,8);
        totalLabel = new JLabel("Total: $0.00");
        carrito = new Carrito();
        carritoListModel = new DefaultListModel<>();
        carritoList = new JList<>(carritoListModel);
        Repositorio<Producto> productoRepositorio = new ProductoRepositorioImp();
        List<Producto> productosDisponibles = productoRepositorio.listar();

        // Componentes
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(40,40,40,40));
        JButton comprarBtn = new JButton("Comprar");
        JButton eliminarBtn = new JButton("Eliminar del carrito");

        panel.add(bienvenidaJLabel);

        // Crear el panel con GridLayout
        JPanel botonPanel = new JPanel(new GridLayout(0, 2)); // 0 filas, 2 columnas
        botonPanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        for (Producto producto : productosDisponibles) {
            // Crear panel para el JLabel con un borde de margen
            JPanel labelPanel = new JPanel(new BorderLayout());
            labelPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Establecer margen
            JLabel label = new JLabel(producto.getNombre() + " - $" + producto.getPrecio());
            labelPanel.add(label, BorderLayout.CENTER);
            botonPanel.add(labelPanel);

            // Crear panel para el JButton con un borde de margen
            JPanel buttonPanel = new JPanel(new BorderLayout());
            buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // Establecer margen
            JButton btn = new JButton("Agregar");
            btn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    carrito.agregarProducto(producto);
                    actualizarCarrito();
                }
            });
            buttonPanel.add(btn, BorderLayout.CENTER);
            botonPanel.add(buttonPanel);
        }

        comprarBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Ticket(carrito);
            }
        });

        eliminarBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = carritoList.getSelectedIndex();
                if (selectedIndex != -1) {
                    carrito.eliminarProducto(selectedIndex);
                    actualizarCarrito();
                }
            }
        });

        botonPanel.add(comprarBtn);
        botonPanel.add(eliminarBtn);
        panel.add(new JScrollPane(carritoList), BorderLayout.CENTER);
        panel.add(totalLabel, BorderLayout.SOUTH);
        add(panel, BorderLayout.CENTER);
        add(botonPanel, BorderLayout.SOUTH);
        setVisible(true);
    }

    private void actualizarCarrito() {
        carritoListModel.clear();
        for (Producto producto : carrito.getProductos()) {
            carritoListModel.addElement(producto.getNombre() + " - $" + producto.getPrecio());
        }
        totalLabel.setText("Total: $" + carrito.calcularTotal());
    }

}
