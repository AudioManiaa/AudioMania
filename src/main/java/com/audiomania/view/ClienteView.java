package com.audiomania.view;

import com.audiomania.controller.ClienteController;
import com.audiomania.model.entities.ClienteEntity;
import com.audiomania.utils.StyleConfigurator;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Scanner;

public class ClienteView extends JFrame {
    private final Scanner scanner;
    private final ClienteController controller;
    private JTextField buscaField;
    private JButton buscarButton;
    private JButton novoButton;
    private JButton editarButton;
    private JButton excluirButton;
    private JButton fecharButton;
    private JTable clientesTable;
    private DefaultTableModel tableModel;
    private JFrame menuView;

    public ClienteView() {
        this(null);
    }

    public ClienteView(JFrame menuView) {
        this.menuView = menuView;

        StyleConfigurator.applyStyles();

        scanner = new Scanner(System.in);
        controller = new ClienteController();
        setVisible(true);
        setTitle("Sistema Audio Mania - Gerenciamento de Clientes");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
        carregarClientes();
    }

    public void initComponents() {

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());

        JLabel titleLabel = new JLabel("GERENCIAR CLIENTES");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));

        JLabel buscaLabel = new JLabel("Buscar:");
        buscaField = new JTextField(15);

        buscarButton = new JButton("Buscar");

        topPanel.add(titleLabel);
        topPanel.add(Box.createHorizontalStrut(20));
        topPanel.add(buscaLabel);
        topPanel.add(buscaField);
        topPanel.add(buscarButton);

        String[] colunas = {"ID", "Nome", "CPF", "Telefone", "Endereço", "Data de Cadastro"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        clientesTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(clientesTable);

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

        fecharButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                if (menuView != null) {
                    menuView.setVisible(true);
                }
            }
        });

        add(panel);
    }

    public void carregarClientes() {
        Object[][] clientes = {
                {1, "cliente01", "12345678910", "45999999999", "endereço01", "01/01/01"},
                {2, "cliente02", "14785236900", "44998000000", "endereço02", "01/01/01"},
                {3, "cliente03", "24681357901", "45887999999", "endereço03", "01/01/01"},
                {4, "cliente04", "78945612310", "21999999999", "endereço04", "01/01/01"},
                {5, "cliente05", "98765432100", "11789999999", "endereço05", "01/01/01"}
        };

        for (Object[] cliente : clientes) {
            tableModel.addRow(cliente);
        }
    }

    public void iniciar() {
        setVisible(true);
    }

    public void iniciarGerenciamento() {
        boolean sair = false;

        while (!sair) {
            System.out.println("\n===== GERENCIAMENTO DE CLIENTES =====\n");
            System.out.println("1. Listar Clientes");
            System.out.println("2. Cadastrar Cliente");
            System.out.println("3. Atualizar Cliente");
            System.out.println("4. Excluir Cliente");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("\nEscolha uma opção: ");

            int opcao = -1;
            try {
                opcao = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Opção inválida!");
                continue;
            }

            switch (opcao) {
                case 1:
                    listarClientes();
                    break;
                case 2:
                    cadastrarCliente();
                    break;
                case 3:
                    atualizarCliente();
                    break;
                case 4:
                    excluirCliente();
                    break;
                case 0:
                    sair = true;
                    break;
                default:
                    System.out.println("Opção inválida!");
            }

            if (!sair) {
                System.out.print("\nPressione ENTER para continuar...");
                scanner.nextLine();
            }
        }
    }

    private void listarClientes() {
        List<ClienteEntity> clientes = controller.listarClientes();

        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado.");
            return;
        }

        System.out.println("\n=== LISTA DE CLIENTES ===\n");
        System.out.printf("%-5s | %-30s | %-15s | %-15s | %-30s | %-12s\n",
                "ID", "NOME", "CPF", "TELEFONE", "ENDEREÇO", "DATA CADASTRO");
        System.out.println("------------------------------------------------------------------------------------------------------------");

        for (ClienteEntity cliente : clientes) {
            System.out.printf("%-5d | %-30s | %-15s | %-15s | %-30s | %-12s\n",
                    cliente.getId(),
                    cliente.getNome(),
                    cliente.getCpf(),
                    cliente.getTelefone(),
                    cliente.getEndereco(),
                    cliente.getDataDeCadastro());
        }
    }

    private void cadastrarCliente() {
        System.out.println("\n=== CADASTRO DE CLIENTE ===\n");

        System.out.print("Nome: ");
        String nome = scanner.nextLine();

        System.out.print("CPF: ");
        String cpf = scanner.nextLine();

        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();

        System.out.print("Endereço: ");
        String endereco = scanner.nextLine();

        boolean sucesso = controller.cadastrarCliente(nome, cpf, telefone, endereco);

        if (sucesso) {
            System.out.println("Cliente cadastrado com sucesso!");
        } else {
            System.out.println("Erro ao cadastrar cliente. CPF já existe ou ocorreu um problema no banco de dados.");
        }
    }

    private void atualizarCliente() {
        System.out.println("\n=== ATUALIZAÇÃO DE CLIENTE ===\n");

        listarClientes();

        System.out.print("\nDigite o ID do cliente que deseja atualizar: ");
        int id;
        try {
            id = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("ID inválido.");
            return;
        }

        System.out.print("Nome: ");
        String nome = scanner.nextLine();

        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();

        System.out.print("Endereço: ");
        String endereco = scanner.nextLine();

        boolean sucesso = controller.atualizarCliente(id, nome, telefone, endereco);

        if (sucesso) {
            System.out.println("Cliente atualizado com sucesso!");
        } else {
            System.out.println("Erro ao atualizar cliente. ID não encontrado ou ocorreu um problema no banco de dados.");
        }
    }

    private void excluirCliente() {
        System.out.println("\n=== EXCLUSÃO DE CLIENTE ===\n");

        listarClientes();

        System.out.print("\nDigite o ID do cliente que deseja excluir: ");
        int id;
        try {
            id = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("ID inválido.");
            return;
        }

        System.out.print("Tem certeza que deseja excluir este cliente? (S/N): ");
        String confirmacao = scanner.nextLine();

        if (confirmacao.equalsIgnoreCase("S")) {
            boolean sucesso = controller.excluirCliente(id);

            if (sucesso) {
                System.out.println("Cliente excluído com sucesso!");
            } else {
                System.out.println("Erro ao excluir cliente. ID não encontrado ou ocorreu um problema no banco de dados.");
            }
        } else {
            System.out.println("Operação cancelada.");
        }
    }
}