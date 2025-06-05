package com.audiomania.view;

import com.audiomania.utils.StyleConfigurator;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProdutoView extends JFrame {
    private JTextField buscaField;
    private JComboBox<String> categoriaCombo;
    private JButton buscarButton;
    private JButton novoButton;
    private JButton editarButton;
    private JButton excluirButton;
    private JButton fecharButton;
    private JTable produtosTable;
    private DefaultTableModel tableModel;

    public ProdutoView() {
        // Aplicar tema padrao
        StyleConfigurator.applyStyles();

        setTitle("Gerenciar Produtos - Audio Mania");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
        carregarProdutos();
    }

    private void initComponents() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Painel busca
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());

        JLabel titleLabel = new JLabel("GERENCIAR PRODUTOS");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));

        JLabel buscaLabel = new JLabel("Buscar:");
        buscaField = new JTextField(15);

        JLabel categoriaLabel = new JLabel("Categoria:");
        String[] categorias = {"Exemplo", "Exemplo01", "Exemplo02", "Exemplo03", "Exemplo04"};
        categoriaCombo = new JComboBox<>(categorias);

        buscarButton = new JButton("Buscar");

        topPanel.add(titleLabel);
        topPanel.add(Box.createHorizontalStrut(20));
        topPanel.add(buscaLabel);
        topPanel.add(buscaField);
        topPanel.add(categoriaLabel);
        topPanel.add(categoriaCombo);
        topPanel.add(buscarButton);

        // Painel tabela
        String[] colunas = {"ID", "Nome", "Categoria", "Marca", "Preço", "Estoque"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        produtosTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(produtosTable);

        // Painel botões
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout());

        novoButton = new JButton("Novo");
        editarButton = new JButton("Editar");
        excluirButton = new JButton("Excluir");
        fecharButton = new JButton("Fechar");

        bottomPanel.add(novoButton);
        bottomPanel.add(editarButton);
        bottomPanel.add(excluirButton);
        bottomPanel.add(fecharButton);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        add(panel);

        // Listeners
        buscarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                buscarProduto();
            }
        });

        novoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                novoProduto();
            }
        });

        editarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editarProduto();
            }
        });

        excluirButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                excluirProduto();
            }
        });

        fecharButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    private void buscarProduto() {
        String termo = buscaField.getText();
        String categoria = (String) categoriaCombo.getSelectedItem();

        if (termo.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Digite algo para buscar!", "Aviso", JOptionPane.WARNING_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this,
                    "Buscando por: " + termo + "\nCategoria: " + categoria,
                    "Busca", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void novoProduto() {
        CadastroProdutoView cadastroView = new CadastroProdutoView(this);
        cadastroView.setVisible(true);
    }

    private void editarProduto() {
        int linha = produtosTable.getSelectedRow();

        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um produto para editar!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        EdicaoProdutoView edicaoView = new EdicaoProdutoView(this, linha);
        edicaoView.setVisible(true);
    }

    private void excluirProduto() {
        int linha = produtosTable.getSelectedRow();

        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um produto para excluir!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String nome = (String) tableModel.getValueAt(linha, 1);

        int opcao = JOptionPane.showConfirmDialog(this,
                "Tem certeza que deseja excluir o produto:\n" + nome + "?",
                "Confirmar Exclusão",
                JOptionPane.YES_NO_OPTION);

        if (opcao == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(this, "Produto excluído com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void carregarProdutos() {
        Object[][] produtos = {
                {1, "exemplo01", "categoria01", "marca01", "R$ 100,00", "10"},
                {2, "exemplo02", "categoria02", "marca02", "R$ 200,00", "5"},
                {3, "exemplo03", "categoria03", "marca03", "R$ 300,00", "15"},
                {4, "exemplo04", "categoria04", "marca04", "R$ 400,00", "8"},
                {5, "exemplo05", "categoria05", "marca05", "R$ 500,00", "20"}
        };

        for (Object[] produto : produtos) {
            tableModel.addRow(produto);
        }
    }

    public void iniciar() {
        setVisible(true);
    }
}

// Classe cadastro
class CadastroProdutoView extends JFrame {
    private JTextField nomeField;
    private JTextField categoriaField;
    private JTextField marcaField;
    private JTextField precoField;
    private JTextField estoqueField;
    private JButton cadastrarButton;
    private JButton voltarButton;
    private ProdutoView produtoView;

    public CadastroProdutoView(ProdutoView produtoView) {
        this.produtoView = produtoView;
        setTitle("Cadastro de Produto");
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

        JLabel titleLabel = new JLabel("=== CADASTRO DE PRODUTO ===");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        gbc.gridwidth = 1;

        // Nome
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Nome:"), gbc);

        nomeField = new JTextField(15);
        gbc.gridx = 1;
        panel.add(nomeField, gbc);

        // Categoria
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Categoria:"), gbc);

        categoriaField = new JTextField(15);
        gbc.gridx = 1;
        panel.add(categoriaField, gbc);

        // Marca
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Marca:"), gbc);

        marcaField = new JTextField(15);
        gbc.gridx = 1;
        panel.add(marcaField, gbc);

        // Preço
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(new JLabel("Preço:"), gbc);

        precoField = new JTextField(15);
        gbc.gridx = 1;
        panel.add(precoField, gbc);

        // Estoque
        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(new JLabel("Estoque:"), gbc);

        estoqueField = new JTextField(15);
        gbc.gridx = 1;
        panel.add(estoqueField, gbc);

        // Botões
        cadastrarButton = new JButton("Cadastrar");
        gbc.gridx = 0;
        gbc.gridy = 6;
        panel.add(cadastrarButton, gbc);

        voltarButton = new JButton("Voltar");
        gbc.gridx = 1;
        panel.add(voltarButton, gbc);

        add(panel);

        // Listeners
        cadastrarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nome = nomeField.getText();
                String categoria = categoriaField.getText();
                String marca = marcaField.getText();
                String preco = precoField.getText();
                String estoque = estoqueField.getText();

                if (nome.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(CadastroProdutoView.this, "Nome é obrigatório!", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                JOptionPane.showMessageDialog(CadastroProdutoView.this,
                        "Produto cadastrado:\nNome: " + nome + "\nCategoria: " + categoria + "\nMarca: " + marca + "\nPreço: R$ " + preco + "\nEstoque: " + estoque,
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

// Classe edição
class EdicaoProdutoView extends JFrame {
    private JTextField nomeField;
    private JTextField categoriaField;
    private JTextField marcaField;
    private JTextField precoField;
    private JTextField estoqueField;
    private JButton salvarButton;
    private JButton voltarButton;
    private ProdutoView produtoView;
    private int linha;

    public EdicaoProdutoView(ProdutoView produtoView, int linha) {
        this.produtoView = produtoView;
        this.linha = linha;
        setTitle("Editar Produto");
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

        JLabel titleLabel = new JLabel("=== EDITAR PRODUTO ===");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        gbc.gridwidth = 1;

        // Campos preenchidos com dados genericoss
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Nome:"), gbc);

        nomeField = new JTextField("Nome do produto", 15);
        gbc.gridx = 1;
        panel.add(nomeField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Categoria:"), gbc);

        categoriaField = new JTextField("Categoria", 15);
        gbc.gridx = 1;
        panel.add(categoriaField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Marca:"), gbc);

        marcaField = new JTextField("Marca", 15);
        gbc.gridx = 1;
        panel.add(marcaField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(new JLabel("Preço:"), gbc);

        precoField = new JTextField("0,00", 15);
        gbc.gridx = 1;
        panel.add(precoField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(new JLabel("Estoque:"), gbc);

        estoqueField = new JTextField("0", 15);
        gbc.gridx = 1;
        panel.add(estoqueField, gbc);

        // Botões
        salvarButton = new JButton("Salvar");
        gbc.gridx = 0;
        gbc.gridy = 6;
        panel.add(salvarButton, gbc);

        voltarButton = new JButton("Voltar");
        gbc.gridx = 1;
        panel.add(voltarButton, gbc);

        add(panel);

        salvarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(EdicaoProdutoView.this,
                        "Produto atualizado com sucesso!",
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