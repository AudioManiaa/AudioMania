package com.audiomania.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginView extends JFrame {
    private JTextField cpfField;
    private JPasswordField senhaField;
    private JButton loginButton;
    private JButton registrarButton;
    private JButton sairButton;

    public LoginView() {
        setTitle("Sistema Audio Mania - Login");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centraliza a janela

        initComponents();
    }

    private void initComponents() {
        // Painel principal
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel titleLabel = new JLabel("SISTEMA AUDIO MANIA");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        gbc.gridwidth = 1;

        JLabel cpfLabel = new JLabel("CPF:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(cpfLabel, gbc);

        cpfField = new JTextField(15);
        gbc.gridx = 1;
        panel.add(cpfField, gbc);

        JLabel senhaLabel = new JLabel("Senha:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(senhaLabel, gbc);

        senhaField = new JPasswordField(15);
        gbc.gridx = 1;
        panel.add(senhaField, gbc);

        // Botões
        loginButton = new JButton("Login");
        registrarButton = new JButton("Registrar");
        sairButton = new JButton("Sair");

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(loginButton, gbc);

        gbc.gridx = 1;
        panel.add(registrarButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        panel.add(sairButton, gbc);

        add(panel);

        // Listeners só para demonstrar (não precisa implementar funcionalidade)
        loginButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Login clicado!"));
        registrarButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Registrar clicado!"));
        sairButton.addActionListener(e -> System.exit(0));
    }

    // Método para iniciar a tela
    public void iniciar() {
        setVisible(true);
    }

    // Main para teste
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginView().iniciar());
    }
}
