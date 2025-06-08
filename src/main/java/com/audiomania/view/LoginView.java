package com.audiomania.view;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder; // Importar EmptyBorder
import com.audiomania.model.entities.FuncionarioEntity;
import com.audiomania.model.service.FuncionarioService;

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
        // Painel principal com BorderLayout para melhor organização
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10)); // 10px de espaçamento
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20)); // Padding ao redor

        // Título
        JLabel titleLabel = new JLabel("SISTEMA AUDIO MANIA", SwingConstants.CENTER); // Centraliza o texto
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Painel para campos de input (CPF e Senha)
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8); // Aumenta o espaçamento interno
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // CPF
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(new JLabel("CPF:"), gbc);

        gbc.gridx = 1;
        cpfField = new JTextField(20); // Aumenta o tamanho do campo
        inputPanel.add(cpfField, gbc);

        // Senha
        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(new JLabel("Senha:"), gbc);

        gbc.gridx = 1;
        senhaField = new JPasswordField(20); // Aumenta o tamanho do campo
        inputPanel.add(senhaField, gbc);

        mainPanel.add(inputPanel, BorderLayout.CENTER);

        // Painel para botões
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10)); // Centraliza botões com espaçamento
        loginButton = new JButton("Login");
        registrarButton = new JButton("Registrar");
        sairButton = new JButton("Sair");

        // Estiliza os botões
        Dimension buttonSize = new Dimension(120, 35); // Tamanho padrão para botões

        loginButton.setPreferredSize(buttonSize);
        loginButton.setFocusPainted(false); // Remove a borda de foco

        registrarButton.setPreferredSize(buttonSize);
        registrarButton.setFocusPainted(false);

        sairButton.setPreferredSize(buttonSize);
        sairButton.setFocusPainted(false);

        buttonPanel.add(loginButton);
        buttonPanel.add(registrarButton);
        buttonPanel.add(sairButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // Listeners (mantidos como estavam)
        loginButton.addActionListener(e -> {
            String cpf = cpfField.getText().trim();
            String senha = new String(senhaField.getPassword());

            if (cpf.isEmpty() || senha.isEmpty()) {
                JOptionPane.showMessageDialog(this, "CPF e senha são obrigatórios!", "Erro de Login", JOptionPane.ERROR_MESSAGE);
                if (cpf.isEmpty()) {
                    cpfField.requestFocus();
                } else {
                    senhaField.requestFocus();
                }
                return;
            }

            if (!cpf.matches("\\d{11}")) {
                JOptionPane.showMessageDialog(this, "O CPF deve conter exatamente 11 dígitos numéricos.", "Erro de Login", JOptionPane.ERROR_MESSAGE);
                cpfField.requestFocus();
                return;
            }

            FuncionarioEntity funcionario = FuncionarioService.autenticar(cpf, senha);

            if (funcionario != null) {
                JOptionPane.showMessageDialog(this, "Login efetuado com sucesso! Bem-vindo, " + funcionario.getNome() + "!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                this.dispose();
                MenuView menuView = new MenuView(funcionario); // Passa o funcionário logado para o MenuView
                menuView.iniciar();
            } else {
                JOptionPane.showMessageDialog(this, "CPF ou senha inválidos!", "Erro de Login", JOptionPane.ERROR_MESSAGE);
                cpfField.requestFocus();
            }
        });

        registrarButton.addActionListener(e -> {
            CadastroFuncionarioView cadastroView = new CadastroFuncionarioView(this);
            cadastroView.setVisible(true);
            this.setVisible(false); // Esconde a tela de login ao abrir cadastro
        });

        sairButton.addActionListener(e -> System.exit(0));
    }

    public void iniciar() {
        setVisible(true);
    }
}

// Classe auxiliar para cadastro de funcionário
class CadastroFuncionarioView extends JFrame {

    private JTextField nomeField;
    private JTextField cpfField;
    private JTextField cargoField;
    private JTextField telefoneField;
    private JPasswordField senhaField;
    private JButton cadastrarButton;
    private JButton voltarButton;

    private LoginView loginView;

