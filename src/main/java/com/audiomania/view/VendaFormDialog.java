package com.audiomania.view;

import com.audiomania.model.Produto;
import com.audiomania.model.Venda;
import com.audiomania.repository.ProdutoRepository;
import com.audiomania.repository.VendaRepository;
import com.audiomania.entities.FuncionarioEntity;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class VendaFormDialog extends JDialog {

    // Helper class for JComboBox display
    static class ProdutoDisplayItem {
        Produto produto;
        public ProdutoDisplayItem(Produto p) { this.produto = p; }
        public Produto getProduto() { return produto; }
        @Override public String toString() {
            if (produto == null) return "Selecione um Produto";
            return produto.getNome() + " (R$ " + produto.getPreco().setScale(2, RoundingMode.HALF_UP) +
                   ", Estoque: " + produto.getQuantidade_Estoque() + ")";
        }
    }

    private JTextField clienteIdField;
    private JComboBox<ProdutoDisplayItem> produtoComboBox;
    private JSpinner quantidadeSpinner;
    private JTextField formaPagamentoField;
    private JTextField descontoField;
    private JLabel precoUnitarioLabel, subtotalLabel, totalFinalLabel;

    private JButton registrarVendaButton, cancelarButton;

    private ProdutoRepository produtoRepo;
    private VendaRepository vendaRepo;
    private FuncionarioEntity loggedInEmployee;

    private boolean saved = false;

    public VendaFormDialog(Dialog owner, String title, boolean modal,
                           ProdutoRepository produtoRepo, VendaRepository vendaRepo, FuncionarioEntity loggedInEmployee) {
        super(owner, title, modal);
        this.produtoRepo = produtoRepo;
        this.vendaRepo = vendaRepo;
        this.loggedInEmployee = loggedInEmployee;

        initComponents();
        layoutComponents();
        addListeners();
        populateProdutoComboBox();
        atualizarValores(); // Initial calculation

        pack();
        setMinimumSize(new Dimension(500, getHeight()));
        setLocationRelativeTo(owner);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }

    private void initComponents() {
        clienteIdField = new JTextField(10);
        produtoComboBox = new JComboBox<>();
        quantidadeSpinner = new JSpinner(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1));
        formaPagamentoField = new JTextField("Dinheiro",15); // Default value
        descontoField = new JTextField("0.00", 10);

        precoUnitarioLabel = new JLabel("Preço Unit.: R$ 0.00");
        subtotalLabel = new JLabel("Subtotal: R$ 0.00");
        totalFinalLabel = new JLabel("TOTAL: R$ 0.00");
        totalFinalLabel.setFont(totalFinalLabel.getFont().deriveFont(Font.BOLD, 16f));


        registrarVendaButton = new JButton("Registrar Venda");
        cancelarButton = new JButton("Cancelar");
    }

    private void populateProdutoComboBox() {
        produtoComboBox.addItem(new ProdutoDisplayItem(null)); // Add a placeholder
        List<Produto> produtosDisponiveis = produtoRepo.listarTodosProdutos();
        if (produtosDisponiveis != null) {
            produtosDisponiveis.stream()
                .filter(p -> p.getQuantidade_Estoque() > 0)
                .map(ProdutoDisplayItem::new)
                .forEach(produtoComboBox::addItem);
        }
    }

    private void layoutComponents() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Row 0: Cliente ID
        gbc.gridx = 0; gbc.gridy = 0; panel.add(new JLabel("Cliente ID:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; gbc.gridwidth = 2; panel.add(clienteIdField, gbc);
        gbc.gridwidth = 1; // reset

        // Row 1: Produto
        gbc.gridx = 0; gbc.gridy = 1; panel.add(new JLabel("Produto:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; gbc.gridwidth = 2; panel.add(produtoComboBox, gbc);
        gbc.gridwidth = 1;

        // Row 2: Quantidade
        gbc.gridx = 0; gbc.gridy = 2; panel.add(new JLabel("Quantidade:"), gbc);
        gbc.gridx = 1; gbc.gridy = 2; gbc.gridwidth = 2; panel.add(quantidadeSpinner, gbc);
        gbc.gridwidth = 1;

        // Row 3: Preco Unitario (display only)
        gbc.gridx = 0; gbc.gridy = 3; panel.add(new JLabel(""), gbc); // Spacer
        gbc.gridx = 1; gbc.gridy = 3; gbc.gridwidth = 2; panel.add(precoUnitarioLabel, gbc);
        gbc.gridwidth = 1;

        // Row 4: Forma de Pagamento
        gbc.gridx = 0; gbc.gridy = 4; panel.add(new JLabel("Forma Pagamento:"), gbc);
        gbc.gridx = 1; gbc.gridy = 4; gbc.gridwidth = 2; panel.add(formaPagamentoField, gbc);
        gbc.gridwidth = 1;

        // Row 5: Desconto
        gbc.gridx = 0; gbc.gridy = 5; panel.add(new JLabel("Desconto (R$):"), gbc);
        gbc.gridx = 1; gbc.gridy = 5; gbc.gridwidth = 2; panel.add(descontoField, gbc);
        gbc.gridwidth = 1;

        // Row 6: Subtotal (display only)
        gbc.gridx = 0; gbc.gridy = 6; panel.add(new JLabel(""), gbc); // Spacer
        gbc.gridx = 1; gbc.gridy = 6; gbc.gridwidth = 2; panel.add(subtotalLabel, gbc);
        gbc.gridwidth = 1;

        // Row 7: Total Final (display only)
        gbc.gridx = 0; gbc.gridy = 7; panel.add(new JLabel(""), gbc); // Spacer
        gbc.gridx = 1; gbc.gridy = 7; gbc.gridwidth = 2; panel.add(totalFinalLabel, gbc);
        gbc.gridwidth = 1;


        // Buttons Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(registrarVendaButton);
        buttonPanel.add(cancelarButton);

        gbc.gridx = 0; gbc.gridy = 8; gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(buttonPanel, gbc);

        add(panel);
    }

    private void addListeners() {
        produtoComboBox.addActionListener(e -> atualizarValores());
        quantidadeSpinner.addChangeListener(e -> atualizarValores());
        descontoField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void changedUpdate(javax.swing.event.DocumentEvent e) { atualizarValores(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { atualizarValores(); }
            public void insertUpdate(javax.swing.event.DocumentEvent e) { atualizarValores(); }
        });

        registrarVendaButton.addActionListener(e -> registrarVenda());
        cancelarButton.addActionListener(e -> dispose());
    }

    private void atualizarValores() {
        ProdutoDisplayItem selectedItem = (ProdutoDisplayItem) produtoComboBox.getSelectedItem();
        if (selectedItem == null || selectedItem.getProduto() == null) {
            precoUnitarioLabel.setText("Preço Unit.: R$ 0.00");
            subtotalLabel.setText("Subtotal: R$ 0.00");
            totalFinalLabel.setText("TOTAL: R$ 0.00");
            return;
        }

        Produto produto = selectedItem.getProduto();
        BigDecimal precoUnit = produto.getPreco().setScale(2, RoundingMode.HALF_UP);
        int quantidade = (Integer) quantidadeSpinner.getValue();

        precoUnitarioLabel.setText("Preço Unit.: R$ " + precoUnit);

        BigDecimal subtotal = precoUnit.multiply(new BigDecimal(quantidade)).setScale(2, RoundingMode.HALF_UP);
        subtotalLabel.setText("Subtotal: R$ " + subtotal);

        BigDecimal desconto = BigDecimal.ZERO;
        try {
            desconto = new BigDecimal(descontoField.getText().trim()).setScale(2, RoundingMode.HALF_UP);
            if (desconto.compareTo(BigDecimal.ZERO) < 0) desconto = BigDecimal.ZERO; // No negative discount
        } catch (NumberFormatException ex) {
            // Ignore, use zero discount if format is invalid for calculation
        }

        BigDecimal totalFinal = subtotal.subtract(desconto);
        if (totalFinal.compareTo(BigDecimal.ZERO) < 0) {
            totalFinal = BigDecimal.ZERO; // Total cannot be negative
        }
        totalFinalLabel.setText("TOTAL: R$ " + totalFinal.setScale(2, RoundingMode.HALF_UP));
    }

    private void registrarVenda() {
        String clienteIdStr = clienteIdField.getText().trim();
        ProdutoDisplayItem selectedProdutoItem = (ProdutoDisplayItem) produtoComboBox.getSelectedItem();
        String formaPagamento = formaPagamentoField.getText().trim();
        String descontoStr = descontoField.getText().trim();
        int quantidade = (Integer) quantidadeSpinner.getValue();

        // Validations
        int idCliente;
        try {
            idCliente = Integer.parseInt(clienteIdStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID do Cliente inválido.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (selectedProdutoItem == null || selectedProdutoItem.getProduto() == null) {
            JOptionPane.showMessageDialog(this, "Selecione um produto.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Produto produtoSelecionado = selectedProdutoItem.getProduto();

        if (quantidade <= 0) {
            JOptionPane.showMessageDialog(this, "Quantidade deve ser maior que zero.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (quantidade > produtoSelecionado.getQuantidade_Estoque()) {
            JOptionPane.showMessageDialog(this, "Quantidade solicitada excede o estoque disponível (" + produtoSelecionado.getQuantidade_Estoque() + ").", "Erro de Estoque", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (formaPagamento.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Forma de pagamento é obrigatória.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return;
        }

        BigDecimal descontoValor;
        try {
            descontoValor = new BigDecimal(descontoStr).setScale(2, RoundingMode.HALF_UP);
             if (descontoValor.compareTo(BigDecimal.ZERO) < 0) {
                JOptionPane.showMessageDialog(this, "Desconto não pode ser negativo.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Valor de desconto inválido.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return;
        }

        BigDecimal valorUnitario = produtoSelecionado.getPreco();
        BigDecimal subTotalVenda = valorUnitario.multiply(new BigDecimal(quantidade));
        BigDecimal valorTotalVenda = subTotalVenda.subtract(descontoValor);
         if (valorTotalVenda.compareTo(BigDecimal.ZERO) < 0) {
            valorTotalVenda = BigDecimal.ZERO;
        }


        Venda novaVenda = new Venda();
        novaVenda.setData(LocalDate.now());
        novaVenda.setIdCliente(idCliente);
        novaVenda.setIdFuncionario(loggedInEmployee.getId()); // Assuming FuncionarioEntity has getId()
        novaVenda.setIdProduto(produtoSelecionado.getId_Produto());
        novaVenda.setQuantidade(quantidade);
        novaVenda.setValorTotal(valorTotalVenda.setScale(2, RoundingMode.HALF_UP));
        novaVenda.setFormaPagamento(formaPagamento);
        novaVenda.setDesconto(descontoValor);

        // Transaction-like behavior
        int estoqueOriginal = produtoSelecionado.getQuantidade_Estoque();
        produtoSelecionado.setQuantidade_Estoque(estoqueOriginal - quantidade);

        boolean estoqueAtualizado = false;
        try {
            // The ProdutoRepository.atualizarProduto prints to console, does not throw specific exception on failure to update
            // For GUI, it's better if repository methods return boolean or throw specific exceptions.
            // We'll assume it works if no SQLException is thrown by the underlying JDBC call.
            produtoRepo.atualizarProduto(produtoSelecionado);
            estoqueAtualizado = true; // Assume success if no exception from repo's internals
        } catch (Exception ex) { // Catching generic exception due to repo limitations
             JOptionPane.showMessageDialog(this, "Erro CRÍTICO ao tentar atualizar o estoque no repositório: " + ex.getMessage(), "Erro Repositório Produto", JOptionPane.ERROR_MESSAGE);
             produtoSelecionado.setQuantidade_Estoque(estoqueOriginal); // Attempt to revert in-memory state
             return; // Critical failure
        }


        if (estoqueAtualizado) {
            boolean vendaSalva = vendaRepo.salvar(novaVenda);
            if (vendaSalva) {
                JOptionPane.showMessageDialog(this, "Venda registrada com sucesso! ID da Venda: " + novaVenda.getId(), "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                saved = true;
                dispose();
            } else {
                // Revert stock
                produtoSelecionado.setQuantidade_Estoque(estoqueOriginal);
                try {
                    produtoRepo.atualizarProduto(produtoSelecionado);
                } catch (Exception exRevert) {
                     JOptionPane.showMessageDialog(this, "FALHA CRÍTICA ao tentar reverter estoque após falha ao salvar venda: " + exRevert.getMessage() , "Erro CRÍTICO Repositório", JOptionPane.ERROR_MESSAGE);
                }
                JOptionPane.showMessageDialog(this, "Erro ao salvar a venda. O estoque do produto foi revertido.", "Erro ao Salvar Venda", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            // This else might not be reached if atualizarProduto doesn't give clear boolean feedback
            // and we rely on catching exceptions from it.
            JOptionPane.showMessageDialog(this, "Erro ao atualizar o estoque do produto. A venda não foi registrada.", "Erro de Estoque", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean isSaved() {
        return saved;
    }
}
