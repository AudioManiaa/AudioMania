package com.audiomania.view;

import com.audiomania.controller.VendaController;
import com.audiomania.model.entities.VendaEntity;
import com.audiomania.utils.StyleConfigurator;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VendaView extends JFrame {
    private JTextField buscaField;
    private JButton buscarButton;
    private JButton novaVendaButton;
    private JButton atualizarButton;
    private JButton cancelarButton;
    private JButton fecharButton;
    private JButton recarregarButton;
    private JTable vendasTable;
    private DefaultTableModel tableModel;
    private JFrame menuView;
    private VendaController controller;

    public VendaView() {
        this(null);
    }

    public VendaView(JFrame menuView) {
        this.menuView = menuView;
        this.controller = new VendaController();

        StyleConfigurator.applyStyles();

        setTitle("Gerenciar Vendas - Audio Mania");
        setSize(1100, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
        carregarDados();
    }

    private void initComponents() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Painel busca
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());

        JLabel titleLabel = new JLabel("GERENCIAR VENDAS");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));

        JLabel buscaLabel = new JLabel("Buscar:");
        buscaField = new JTextField(20);

        buscarButton = new JButton("Buscar");
        recarregarButton = new JButton("Recarregar");

        topPanel.add(titleLabel);
        topPanel.add(Box.createHorizontalStrut(20));
        topPanel.add(buscaLabel);
        topPanel.add(buscaField);
        topPanel.add(buscarButton);
        topPanel.add(recarregarButton);

        // Painel tabela
        String[] colunas = {"ID", "Data", "Cliente", "Produto", "Quantidade", "Valor Total", "Pagamento"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        vendasTable = new JTable(tableModel);
        vendasTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(vendasTable);

        // Painel botões
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout());

        novaVendaButton = new JButton("Nova Venda");
        atualizarButton = new JButton("Atualizar");
        cancelarButton = new JButton("Cancelar Venda");
        fecharButton = new JButton("Fechar");

        bottomPanel.add(novaVendaButton);
        bottomPanel.add(atualizarButton);
        bottomPanel.add(cancelarButton);
        bottomPanel.add(fecharButton);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        add(panel);

        configurarListeners();
    }

    private void configurarListeners() {
        buscarButton.addActionListener(e -> buscarVendas());
        recarregarButton.addActionListener(e -> carregarDados());
        novaVendaButton.addActionListener(e -> novaVenda());
        atualizarButton.addActionListener(e -> atualizarVenda());
        cancelarButton.addActionListener(e -> cancelarVenda());
        fecharButton.addActionListener(e -> fecharTela());
        buscaField.addActionListener(e -> buscarVendas());
    }

    private void buscarVendas() {
        String termo = buscaField.getText();

        List<VendaEntity> vendas = controller.buscarVendas(termo);
        atualizarTabela(vendas);

        if (vendas.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Nenhuma venda encontrada com o termo de busca.",
                    "Busca", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void novaVenda() {
        NovaVendaView novaVendaView = new NovaVendaView(this, controller);
        novaVendaView.setVisible(true);
    }

    private void atualizarVenda() {
        int linha = vendasTable.getSelectedRow();

        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma venda para atualizar!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Integer id = (Integer) tableModel.getValueAt(linha, 0);
        VendaEntity venda = controller.buscarPorId(id);

        if (venda != null) {
            AtualizarVendaView atualizarView = new AtualizarVendaView(this, controller, venda);
            atualizarView.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Venda não encontrada!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cancelarVenda() {
        int linha = vendasTable.getSelectedRow();

        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma venda para cancelar!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Integer id = (Integer) tableModel.getValueAt(linha, 0);
        String cliente = (String) tableModel.getValueAt(linha, 2);
        String produto = (String) tableModel.getValueAt(linha, 3);

        int opcao = JOptionPane.showConfirmDialog(this,
                "Tem certeza que deseja cancelar a venda:\nCliente: " + cliente + "\nProduto: " + produto + "?",
                "Confirmar Cancelamento",
                JOptionPane.YES_NO_OPTION);

        if (opcao == JOptionPane.YES_OPTION) {
            if (controller.cancelarVenda(id)) {
                JOptionPane.showMessageDialog(this, "Venda cancelada com sucesso!\nProdutos devolvidos ao estoque.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                carregarDados();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao cancelar venda!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void fecharTela() {
        dispose();
        if (menuView != null) {
            menuView.setVisible(true);
        }
    }

    private void carregarDados() {
        carregarVendas();
    }

    private void carregarVendas() {
        List<VendaEntity> vendas = controller.listarVendas();
        atualizarTabela(vendas);
    }

    private void atualizarTabela(List<VendaEntity> vendas) {
        tableModel.setRowCount(0);
        for (VendaEntity venda : vendas) {
            Object[] linha = controller.formatarVendaParaTabela(venda);
            tableModel.addRow(linha);
        }
    }

    public void atualizarDados() {
        carregarDados();
    }

    public void iniciar() {
        setVisible(true);
    }
}

// Classe nova venda sem lógica de negócio
class NovaVendaView extends JFrame {
    private JComboBox<String> clienteCombo;
    private JComboBox<String> produtoCombo;
    private JTextField quantidadeField;
    private JTextField descontoField;
    private JComboBox<String> pagamentoCombo;
    private JLabel valorTotalLabel;
    private JButton calcularButton;
    private JButton registrarButton;
    private JButton voltarButton;
    private VendaView vendaView;
    private VendaController controller;

    public NovaVendaView(VendaView vendaView, VendaController controller) {
        this.vendaView = vendaView;
        this.controller = controller;
        setTitle("Registrar Nova Venda");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
        carregarDados();
    }

    private void initComponents() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("=== NOVA VENDA ===");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        gbc.gridwidth = 1;

        // Cliente
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Cliente:*"), gbc);

        clienteCombo = new JComboBox<>();
        gbc.gridx = 1;
        panel.add(clienteCombo, gbc);

        // Produto
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Produto:*"), gbc);

        produtoCombo = new JComboBox<>();
        gbc.gridx = 1;
        panel.add(produtoCombo, gbc);

        // Quantidade
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Quantidade:*"), gbc);

        quantidadeField = new JTextField("1", 15);
        gbc.gridx = 1;
        panel.add(quantidadeField, gbc);

        // Desconto
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(new JLabel("Desconto:"), gbc);

        descontoField = new JTextField("0,00", 15);
        gbc.gridx = 1;
        panel.add(descontoField, gbc);

        // Forma de Pagamento
        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(new JLabel("Pagamento:*"), gbc);

        pagamentoCombo = new JComboBox<>();
        gbc.gridx = 1;
        panel.add(pagamentoCombo, gbc);

        // Valor Total
        gbc.gridx = 0;
        gbc.gridy = 6;
        panel.add(new JLabel("Valor Total:"), gbc);

        valorTotalLabel = new JLabel("R$ 0,00");
        valorTotalLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 1;
        panel.add(valorTotalLabel, gbc);

        // Nota sobre campos obrigatórios
        JLabel notaLabel = new JLabel("* Campos obrigatórios");
        notaLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        panel.add(notaLabel, gbc);

        // Botões
        JPanel buttonPanel = new JPanel(new FlowLayout());
        calcularButton = new JButton("Calcular Total");
        registrarButton = new JButton("Registrar");
        voltarButton = new JButton("Voltar");

        buttonPanel.add(calcularButton);
        buttonPanel.add(registrarButton);
        buttonPanel.add(voltarButton);

        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        panel.add(buttonPanel, gbc);

        add(panel);

        configurarListeners();
    }

    private void configurarListeners() {
        calcularButton.addActionListener(e -> calcularTotal());
        registrarButton.addActionListener(e -> registrarVenda());
        voltarButton.addActionListener(e -> dispose());

        // Listeners simples - apenas delegam para o controller
        quantidadeField.addActionListener(e -> calcularTotal());
        descontoField.addActionListener(e -> calcularTotal());
        produtoCombo.addActionListener(e -> calcularTotal());

        // Listener para mudança de foco - recalcula quando sai do campo
        quantidadeField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent e) { calcularTotal(); }
        });

        descontoField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent e) { calcularTotal(); }
        });
    }

    private void carregarDados() {
        controller.carregarDadosNovaVenda(clienteCombo, produtoCombo, pagamentoCombo);
    }

    private void calcularTotal() {
        Object produtoSelecionado = produtoCombo.getSelectedItem();
        String quantidade = quantidadeField.getText();
        String desconto = descontoField.getText();

        String valorCalculado = controller.calcularValorTotal(produtoSelecionado, quantidade, desconto);
        valorTotalLabel.setText(valorCalculado);
    }

    private void registrarVenda() {
        Object clienteSelecionado = clienteCombo.getSelectedItem();
        Object produtoSelecionado = produtoCombo.getSelectedItem();
        String quantidade = quantidadeField.getText();
        String desconto = descontoField.getText();
        Object pagamentoSelecionado = pagamentoCombo.getSelectedItem();
        String valorTotal = valorTotalLabel.getText();

        String resultado = controller.registrarVenda(clienteSelecionado, produtoSelecionado,
                quantidade, desconto, pagamentoSelecionado, valorTotal);

        switch (resultado) {
            case "SUCESSO":
                JOptionPane.showMessageDialog(this,
                        "Venda registrada com sucesso!",
                        "Sucesso", JOptionPane.INFORMATION_MESSAGE);

                vendaView.atualizarDados();
                limparCampos();
                break;

            case "CLIENTE_NAO_SELECIONADO":
                JOptionPane.showMessageDialog(this, "Selecione um cliente!", "Erro", JOptionPane.ERROR_MESSAGE);
                clienteCombo.requestFocus();
                break;

            case "PRODUTO_NAO_SELECIONADO":
                JOptionPane.showMessageDialog(this, "Selecione um produto!", "Erro", JOptionPane.ERROR_MESSAGE);
                produtoCombo.requestFocus();
                break;

            case "QUANTIDADE_INVALIDA":
                JOptionPane.showMessageDialog(this, "Quantidade deve ser um número inteiro maior que zero!", "Erro", JOptionPane.ERROR_MESSAGE);
                quantidadeField.requestFocus();
                break;

            case "DESCONTO_INVALIDO":
                JOptionPane.showMessageDialog(this, "Desconto deve ser um valor numérico válido!", "Erro", JOptionPane.ERROR_MESSAGE);
                descontoField.requestFocus();
                break;

            case "PAGAMENTO_NAO_SELECIONADO":
                JOptionPane.showMessageDialog(this, "Selecione uma forma de pagamento!", "Erro", JOptionPane.ERROR_MESSAGE);
                pagamentoCombo.requestFocus();
                break;

            case "ESTOQUE_INSUFICIENTE":
                JOptionPane.showMessageDialog(this, "Estoque insuficiente para a quantidade solicitada!", "Erro", JOptionPane.ERROR_MESSAGE);
                quantidadeField.requestFocus();
                break;

            default:
                JOptionPane.showMessageDialog(this, "Erro ao registrar venda: " + resultado, "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limparCampos() {
        clienteCombo.setSelectedIndex(0);
        produtoCombo.setSelectedIndex(0);
        quantidadeField.setText("1");
        descontoField.setText("0,00");
        pagamentoCombo.setSelectedIndex(0);
        valorTotalLabel.setText("R$ 0,00");
    }
}

// Classe atualizar venda sem lógica de negócio
class AtualizarVendaView extends JFrame {
    private JComboBox<String> pagamentoCombo;
    private JTextField descontoField;
    private JLabel vendaInfoLabel;
    private JLabel valorTotalLabel;
    private JButton calcularButton;
    private JButton salvarButton;
    private JButton voltarButton;
    private VendaView vendaView;
    private VendaController controller;
    private VendaEntity venda;

    public AtualizarVendaView(VendaView vendaView, VendaController controller, VendaEntity venda) {
        this.vendaView = vendaView;
        this.controller = controller;
        this.venda = venda;
        setTitle("Atualizar Venda - ID: " + venda.getId());
        setSize(450, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
        carregarDados();
    }

    private void initComponents() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("=== ATUALIZAR VENDA ===");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        gbc.gridwidth = 1;

        // Info da venda
        vendaInfoLabel = new JLabel();
        vendaInfoLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        panel.add(vendaInfoLabel, gbc);

        gbc.gridwidth = 1;

        // Forma de Pagamento
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Novo Pagamento:"), gbc);

        pagamentoCombo = new JComboBox<>();
        gbc.gridx = 1;
        panel.add(pagamentoCombo, gbc);

        // Desconto
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Novo Desconto:"), gbc);

        descontoField = new JTextField(15);
        gbc.gridx = 1;
        panel.add(descontoField, gbc);

        // Valor Total
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(new JLabel("Valor Total:"), gbc);

        valorTotalLabel = new JLabel();
        valorTotalLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 1;
        panel.add(valorTotalLabel, gbc);

        // Botões
        JPanel buttonPanel = new JPanel(new FlowLayout());
        calcularButton = new JButton("Recalcular");
        salvarButton = new JButton("Salvar");
        voltarButton = new JButton("Voltar");

        buttonPanel.add(calcularButton);
        buttonPanel.add(salvarButton);
        buttonPanel.add(voltarButton);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        panel.add(buttonPanel, gbc);

        add(panel);

        configurarListeners();
    }

    private void configurarListeners() {
        calcularButton.addActionListener(e -> recalcularTotal());
        salvarButton.addActionListener(e -> salvarVenda());
        voltarButton.addActionListener(e -> dispose());
        descontoField.addActionListener(e -> recalcularTotal());
    }

    private void carregarDados() {
        controller.carregarDadosAtualizacao(venda, vendaInfoLabel, pagamentoCombo,
                descontoField, valorTotalLabel);
    }

    private void recalcularTotal() {
        String desconto = descontoField.getText();
        String novoValor = controller.recalcularValorTotal(venda, desconto);
        valorTotalLabel.setText(novoValor);
    }

    private void salvarVenda() {
        Object pagamentoSelecionado = pagamentoCombo.getSelectedItem();
        String desconto = descontoField.getText();

        String resultado = controller.atualizarVenda(venda, pagamentoSelecionado, desconto);

        switch (resultado) {
            case "SUCESSO":
                JOptionPane.showMessageDialog(this,
                        "Venda atualizada com sucesso!",
                        "Sucesso", JOptionPane.INFORMATION_MESSAGE);

                vendaView.atualizarDados();
                dispose();
                break;

            case "DESCONTO_INVALIDO":
                JOptionPane.showMessageDialog(this, "Desconto deve ser um valor numérico válido!", "Erro", JOptionPane.ERROR_MESSAGE);
                descontoField.requestFocus();
                break;

            case "PAGAMENTO_NAO_SELECIONADO":
                JOptionPane.showMessageDialog(this, "Selecione uma forma de pagamento!", "Erro", JOptionPane.ERROR_MESSAGE);
                pagamentoCombo.requestFocus();
                break;

            default:
                JOptionPane.showMessageDialog(this, "Erro ao atualizar venda: " + resultado, "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}