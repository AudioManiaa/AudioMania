package com.audiomania.view;

import com.audiomania.controller.ProdutoController;
import com.audiomania.model.entities.ProdutoEntity;
import com.audiomania.utils.StyleConfigurator;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ProdutoView extends JFrame {
    private JTextField buscaField;
    private JComboBox<String> categoriaCombo;
    private JButton buscarButton;
    private JButton novoButton;
    private JButton editarButton;
    private JButton excluirButton;
    private JButton fecharButton;
    private JButton recarregarButton;
    private JTable produtosTable;
    private DefaultTableModel tableModel;
    private JFrame menuView;
    private ProdutoController controller;

    public ProdutoView() {
        this(null);
    }

    public ProdutoView(JFrame menuView) {
        this.menuView = menuView;
        this.controller = new ProdutoController();

        StyleConfigurator.applyStyles();

        setTitle("Gerenciar Produtos - Audio Mania");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
        carregarDados();
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
        categoriaCombo = new JComboBox<>();

        buscarButton = new JButton("Buscar");
        recarregarButton = new JButton("Recarregar");

        topPanel.add(titleLabel);
        topPanel.add(Box.createHorizontalStrut(20));
        topPanel.add(buscaLabel);
        topPanel.add(buscaField);
        topPanel.add(categoriaLabel);
        topPanel.add(categoriaCombo);
        topPanel.add(buscarButton);
        topPanel.add(recarregarButton);

        // Painel tabela
        String[] colunas = {"ID", "Nome", "Descrição", "Categoria", "Marca", "Preço", "Estoque"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        produtosTable = new JTable(tableModel);
        produtosTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
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

        configurarListeners();
    }

    private void configurarListeners() {
        buscarButton.addActionListener(e -> buscarProdutos());
        recarregarButton.addActionListener(e -> carregarDados());
        novoButton.addActionListener(e -> novoProduto());
        editarButton.addActionListener(e -> editarProduto());
        excluirButton.addActionListener(e -> excluirProduto());
        fecharButton.addActionListener(e -> fecharTela());
        buscaField.addActionListener(e -> buscarProdutos());
    }

    private void buscarProdutos() {
        String termo = buscaField.getText();
        String categoria = (String) categoriaCombo.getSelectedItem();

        List<ProdutoEntity> produtos = controller.buscarProdutos(termo, categoria);
        atualizarTabela(produtos);

        if (produtos.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Nenhum produto encontrado com os filtros aplicados.",
                    "Busca", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void novoProduto() {
        CadastroProdutoView cadastroView = new CadastroProdutoView(this, controller);
        cadastroView.setVisible(true);
    }

    private void editarProduto() {
        int linha = produtosTable.getSelectedRow();

        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um produto para editar!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Integer id = (Integer) tableModel.getValueAt(linha, 0);
        ProdutoEntity produto = controller.buscarPorId(id);

        if (produto != null) {
            EdicaoProdutoView edicaoView = new EdicaoProdutoView(this, controller, produto);
            edicaoView.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Produto não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void excluirProduto() {
        int linha = produtosTable.getSelectedRow();

        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um produto para excluir!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Integer id = (Integer) tableModel.getValueAt(linha, 0);
        String nome = (String) tableModel.getValueAt(linha, 1);

        int opcao = JOptionPane.showConfirmDialog(this,
                "Tem certeza que deseja excluir o produto:\n" + nome + "?",
                "Confirmar Exclusão",
                JOptionPane.YES_NO_OPTION);

        if (opcao == JOptionPane.YES_OPTION) {
            if (controller.excluirProduto(id)) {
                JOptionPane.showMessageDialog(this, "Produto excluído com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                carregarDados();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao excluir produto!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void fecharTela() {
        dispose();
        if (menuView != null) {
            menuView.setVisible(true);
        }
    }

    private void carregarDados() {
        carregarProdutos();
        carregarCategorias();
    }

    private void carregarProdutos() {
        List<ProdutoEntity> produtos = controller.listarProdutos();
        atualizarTabela(produtos);
    }

    private void atualizarTabela(List<ProdutoEntity> produtos) {
        tableModel.setRowCount(0);
        for (ProdutoEntity produto : produtos) {
            Object[] linha = controller.formatarProdutoParaTabela(produto);
            tableModel.addRow(linha);
        }
    }

    private void carregarCategorias() {
        List<String> categorias = controller.listarCategorias();
        categoriaCombo.removeAllItems();
        for (String categoria : categorias) {
            categoriaCombo.addItem(categoria);
        }
    }

    public void atualizarDados() {
        carregarDados();
    }

    public void iniciar() {
        setVisible(true);
    }
}

// Classe cadastro sem lógica de negócio
class CadastroProdutoView extends JFrame {
    private JTextField nomeField;
    private JTextArea descricaoArea;
    private JComboBox<String> categoriaCombo;
    private JTextField marcaField;
    private JTextField precoField;
    private JTextField estoqueField;
    private JButton cadastrarButton;
    private JButton voltarButton;
    private ProdutoView produtoView;
    private ProdutoController controller;

    public CadastroProdutoView(ProdutoView produtoView, ProdutoController controller) {
        this.produtoView = produtoView;
        this.controller = controller;
        setTitle("Cadastro de Produto");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
        carregarCategorias();
    }

    private void initComponents() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

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
        panel.add(new JLabel("Nome:*"), gbc);

        nomeField = new JTextField(20);
        gbc.gridx = 1;
        panel.add(nomeField, gbc);

        // Descrição
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Descrição:"), gbc);

        descricaoArea = new JTextArea(3, 20);
        descricaoArea.setLineWrap(true);
        descricaoArea.setWrapStyleWord(true);
        JScrollPane descricaoScroll = new JScrollPane(descricaoArea);
        gbc.gridx = 1;
        panel.add(descricaoScroll, gbc);

        // Categoria
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Categoria:"), gbc);

        JPanel categoriaPanel = new JPanel(new BorderLayout(5, 0));
        categoriaCombo = new JComboBox<>();
        categoriaCombo.setEditable(true);

        JButton novaCategoriaButton = new JButton("Nova");
        novaCategoriaButton.setPreferredSize(new Dimension(60, 25));

        categoriaPanel.add(categoriaCombo, BorderLayout.CENTER);
        categoriaPanel.add(novaCategoriaButton, BorderLayout.EAST);

        gbc.gridx = 1;
        panel.add(categoriaPanel, gbc);

        // Listener para nova categoria
        novaCategoriaButton.addActionListener(e -> criarNovaCategoria());

        // Marca
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(new JLabel("Marca:"), gbc);

        marcaField = new JTextField(20);
        gbc.gridx = 1;
        panel.add(marcaField, gbc);

        // Preço
        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(new JLabel("Preço:*"), gbc);

        precoField = new JTextField(20);
        gbc.gridx = 1;
        panel.add(precoField, gbc);

        // Estoque
        gbc.gridx = 0;
        gbc.gridy = 6;
        panel.add(new JLabel("Estoque:*"), gbc);

        estoqueField = new JTextField(20);
        gbc.gridx = 1;
        panel.add(estoqueField, gbc);

        // Nota sobre campos obrigatórios
        JLabel notaLabel = new JLabel("* Campos obrigatórios");
        notaLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        panel.add(notaLabel, gbc);

        // Botões
        JPanel buttonPanel = new JPanel(new FlowLayout());
        cadastrarButton = new JButton("Cadastrar");
        voltarButton = new JButton("Voltar");

        buttonPanel.add(cadastrarButton);
        buttonPanel.add(voltarButton);

        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        panel.add(buttonPanel, gbc);

        add(panel);

        configurarListeners();
        nomeField.requestFocus();
    }

    private void configurarListeners() {
        cadastrarButton.addActionListener(e -> cadastrarProduto());
        voltarButton.addActionListener(e -> dispose());
    }

    private void criarNovaCategoria() {
        String novaCategoria = JOptionPane.showInputDialog(
                this,
                "Digite o nome da nova categoria:",
                "Nova Categoria",
                JOptionPane.PLAIN_MESSAGE
        );

        if (novaCategoria != null && !novaCategoria.trim().isEmpty()) {
            String resultado = controller.adicionarNovaCategoria(novaCategoria.trim(), categoriaCombo);

            if (resultado.equals("ADICIONADA")) {
                categoriaCombo.setSelectedItem(novaCategoria.trim());
            } else if (resultado.equals("EXISTE")) {
                JOptionPane.showMessageDialog(this,
                        "Esta categoria já existe!",
                        "Aviso",
                        JOptionPane.WARNING_MESSAGE);
                categoriaCombo.setSelectedItem(novaCategoria.trim());
            }
        }
    }

    private void carregarCategorias() {
        List<String> categorias = controller.listarCategoriasParaCadastro();
        categoriaCombo.removeAllItems();
        for (String categoria : categorias) {
            categoriaCombo.addItem(categoria);
        }
    }

    private void cadastrarProduto() {
        String nome = nomeField.getText();
        String descricao = descricaoArea.getText();
        Object categoriaObj = categoriaCombo.getSelectedItem();
        String categoria = categoriaObj != null ? categoriaObj.toString() : "";
        String marca = marcaField.getText();
        String precoStr = precoField.getText();
        String estoqueStr = estoqueField.getText();

        String resultado = controller.cadastrarProduto(nome, descricao, categoria, marca, precoStr, estoqueStr);

        switch (resultado) {
            case "SUCESSO":
                JOptionPane.showMessageDialog(this,
                        "Produto cadastrado com sucesso!",
                        "Sucesso", JOptionPane.INFORMATION_MESSAGE);

                produtoView.atualizarDados();
                limparCampos();
                nomeField.requestFocus();
                break;

            case "NOME_VAZIO":
                JOptionPane.showMessageDialog(this, "Nome é obrigatório!", "Erro", JOptionPane.ERROR_MESSAGE);
                nomeField.requestFocus();
                break;

            case "PRECO_VAZIO":
                JOptionPane.showMessageDialog(this, "Preço é obrigatório!", "Erro", JOptionPane.ERROR_MESSAGE);
                precoField.requestFocus();
                break;

            case "ESTOQUE_VAZIO":
                JOptionPane.showMessageDialog(this, "Estoque é obrigatório!", "Erro", JOptionPane.ERROR_MESSAGE);
                estoqueField.requestFocus();
                break;

            case "PRECO_INVALIDO":
                JOptionPane.showMessageDialog(this, "Preço inválido! Use números com ponto ou vírgula para decimais.", "Erro", JOptionPane.ERROR_MESSAGE);
                precoField.requestFocus();
                break;

            case "ESTOQUE_INVALIDO":
                JOptionPane.showMessageDialog(this, "Estoque inválido! Use apenas números inteiros.", "Erro", JOptionPane.ERROR_MESSAGE);
                estoqueField.requestFocus();
                break;

            default:
                JOptionPane.showMessageDialog(this, "Erro ao cadastrar produto: " + resultado, "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limparCampos() {
        nomeField.setText("");
        descricaoArea.setText("");
        categoriaCombo.setSelectedIndex(0);
        marcaField.setText("");
        precoField.setText("");
        estoqueField.setText("");
    }
}

// Classe edição sem lógica de negócio
class EdicaoProdutoView extends JFrame {
    private JTextField nomeField;
    private JTextArea descricaoArea;
    private JComboBox<String> categoriaCombo;
    private JTextField marcaField;
    private JTextField precoField;
    private JTextField estoqueField;
    private JButton salvarButton;
    private JButton voltarButton;
    private ProdutoView produtoView;
    private ProdutoController controller;
    private ProdutoEntity produto;

    public EdicaoProdutoView(ProdutoView produtoView, ProdutoController controller, ProdutoEntity produto) {
        this.produtoView = produtoView;
        this.controller = controller;
        this.produto = produto;
        setTitle("Editar Produto - " + produto.getNome());
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
        carregarCategorias();
        carregarDadosProduto();
    }

    private void initComponents() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("=== EDITAR PRODUTO ===");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        gbc.gridwidth = 1;

        // Nome
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Nome:*"), gbc);

        nomeField = new JTextField(20);
        gbc.gridx = 1;
        panel.add(nomeField, gbc);

        // Descrição
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Descrição:"), gbc);

        descricaoArea = new JTextArea(3, 20);
        descricaoArea.setLineWrap(true);
        descricaoArea.setWrapStyleWord(true);
        JScrollPane descricaoScroll = new JScrollPane(descricaoArea);
        gbc.gridx = 1;
        panel.add(descricaoScroll, gbc);

        // Categoria
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Categoria:"), gbc);

        JPanel categoriaPanel = new JPanel(new BorderLayout(5, 0));
        categoriaCombo = new JComboBox<>();
        categoriaCombo.setEditable(true);

        JButton novaCategoriaButton = new JButton("Nova");
        novaCategoriaButton.setPreferredSize(new Dimension(60, 25));

        categoriaPanel.add(categoriaCombo, BorderLayout.CENTER);
        categoriaPanel.add(novaCategoriaButton, BorderLayout.EAST);

        gbc.gridx = 1;
        panel.add(categoriaPanel, gbc);

        // Listener para nova categoria
        novaCategoriaButton.addActionListener(e -> criarNovaCategoria());

        // Marca
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(new JLabel("Marca:"), gbc);

        marcaField = new JTextField(20);
        gbc.gridx = 1;
        panel.add(marcaField, gbc);

        // Preço
        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(new JLabel("Preço:*"), gbc);

        precoField = new JTextField(20);
        gbc.gridx = 1;
        panel.add(precoField, gbc);

        // Estoque
        gbc.gridx = 0;
        gbc.gridy = 6;
        panel.add(new JLabel("Estoque:*"), gbc);

        estoqueField = new JTextField(20);
        gbc.gridx = 1;
        panel.add(estoqueField, gbc);

        // Nota sobre campos obrigatórios
        JLabel notaLabel = new JLabel("* Campos obrigatórios");
        notaLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        panel.add(notaLabel, gbc);

        // Botões
        JPanel buttonPanel = new JPanel(new FlowLayout());
        salvarButton = new JButton("Salvar");
        voltarButton = new JButton("Voltar");

        buttonPanel.add(salvarButton);
        buttonPanel.add(voltarButton);

        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        panel.add(buttonPanel, gbc);

        add(panel);

        configurarListeners();
    }

    private void configurarListeners() {
        salvarButton.addActionListener(e -> salvarProduto());
        voltarButton.addActionListener(e -> dispose());
    }

    private void criarNovaCategoria() {
        String novaCategoria = JOptionPane.showInputDialog(
                this,
                "Digite o nome da nova categoria:",
                "Nova Categoria",
                JOptionPane.PLAIN_MESSAGE
        );

        if (novaCategoria != null && !novaCategoria.trim().isEmpty()) {
            String resultado = controller.adicionarNovaCategoria(novaCategoria.trim(), categoriaCombo);

            if (resultado.equals("ADICIONADA")) {
                categoriaCombo.setSelectedItem(novaCategoria.trim());
            } else if (resultado.equals("EXISTE")) {
                JOptionPane.showMessageDialog(this,
                        "Esta categoria já existe!",
                        "Aviso",
                        JOptionPane.WARNING_MESSAGE);
                categoriaCombo.setSelectedItem(novaCategoria.trim());
            }
        }
    }

    private void carregarCategorias() {
        List<String> categorias = controller.listarCategoriasParaCadastro();
        categoriaCombo.removeAllItems();
        for (String categoria : categorias) {
            categoriaCombo.addItem(categoria);
        }
    }

    private void carregarDadosProduto() {
        controller.preencherCamposEdicao(produto, nomeField, descricaoArea, categoriaCombo, marcaField, precoField, estoqueField);
    }

    private void salvarProduto() {
        String nome = nomeField.getText();
        String descricao = descricaoArea.getText();
        Object categoriaObj = categoriaCombo.getSelectedItem();
        String categoria = categoriaObj != null ? categoriaObj.toString() : "";
        String marca = marcaField.getText();
        String precoStr = precoField.getText();
        String estoqueStr = estoqueField.getText();

        String resultado = controller.atualizarProduto(produto, nome, descricao, categoria, marca, precoStr, estoqueStr);

        switch (resultado) {
            case "SUCESSO":
                JOptionPane.showMessageDialog(this,
                        "Produto atualizado com sucesso!",
                        "Sucesso", JOptionPane.INFORMATION_MESSAGE);

                produtoView.atualizarDados();
                dispose();
                break;

            case "NOME_VAZIO":
                JOptionPane.showMessageDialog(this, "Nome é obrigatório!", "Erro", JOptionPane.ERROR_MESSAGE);
                nomeField.requestFocus();
                break;

            case "PRECO_VAZIO":
                JOptionPane.showMessageDialog(this, "Preço é obrigatório!", "Erro", JOptionPane.ERROR_MESSAGE);
                precoField.requestFocus();
                break;

            case "ESTOQUE_VAZIO":
                JOptionPane.showMessageDialog(this, "Estoque é obrigatório!", "Erro", JOptionPane.ERROR_MESSAGE);
                estoqueField.requestFocus();
                break;

            case "PRECO_INVALIDO":
                JOptionPane.showMessageDialog(this, "Preço inválido! Use números com ponto ou vírgula para decimais.", "Erro", JOptionPane.ERROR_MESSAGE);
                precoField.requestFocus();
                break;

            case "ESTOQUE_INVALIDO":
                JOptionPane.showMessageDialog(this, "Estoque inválido! Use apenas números inteiros.", "Erro", JOptionPane.ERROR_MESSAGE);
                estoqueField.requestFocus();
                break;

            default:
                JOptionPane.showMessageDialog(this, "Erro ao atualizar produto: " + resultado, "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}