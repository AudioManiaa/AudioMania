package com.audiomania.view;

import com.audiomania.utils.StyleConfigurator;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VendaView extends JFrame {
    private JTextField buscaField;
    private JComboBox<String> statusCombo;
    private JButton buscarButton;
    private JButton novaVendaButton;
    private JButton atualizarButton;
    private JButton cancelarButton;
    private JButton fecharButton;
    private JTable vendasTable;
    private DefaultTableModel tableModel;
    private JFrame menuView;

    public VendaView() {
        this(null);
    }

    public VendaView(JFrame menuView) {
        this.menuView = menuView;

        // Aplicar tema padrao
        StyleConfigurator.applyStyles();

        setTitle("Gerenciar Vendas - Audio Mania");
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
        carregarVendas();
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
        buscaField = new JTextField(15);

        JLabel statusLabel = new JLabel("Status:");
        String[] status = {"Todas", "Pendente", "Paga", "Cancelada", "Entregue"};
        statusCombo = new JComboBox<>(status);

        buscarButton = new JButton("Buscar");

        topPanel.add(titleLabel);
        topPanel.add(Box.createHorizontalStrut(20));
        topPanel.add(buscaLabel);
        topPanel.add(buscaField);
        topPanel.add(statusLabel);
        topPanel.add(statusCombo);
        topPanel.add(buscarButton);

        // Painel tabela
        String[] colunas = {"ID", "Data", "Cliente", "Produto", "Quantidade", "Valor Total", "Pagamento"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        vendasTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(vendasTable);

        // Painel botões
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout());

        novaVendaButton = new JButton("Nova Venda");
        atualizarButton = new JButton("Atualizar");
        cancelarButton = new JButton("Cancelar");
        fecharButton = new JButton("Fechar");

        bottomPanel.add(novaVendaButton);
        bottomPanel.add(atualizarButton);
        bottomPanel.add(cancelarButton);
        bottomPanel.add(fecharButton);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        add(panel);

        // Listeners
        buscarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                buscarVenda();
            }
        });

        novaVendaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                novaVenda();
            }
        });

        atualizarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                atualizarVenda();
            }
        });

        cancelarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cancelarVenda();
            }
        });

        fecharButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); // Fecha o VendaView
                if (menuView != null) {
                    menuView.setVisible(true); // Volta para o menu
                }
            }
        });
    }

    private void buscarVenda() {
        String termo = buscaField.getText();
        String status = (String) statusCombo.getSelectedItem();

        if (termo.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Digite algo para buscar!", "Aviso", JOptionPane.WARNING_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this,
                    "Buscando por: " + termo + "\nStatus: " + status,
                    "Busca", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void novaVenda() {
        NovaVendaView novaVendaView = new NovaVendaView(this);
        novaVendaView.setVisible(true);
    }

    private void atualizarVenda() {
        int linha = vendasTable.getSelectedRow();

        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma venda para atualizar!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        AtualizarVendaView atualizarView = new AtualizarVendaView(this, linha);
        atualizarView.setVisible(true);
    }

    private void cancelarVenda() {
        int linha = vendasTable.getSelectedRow();

        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma venda para cancelar!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String cliente = (String) tableModel.getValueAt(linha, 2);
        String produto = (String) tableModel.getValueAt(linha, 3);

        int opcao = JOptionPane.showConfirmDialog(this,
                "Tem certeza que deseja cancelar a venda:\nCliente: " + cliente + "\nProduto: " + produto + "?",
                "Confirmar Cancelamento",
                JOptionPane.YES_NO_OPTION);

        if (opcao == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(this, "Venda cancelada com sucesso!\nProdutos devolvidos ao estoque.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void carregarVendas() {
        Object[][] vendas = {
                {1, "05/06/2025", "João Silva", "Modulo Taramps DS 800x4", "1", "R$ 899,90", "PIX"},
                {2, "05/06/2025", "Maria Santos", "Subwoofer JBL 12", "2", "R$ 919,80", "Cartão"},
                {3, "04/06/2025", "Carlos Lima", "Kit 2 Vias Bravox", "1", "R$ 329,90", "Dinheiro"},
                {4, "04/06/2025", "Ana Costa", "Cabo RCA 5m", "3", "R$ 269,70", "PIX"},
                {5, "03/06/2025", "Pedro Alves", "Tweeter Selenium", "2", "R$ 359,80", "Cartão"}
        };

        for (Object[] venda : vendas) {
            tableModel.addRow(venda);
        }
    }

    public void iniciar() {
        setVisible(true);
    }
}

// Classe auxiliar para nova venda
class NovaVendaView extends JFrame {
    private JComboBox<String> clienteCombo;
    private JComboBox<String> produtoCombo;
    private JTextField quantidadeField;
    private JTextField descontoField;
    private JComboBox<String> pagamentoCombo;
    private JLabel valorTotalLabel;
    private JButton registrarButton;
    private JButton voltarButton;
    private VendaView vendaView;

    public NovaVendaView(VendaView vendaView) {
        this.vendaView = vendaView;
        setTitle("Registrar Nova Venda");
        setSize(450, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
    }

    private void initComponents() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

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
        panel.add(new JLabel("Cliente:"), gbc);

        String[] clientes = {"João Silva", "Maria Santos", "Carlos Lima", "Ana Costa", "Pedro Alves"};
        clienteCombo = new JComboBox<>(clientes);
        gbc.gridx = 1;
        panel.add(clienteCombo, gbc);

        // Produto
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Produto:"), gbc);

        String[] produtos = {"Modulo Taramps DS 800x4 - R$ 899,90", "Subwoofer JBL 12 - R$ 459,90", "Kit 2 Vias Bravox - R$ 329,90"};
        produtoCombo = new JComboBox<>(produtos);
        gbc.gridx = 1;
        panel.add(produtoCombo, gbc);

        // Quantidade
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Quantidade:"), gbc);

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
        panel.add(new JLabel("Pagamento:"), gbc);

        String[] pagamentos = {"PIX", "Cartão", "Dinheiro", "Transferência"};
        pagamentoCombo = new JComboBox<>(pagamentos);
        gbc.gridx = 1;
        panel.add(pagamentoCombo, gbc);

        // Valor Total
        gbc.gridx = 0;
        gbc.gridy = 6;
        panel.add(new JLabel("Valor Total:"), gbc);

        valorTotalLabel = new JLabel("R$ 899,90");
        valorTotalLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 1;
        panel.add(valorTotalLabel, gbc);

        // Botões
        registrarButton = new JButton("Registrar");
        gbc.gridx = 0;
        gbc.gridy = 7;
        panel.add(registrarButton, gbc);

        voltarButton = new JButton("Voltar");
        gbc.gridx = 1;
        panel.add(voltarButton, gbc);

        add(panel);

        // Listeners
        registrarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String cliente = (String) clienteCombo.getSelectedItem();
                String produto = (String) produtoCombo.getSelectedItem();
                String quantidade = quantidadeField.getText();
                String pagamento = (String) pagamentoCombo.getSelectedItem();

                if (quantidade.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(NovaVendaView.this, "Quantidade é obrigatória!", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                JOptionPane.showMessageDialog(NovaVendaView.this,
                        "Venda registrada com sucesso!\nCliente: " + cliente + "\nProduto: " + produto + "\nQuantidade: " + quantidade + "\nPagamento: " + pagamento + "\nValor: " + valorTotalLabel.getText(),
                        "Sucesso", JOptionPane.INFORMATION_MESSAGE);

                dispose();
            }
        });

        voltarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
}

// Classe auxiliar para atualizar venda
class AtualizarVendaView extends JFrame {
    private JComboBox<String> pagamentoCombo;
    private JTextField descontoField;
    private JLabel vendaInfoLabel;
    private JButton salvarButton;
    private JButton voltarButton;
    private VendaView vendaView;
    private int linha;

    public AtualizarVendaView(VendaView vendaView, int linha) {
        this.vendaView = vendaView;
        this.linha = linha;
        setTitle("Atualizar Venda");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
    }

    private void initComponents() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel titleLabel = new JLabel("=== ATUALIZAR VENDA ===");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        gbc.gridwidth = 1;

        // Info da venda
        vendaInfoLabel = new JLabel("<html>Venda ID: 1<br>Cliente: João Silva<br>Produto: Modulo Taramps</html>");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        panel.add(vendaInfoLabel, gbc);

        gbc.gridwidth = 1;

        // Forma de Pagamento
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Novo Pagamento:"), gbc);

        String[] pagamentos = {"PIX", "Cartão", "Dinheiro", "Transferência"};
        pagamentoCombo = new JComboBox<>(pagamentos);
        gbc.gridx = 1;
        panel.add(pagamentoCombo, gbc);

        // Desconto
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Novo Desconto:"), gbc);

        descontoField = new JTextField("0,00", 15);
        gbc.gridx = 1;
        panel.add(descontoField, gbc);

        // Botões
        salvarButton = new JButton("Salvar");
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(salvarButton, gbc);

        voltarButton = new JButton("Voltar");
        gbc.gridx = 1;
        panel.add(voltarButton, gbc);

        add(panel);

        // Listeners
        salvarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(AtualizarVendaView.this,
                        "Venda atualizada com sucesso!",
                        "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            }
        });

        voltarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
}