    public CadastroFuncionarioView(LoginView loginView) {
        this.loginView = loginView;
        setTitle("Cadastro de Funcionário");
        setSize(450, 450); // Aumenta o tamanho da janela
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Centraliza

        initComponents();
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("=== CADASTRO DE FUNCIONÁRIO ===", SwingConstants.CENTER);
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Nome
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1;
        nomeField = new JTextField(25); // Aumenta o tamanho do campo
        inputPanel.add(nomeField, gbc);

        // CPF
        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(new JLabel("CPF:"), gbc);
        gbc.gridx = 1;
        cpfField = new JTextField(25);
        inputPanel.add(cpfField, gbc);

        // Cargo
        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(new JLabel("Cargo:"), gbc);
        gbc.gridx = 1;
        cargoField = new JTextField(25);
        inputPanel.add(cargoField, gbc);

        // Telefone
        gbc.gridx = 0;
        gbc.gridy = 3;
        inputPanel.add(new JLabel("Telefone:"), gbc);
        gbc.gridx = 1;
        telefoneField = new JTextField(25);
        inputPanel.add(telefoneField, gbc);

        // Senha
        gbc.gridx = 0;
        gbc.gridy = 4;
        inputPanel.add(new JLabel("Senha:"), gbc);
        gbc.gridx = 1;
        senhaField = new JPasswordField(25);
        inputPanel.add(senhaField, gbc);

        mainPanel.add(inputPanel, BorderLayout.CENTER);

        // Painel para botões
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15)); // Espaçamento maior
        cadastrarButton = new JButton("Cadastrar");
        voltarButton = new JButton("Voltar");

        Dimension buttonSize = new Dimension(150, 40); // Botões maiores

        cadastrarButton.setPreferredSize(buttonSize);
        cadastrarButton.setFocusPainted(false);

        voltarButton.setPreferredSize(buttonSize);
        voltarButton.setFocusPainted(false);

        buttonPanel.add(cadastrarButton);
        buttonPanel.add(voltarButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // Listener cadastrar
        cadastrarButton.addActionListener(e -> {
            String nome = nomeField.getText().trim();
            String cpf = cpfField.getText().trim();
            String cargo = cargoField.getText().trim();
            String telefone = telefoneField.getText().trim();
            String senha = new String(senhaField.getPassword());

            if (nome.isEmpty() || cpf.isEmpty() || cargo.isEmpty() || telefone.isEmpty() || senha.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos os campos são obrigatórios!", "Erro de Cadastro", JOptionPane.ERROR_MESSAGE);
                if (nome.isEmpty()) nomeField.requestFocus();
                else if (cpf.isEmpty()) cpfField.requestFocus();
                else if (cargo.isEmpty()) cargoField.requestFocus();
                else if (telefone.isEmpty()) telefoneField.requestFocus();
                else senhaField.requestFocus();
                return;
            }

            if (!cpf.matches("\\d{11}")) {
                JOptionPane.showMessageDialog(this, "O CPF deve conter exatamente 11 dígitos numéricos.", "Erro de Cadastro", JOptionPane.ERROR_MESSAGE);
                cpfField.requestFocus();
                return;
            }
            
            // Verificar se o telefone contém apenas números e tem um tamanho razoável (ex: 10 a 11 dígitos)
            if (!telefone.matches("\\d{10,11}")) {
                JOptionPane.showMessageDialog(this, "O telefone deve conter entre 10 e 11 dígitos numéricos.", "Erro de Cadastro", JOptionPane.ERROR_MESSAGE);
                telefoneField.requestFocus();
                return;
            }

            FuncionarioEntity novoFuncionario = new FuncionarioEntity();
            novoFuncionario.setNome(nome);
            novoFuncionario.setCpf(cpf);
            novoFuncionario.setCargo(cargo);
            novoFuncionario.setTelefone(telefone);
            novoFuncionario.setSenha(senha); // A senha será hashed pelo service

            boolean sucesso = FuncionarioService.cadastrarFuncionario(novoFuncionario);

            if (sucesso) {
                JOptionPane.showMessageDialog(this,
                        "Funcionário " + nome + " cadastrado com sucesso!",
                        "Cadastro Realizado",
                        JOptionPane.INFORMATION_MESSAGE);
                // Limpar campos após o cadastro
                nomeField.setText("");
                cpfField.setText("");
                cargoField.setText("");
                telefoneField.setText("");
                senhaField.setText("");
                // Opcional: fechar a janela de cadastro e voltar para login
                // this.dispose();
                // loginView.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao cadastrar funcionário. Verifique se o CPF já existe ou se os dados são válidos.", "Erro de Cadastro", JOptionPane.ERROR_MESSAGE);
                cpfField.requestFocus();
            }
        });

        // Listener voltar
        voltarButton.addActionListener(e -> {
            this.dispose();
            loginView.setVisible(true);
        });
    }
}

// Nota: A classe MenuView precisa ser definida para que o LoginView compile completamente.
// Exemplo básico de MenuView (apenas para compilação):
/*
class MenuView extends JFrame {
    public MenuView() {
        setTitle("Menu Principal");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        JLabel label = new JLabel("Bem-vindo ao Menu Principal!", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        add(label);
    }
    public void iniciar() {
        setVisible(true);
    }
}
*/
