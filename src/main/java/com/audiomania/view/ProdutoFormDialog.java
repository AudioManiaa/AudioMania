package com.audiomania.view;

import com.audiomania.model.Produto;
import com.audiomania.repository.ProdutoRepository;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;

public class ProdutoFormDialog extends JDialog {
    private JTextField nomeField, descricaoField, precoField, quantidadeEstoqueField, categoriaField, marcaField;
    private JButton salvarButton, cancelarButton;

    private ProdutoRepository repository;
    private Produto currentProduto; // Produto being edited
    private boolean isEditMode;
    private boolean saved = false;

    public ProdutoFormDialog(Frame parent, String title, boolean modal, Produto produto, boolean isEditMode, ProdutoRepository repository) {
        super(parent, title, modal);
        this.currentProduto = produto;
        this.isEditMode = isEditMode;
        this.repository = repository;

        initComponents();
        layoutComponents();
        addListeners();

        if (isEditMode && produto != null) {
            preencherCampos(produto);
        }

        pack();
        setMinimumSize(new Dimension(400, 0)); // Minimum width
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }

    private void initComponents() {
        nomeField = new JTextField(30);
        descricaoField = new JTextField(30);
        precoField = new JTextField(10);
        quantidadeEstoqueField = new JTextField(10);
        categoriaField = new JTextField(20);
        marcaField = new JTextField(20);

        salvarButton = new JButton("Salvar");
        cancelarButton = new JButton("Cancelar");
    }

    private void layoutComponents() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Nome
        gbc.gridx = 0; gbc.gridy = 0; panel.add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; panel.add(nomeField, gbc);

        // Descricao
        gbc.gridx = 0; gbc.gridy = 1; panel.add(new JLabel("Descrição:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; panel.add(descricaoField, gbc);

        // Preco
        gbc.gridx = 0; gbc.gridy = 2; panel.add(new JLabel("Preço (R$):"), gbc);
        gbc.gridx = 1; gbc.gridy = 2; panel.add(precoField, gbc);

        // Quantidade Estoque
        gbc.gridx = 0; gbc.gridy = 3; panel.add(new JLabel("Estoque (Qtd):"), gbc);
        gbc.gridx = 1; gbc.gridy = 3; panel.add(quantidadeEstoqueField, gbc);

        // Categoria
        gbc.gridx = 0; gbc.gridy = 4; panel.add(new JLabel("Categoria:"), gbc);
        gbc.gridx = 1; gbc.gridy = 4; panel.add(categoriaField, gbc);

        // Marca
        gbc.gridx = 0; gbc.gridy = 5; panel.add(new JLabel("Marca:"), gbc);
        gbc.gridx = 1; gbc.gridy = 5; panel.add(marcaField, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(salvarButton);
        buttonPanel.add(cancelarButton);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(buttonPanel, gbc);

        add(panel);
    }

    private void preencherCampos(Produto p) {
        nomeField.setText(p.getNome());
        descricaoField.setText(p.getDescricao());
        precoField.setText(p.getPreco().toString());
        quantidadeEstoqueField.setText(String.valueOf(p.getQuantidade_Estoque()));
        categoriaField.setText(p.getCategoria());
        marcaField.setText(p.getMarca());
    }

    private void addListeners() {
        salvarButton.addActionListener(e -> salvarProduto());
        cancelarButton.addActionListener(e -> dispose());
    }

    private void salvarProduto() {
        String nome = nomeField.getText().trim();
        String descricao = descricaoField.getText().trim();
        String precoStr = precoField.getText().trim();
        String qtdEstoqueStr = quantidadeEstoqueField.getText().trim();
        String categoria = categoriaField.getText().trim();
        String marca = marcaField.getText().trim();

        if (nome.isEmpty() || categoria.isEmpty() || marca.isEmpty() || precoStr.isEmpty() || qtdEstoqueStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nome, Categoria, Marca, Preço e Quantidade em Estoque são obrigatórios.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return;
        }

        BigDecimal preco;
        int quantidadeEstoque;

        try {
            preco = new BigDecimal(precoStr);
            if (preco.compareTo(BigDecimal.ZERO) < 0) {
                JOptionPane.showMessageDialog(this, "Preço não pode ser negativo.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Preço inválido. Use formato numérico (ex: 19.99).", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            quantidadeEstoque = Integer.parseInt(qtdEstoqueStr);
            if (quantidadeEstoque < 0) {
                JOptionPane.showMessageDialog(this, "Quantidade em Estoque não pode ser negativa.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Quantidade em Estoque inválida. Use um número inteiro.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            if (isEditMode) {
                currentProduto.setNome(nome);
                currentProduto.setDescricao(descricao);
                currentProduto.setPreco(preco);
                currentProduto.setQuantidade_Estoque(quantidadeEstoque);
                currentProduto.setCategoria(categoria);
                currentProduto.setMarca(marca);
                repository.atualizarProduto(currentProduto);
                JOptionPane.showMessageDialog(this, "Produto atualizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } else {
                Produto novoProduto = new Produto(nome, descricao, preco, quantidadeEstoque, categoria, marca);
                repository.adicionarProduto(novoProduto);
                JOptionPane.showMessageDialog(this, "Produto adicionado com sucesso! ID: " + novoProduto.getId_Produto(), "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            }
            saved = true;
            dispose();
        } catch (Exception ex) {
            // Catching generic Exception because repository methods print to console and don't throw specific SQL exceptions upwards
            JOptionPane.showMessageDialog(this, "Erro ao salvar produto: " + ex.getMessage(), "Erro no Repositório", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean isSaved() {
        return saved;
    }
}
