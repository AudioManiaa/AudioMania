package com.audiomania.view;

import com.audiomania.controller.ClienteController;
import com.audiomania.model.entities.ClienteEntity;
import com.audiomania.utils.StyleConfigurator;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ClienteView extends JFrame {
    private JTextField buscaField;
    private JButton buscarButton;
    private JButton novoButton;
    private JButton editarButton;
    private JButton excluirButton;
    private JButton fecharButton;
    private JButton recarregarButton;
    private JTable clientesTable;
    private DefaultTableModel tableModel;
    private JFrame menuView;
    private ClienteController controller;

    public ClienteView() {
        this(null);
    }

    public ClienteView(JFrame menuView) {
        this.menuView = menuView;
        this.controller = new ClienteController();

        StyleConfigurator.applyStyles();

        setTitle("Sistema Audio Mania - Gerenciamento de Clientes");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
        carregarClientes();
    }

    private void initComponents() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Painel superior
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());

        JLabel titleLabel = new JLabel("GERENCIAR CLIENTES");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));

        JLabel buscaLabel = new JLabel("Buscar:");
        buscaField = new JTextField(15);

        buscarButton = new JButton("Buscar");
        recarregarButton = new JButton("Recarregar");

        topPanel.add(titleLabel);
        topPanel.add(Box.createHorizontalStrut(20));
        topPanel.add(buscaLabel);
        topPanel.add(buscaField);
        topPanel.add(buscarButton);
        topPanel.add(recarregarButton);

        // Painel tabela
        String[] colunas = {"ID", "Nome", "CPF", "Telefone", "Endereço", "Data de Cadastro"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        clientesTable = new JTable(tableModel);
        clientesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(clientesTable);

        // Painel inferior
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout());

        novoButton = new JButton("Novo");
        editarButton = new JButton("Editar");
        excluirButton = new JButton("Excluir");
        fecharButton = new JButton("Fechar");

        bottomPanel.add(novoButton);
        bottomPanel.add(editarButton);
        bottomPanel.add(excluirButton);
        bottomPanel.add(fecharButton);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        add(panel);

        configurarListeners();
    }

    private void configurarListeners() {
        buscarButton.addActionListener(e -> buscarClientes());
        recarregarButton.addActionListener(e -> carregarClientes());
        novoButton.addActionListener(e -> novoCliente());
        editarButton.addActionListener(e -> editarCliente());
        excluirButton.addActionListener(e -> excluirCliente());
        fecharButton.addActionListener(e -> fecharTela());
        buscaField.addActionListener(e -> buscarClientes());
    }

    private void buscarClientes() {
        String termo = buscaField.getText();
        List<ClienteEntity> clientes = controller.buscarClientes(termo);
        atualizarTabela(clientes);

        if (clientes.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Nenhum cliente encontrado com o termo de busca.",
                    "Busca", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void novoCliente() {
        CadastroClienteView cadastroView = new CadastroClienteView(this, controller);
        cadastroView.setVisible(true);
    }

    private void editarCliente() {
        int linha = clientesTable.getSelectedRow();

        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um cliente para editar!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Integer id = (Integer) tableModel.getValueAt(linha, 0);
        ClienteEntity cliente = controller.buscarPorId(id);

        if (cliente != null) {
            EdicaoClienteView edicaoView = new EdicaoClienteView(this, controller, cliente);
            edicaoView.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Cliente não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void excluirCliente() {
        int linha = clientesTable.getSelectedRow();

        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um cliente para excluir!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Integer id = (Integer) tableModel.getValueAt(linha, 0);
        String nome = (String) tableModel.getValueAt(linha, 1);

        int opcao = JOptionPane.showConfirmDialog(this,
                "Tem certeza que deseja excluir o cliente:\n" + nome + "?",
                "Confirmar Exclusão",
                JOptionPane.YES_NO_OPTION);

        if (opcao == JOptionPane.YES_OPTION) {
            if (controller.excluirCliente(id)) {
                JOptionPane.showMessageDialog(this, "Cliente excluído com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                carregarClientes();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao excluir cliente!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void fecharTela() {
        dispose();
        if (menuView != null) {
            menuView.setVisible(true);
        }
    }

    private void carregarClientes() {
        List<ClienteEntity> clientes = controller.listarClientes();
        atualizarTabela(clientes);
    }

    private void atualizarTabela(List<ClienteEntity> clientes) {
        tableModel.setRowCount(0);
        for (ClienteEntity cliente : clientes) {
            Object[] linha = controller.formatarClienteParaTabela(cliente);
            tableModel.addRow(linha);
        }
    }

    public void atualizarDados() {
        carregarClientes();
    }

    public void iniciar() {
        setVisible(true);
    }
}

// Classe cadastro sem lógica de negócio
class CadastroClienteView extends JFrame {
    private JTextField nomeField;
    private JTextField cpfField;
    private JTextField telefoneField;
    private JTextField enderecoField;
    private JButton cadastrarButton;
    private JButton voltarButton;
    private ClienteView clienteView;
    private ClienteController controller;

    public CadastroClienteView(ClienteView clienteView, ClienteController controller) {
        this.clienteView = clienteView;
        this.controller = controller;
        setTitle("Cadastro de Cliente");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
    }

    private void initComponents() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("=== CADASTRO DE CLIENTE ===");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        gbc.gridwidth = 1;

        // Nome
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Nome:*"), gbc);

        nomeField = new JTextField(20);
        gbc.gridx = 1;
        panel.add(nomeField, gbc);

        // CPF
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("CPF:*"), gbc);

        cpfField = new JTextField(20);
        gbc.gridx = 1;
        panel.add(cpfField, gbc);

        // Telefone
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Telefone:"), gbc);

        telefoneField = new JTextField(20);
        gbc.gridx = 1;
        panel.add(telefoneField, gbc);

        // Endereço
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(new JLabel("Endereço:"), gbc);

        enderecoField = new JTextField(20);
        gbc.gridx = 1;
        panel.add(enderecoField, gbc);

        // Nota sobre campos obrigatórios
        JLabel notaLabel = new JLabel("* Campos obrigatórios");
        notaLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        panel.add(notaLabel, gbc);

        // Botões
        JPanel buttonPanel = new JPanel(new FlowLayout());
        cadastrarButton = new JButton("Cadastrar");
        voltarButton = new JButton("Voltar");

        buttonPanel.add(cadastrarButton);
        buttonPanel.add(voltarButton);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        panel.add(buttonPanel, gbc);

        add(panel);

        configurarListeners();
        nomeField.requestFocus();
    }

    private void configurarListeners() {
        cadastrarButton.addActionListener(e -> cadastrarCliente());
        voltarButton.addActionListener(e -> dispose());
    }

    private void cadastrarCliente() {
        String nome = nomeField.getText();
        String cpf = cpfField.getText();
        String telefone = telefoneField.getText();
        String endereco = enderecoField.getText();

        String resultado = controller.cadastrarCliente(nome, cpf, telefone, endereco);

        switch (resultado) {
            case "SUCESSO":
                JOptionPane.showMessageDialog(this,
                        "Cliente cadastrado com sucesso!",
                        "Sucesso", JOptionPane.INFORMATION_MESSAGE);

                clienteView.atualizarDados();
                limparCampos();
                nomeField.requestFocus();
                break;

            case "NOME_VAZIO":
                JOptionPane.showMessageDialog(this, "Nome é obrigatório!", "Erro", JOptionPane.ERROR_MESSAGE);
                nomeField.requestFocus();
                break;

            case "CPF_VAZIO":
                JOptionPane.showMessageDialog(this, "CPF é obrigatório!", "Erro", JOptionPane.ERROR_MESSAGE);
                cpfField.requestFocus();
                break;

            case "CPF_INVALIDO":
                JOptionPane.showMessageDialog(this, "CPF deve conter exatamente 11 dígitos numéricos!", "Erro", JOptionPane.ERROR_MESSAGE);
                cpfField.requestFocus();
                break;

            case "CPF_EXISTE":
                JOptionPane.showMessageDialog(this, "CPF já cadastrado no sistema!", "Erro", JOptionPane.ERROR_MESSAGE);
                cpfField.requestFocus();
                break;

            case "TELEFONE_INVALIDO":
                JOptionPane.showMessageDialog(this, "Telefone deve conter entre 10 e 11 dígitos numéricos!", "Erro", JOptionPane.ERROR_MESSAGE);
                telefoneField.requestFocus();
                break;

            default:
                JOptionPane.showMessageDialog(this, "Erro ao cadastrar cliente: " + resultado, "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limparCampos() {
        nomeField.setText("");
        cpfField.setText("");
        telefoneField.setText("");
        enderecoField.setText("");
    }
}

// Classe edição sem lógica de negócio
class EdicaoClienteView extends JFrame {
    private JTextField nomeField;
    private JTextField cpfField;
    private JTextField telefoneField;
    private JTextField enderecoField;
    private JButton salvarButton;
    private JButton voltarButton;
    private ClienteView clienteView;
    private ClienteController controller;
    private ClienteEntity cliente;

    public EdicaoClienteView(ClienteView clienteView, ClienteController controller, ClienteEntity cliente) {
        this.clienteView = clienteView;
        this.controller = controller;
        this.cliente = cliente;
        setTitle("Editar Cliente - " + cliente.getNome());
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
        carregarDadosCliente();
    }

    private void initComponents() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("=== EDITAR CLIENTE ===");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        gbc.gridwidth = 1;

        // Nome
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Nome:*"), gbc);

        nomeField = new JTextField(20);
        gbc.gridx = 1;
        panel.add(nomeField, gbc);

        // CPF (não editável)
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("CPF:"), gbc);

        cpfField = new JTextField(20);
        cpfField.setEditable(false);
        cpfField.setBackground(Color.LIGHT_GRAY);
        gbc.gridx = 1;
        panel.add(cpfField, gbc);

        // Telefone
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Telefone:"), gbc);

        telefoneField = new JTextField(20);
        gbc.gridx = 1;
        panel.add(telefoneField, gbc);

        // Endereço
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(new JLabel("Endereço:"), gbc);

        enderecoField = new JTextField(20);
        gbc.gridx = 1;
        panel.add(enderecoField, gbc);

        // Nota sobre campos obrigatórios
        JLabel notaLabel = new JLabel("* Campos obrigatórios | CPF não pode ser alterado");
        notaLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        panel.add(notaLabel, gbc);

        // Botões
        JPanel buttonPanel = new JPanel(new FlowLayout());
        salvarButton = new JButton("Salvar");
        voltarButton = new JButton("Voltar");

        buttonPanel.add(salvarButton);
        buttonPanel.add(voltarButton);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        panel.add(buttonPanel, gbc);

        add(panel);

        configurarListeners();
    }

    private void configurarListeners() {
        salvarButton.addActionListener(e -> salvarCliente());
        voltarButton.addActionListener(e -> dispose());
    }

    private void carregarDadosCliente() {
        controller.preencherCamposEdicao(cliente, nomeField, cpfField, telefoneField, enderecoField);
    }

    private void salvarCliente() {
        String nome = nomeField.getText();
        String telefone = telefoneField.getText();
        String endereco = enderecoField.getText();

        String resultado = controller.atualizarCliente(cliente, nome, telefone, endereco);

        switch (resultado) {
            case "SUCESSO":
                JOptionPane.showMessageDialog(this,
                        "Cliente atualizado com sucesso!",
                        "Sucesso", JOptionPane.INFORMATION_MESSAGE);

                clienteView.atualizarDados();
                dispose();
                break;

            case "NOME_VAZIO":
                JOptionPane.showMessageDialog(this, "Nome é obrigatório!", "Erro", JOptionPane.ERROR_MESSAGE);
                nomeField.requestFocus();
                break;

            case "TELEFONE_INVALIDO":
                JOptionPane.showMessageDialog(this, "Telefone deve conter entre 10 e 11 dígitos numéricos!", "Erro", JOptionPane.ERROR_MESSAGE);
                telefoneField.requestFocus();
                break;

            default:
                JOptionPane.showMessageDialog(this, "Erro ao atualizar cliente: " + resultado, "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}