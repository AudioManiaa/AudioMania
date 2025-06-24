package com.audiomania.view;

import com.audiomania.controller.HistoricoController;
import com.audiomania.model.entities.VendaEntity;
import com.audiomania.utils.StyleConfigurator;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class HistoricoView extends JFrame {
    private JTextField dataInicioField;
    private JTextField dataFimField;
    private JTextField buscaField;
    private JButton buscarButton;
    private JButton filtrarPorDataButton;
    private JButton limparFiltrosButton;
    private JButton fecharButton;
    private JTable historicoTable;
    private DefaultTableModel tableModel;
    private JLabel totalVendasLabel;
    private JLabel valorTotalLabel;
    private JFrame menuView;
    private HistoricoController controller;

    public HistoricoView() {
        this(null);
    }

    public HistoricoView(JFrame menuView) {
        this.menuView = menuView;
        this.controller = new HistoricoController();

        // Aplicar o StyleConfigurator existente
        StyleConfigurator.applyStyles();

        setTitle("Histórico de Vendas - Audio Mania");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
        carregarDados();
    }

    private void initComponents() {
        // Painel principal com fundo do tema
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Painel superior - Título e filtros
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));

        // Título principal
        JLabel titleLabel = new JLabel("HISTÓRICO DE VENDAS");
        titleLabel.setFont(new Font("Inter", Font.BOLD, 18));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        topPanel.add(titleLabel);
        topPanel.add(Box.createVerticalStrut(15));

        // Painel de filtros
        JPanel filtrosPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));

        // Busca por cliente/produto
        JLabel buscaLabel = new JLabel("Buscar:");
        buscaField = new JTextField(15);
        buscarButton = new JButton("Buscar");

        filtrosPanel.add(buscaLabel);
        filtrosPanel.add(buscaField);
        filtrosPanel.add(buscarButton);
        filtrosPanel.add(Box.createHorizontalStrut(20));

        // Filtro por data
        JLabel dataInicioLabel = new JLabel("De:");
        dataInicioField = new JTextField(10);
        dataInicioField.setText(LocalDate.now().minusMonths(1).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

        JLabel dataFimLabel = new JLabel("Até:");
        dataFimField = new JTextField(10);
        dataFimField.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

        filtrarPorDataButton = new JButton("Filtrar por Data");
        limparFiltrosButton = new JButton("Limpar Filtros");

        filtrosPanel.add(dataInicioLabel);
        filtrosPanel.add(dataInicioField);
        filtrosPanel.add(dataFimLabel);
        filtrosPanel.add(dataFimField);
        filtrosPanel.add(filtrarPorDataButton);
        filtrosPanel.add(limparFiltrosButton);

        topPanel.add(filtrosPanel);

        // Painel da tabela
        JPanel tablePanel = new JPanel(new BorderLayout());

        JLabel tableTitle = new JLabel("Registro de Vendas");
        tableTitle.setFont(new Font("Inter", Font.BOLD, 14));
        tableTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        tablePanel.add(tableTitle, BorderLayout.NORTH);

        // Criar tabela
        String[] colunas = {"ID", "Data", "Cliente", "Produto", "Quantidade", "Valor Unitário", "Desconto", "Valor Total", "Pagamento"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        historicoTable = new JTable(tableModel);
        historicoTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Configurar larguras das colunas
        historicoTable.getColumnModel().getColumn(0).setPreferredWidth(50);  // ID
        historicoTable.getColumnModel().getColumn(1).setPreferredWidth(80);  // Data
        historicoTable.getColumnModel().getColumn(2).setPreferredWidth(120); // Cliente
        historicoTable.getColumnModel().getColumn(3).setPreferredWidth(150); // Produto
        historicoTable.getColumnModel().getColumn(4).setPreferredWidth(80);  // Quantidade
        historicoTable.getColumnModel().getColumn(5).setPreferredWidth(100); // Valor Unitário
        historicoTable.getColumnModel().getColumn(6).setPreferredWidth(80);  // Desconto
        historicoTable.getColumnModel().getColumn(7).setPreferredWidth(100); // Valor Total
        historicoTable.getColumnModel().getColumn(8).setPreferredWidth(100); // Pagamento

        JScrollPane scrollPane = new JScrollPane(historicoTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        // Painel de estatísticas
        JPanel statsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));

        totalVendasLabel = new JLabel("Total de Vendas: 0");
        totalVendasLabel.setFont(new Font("Inter", Font.BOLD, 14));

        valorTotalLabel = new JLabel("Valor Total: R$ 0,00");
        valorTotalLabel.setFont(new Font("Inter", Font.BOLD, 14));

        statsPanel.add(totalVendasLabel);
        statsPanel.add(Box.createHorizontalStrut(30));
        statsPanel.add(valorTotalLabel);

        tablePanel.add(statsPanel, BorderLayout.SOUTH);

        // Painel de botões
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));

        fecharButton = new JButton("Fechar");

        buttonPanel.add(fecharButton);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(tablePanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
        configurarListeners();
    }

    private void configurarListeners() {
        buscarButton.addActionListener(e -> buscarVendas());
        filtrarPorDataButton.addActionListener(e -> filtrarPorData());
        limparFiltrosButton.addActionListener(e -> limparFiltros());
        fecharButton.addActionListener(e -> fecharTela());

        // Listeners para Enter nos campos
        buscaField.addActionListener(e -> buscarVendas());
        dataInicioField.addActionListener(e -> filtrarPorData());
        dataFimField.addActionListener(e -> filtrarPorData());
    }

    private void buscarVendas() {
        String termo = buscaField.getText();

        List<VendaEntity> vendas = controller.buscarVendas(termo);
        atualizarTabela(vendas);
        atualizarEstatisticas(vendas);

        if (vendas.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Nenhuma venda encontrada com o termo de busca.",
                    "Busca", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void filtrarPorData() {
        String dataInicio = dataInicioField.getText();
        String dataFim = dataFimField.getText();

        String resultado = controller.validarDatas(dataInicio, dataFim);

        if (!resultado.equals("VALIDO")) {
            switch (resultado) {
                case "DATA_INICIO_INVALIDA":
                    JOptionPane.showMessageDialog(this, "Data de início inválida! Use o formato dd/MM/yyyy", "Erro", JOptionPane.ERROR_MESSAGE);
                    dataInicioField.requestFocus();
                    return;

                case "DATA_FIM_INVALIDA":
                    JOptionPane.showMessageDialog(this, "Data de fim inválida! Use o formato dd/MM/yyyy", "Erro", JOptionPane.ERROR_MESSAGE);
                    dataFimField.requestFocus();
                    return;

                case "DATA_INICIO_MAIOR":
                    JOptionPane.showMessageDialog(this, "Data de início não pode ser maior que data de fim!", "Erro", JOptionPane.ERROR_MESSAGE);
                    dataInicioField.requestFocus();
                    return;
            }
        }

        List<VendaEntity> vendas = controller.filtrarPorPeriodo(dataInicio, dataFim);
        atualizarTabela(vendas);
        atualizarEstatisticas(vendas);

        if (vendas.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Nenhuma venda encontrada no período selecionado.",
                    "Filtro por Data", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this,
                    String.format("Encontradas %d vendas no período de %s a %s",
                            vendas.size(), dataInicio, dataFim),
                    "Filtro por Data", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void limparFiltros() {
        buscaField.setText("");
        dataInicioField.setText(LocalDate.now().minusMonths(1).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        dataFimField.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        carregarDados();
    }

    private void fecharTela() {
        dispose();
        if (menuView != null) {
            menuView.setVisible(true);
        }
    }

    private void carregarDados() {
        List<VendaEntity> vendas = controller.listarTodasVendas();
        atualizarTabela(vendas);
        atualizarEstatisticas(vendas);
    }

    private void atualizarTabela(List<VendaEntity> vendas) {
        tableModel.setRowCount(0);
        for (VendaEntity venda : vendas) {
            Object[] linha = controller.formatarVendaParaTabela(venda);
            tableModel.addRow(linha);
        }
    }

    private void atualizarEstatisticas(List<VendaEntity> vendas) {
        int totalVendas = vendas.size();
        double valorTotal = controller.calcularValorTotal(vendas);

        totalVendasLabel.setText("Total de Vendas: " + totalVendas);
        valorTotalLabel.setText("Valor Total: " + String.format("R$ %.2f", valorTotal));
    }

    public void iniciar() {
        setVisible(true);
    }
}