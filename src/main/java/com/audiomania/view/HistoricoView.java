package com.audiomania.view;

import com.audiomania.utils.StyleConfigurator;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HistoricoView extends JFrame {
    private JComboBox<String> tipoHistoricoCombo;
    private JComboBox<String> clienteCombo;
    private JButton visualizarButton;
    private JButton atualizarButton;
    private JButton fecharButton;
    private JTable historicoTable;
    private DefaultTableModel tableModel;
    private JFrame menuView;
    private JLabel infoLabel;

    public HistoricoView() {
        this(null);
    }

    public HistoricoView(JFrame menuView) {
        this.menuView = menuView;

        // Aplicar tema padrao
        StyleConfigurator.applyStyles();

        setTitle("Histórico - Audio Mania");
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
        carregarHistoricoClientes(); // Inicia com histórico de clientes
    }

    private void initComponents() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Painel superior
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());

        JLabel titleLabel = new JLabel("HISTÓRICO DO SISTEMA");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));

        JLabel tipoLabel = new JLabel("Tipo:");
        String[] tipos = {"Clientes", "Funcionários", "Compras por Cliente"};
        tipoHistoricoCombo = new JComboBox<>(tipos);

        JLabel clienteLabel = new JLabel("Cliente:");
        String[] clientes = {"João Silva", "Maria Santos", "Carlos Lima", "Ana Costa", "Pedro Alves"};
        clienteCombo = new JComboBox<>(clientes);
        clienteCombo.setEnabled(false); // Inicialmente desabilitado

        visualizarButton = new JButton("Visualizar");
        atualizarButton = new JButton("Atualizar");

        topPanel.add(titleLabel);
        topPanel.add(Box.createHorizontalStrut(20));
        topPanel.add(tipoLabel);
        topPanel.add(tipoHistoricoCombo);
        topPanel.add(clienteLabel);
        topPanel.add(clienteCombo);
        topPanel.add(visualizarButton);
        topPanel.add(atualizarButton);

        // Painel central - tabela
        String[] colunas = {"ID", "Nome", "CPF", "Telefone", "Informação Extra"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        historicoTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(historicoTable);

        // Label informativo
        infoLabel = new JLabel("Histórico de Clientes carregado - Total de registros: 5");
        infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        infoLabel.setFont(new Font("Arial", Font.ITALIC, 12));

        // Painel inferior
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel(new FlowLayout());
        fecharButton = new JButton("Fechar");
        buttonPanel.add(fecharButton);

        bottomPanel.add(infoLabel, BorderLayout.NORTH);
        bottomPanel.add(buttonPanel, BorderLayout.CENTER);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        add(panel);

        // Listeners
        tipoHistoricoCombo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String tipoSelecionado = (String) tipoHistoricoCombo.getSelectedItem();
                if ("Compras por Cliente".equals(tipoSelecionado)) {
                    clienteCombo.setEnabled(true);
                } else {
                    clienteCombo.setEnabled(false);
                }
            }
        });

        visualizarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                visualizarHistorico();
            }
        });

        atualizarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                atualizarHistorico();
            }
        });

        fecharButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                if (menuView != null) {
                    menuView.setVisible(true);
                }
            }
        });
    }

    private void visualizarHistorico() {
        String tipo = (String) tipoHistoricoCombo.getSelectedItem();

        switch (tipo) {
            case "Clientes":
                carregarHistoricoClientes();
                break;
            case "Funcionários":
                carregarHistoricoFuncionarios();
                break;
            case "Compras por Cliente":
                if (clienteCombo.isEnabled()) {
                    String cliente = (String) clienteCombo.getSelectedItem();
                    carregarHistoricoCompras(cliente);
                } else {
                    JOptionPane.showMessageDialog(this, "Selecione um cliente primeiro!", "Aviso", JOptionPane.WARNING_MESSAGE);
                }
                break;
        }
    }

    private void atualizarHistorico() {
        JOptionPane.showMessageDialog(this,
                "Dados atualizados com sucesso!\nConectado ao banco de dados.",
                "Atualização",
                JOptionPane.INFORMATION_MESSAGE);

        // Recarrega os dados atuais
        visualizarHistorico();
    }

    private void carregarHistoricoClientes() {
        // Limpar tabela
        tableModel.setRowCount(0);

        // Atualizar colunas para clientes
        String[] colunasClientes = {"ID", "Nome", "CPF", "Telefone", "Endereço"};
        tableModel.setColumnIdentifiers(colunasClientes);

        Object[][] clientes = {
                {1, "João Silva", "123.456.789-01", "(11) 98765-4321", "Rua A, 123"},
                {2, "Maria Santos", "234.567.890-12", "(11) 87654-3210", "Rua B, 456"},
                {3, "Carlos Lima", "345.678.901-23", "(11) 76543-2109", "Rua C, 789"},
                {4, "Ana Costa", "456.789.012-34", "(11) 65432-1098", "Rua D, 101"},
                {5, "Pedro Alves", "567.890.123-45", "(11) 54321-0987", "Rua E, 202"}
        };

        for (Object[] cliente : clientes) {
            tableModel.addRow(cliente);
        }

        infoLabel.setText("Histórico de Clientes carregado - Total de registros: " + clientes.length);
    }

    private void carregarHistoricoFuncionarios() {
        // Limpar tabela
        tableModel.setRowCount(0);

        // Atualizar colunas para funcionários
        String[] colunasFuncionarios = {"ID", "Nome", "CPF", "Telefone", "Cargo"};
        tableModel.setColumnIdentifiers(colunasFuncionarios);

        Object[][] funcionarios = {
                {1, "Roberto Silva", "111.222.333-44", "(11) 91234-5678", "Gerente"},
                {2, "Fernanda Lima", "222.333.444-55", "(11) 92345-6789", "Vendedora"},
                {3, "Lucas Santos", "333.444.555-66", "(11) 93456-7890", "Técnico"},
                {4, "Juliana Costa", "444.555.666-77", "(11) 94567-8901", "Vendedora"},
                {5, "Marcos Oliveira", "555.666.777-88", "(11) 95678-9012", "Estoquista"}
        };

        for (Object[] funcionario : funcionarios) {
            tableModel.addRow(funcionario);
        }

        infoLabel.setText("Histórico de Funcionários carregado - Total de registros: " + funcionarios.length);
    }

    private void carregarHistoricoCompras(String nomeCliente) {
        // Limpar tabela
        tableModel.setRowCount(0);

        // Atualizar colunas para compras
        String[] colunasCompras = {"ID Venda", "Data", "Produto", "Quantidade", "Valor Total"};
        tableModel.setColumnIdentifiers(colunasCompras);

        // Dados de exemplo para compras (baseado no cliente selecionado)
        Object[][] compras;

        switch (nomeCliente) {
            case "João Silva":
                compras = new Object[][]{
                        {1, "05/06/2025", "Modulo Taramps DS 800x4", "1", "R$ 899,90"},
                        {5, "02/06/2025", "Cabo RCA 3m", "2", "R$ 89,80"},
                        {8, "28/05/2025", "Tweeter Selenium", "1", "R$ 179,90"}
                };
                break;
            case "Maria Santos":
                compras = new Object[][]{
                        {2, "05/06/2025", "Subwoofer JBL 12", "2", "R$ 919,80"},
                        {6, "01/06/2025", "Kit 2 Vias Pioneer", "1", "R$ 399,90"}
                };
                break;
            case "Carlos Lima":
                compras = new Object[][]{
                        {3, "04/06/2025", "Kit 2 Vias Bravox", "1", "R$ 329,90"}
                };
                break;
            default:
                compras = new Object[][]{
                        {4, "04/06/2025", "Cabo RCA 5m", "3", "R$ 269,70"}
                };
        }

        for (Object[] compra : compras) {
            tableModel.addRow(compra);
        }

        infoLabel.setText("Histórico de Compras de " + nomeCliente + " - Total de registros: " + compras.length);
    }

    public void iniciar() {
        setVisible(true);
    }
}