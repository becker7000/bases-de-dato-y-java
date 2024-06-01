package mx.ipn.app.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Inicio extends JFrame{

    private JButton administradorButton;
    private JButton usuarioCompradorButton;
    private JPanel panel;

    public Inicio() {
        setTitle("Inicio");
        setSize(500, 400);
        setMinimumSize(new Dimension(480, 380));
        setVisible(true);
        setLocationRelativeTo(null);
        colocarComponentes();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void colocarComponentes() {
        getContentPane().add(panel);
        configurarBotones();
    }

    private void configurarBotones() {

        administradorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new LoginAdminGUI();
            }
        });

        usuarioCompradorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Cerrar ventana
                new LoginUserGUI();
            }
        });

    }

}
