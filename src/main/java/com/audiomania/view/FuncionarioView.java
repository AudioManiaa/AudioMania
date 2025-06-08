package com.audiomania.view;

import com.audiomania.model.entities.FuncionarioEntity;
import com.audiomania.model.service.FuncionarioService;
import com.audiomania.utils.FabricaDeIcones;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class FuncionarioView extends JFrame {

    private JTextField idField;
    private JTextField nomeField;
    private JTextField cpfField;
    private JTextField cargoField;
    private JTextField telefoneField;
    private JPasswordField senhaField;
    private JButton cadastrarButton;
    private JButton atualizarButton;
    private JButton excluirButton;
    private JButton limparButton;
    private JButton voltarButton;
    private JTable funcionariosTable;
    private DefaultTableModel tableModel;

    // FuncionarioService contém apenas métodos estáticos, não precisa de instância.

    public FuncionarioView() {
        // funcionarioService = new FuncionarioService(); // Não é necessário instanciar
        initComponents();
        listarFuncionarios();
    }

    private void initComponents() {
        setTitle("Gerenciamento de Funcionários");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Para não fechar a aplicação principal
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Painel de Formulário (Norte)
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // ID (não editável, apenas para exibição ao selecionar)
        gbc.gridx = 0; gbc.gridy = 0; formPanel.add(new JLabel("ID:"), gbc);
        idField = new JTextField(5);
        idField.setEditable(false);
        gbc.gridx = 1; gbc.gridy = 0; formPanel.add(idField, gbc);

        // Nome
        gbc.gridx = 0; gbc.gridy = 1; formPanel.add(new JLabel("Nome:"), gbc);
        nomeField = new JTextField(20);
        gbc.gridx = 1; gbc.gridy = 1; gbc.gridwidth = 3; formPanel.add(nomeField, gbc);
        gbc.gridwidth = 1; // Reset gridwidth

        // CPF
        gbc.gridx = 0; gbc.gridy = 2; formPanel.add(new JLabel("CPF:"), gbc);
        cpfField = new JTextField(15);
        gbc.gridx = 1; gbc.gridy = 2; formPanel.add(cpfField, gbc);

        // Cargo
        gbc.gridx = 2; gbc.gridy = 2; formPanel.add(new JLabel("Cargo:"), gbc);
        cargoField = new JTextField(15);
        gbc.gridx = 3; gbc.gridy = 2; formPanel.add(cargoField, gbc);

        // Telefone
        gbc.gridx = 0; gbc.gridy = 3; formPanel.add(new JLabel("Telefone:"), gbc);
        telefoneField = new JTextField(15);
        gbc.gridx = 1; gbc.gridy = 3; formPanel.add(telefoneField, gbc);

        // Senha
        gbc.gridx = 2; gbc.gridy = 3; formPanel.add(new JLabel("Senha:"), gbc);
        senhaField = new JPasswordField(15);
        gbc.gridx = 3; gbc.gridy = 3; formPanel.add(senhaField, gbc);

        add(formPanel, BorderLayout.NORTH);

        // Painel da Tabela (Centro)
        tableModel = new DefaultTableModel(new Object[]{"ID", "Nome", "CPF", "Cargo", "Telefone", "Admissão"}, 0);
        funcionariosTable = new JTable(tableModel);
        funcionariosTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        funcionariosTable.getSelectionModel().addListSelectionListener(e -> preencherCamposComSelecionado());
        JScrollPane scrollPane = new JScrollPane(funcionariosTable);
        add(scrollPane, BorderLayout.CENTER);

        // Painel de Botões (Sul)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        cadastrarButton = new JButton("Cadastrar", FabricaDeIcones.criarIcone("/icons/add.png", "Cadastrar", 20));
        cadastrarButton.addActionListener(e -> cadastrarFuncionario());
        buttonPanel.add(cadastrarButton);

        atualizarButton = new JButton("Atualizar", FabricaDeIcones.criarIcone("/icons/edit.png", "Atualizar", 20));
        atualizarButton.addActionListener(e -> atualizarFuncionario());
        buttonPanel.add(atualizarButton);

        excluirButton = new JButton("Excluir", FabricaDeIcones.criarIcone("/icons/delete.png", "Excluir", 20));
        excluirButton.addActionListener(e -> excluirFuncionario());
        buttonPanel.add(excluirButton);

        limparButton = new JButton("Limpar", FabricaDeIcones.criarIcone("/icons/clear.png", "Limpar", 20));
        limparButton.addActionListener(e -> limparCampos());
        buttonPanel.add(limparButton);

        voltarButton = new JButton("Voltar", FabricaDeIcones.criarIcone("/icons/back.png", "Voltar", 20));
        voltarButton.addActionListener(e -> fecharJanela());
        buttonPanel.add(voltarButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void listarFuncionarios() {
        tableModel.setRowCount(0); // Limpa a tabela
        List<FuncionarioEntity> funcionarios = FuncionarioService.listarTodos();
        for (FuncionarioEntity func : funcionarios) {
            tableModel.addRow(new Object[]{
                    func.getId(),
                    func.getNome(),
                    func.getCpf(),
                    func.getCargo(),
                    func.getTelefone(),
                    func.getDataAdmissao() != null ? func.getDataAdmissao().toString() : "N/A"
            });
        }
    }

    private void cadastrarFuncionario() {
        String nome = nomeField.getText().trim();
        String cpf = cpfField.getText().trim();
        String cargo = cargoField.getText().trim();
        String telefone = telefoneField.getText().trim();
        String senha = new String(senhaField.getPassword());

        if (nome.isEmpty() || cpf.isEmpty() || cargo.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nome, CPF, Cargo e Senha são obrigatórios.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validação do CPF para 11 dígitos numéricos
        if (!cpf.matches("\\d{11}")) {
            JOptionPane.showMessageDialog(this, "O CPF deve conter exatamente 11 dígitos numéricos.", "Erro de Validação do CPF", JOptionPane.ERROR_MESSAGE);
            cpfField.requestFocus(); // Foca no campo CPF para correção
            return;
        }

        FuncionarioEntity novoFuncionario = new FuncionarioEntity();
        novoFuncionario.setNome(nome);
        novoFuncionario.setCpf(cpf);
        novoFuncionario.setCargo(cargo);
        novoFuncionario.setTelefone(telefone);
        novoFuncionario.setSenha(senha); // A senha será tratada (ex: hashed) pelo serviço, se necessário

        boolean sucesso = FuncionarioService.cadastrarFuncionario(novoFuncionario);
        if (sucesso) {
            JOptionPane.showMessageDialog(this, "Funcionário cadastrado com sucesso!");
            listarFuncionarios();
            limparCampos();
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar funcionário. Verifique se o CPF já existe ou se os dados são válidos.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void atualizarFuncionario() {
        if (idField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecione um funcionário na tabela para atualizar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Integer id = Integer.parseInt(idField.getText());
        String nome = nomeField.getText().trim();
        String cpf = cpfField.getText().trim(); // Adicionado para validação
        String cargo = cargoField.getText().trim();
        String telefone = telefoneField.getText().trim();
        String senha = new String(senhaField.getPassword());

        if (nome.isEmpty() || cargo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nome e Cargo são obrigatórios.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            nomeField.requestFocus(); // Foca no primeiro campo problemático
            return;
        }

        // Validação do CPF para 11 dígitos numéricos
        if (!cpf.matches("\\d{11}")) {
            JOptionPane.showMessageDialog(this, "O CPF deve conter exatamente 11 dígitos numéricos.", "Erro de Validação do CPF", JOptionPane.ERROR_MESSAGE);
            cpfField.requestFocus(); // Foca no campo CPF para correção
            return;
        }

        // Se o campo senha estiver vazio, não atualiza a senha (passa null ou string vazia para o service)
        String senhaParaAtualizar = senha.isEmpty() ? null : senha;

        boolean sucesso = FuncionarioService.atualizarFuncionario(id, nome, cpf, cargo, telefone, senhaParaAtualizar);
        if (sucesso) {
            JOptionPane.showMessageDialog(this, "Funcionário atualizado com sucesso!");
            listarFuncionarios();
            limparCampos();
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar funcionário. Verifique os dados e tente novamente.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void excluirFuncionario() {
        if (idField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecione um funcionário na tabela para excluir.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Integer id = Integer.parseInt(idField.getText());

        int confirmacao = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja excluir este funcionário?", "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);
        if (confirmacao == JOptionPane.YES_OPTION) {
            boolean sucesso = FuncionarioService.excluirFuncionario(id);
            if (sucesso) {
                JOptionPane.showMessageDialog(this, "Funcionário excluído com sucesso!");
                listarFuncionarios();
                limparCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao excluir funcionário.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void preencherCamposComSelecionado() {
        int selectedRow = funcionariosTable.getSelectedRow();
        if (selectedRow != -1) {
            idField.setText(tableModel.getValueAt(selectedRow, 0).toString());
            nomeField.setText(tableModel.getValueAt(selectedRow, 1).toString());
            cpfField.setText(tableModel.getValueAt(selectedRow, 2).toString());
            cargoField.setText(tableModel.getValueAt(selectedRow, 3).toString());
            telefoneField.setText(tableModel.getValueAt(selectedRow, 4) != null ? tableModel.getValueAt(selectedRow, 4).toString() : "");
            senhaField.setText(""); // Limpa o campo senha por segurança
        }
    }

    private void limparCampos() {
        idField.setText("");
        nomeField.setText("");
        cpfField.setText("");
        cargoField.setText("");
        telefoneField.setText("");
        senhaField.setText("");
        funcionariosTable.clearSelection();
    }

    private void fecharJanela() {
        dispose(); // Fecha apenas esta janela
    }

    public void iniciarGerenciamento() {
        // Torna a janela visível. O construtor já inicializa os componentes.
        setVisible(true);
    }
}