package com.audiomania.view;

import com.audiomania.entities.FuncionarioEntity;
import com.audiomania.service.FuncionarioService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Optional;

public class FuncionarioView {

    private JTable funcionarioTable;
    private DefaultTableModel tableModel;
    private JDialog dialog;
    private List<FuncionarioEntity> currentFuncionariosList; // To store the currently loaded list

    public FuncionarioView() {
        // Constructor can be used for other initializations if needed
    }

    public void mostrarJanelaGerenciamento(Frame parentFrame) {
        dialog = new JDialog(parentFrame, "Gerenciar Funcionários", true);
        dialog.setLayout(new BorderLayout());

        // Table Panel
        tableModel = new DefaultTableModel(new Object[]{"ID", "Nome", "CPF", "Cargo", "Telefone"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table cells non-editable
            }
        };
        funcionarioTable = new JTable(tableModel);
        funcionarioTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(funcionarioTable);
        dialog.add(scrollPane, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton addButton = new JButton("Adicionar");
        JButton editButton = new JButton("Editar");
        JButton deleteButton = new JButton("Excluir");
        JButton refreshButton = new JButton("Atualizar Lista");
        JButton closeButton = new JButton("Fechar");

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);
        buttonPanel.add(closeButton);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        // Action Listeners
        addButton.addActionListener(e -> adicionarFuncionario());
        editButton.addActionListener(e -> editarFuncionario());
        deleteButton.addActionListener(e -> excluirFuncionario());
        refreshButton.addActionListener(e -> carregarFuncionariosNaTabela());
        closeButton.addActionListener(e -> dialog.dispose());

        carregarFuncionariosNaTabela(); // Load data initially

        dialog.setSize(800, 600);
        dialog.setLocationRelativeTo(parentFrame);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setVisible(true);
    }

    private void carregarFuncionariosNaTabela() {
        tableModel.setRowCount(0); // Clear existing data
        currentFuncionariosList = FuncionarioService.listarTodos(); // Store the fetched list
        if (currentFuncionariosList != null) {
            for (FuncionarioEntity funcionario : currentFuncionariosList) {
                tableModel.addRow(new Object[]{
                        funcionario.getId(),
                        funcionario.getNome(),
                        funcionario.getCpf(),
                        funcionario.getCargo(),
                        funcionario.getTelefone()
                });
            }
        } else {
            JOptionPane.showMessageDialog(dialog, "Erro ao carregar lista de funcionários. A lista retornada é nula.", "Erro de Carregamento", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void adicionarFuncionario() {
        FuncionarioFormDialog form = new FuncionarioFormDialog(dialog, "Adicionar Novo Funcionário", true, null, false);
        form.setVisible(true);
        if (form.isSaved()) {
            carregarFuncionariosNaTabela();
        }
    }

    private void editarFuncionario() {
        int selectedRow = funcionarioTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(dialog, "Por favor, selecione um funcionário da tabela para editar.", "Nenhum Funcionário Selecionado", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Integer funcionarioId = (Integer) tableModel.getValueAt(selectedRow, 0);

        if (currentFuncionariosList == null) {
            JOptionPane.showMessageDialog(dialog, "A lista de funcionários não está carregada. Tente atualizar.", "Erro Interno", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Optional<FuncionarioEntity> funcionarioOpt = currentFuncionariosList.stream()
                .filter(f -> f.getId().equals(funcionarioId))
                .findFirst();

        if (funcionarioOpt.isPresent()) {
            FuncionarioEntity selectedFuncionario = funcionarioOpt.get();
            FuncionarioFormDialog form = new FuncionarioFormDialog(dialog, "Editar Funcionário: " + selectedFuncionario.getNome(), true, selectedFuncionario, true);
            form.setVisible(true);
            if (form.isSaved()) {
                carregarFuncionariosNaTabela();
            }
        } else {
            JOptionPane.showMessageDialog(dialog, "O funcionário selecionado (ID: " + funcionarioId + ") não foi encontrado na lista carregada. A lista pode estar desatualizada. Por favor, atualize.", "Funcionário Não Encontrado", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void excluirFuncionario() {
        int selectedRow = funcionarioTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(dialog, "Por favor, selecione um funcionário da tabela para excluir.", "Nenhum Funcionário Selecionado", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Integer funcionarioId = (Integer) tableModel.getValueAt(selectedRow, 0);
        String funcionarioNome = (String) tableModel.getValueAt(selectedRow, 1); // For confirmation dialog

        int confirm = JOptionPane.showConfirmDialog(dialog,
                "Tem certeza que deseja excluir o funcionário:\nID: " + funcionarioId + "\nNome: " + funcionarioNome,
                "Confirmar Exclusão",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            boolean success = FuncionarioService.excluirFuncionario(funcionarioId);
            if (success) {
                JOptionPane.showMessageDialog(dialog, "Funcionário ID " + funcionarioId + " excluído com sucesso!", "Exclusão Bem-sucedida", JOptionPane.INFORMATION_MESSAGE);
                carregarFuncionariosNaTabela();
            } else {
                JOptionPane.showMessageDialog(dialog, "Falha ao excluir o funcionário ID " + funcionarioId + ". Verifique se o funcionário existe ou se há dados relacionados.", "Erro na Exclusão", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
