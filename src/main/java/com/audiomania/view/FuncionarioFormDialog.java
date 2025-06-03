package com.audiomania.view;

import com.audiomania.entities.FuncionarioEntity;
import com.audiomania.service.FuncionarioService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FuncionarioFormDialog extends JDialog {
    private JTextField nomeField, cpfField, cargoField, telefoneField;
    private JPasswordField senhaField;
    private JButton salvarButton, cancelarButton;
    private boolean saved = false;
    private boolean isEditMode;
    private FuncionarioEntity funcionario;

    public FuncionarioFormDialog(Frame parent, String title, boolean modal, FuncionarioEntity funcionario, boolean isEditMode) {
        super(parent, title, modal);
        this.funcionario = funcionario;
        this.isEditMode = isEditMode;

        initComponents();
        layoutComponents();
        addListeners();

        if (isEditMode && funcionario != null) {
            preencherCampos(funcionario);
        }

        pack();
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }

    private void initComponents() {
        nomeField = new JTextField(30);
        cpfField = new JTextField(15);
        cargoField = new JTextField(20);
        telefoneField = new JTextField(15);
        senhaField = new JPasswordField(20);

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

        // CPF
        gbc.gridx = 0; gbc.gridy = 1; panel.add(new JLabel("CPF:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; panel.add(cpfField, gbc);

        // Cargo
        gbc.gridx = 0; gbc.gridy = 2; panel.add(new JLabel("Cargo:"), gbc);
        gbc.gridx = 1; gbc.gridy = 2; panel.add(cargoField, gbc);

        // Telefone
        gbc.gridx = 0; gbc.gridy = 3; panel.add(new JLabel("Telefone:"), gbc);
        gbc.gridx = 1; gbc.gridy = 3; panel.add(telefoneField, gbc);

        // Senha
        gbc.gridx = 0; gbc.gridy = 4; panel.add(new JLabel("Senha:"), gbc);
        gbc.gridx = 1; gbc.gridy = 4; panel.add(senhaField, gbc);
        if (isEditMode) {
            panel.add(new JLabel("(Deixe em branco para não alterar)"), gbc.gridx = 2, gbc.gridy = 4);
            gbc.gridx = 1; // reset gridx for next components
        }


        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(salvarButton);
        buttonPanel.add(cancelarButton);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(buttonPanel, gbc);

        add(panel);
    }

    private void preencherCampos(FuncionarioEntity func) {
        nomeField.setText(func.getNome());
        cpfField.setText(func.getCpf());
        cpfField.setEditable(false); // CPF não pode ser editado
        cargoField.setText(func.getCargo());
        telefoneField.setText(func.getTelefone());
        // Senha não é preenchida por segurança e para evitar atualização acidental
    }

    private void addListeners() {
        salvarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salvarFuncionario();
            }
        });

        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    private void salvarFuncionario() {
        String nome = nomeField.getText().trim();
        String cpf = cpfField.getText().trim();
        String cargo = cargoField.getText().trim();
        String telefone = telefoneField.getText().trim();
        String senha = new String(senhaField.getPassword());

        if (nome.isEmpty() || cpf.isEmpty() || cargo.isEmpty() || telefone.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos os campos, exceto senha, são obrigatórios.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!isEditMode && senha.isEmpty()) {
             JOptionPane.showMessageDialog(this, "Senha é obrigatória para novos funcionários.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean success;
        if (isEditMode) {
            // Se a senha estiver vazia, não atualize. Senão, atualize.
            String senhaParaAtualizar = senha.isEmpty() ? null : senha;
            success = FuncionarioService.atualizarFuncionario(funcionario.getId(), nome, cargo, telefone, senhaParaAtualizar);
        } else {
            success = FuncionarioService.cadastrarFuncionario(nome, cpf, cargo, telefone, senha);
        }

        if (success) {
            saved = true;
            JOptionPane.showMessageDialog(this, "Funcionário salvo com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao salvar funcionário.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean isSaved() {
        return saved;
    }
}
