package com.audiomania.view;

import com.audiomania.model.Produto;
import com.audiomania.model.Venda;
import com.audiomania.repository.ProdutoRepository;
import com.audiomania.repository.VendaRepository;
import com.audiomania.entities.FuncionarioEntity;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class VendaView {

    private VendaRepository vendaRepo;
    private ProdutoRepository produtoRepo;
    private FuncionarioEntity loggedInUser;

    private JTable vendasTable;
    private DefaultTableModel tableModel;
    private JDialog dialog;
    private List<Venda> currentVendasList; // To help get full object for cancellation if needed

    public VendaView(FuncionarioEntity loggedInUser) {
        this.loggedInUser = loggedInUser;
        this.vendaRepo = new VendaRepository();
        this.produtoRepo = new ProdutoRepository();
    }

    public void mostrarJanelaGerenciamento(Frame parentFrame) {
        dialog = new JDialog(parentFrame, "Gerenciar Vendas", true);
        dialog.setLayout(new BorderLayout());

        // Table Panel
        tableModel = new DefaultTableModel(
                new Object[]{"ID Venda", "Data", "Cliente ID", "Func. ID", "Prod. ID", "Qtd", "Total R$", "Pagamento", "Desconto R$"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Non-editable cells
            }
        };
        vendasTable = new JTable(tableModel);
        vendasTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(vendasTable);
        dialog.add(scrollPane, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton novaVendaButton = new JButton("Nova Venda");
        JButton cancelarVendaButton = new JButton("Cancelar Venda");
        JButton atualizarButton = new JButton("Atualizar Lista");
        JButton fecharButton = new JButton("Fechar");

        buttonPanel.add(novaVendaButton);
        buttonPanel.add(cancelarVendaButton);
        buttonPanel.add(atualizarButton);
        buttonPanel.add(fecharButton);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        // Action Listeners
        novaVendaButton.addActionListener(e -> abrirFormularioNovaVenda());
        cancelarVendaButton.addActionListener(e -> cancelarVendaSelecionada());
        atualizarButton.addActionListener(e -> carregarVendasNaTabela());
        fecharButton.addActionListener(e -> dialog.dispose());

        carregarVendasNaTabela(); // Initial data load

        dialog.setSize(900, 600);
        dialog.setLocationRelativeTo(parentFrame);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setVisible(true);
    }

    private void carregarVendasNaTabela() {
        tableModel.setRowCount(0); // Clear existing data
        currentVendasList = vendaRepo.buscarTodas();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        if (currentVendasList != null) {
            for (Venda venda : currentVendasList) {
                tableModel.addRow(new Object[]{
                        venda.getId(),
                        venda.getData().format(dateFormatter),
                        venda.getIdCliente(),
                        venda.getIdFuncionario(),
                        venda.getIdProduto(),
                        venda.getQuantidade(),
                        venda.getValorTotal(),
                        venda.getFormaPagamento(),
                        venda.getDesconto() != null ? venda.getDesconto() : BigDecimal.ZERO
                });
            }
        } else {
            JOptionPane.showMessageDialog(dialog, "Erro ao carregar lista de vendas.", "Erro de Carregamento", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void abrirFormularioNovaVenda() {
        VendaFormDialog form = new VendaFormDialog(dialog, "Registrar Nova Venda", true,
                this.produtoRepo, this.vendaRepo, this.loggedInUser);
        form.setVisible(true);
        if (form.isSaved()) {
            carregarVendasNaTabela();
        }
    }

    private void cancelarVendaSelecionada() {
        int selectedRow = vendasTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(dialog, "Por favor, selecione uma venda da tabela para cancelar.", "Nenhuma Venda Selecionada", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int vendaId = (int) tableModel.getValueAt(selectedRow, 0); // ID is in the first column

        int confirm = JOptionPane.showConfirmDialog(dialog,
                "Tem certeza que deseja cancelar a venda ID " + vendaId + "?\nEsta ação tentará reverter o estoque do produto.",
                "Confirmar Cancelamento de Venda",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        Venda venda = vendaRepo.buscarPorId(vendaId);
        if (venda == null) {
            JOptionPane.showMessageDialog(dialog, "Venda (ID: " + vendaId + ") não encontrada no banco de dados. Pode já ter sido removida.", "Erro ao Cancelar", JOptionPane.ERROR_MESSAGE);
            carregarVendasNaTabela();
            return;
        }

        Produto produtoVendido = produtoRepo.buscarProdutoPorId(venda.getIdProduto());
        if (produtoVendido == null) {
            JOptionPane.showMessageDialog(dialog, "Produto associado à venda (ID Prod: " + venda.getIdProduto() + ") não encontrado. Não é possível reverter estoque. Cancelando apenas a venda.", "Aviso de Cancelamento", JOptionPane.WARNING_MESSAGE);
            // Proceed to cancel venda without stock operation if product is missing (data integrity issue)
        }

        boolean estoqueRevertidoComSucesso = false;
        int estoqueOriginalProduto = -1;

        if (produtoVendido != null) {
            estoqueOriginalProduto = produtoVendido.getQuantidade_Estoque();
            produtoVendido.setQuantidade_Estoque(estoqueOriginalProduto + venda.getQuantidade());
            try {
                produtoRepo.atualizarProduto(produtoVendido); // Assumes this method works or throws exception
                estoqueRevertidoComSucesso = true;
            } catch (Exception e) {
                 JOptionPane.showMessageDialog(dialog, "Erro CRÍTICO ao tentar reverter o estoque do produto ID " + produtoVendido.getId_Produto() + ": " + e.getMessage() + ". A venda NÃO será cancelada.", "Erro Repositório Produto", JOptionPane.ERROR_MESSAGE);
                 // Critical failure, do not proceed with venda removal if stock cannot be reliably updated.
                 // Manually revert in-memory change for safety, though DB is the source of truth.
                 produtoVendido.setQuantidade_Estoque(estoqueOriginalProduto);
                 return;
            }
        } else {
             estoqueRevertidoComSucesso = true; // No stock operation to perform, so proceed with sale cancellation
        }


        if (estoqueRevertidoComSucesso) {
            boolean vendaRemovida = vendaRepo.remover(vendaId);
            if (vendaRemovida) {
                JOptionPane.showMessageDialog(dialog, "Venda ID " + vendaId + " cancelada com sucesso. Estoque (se aplicável) revertido.", "Cancelamento Bem-sucedido", JOptionPane.INFORMATION_MESSAGE);
                carregarVendasNaTabela();
            } else {
                // Venda removal failed, attempt to revert stock update again
                if (produtoVendido != null) {
                    produtoVendido.setQuantidade_Estoque(estoqueOriginalProduto); // Revert to original before failed cancellation
                     try {
                        produtoRepo.atualizarProduto(produtoVendido);
                    } catch (Exception eRevert) {
                        JOptionPane.showMessageDialog(dialog, "FALHA CRÍTICA ao tentar re-reverter estoque após falha ao remover venda: " + eRevert.getMessage() , "Erro CRÍTICO Repositório", JOptionPane.ERROR_MESSAGE);
                    }
                }
                JOptionPane.showMessageDialog(dialog, "Erro ao remover a venda ID " + vendaId + " do banco de dados. O estoque (se aplicável) foi revertido.", "Erro ao Cancelar Venda", JOptionPane.ERROR_MESSAGE);
            }
        }
        // The case where stock update itself failed is handled above by returning.
    }
}
