package com.audiomania.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.audiomania.utils.FabricaDeIcones;
import com.audiomania.model.entities.FuncionarioEntity;

/**
 * MenuView exibe o menu principal da aplicação, permitindo acesso ao gerenciamento de clientes,
 * funcionários, produtos, vendas e histórico. Implementado em Swing para interface gráfica.
 */
public class MenuView extends JFrame {
    private FuncionarioEntity funcionarioLogado;
    private JButton clientesButton;
    private JButton funcionariosButton;
    private JButton produtosButton;
    private JButton vendasButton;
    private JButton historicoButton;
    private JButton sairButton;

    /**
     * Construtor do menu principal. Inicializa componentes e listeners.
     */
    public MenuView(FuncionarioEntity funcionario) {
        this.funcionarioLogado = funcionario;
        if (funcionario != null && funcionario.getNome() != null && !funcionario.getNome().isEmpty()) {
            setTitle("Menu Principal - Audio Mania (Usuário: " + funcionario.getNome() + ")");
        } else {
            setTitle("Menu Principal - Audio Mania"); // Título padrão se não houver funcionário
        }
        setSize(600, 800); // Aumentei a altura para mais espaçamento
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initComponents(); // ESSENCIAL para construir a UI
    }

    // Construtor padrão agora chama o construtor principal com null
    public MenuView() {
        this(null); 
    }

    /**
     * Inicializa os componentes da interface e listeners dos botões.
     */
    private void initComponents() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 20, 15, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        JLabel titleLabel = new JLabel("Audio Mania 🔉");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        gbc.gridy = 1;
        gbc.gridwidth = 1;

        // --- Criação dos Ícones usando a FabricaDeIcones ---
        ImageIcon clientesIcon = FabricaDeIcones.criarIcone("/icons/customer.png", "Ícone de Clientes", 50);
        ImageIcon funcionariosIcon = FabricaDeIcones.criarIcone("/icons/employee.png", "Ícone de Funcionários", 50);
        ImageIcon produtosIcon = FabricaDeIcones.criarIcone("/icons/product.png", "Ícone de Produtos", 50);
        ImageIcon vendasIcon = FabricaDeIcones.criarIcone("/icons/sale.png", "Ícone de Vendas", 50);
        ImageIcon historicoIcon = FabricaDeIcones.criarIcone("/icons/history.png", "Ícone de Histórico", 50);
        ImageIcon sairIcon = FabricaDeIcones.criarIcone("/icons/exit.png", "Ícone de Saída", 50);

        // --- Criação dos Botões com Ícones ---
        clientesButton = new JButton("Gerenciar Clientes", clientesIcon);
        funcionariosButton = new JButton("Gerenciar Funcionários", funcionariosIcon);
        produtosButton = new JButton("Gerenciar Produtos", produtosIcon);
        vendasButton = new JButton("Gerenciar Vendas", vendasIcon);
        historicoButton = new JButton("Visualizar Histórico", historicoIcon);
        sairButton = new JButton("Sair", sairIcon);

        sairButton.setBackground(new Color(150, 20, 20));

        // --- Configuração dos Botões ---
        JButton[] buttons = {clientesButton, funcionariosButton, produtosButton, vendasButton, historicoButton, sairButton};
        for (JButton btn : buttons) {
            btn.setPreferredSize(new Dimension(250, 50));
            btn.setHorizontalAlignment(SwingConstants.LEFT);
            btn.setIconTextGap(15);
            btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        }

        // Adiciona os botões ao painel
        gbc.gridx = 0; gbc.gridy = 1; panel.add(clientesButton, gbc);
        gbc.gridx = 0; gbc.gridy = 2; panel.add(funcionariosButton, gbc);
        gbc.gridx = 0; gbc.gridy = 3; panel.add(produtosButton, gbc);
        gbc.gridx = 0; gbc.gridy = 4; panel.add(vendasButton, gbc);
        gbc.gridx = 0; gbc.gridy = 5; panel.add(historicoButton, gbc);
        gbc.gridx = 0; gbc.gridy = 6; panel.add(sairButton, gbc);

        add(panel);

        // Listeners
        clientesButton.addActionListener(e -> abrirClienteView());
        funcionariosButton.addActionListener(e -> abrirFuncionarioView());
        produtosButton.addActionListener(e -> abrirProdutoView());
        vendasButton.addActionListener(e -> abrirVendaView());
        historicoButton.addActionListener(e -> abrirHistoricoView());
        sairButton.addActionListener(e -> sair());
    }

    /**
     * Exibe o menu principal.
     */
    public void iniciar() {
        setVisible(true);
    }

    /**
     * Abre a tela de gerenciamento de clientes.
     */
    private void abrirClienteView() {
        ClienteView clienteView = new ClienteView(this);
        setVisible(false);
        clienteView.iniciar();
    }

    /**
     * Abre a tela de gerenciamento de funcionários.
     */
    private void abrirFuncionarioView() {
        FuncionarioView funcionarioView = new FuncionarioView();
        setVisible(false);
        funcionarioView.iniciarGerenciamento();
        setVisible(true);
    }

    /**
     * Abre a tela de gerenciamento de produtos.
     */
    private void abrirProdutoView() {
        ProdutoView produtoView = new ProdutoView(this);
        setVisible(false);
        produtoView.iniciar();
    }

    /**
     * Abre a tela de gerenciamento de vendas.
     */
    private void abrirVendaView() {
        VendaView vendaView = new VendaView(this);
        setVisible(false);
        vendaView.iniciar();
    }

    /**
     * Abre a tela de histórico.
     */
    private void abrirHistoricoView() {
        HistoricoView historicoView = new HistoricoView();
        setVisible(false);
        //historicoView.iniciar();
        setVisible(true);
    }

    /**
     * Fecha o menu e encerra a aplicação.
     */
    private void sair() {
        dispose();
        System.exit(0);
    }
}