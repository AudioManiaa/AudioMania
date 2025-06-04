package com.audiomania.view;

import javax.swing.*;
import java.awt.*;

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

        // Listeners
        loginButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Login clicado!"));

        registrarButton.addActionListener(e -> {
            CadastroFuncionarioView cadastroView = new CadastroFuncionarioView();
            cadastroView.setVisible(true);
        });

        sairButton.addActionListener(e -> System.exit(0));
    }

    public void iniciar() {
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginView().iniciar());
    }
}

// Classe auxiliar, sem public!
class CadastroFuncionarioView extends JFrame {

    private JTextField nomeField;
    private JTextField cpfField;
    private JTextField cargoField;
    private JTextField telefoneField;
    private JPasswordField senhaField;
    private JButton cadastrarButton;

    public CadastroFuncionarioView() {
        setTitle("Cadastro de Funcionário");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Centraliza

        initComponents();
    }

    private void initComponents() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel titleLabel = new JLabel("=== CADASTRO DE FUNCIONÁRIO ===");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        gbc.gridwidth = 1;

        // Nome
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Nome:"), gbc);

        nomeField = new JTextField(15);
        gbc.gridx = 1;
        panel.add(nomeField, gbc);

        // CPF
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("CPF:"), gbc);

        cpfField = new JTextField(15);
        gbc.gridx = 1;
        panel.add(cpfField, gbc);

        // Cargo
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Cargo:"), gbc);

        cargoField = new JTextField(15);
        gbc.gridx = 1;
        panel.add(cargoField, gbc);

        // Telefone
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(new JLabel("Telefone:"), gbc);

        telefoneField = new JTextField(15);
        gbc.gridx = 1;
        panel.add(telefoneField, gbc);

        // Senha
        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(new JLabel("Senha:"), gbc);

        senhaField = new JPasswordField(15);
        gbc.gridx = 1;
        panel.add(senhaField, gbc);

        // Botão cadastrar
        cadastrarButton = new JButton("Cadastrar");
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        panel.add(cadastrarButton, gbc);

        add(panel);

        // Listener
        cadastrarButton.addActionListener(e -> {
            String nome = nomeField.getText();
            String cpf = cpfField.getText();
            String cargo = cargoField.getText();
            String telefone = telefoneField.getText();
            String senha = new String(senhaField.getPassword());

            JOptionPane.showMessageDialog(this,
                    "Nome: " + nome + "\nCPF: " + cpf + "\nCargo: " + cargo + "\nTelefone: " + telefone + "\nSenha: " + senha,
                    "Dados Cadastrados",
                    JOptionPane.INFORMATION_MESSAGE);
        });
    }
}
