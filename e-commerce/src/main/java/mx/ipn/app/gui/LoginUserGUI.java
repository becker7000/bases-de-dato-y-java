package mx.ipn.app.gui;

import mx.ipn.app.model.Usuario;
import mx.ipn.app.repository.Repositorio;
import mx.ipn.app.repository.UsuarioRepositorioImp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class LoginUserGUI extends JFrame {

    private JPanel panel;
    private JTextField usuarioTF;
    private JTextField contraTF;
    private JButton ingresarButton;

    public LoginUserGUI(){

        setTitle("Tienda virtual");
        setSize(500, 400);
        setMinimumSize(new Dimension(480, 380));
        setVisible(true);
        setLocationRelativeTo(null);
        colocarComponentes();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void colocarComponentes() {
        getContentPane().add(panel);
        configurarBoton();
    }

    private void configurarBoton() {

        ingresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String user;
                String contrasena;
                Usuario usuario = new Usuario();
                user = usuarioTF.getText();
                contrasena = contraTF.getText();

                if (user.isEmpty() || contrasena.isEmpty()) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Los campos de usuario y contraseña son obligatorios",
                            "Error en la caputura de datos",
                            JOptionPane.WARNING_MESSAGE
                    );
                } else {

                    boolean fueEncontrado = false;
                    usuario.setNombre(user);
                    usuario.setContrasena(contrasena);
                    Repositorio<Usuario> usuarioRepositorio = new UsuarioRepositorioImp();
                    List<Usuario> usuarios = usuarioRepositorio.listar();

                    // Se valida si el usuario existe
                    for (Usuario auxiliar : usuarios) {
                        if (auxiliar.getNombre().equals(usuario.getNombre()) && auxiliar.getContrasena().equals(usuario.getContrasena())) {
                            fueEncontrado = true;
                            JOptionPane.showMessageDialog(
                                    null,
                                    "Usuario correcto",
                                    "Acceso autorizado",
                                    JOptionPane.INFORMATION_MESSAGE
                            );

                            dispose();
                            System.out.println("\n\t Se abre tienda virtual");
                            new CarritoCompras(usuario);
                            break;
                        }
                    }

                    if(!fueEncontrado){
                        JOptionPane.showMessageDialog(
                                null,
                                "Usuario o contraseña incorrectos",
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
        usuarioTF.setText("");
        contraTF.setText("");
    }

}
