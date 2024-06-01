package mx.ipn.app.gui;

import mx.ipn.app.model.Administrador;
import mx.ipn.app.repository.AdministradorRepositorioImp;
import mx.ipn.app.repository.Repositorio;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class LoginAdminGUI extends JFrame {

    private JTextField adminTextField;
    private JPasswordField contraTextField;
    private JButton ingresarButton;
    private JPanel panel_login;

    public LoginAdminGUI() {
        setTitle("Sistema gestor de productos");
        setSize(500, 400);
        setMinimumSize(new Dimension(480, 380));
        setVisible(true);
        setLocationRelativeTo(null);
        colocarComponentes();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void colocarComponentes() {
        getContentPane().add(panel_login);
        configurarBoton();
    }

    private void configurarBoton() {

        ingresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String admin;
                String contrasena;
                Administrador administrador = new Administrador();
                admin = adminTextField.getText();
                contrasena = contraTextField.getText();

                if (admin.isEmpty() || contrasena.isEmpty()) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Los campos de administrador y contraseña son obligatorios",
                            "Error en la caputura de datos",
                            JOptionPane.WARNING_MESSAGE
                    );
                } else {

                    boolean fueEncontrado = false;
                    administrador.setNombre(admin);
                    administrador.setContrasena(contrasena);
                    Repositorio<Administrador> administradorRepositorio = new AdministradorRepositorioImp();
                    List<Administrador> administradores = administradorRepositorio.listar();

                    // Se valida si el administrador existe
                    for (Administrador auxiliar : administradores) {
                        if (auxiliar.getNombre().equals(administrador.getNombre()) && auxiliar.getContrasena().equals(administrador.getContrasena())) {
                            fueEncontrado = true;
                            JOptionPane.showMessageDialog(
                                    null,
                                    "Administrador correcto",
                                    "Acceso autorizado",
                                    JOptionPane.INFORMATION_MESSAGE
                            );

                            dispose();
                            new GestorProductosGUI(auxiliar);
                            break;
                        }
                    }

                    if(!fueEncontrado){
                        JOptionPane.showMessageDialog(
                                null,
                                "Administrador o contraseña incorrectos",
                                "Acceso denegado",
                                JOptionPane.ERROR_MESSAGE
                        );
                        limpiarEntradas();
                    }

                }

            }
        });

    }

    private void limpiarEntradas(){
        adminTextField.setText("");
        contraTextField.setText("");
    }

}
