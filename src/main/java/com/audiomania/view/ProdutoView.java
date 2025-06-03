package com.audiomania.view;

import com.audiomania.model.Produto;
import com.audiomania.repository.ProdutoRepository;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Optional;

public class ProdutoView {

    private ProdutoRepository repository;
    private DefaultTableModel tableModel;
    private JTable produtoTable;
    private JDialog dialog;
    private List<Produto> currentProdutosList;

    public ProdutoView() {
        this.repository = new ProdutoRepository(); // Instantiate the repository
    }

    public void mostrarJanelaGerenciamento(Frame parentFrame) {
        dialog = new JDialog(parentFrame, "Gerenciar Produtos", true);
        dialog.setLayout(new BorderLayout());

        // Table Panel
        tableModel = new DefaultTableModel(
                new Object[]{"ID", "Nome", "Preço", "Estoque", "Categoria", "Marca"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Non-editable cells
            }
        };
        produtoTable = new JTable(tableModel);
        produtoTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(produtoTable);
        dialog.add(scrollPane, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton addButton = new JButton("Adicionar");
        JButton editButton = new JButton("Editar");
        JButton removeButton = new JButton("Remover");
        JButton refreshButton = new JButton("Atualizar Lista");
        JButton closeButton = new JButton("Fechar");

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(refreshButton);
        buttonPanel.add(closeButton);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        // Action Listeners
        addButton.addActionListener(e -> adicionarProduto());
        editButton.addActionListener(e -> editarProduto());
        removeButton.addActionListener(e -> removerProduto());
        refreshButton.addActionListener(e -> carregarProdutosNaTabela());
        closeButton.addActionListener(e -> dialog.dispose());

        carregarProdutosNaTabela(); // Initial data load

        dialog.setSize(800, 600);
        dialog.setLocationRelativeTo(parentFrame);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setVisible(true);
    }

    private void carregarProdutosNaTabela() {
        tableModel.setRowCount(0); // Clear existing data
        currentProdutosList = repository.listarTodosProdutos();
        if (currentProdutosList != null) {
            for (Produto produto : currentProdutosList) {
                tableModel.addRow(new Object[]{
                        produto.getId_Produto(),
                        produto.getNome(),
                        produto.getPreco(),
                        produto.getQuantidade_Estoque(),
                        produto.getCategoria(),
                        produto.getMarca()
                });
            }
        } else {
            JOptionPane.showMessageDialog(dialog, "Erro ao carregar lista de produtos.", "Erro de Carregamento", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void adicionarProduto() {
        ProdutoFormDialog form = new ProdutoFormDialog(dialog, "Adicionar Novo Produto", true, null, false, this.repository);
        form.setVisible(true);
        if (form.isSaved()) {
            carregarProdutosNaTabela();
        }
    }

    private void editarProduto() {
        int selectedRow = produtoTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(dialog, "Por favor, selecione um produto da tabela para editar.", "Nenhum Produto Selecionado", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int produtoId = (int) tableModel.getValueAt(selectedRow, 0);

        if (currentProdutosList == null) {
             JOptionPane.showMessageDialog(dialog, "A lista de produtos não está carregada. Tente atualizar.", "Erro Interno", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Produto selectedProduto = currentProdutosList.stream()
                .filter(p -> p.getId_Produto() == produtoId)
                .findFirst()
                .orElse(null); // More direct way if list is kept in sync

        // Fallback or alternative: Produto selectedProduto = repository.buscarProdutoPorId(produtoId);

        if (selectedProduto != null) {
            ProdutoFormDialog form = new ProdutoFormDialog(dialog, "Editar Produto: " + selectedProduto.getNome(), true, selectedProduto, true, this.repository);
            form.setVisible(true);
            if (form.isSaved()) {
                carregarProdutosNaTabela();
            }
        } else {
            JOptionPane.showMessageDialog(dialog, "O produto selecionado (ID: " + produtoId + ") não foi encontrado. A lista pode estar desatualizada.", "Produto Não Encontrado", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void removerProduto() {
        int selectedRow = produtoTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(dialog, "Por favor, selecione um produto da tabela para remover.", "Nenhum Produto Selecionado", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int produtoId = (int) tableModel.getValueAt(selectedRow, 0);
        String produtoNome = (String) tableModel.getValueAt(selectedRow, 1);

        int confirm = JOptionPane.showConfirmDialog(dialog,
                "Tem certeza que deseja remover o produto:\nID: " + produtoId + "\nNome: " + produtoNome,
                "Confirmar Remoção",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            repository.removerProduto(produtoId);
            // Since repository.removerProduto prints to console and doesn't return success/failure,
            // we'll assume success if no exception is thrown up to this point, and refresh.
            // For a more robust solution, repository methods should return boolean or throw exceptions.
            JOptionPane.showMessageDialog(dialog, "Tentativa de remoção do produto ID " + produtoId + " concluída. Verifique a lista.", "Remoção Processada", JOptionPane.INFORMATION_MESSAGE);
            carregarProdutosNaTabela();
        }
    }
}
