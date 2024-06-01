package mx.ipn.app.gui;

import mx.ipn.app.model.Administrador;
import mx.ipn.app.model.Producto;
import mx.ipn.app.repository.ProductoRepositorioImp;
import mx.ipn.app.repository.Repositorio;
import mx.ipn.app.util.ConexionBD;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

public class GestorProductosGUI extends JFrame{

    private JPanel panel_general;
    private JLabel bienvenidaJLabel;
    private JButton cerrarSesionButton;
    private JButton nuevoButton;
    private JButton modificarButton;
    private JButton eliminarButton;
    private JButton buscarButton;
    private JButton verTodosButton;
    private JTable tablaProductos;
    private JTextField nombreTF;
    private JTextField precioTF;
    private JTextField stockTF;
    private JTextField categoriaTF;
    private JTextField idTF;
    private Administrador administrador;
    private DefaultTableModel defaultTableModel;
    private Connection connection;
    private Repositorio<Producto> productoRepositorio;


    public GestorProductosGUI(Administrador administrador){
        this.administrador = administrador;
        bienvenidaJLabel.setText("Bienvend@ al sistema "+administrador.getNombre());
        setTitle("Gestor de productos");
        setSize(800,600);
        setMinimumSize(new Dimension(780,580));
        setVisible(true);
        setLocationRelativeTo(null);
        colocarComponentes();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void colocarComponentes() {
        getContentPane().add(panel_general);
        configurarTabla();
        configurarBotones();
    }

    private void configurarTabla() {

        // Agregamos las cabeceras
        defaultTableModel = new DefaultTableModel();
        String[] cabeceras = {"Código","Nombre","Precio","Stock","Categoría"};
        defaultTableModel.setColumnIdentifiers(cabeceras);
        tablaProductos.setModel(defaultTableModel);

        // Centramos todos los elementos:
        // Centramos el texto de las columnas de tabla:
        DefaultTableCellRenderer centradorTabla = new DefaultTableCellRenderer();
        centradorTabla.setHorizontalAlignment(JLabel.CENTER);
        for(int i=0; i<tablaProductos.getColumnCount();i++){
            tablaProductos.getColumnModel().getColumn(i).setCellRenderer(centradorTabla);
        }
        mostrarProductos();
    }

    private void mostrarProductos() {
        try{
            connection = ConexionBD.getInstance();
            productoRepositorio = new ProductoRepositorioImp();
            actualizarTabla();
        }catch (SQLException e) {
            System.out.println("\n\t (Mostrar productos) Error: "+e.getMessage());
        }
    }

    private void actualizarTabla() {
        limpiarTabla();
        productoRepositorio.listar().forEach(this::crearFila);
    }

    private void crearFila(Producto producto) {
        defaultTableModel.addRow(new Object[]{ // Agrega una fila a la tabla
                producto.getId(),
                producto.getNombre(),
                producto.getPrecio(),
                producto.getStock(),
                producto.getCategoria()
        });
    }

    private void limpiarTabla() {
        int numeroFilas = defaultTableModel.getRowCount();
        for(int i=0; i<numeroFilas;i++){
            defaultTableModel.removeRow(0);
        }
    }

    private void limpiarEntradas(){
        idTF.setText("");
        nombreTF.setText("");
        precioTF.setText("");
        stockTF.setText("");
        categoriaTF.setText("");
    }

    private void configurarBotones() {

        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarTabla();
                int id;
                Producto producto;
                try{
                    id = Integer.parseInt(idTF.getText());
                    producto = productoRepositorio.porId(id);
                    crearFila(producto);
                }catch (NumberFormatException exception){
                    JOptionPane.showMessageDialog(
                            null,
                            "El código debe ser un número",
                            "Error de formato",
                            JOptionPane.ERROR_MESSAGE
                    );
                }catch (NullPointerException exception){
                    JOptionPane.showMessageDialog(
                            null,
                            "Producto no encontrado",
                            "Error de busqueda",
                            JOptionPane.ERROR_MESSAGE
                    );
                }

                limpiarEntradas();
            }
        });

        verTodosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarTabla();
            }
        });

        nuevoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Producto producto = new Producto();
                producto.setNombre(nombreTF.getText());
                producto.setPrecio(Double.parseDouble(precioTF.getText()));
                producto.setStock(Integer.parseInt(stockTF.getText()));
                producto.setCategoria(categoriaTF.getText());
                productoRepositorio.guardar(producto);
                actualizarTabla();
                limpiarEntradas();
            }
        });

        modificarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Producto producto = new Producto();
                producto.setId(Integer.parseInt(idTF.getText()));
                producto.setNombre(nombreTF.getText());
                producto.setPrecio(Double.parseDouble(precioTF.getText()));
                producto.setStock(Integer.parseInt(stockTF.getText()));
                producto.setCategoria(categoriaTF.getText());
                productoRepositorio.guardar(producto);
                actualizarTabla();
                limpiarEntradas();
            }
        });

        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(idTF.getText());
                productoRepositorio.eliminar(id);
                actualizarTabla();
                limpiarEntradas();
            }
        });

        cerrarSesionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Inicio();
            }
        });

    }

}
