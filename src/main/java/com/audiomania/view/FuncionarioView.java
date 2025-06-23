package com.audiomania.view;

import com.audiomania.controller.SistemaController;
import com.audiomania.model.entities.FuncionarioEntity;
import com.audiomania.utils.FabricaDeIcones;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class FuncionarioView extends JFrame {

    private JTextField buscaField;
    private JTextField idField;
    private JTextField nomeField;
    private JTextField cpfField;
    private JTextField cargoField;
    private JTextField telefoneField;
    private JPasswordField senhaField;
    private JCheckBox alterarSenhaCheck;
    private JButton buscarButton;
    private JButton cadastrarButton;
    private JButton atualizarButton;
    private JButton excluirButton;
    private JButton limparButton;
    private JButton voltarButton;
    private JButton recarregarButton;
    private JTable funcionariosTable;
    private DefaultTableModel tableModel;
    private SistemaController controller;
    private JFrame parentFrame;

    public FuncionarioView() {
        this(null);
    }

    public FuncionarioView(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        this.controller = new SistemaController();
        initComponents();
        carregarFuncionarios();
    }

    private void initComponents() {
        setTitle("Gerenciamento de Funcionários");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Painel Principal
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Painel Superior - Busca
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBorder(BorderFactory.createTitledBorder("Buscar Funcionário"));

        JLabel buscaLabel = new JLabel("Buscar:");
        buscaField = new JTextField(20);
        buscarButton = new JButton("Buscar", FabricaDeIcones.criarIcone("/icons/search.png", "Buscar", 16));
        recarregarButton = new JButton("Recarregar", FabricaDeIcones.criarIcone("/icons/refresh.png", "Recarregar", 16));

        topPanel.add(buscaLabel);
        topPanel.add(buscaField);
        topPanel.add(buscarButton);
        topPanel.add(recarregarButton);

        mainPanel.add(topPanel, BorderLayout.NORTH);

        // Painel Central
        JPanel centerPanel = new JPanel(new BorderLayout(10, 0));

        // Painel do Formulário (Esquerda)
        JPanel formPanel = createFormPanel();
        centerPanel.add(formPanel, BorderLayout.WEST);

        // Painel da Tabela (Centro)
        JPanel tablePanel = createTablePanel();
        centerPanel.add(tablePanel, BorderLayout.CENTER);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Painel Inferior - Botão Voltar
        JPanel bottomPanel = new JPanel(new FlowLayout());
        voltarButton = new JButton("Voltar", FabricaDeIcones.criarIcone("/icons/back.png", "Voltar", 20));
        bottomPanel.add(voltarButton);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);
        configurarListeners();
    }

    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Dados do Funcionário"));
        formPanel.setPreferredSize(new Dimension(400, 0));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // ID
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("ID:"), gbc);
        idField = new JTextField(15);
        idField.setEditable(false);
        idField.setBackground(Color.LIGHT_GRAY);
        gbc.gridx = 1;
        formPanel.add(idField, gbc);

        // Nome
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Nome:*"), gbc);
        nomeField = new JTextField(15);
        gbc.gridx = 1;
        formPanel.add(nomeField, gbc);

        // CPF
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("CPF:*"), gbc);
        cpfField = new JTextField(15);
        gbc.gridx = 1;
        formPanel.add(cpfField, gbc);

        // Cargo
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Cargo:*"), gbc);
        cargoField = new JTextField(15);
        gbc.gridx = 1;
        formPanel.add(cargoField, gbc);

        // Telefone
        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(new JLabel("Telefone:"), gbc);
        telefoneField = new JTextField(15);
        gbc.gridx = 1;
        formPanel.add(telefoneField, gbc);

        // Senha
        gbc.gridx = 0; gbc.gridy = 5;
        formPanel.add(new JLabel("Senha:*"), gbc);
        senhaField = new JPasswordField(15);
        gbc.gridx = 1;
        formPanel.add(senhaField, gbc);

        // Checkbox para alterar senha
        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 2;
        alterarSenhaCheck = new JCheckBox("Alterar senha (deixe marcado para alterar)");
        alterarSenhaCheck.setSelected(true);
        formPanel.add(alterarSenhaCheck, gbc);

        // Nota
        gbc.gridx = 0; gbc.gridy = 7; gbc.gridwidth = 2;
        JLabel notaLabel = new JLabel("<html>* Campos obrigatórios<br>Para edição: desmarque 'Alterar senha' para manter a senha atual</html>");
        notaLabel.setFont(new Font("Arial", Font.ITALIC, 11));
        formPanel.add(notaLabel, gbc);

        // Painel de Botões
        gbc.gridx = 0; gbc.gridy = 8; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.NONE;
        JPanel buttonPanel = new JPanel(new FlowLayout());

        cadastrarButton = new JButton("Cadastrar", FabricaDeIcones.criarIcone("/icons/add.png", "Cadastrar", 16));
        atualizarButton = new JButton("Atualizar", FabricaDeIcones.criarIcone("/icons/edit.png", "Atualizar", 16));
        excluirButton = new JButton("Excluir", FabricaDeIcones.criarIcone("/icons/delete.png", "Excluir", 16));
        limparButton = new JButton("Limpar", FabricaDeIcones.criarIcone("/icons/clear.png", "Limpar", 16));

        buttonPanel.add(cadastrarButton);
        buttonPanel.add(atualizarButton);
        buttonPanel.add(excluirButton);
        buttonPanel.add(limparButton);

        formPanel.add(buttonPanel, gbc);

        return formPanel;
    }

    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createTitledBorder("Lista de Funcionários"));

        tableModel = new DefaultTableModel(new Object[]{"ID", "Nome", "CPF", "Cargo", "Telefone", "Admissão"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        funcionariosTable = new JTable(tableModel);
        funcionariosTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        funcionariosTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                preencherCamposComSelecionado();
            }
        });

        // Configurar larguras das colunas
        funcionariosTable.getColumnModel().getColumn(0).setPreferredWidth(50);  // ID
        funcionariosTable.getColumnModel().getColumn(1).setPreferredWidth(150); // Nome
        funcionariosTable.getColumnModel().getColumn(2).setPreferredWidth(120); // CPF
        funcionariosTable.getColumnModel().getColumn(3).setPreferredWidth(100); // Cargo
        funcionariosTable.getColumnModel().getColumn(4).setPreferredWidth(120); // Telefone
        funcionariosTable.getColumnModel().getColumn(5).setPreferredWidth(100); // Admissão

        JScrollPane scrollPane = new JScrollPane(funcionariosTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        return tablePanel;
    }

    private void configurarListeners() {
        buscarButton.addActionListener(e -> buscarFuncionarios());
        recarregarButton.addActionListener(e -> carregarFuncionarios());
        cadastrarButton.addActionListener(e -> cadastrarFuncionario());
        atualizarButton.addActionListener(e -> atualizarFuncionario());
        excluirButton.addActionListener(e -> excluirFuncionario());
        limparButton.addActionListener(e -> limparCampos());
        voltarButton.addActionListener(e -> voltarTela());
        buscaField.addActionListener(e -> buscarFuncionarios());

        // Listener para o checkbox da senha
        alterarSenhaCheck.addActionListener(e -> {
            boolean alterarSenha = alterarSenhaCheck.isSelected();
            senhaField.setEnabled(alterarSenha);
            if (!alterarSenha) {
                senhaField.setText("");
            }
        });
    }

    private void buscarFuncionarios() {
        String termo = buscaField.getText();
        List<FuncionarioEntity> funcionarios = controller.buscarFuncionarios(termo);
        atualizarTabela(funcionarios);

        if (funcionarios.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Nenhum funcionário encontrado com o termo de busca.",
                    "Busca", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void cadastrarFuncionario() {
        String nome = nomeField.getText();
        String cpf = cpfField.getText();
        String cargo = cargoField.getText();
        String telefone = telefoneField.getText();
        String senha = new String(senhaField.getPassword());

        String resultado = controller.cadastrarFuncionario(nome, cpf, cargo, telefone, senha);

        switch (resultado) {
            case "SUCESSO":
                JOptionPane.showMessageDialog(this, "Funcionário cadastrado com sucesso!");
                carregarFuncionarios();
                limparCampos();
                nomeField.requestFocus();
                break;

            case "NOME_VAZIO":
                JOptionPane.showMessageDialog(this, "Nome é obrigatório!", "Erro", JOptionPane.ERROR_MESSAGE);
                nomeField.requestFocus();
                break;

            case "CPF_VAZIO":
                JOptionPane.showMessageDialog(this, "CPF é obrigatório!", "Erro", JOptionPane.ERROR_MESSAGE);
                cpfField.requestFocus();
                break;

            case "CPF_INVALIDO":
                JOptionPane.showMessageDialog(this, "CPF deve conter exatamente 11 dígitos numéricos!", "Erro", JOptionPane.ERROR_MESSAGE);
                cpfField.requestFocus();
                break;

            case "CPF_EXISTE":
                JOptionPane.showMessageDialog(this, "CPF já cadastrado no sistema!", "Erro", JOptionPane.ERROR_MESSAGE);
                cpfField.requestFocus();
                break;

            case "CARGO_VAZIO":
                JOptionPane.showMessageDialog(this, "Cargo é obrigatório!", "Erro", JOptionPane.ERROR_MESSAGE);
                cargoField.requestFocus();
                break;

            case "SENHA_VAZIA":
                JOptionPane.showMessageDialog(this, "Senha é obrigatória!", "Erro", JOptionPane.ERROR_MESSAGE);
                senhaField.requestFocus();
                break;

            case "TELEFONE_INVALIDO":
                JOptionPane.showMessageDialog(this, "Telefone deve conter entre 10 e 11 dígitos numéricos!", "Erro", JOptionPane.ERROR_MESSAGE);
                telefoneField.requestFocus();
                break;

            default:
                JOptionPane.showMessageDialog(this, "Erro ao cadastrar funcionário: " + resultado, "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void atualizarFuncionario() {
        if (idField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecione um funcionário na tabela para atualizar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Integer id = Integer.parseInt(idField.getText());
        String nome = nomeField.getText();
        String cpf = cpfField.getText();
        String cargo = cargoField.getText();
        String telefone = telefoneField.getText();

        // Verificar se deve alterar a senha
        String senha = null;
        if (alterarSenhaCheck.isSelected()) {
            senha = new String(senhaField.getPassword());
        }

        String resultado = controller.atualizarFuncionario(id, nome, cpf, cargo, telefone, senha, alterarSenhaCheck.isSelected());

        switch (resultado) {
            case "SUCESSO":
                JOptionPane.showMessageDialog(this, "Funcionário atualizado com sucesso!");
                carregarFuncionarios();
                limparCampos();
                break;

            case "NOME_VAZIO":
                JOptionPane.showMessageDialog(this, "Nome é obrigatório!", "Erro", JOptionPane.ERROR_MESSAGE);
                nomeField.requestFocus();
                break;

            case "CPF_INVALIDO":
                JOptionPane.showMessageDialog(this, "CPF deve conter exatamente 11 dígitos numéricos!", "Erro", JOptionPane.ERROR_MESSAGE);
                cpfField.requestFocus();
                break;

            case "CARGO_VAZIO":
                JOptionPane.showMessageDialog(this, "Cargo é obrigatório!", "Erro", JOptionPane.ERROR_MESSAGE);
                cargoField.requestFocus();
                break;

            case "SENHA_VAZIA_ALTERACAO":
                JOptionPane.showMessageDialog(this, "Se marcou 'Alterar senha', a senha é obrigatória!", "Erro", JOptionPane.ERROR_MESSAGE);
                senhaField.requestFocus();
                break;

            case "TELEFONE_INVALIDO":
                JOptionPane.showMessageDialog(this, "Telefone deve conter entre 10 e 11 dígitos numéricos!", "Erro", JOptionPane.ERROR_MESSAGE);
                telefoneField.requestFocus();
                break;

            default:
                JOptionPane.showMessageDialog(this, "Erro ao atualizar funcionário: " + resultado, "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void excluirFuncionario() {
        if (idField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecione um funcionário na tabela para excluir.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Integer id = Integer.parseInt(idField.getText());
        String nome = nomeField.getText();

        int confirmacao = JOptionPane.showConfirmDialog(this,
                "Tem certeza que deseja excluir o funcionário:\n" + nome + "?",
                "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);

        if (confirmacao == JOptionPane.YES_OPTION) {
            if (controller.excluirFuncionario(id)) {
                JOptionPane.showMessageDialog(this, "Funcionário excluído com sucesso!");
                carregarFuncionarios();
                limparCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao excluir funcionário.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void preencherCamposComSelecionado() {
        int selectedRow = funcionariosTable.getSelectedRow();
        if (selectedRow != -1) {
            Integer id = (Integer) tableModel.getValueAt(selectedRow, 0);
            FuncionarioEntity funcionario = controller.buscarFuncionarioPorId(id);

            if (funcionario != null) {
                controller.preencherCamposEdicao(funcionario, idField, nomeField, cpfField,
                        cargoField, telefoneField, senhaField);

                // Configurar checkbox para modo de edição
                alterarSenhaCheck.setSelected(false);
                senhaField.setEnabled(false);
                senhaField.setText("");
            }
        }
    }

    private void carregarFuncionarios() {
        List<FuncionarioEntity> funcionarios = controller.listarFuncionarios();
        atualizarTabela(funcionarios);
    }

    private void atualizarTabela(List<FuncionarioEntity> funcionarios) {
        tableModel.setRowCount(0);
        for (FuncionarioEntity funcionario : funcionarios) {
            Object[] linha = controller.formatarFuncionarioParaTabela(funcionario);
            tableModel.addRow(linha);
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

        // Resetar checkbox para modo cadastro
        alterarSenhaCheck.setSelected(true);
        senhaField.setEnabled(true);
    }

    private void voltarTela() {
        dispose();
        if (parentFrame != null) {
            parentFrame.setVisible(true);
        }
    }

    public void iniciarGerenciamento() {
        setVisible(true);
    }
}