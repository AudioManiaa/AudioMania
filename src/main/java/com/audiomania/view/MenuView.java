package com.audiomania.view;

/**
 * @script Menu principal da aplicação
 * @field iniciar() Exibe o menu principal com acesso a todas as áreas do sistema
 * @author Joao
 *
 * MenuView exibe o menu principal da aplicação, permitindo acesso ao gerenciamento de clientes,
 * funcionários, produtos, vendas e histórico. Implementado em Swing para interface gráfica.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * MenuView exibe o menu principal da aplicação, permitindo acesso ao gerenciamento de clientes,
 * funcionários, produtos, vendas e histórico. Implementado em Swing para interface gráfica.
 */
public class MenuView extends JFrame {
    private JButton clientesButton; // clientesButton Botão para gerenciamento de clientes
    private JButton funcionariosButton; // funcionariosButton Botão para gerenciamento de funcionários
    private JButton produtosButton; // produtosButton Botão para gerenciamento de produtos
    private JButton vendasButton; // vendasButton Botão para gerenciamento de vendas
    private JButton historicoButton; // historicoButton Botão para visualizar histórico
    private JButton sairButton; // sairButton Botão para sair do sistema

    /**
     * Construtor do menu principal. Inicializa componentes e listeners.
     */
    public MenuView() {
        setTitle("Menu Principal - Audio Mania");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centraliza a janela
        initComponents();
    }

    /**
     * Inicializa os componentes da interface e listeners dos botões.
     */
    private void initComponents() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("MENU PRINCIPAL");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);
        gbc.gridwidth = 1;

        clientesButton = new JButton("Gerenciar Clientes");
        funcionariosButton = new JButton("Gerenciar Funcionários");
        produtosButton = new JButton("Gerenciar Produtos");
        vendasButton = new JButton("Gerenciar Vendas");
        historicoButton = new JButton("Visualizar Histórico");
        sairButton = new JButton("Sair");

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(clientesButton, gbc);
        gbc.gridy = 2;
        panel.add(funcionariosButton, gbc);
        gbc.gridy = 3;
        panel.add(produtosButton, gbc);
        gbc.gridy = 4;
        panel.add(vendasButton, gbc);
        gbc.gridy = 5;
        panel.add(historicoButton, gbc);
        gbc.gridy = 6;
        panel.add(sairButton, gbc);

        add(panel);

        // Listeners
        clientesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirClienteView();
            }
        });
        funcionariosButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirFuncionarioView();
            }
        });
        produtosButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirProdutoView();
            }
        });
        vendasButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirVendaView();
            }
        });
        historicoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirHistoricoView();
            }
        });
        sairButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sair();
            }
        });
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
        ClienteView clienteView = new ClienteView();
        setVisible(false);
        // Chama o método de gerenciamento terminal
        clienteView.iniciarGerenciamento();
        setVisible(true);
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
        ProdutoView produtoView = new ProdutoView();
        setVisible(false);
        produtoView.iniciarGerenciamento();
        setVisible(true);
    }

    /**
     * Abre a tela de gerenciamento de vendas.
     */
    private void abrirVendaView() {
        // Aqui seria necessário passar o funcionário logado, se houver controle de sessão
        VendaView vendaView = new VendaView(null); // @todo: passar funcionario logado
        setVisible(false);
        vendaView.iniciarGerenciamento();
        setVisible(true);
    }

    /**
     * Abre a tela de histórico.
     */
    private void abrirHistoricoView() {
        HistoricoView historicoView = new HistoricoView();
        setVisible(false);
        // @warn: HistoricoView pode não ter método iniciarGerenciamento, ajustar se necessário
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

