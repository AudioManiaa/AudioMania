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
        //carregarClientes();
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

        //configurarListeners();
    }
}

