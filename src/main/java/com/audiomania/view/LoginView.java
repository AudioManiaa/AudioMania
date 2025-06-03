package com.audiomania.view;

import com.audiomania.controller.SistemaController;
import com.audiomania.entities.FuncionarioEntity;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginView {
    private final SistemaController controller;
    private FuncionarioEntity authenticatedUser = null;

    public LoginView() {
        controller = new SistemaController();
    }

    /**
     * Inicia a interface gráfica de login.
     * @return Funcionário autenticado ou null se o login falhar ou for cancelado.
     */
    public FuncionarioEntity iniciarLogin() {
        // Use SwingUtilities.invokeLater to ensure GUI updates are on the Event Dispatch Thread
        // However, since this method needs to return a value and block,
        // we'll run the dialog creation and setVisible(true) directly.
        // The modal nature of JDialog will handle the blocking.

        JDialog loginDialog = new JDialog((Frame) null, "Login - Audio Mania", true); // Modal
        loginDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        loginDialog.setSize(350, 200);
        loginDialog.setLocationRelativeTo(null); // Center on screen

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // CPF Label and TextField
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("CPF:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        JTextField cpfField = new JTextField(20);
        panel.add(cpfField, gbc);

        // Senha Label and PasswordField
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Senha:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        JPasswordField passwordField = new JPasswordField(20);
        panel.add(passwordField, gbc);

        // Login Button
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton loginButton = new JButton("Login");
        panel.add(loginButton, gbc);

        // Cancel Button (Optional)
        // JButton cancelButton = new JButton("Cancelar");
        // gbc.gridx = 0; (adjust as needed if adding cancel button)
        // gbc.gridy = 3;
        // panel.add(cancelButton, gbc);
        // cancelButton.addActionListener(e -> loginDialog.dispose());


        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cpf = cpfField.getText();
                String senha = new String(passwordField.getPassword());

                // Basic validation (optional, but good practice)
                if (cpf.isEmpty() || senha.isEmpty()) {
                    JOptionPane.showMessageDialog(loginDialog, "CPF e Senha não podem estar vazios.", "Erro de Entrada", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                authenticatedUser = controller.realizarLogin(cpf, senha);

                if (authenticatedUser != null) {
                    JOptionPane.showMessageDialog(loginDialog, "Login realizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    loginDialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(loginDialog, "CPF ou Senha inválidos.", "Erro de Login", JOptionPane.ERROR_MESSAGE);
                    passwordField.setText(""); // Clear password field
                    // cpfField.setText(""); // Optionally clear CPF field
                }
            }
        });

        loginDialog.add(panel);
        // loginDialog.pack(); // pack() can be used instead of setSize if preferred
        loginDialog.setVisible(true); // This blocks until the dialog is disposed

        return authenticatedUser;
    }

    // The fechar() method is no longer needed as Scanner is removed.
    // If SistemaController needs resource cleanup, it should handle it itself
    // or provide a method that MainApp can call.
    // public void fechar() {
    // }
}