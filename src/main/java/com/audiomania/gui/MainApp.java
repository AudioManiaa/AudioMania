package com.audiomania.gui;

import com.audiomania.entities.FuncionarioEntity;
import com.audiomania.view.FuncionarioView;
import com.audiomania.view.LoginView;
import com.audiomania.view.ProdutoView;
import com.audiomania.view.VendaView; // Added import

import javax.swing.*;
import java.awt.EventQueue;

public class MainApp {

    // This field stores the logged-in user globally for the app if needed,
    // but createAndShowGUI receives it as a parameter for clarity.
    private static FuncionarioEntity loggedInUser;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            LoginView loginView = new LoginView();
            FuncionarioEntity funcionario = loginView.iniciarLogin();

            if (funcionario != null) {
                loggedInUser = funcionario; // Store globally
                JOptionPane.showMessageDialog(null,
                        "Login Bem-sucedido! Abrindo aplicativo principal...",
                        "Login Sucesso",
                        JOptionPane.INFORMATION_MESSAGE);
                createAndShowGUI(loggedInUser); // Pass to GUI creation method
            } else {
                JOptionPane.showMessageDialog(null,
                        "Falha no login. Aplicativo será encerrado.",
                        "Login Falhou",
                        JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }
        });
    }

    private static void createAndShowGUI(FuncionarioEntity user) {
        // mainFrame is effectively final for use in lambdas
        final JFrame mainFrame = new JFrame("AudioMania - Sistema de Gerenciamento (Usuário: " + user.getNome() + ")");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JMenuBar menuBar = new JMenuBar();

        // Arquivo Menu
        JMenu fileMenu = new JMenu("Arquivo");
        JMenuItem sairItem = new JMenuItem("Sair");
        sairItem.addActionListener(e -> System.exit(0));
        fileMenu.add(sairItem);

        // Funcionário Menu
        JMenu employeeMenu = new JMenu("Funcionário");
        JMenuItem verDadosItem = new JMenuItem("Ver Meus Dados");
        verDadosItem.addActionListener(e -> JOptionPane.showMessageDialog(mainFrame,
                "Funcionário Logado: " + user.getNome() + "\n" +
                "CPF: " + user.getCpf() + "\n" +
                "Cargo: " + user.getCargo(),
                "Meus Dados",
                JOptionPane.INFORMATION_MESSAGE));
        employeeMenu.add(verDadosItem);

        // Cadastros Menu
        JMenu registrationMenu = new JMenu("Cadastros");
        JMenuItem gerenciarFuncionariosItem = new JMenuItem("Gerenciar Funcionários");
        gerenciarFuncionariosItem.addActionListener(e -> {
            FuncionarioView funcionarioView = new FuncionarioView();
            // Pass mainFrame so the dialog can be modal to it
            funcionarioView.mostrarJanelaGerenciamento(mainFrame);
        });
        registrationMenu.add(gerenciarFuncionariosItem);

        JMenuItem produtosItem = new JMenuItem("Produtos");
        produtosItem.addActionListener(e -> {
            ProdutoView produtoView = new ProdutoView();
            produtoView.mostrarJanelaGerenciamento(mainFrame);
        });
        registrationMenu.add(produtosItem);

        JMenuItem clientesItem = new JMenuItem("Clientes");
        clientesItem.addActionListener(e -> JOptionPane.showMessageDialog(mainFrame, "Ação para 'Clientes' acionada.", "Placeholder", JOptionPane.INFORMATION_MESSAGE));
        registrationMenu.add(clientesItem);

        // Vendas Menu
        JMenu salesMenu = new JMenu("Vendas");
        JMenuItem realizarVendaItem = new JMenuItem("Realizar Venda/Orçamento");
        realizarVendaItem.addActionListener(e -> {
            // MainApp.loggedInUser should be the static field holding the logged-in FuncionarioEntity
            // 'user' is the parameter passed to createAndShowGUI, which is MainApp.loggedInUser
            if (loggedInUser != null) {
                VendaView vendaView = new VendaView(loggedInUser);
                vendaView.mostrarJanelaGerenciamento(mainFrame);
            } else {
                JOptionPane.showMessageDialog(mainFrame, "Erro: Usuário não está logado. Não é possível abrir a tela de vendas.", "Erro de Autenticação", JOptionPane.ERROR_MESSAGE);
            }
        });
        salesMenu.add(realizarVendaItem);

        // Histórico Menu
        JMenu historyMenu = new JMenu("Histórico");
        JMenuItem verHistoricoItem = new JMenuItem("Ver Histórico");
        verHistoricoItem.addActionListener(e -> JOptionPane.showMessageDialog(mainFrame, "Ação para 'Ver Histórico' acionada.", "Placeholder", JOptionPane.INFORMATION_MESSAGE));
        historyMenu.add(verHistoricoItem);

        menuBar.add(fileMenu);
        menuBar.add(employeeMenu);
        menuBar.add(registrationMenu);
        menuBar.add(salesMenu);
        menuBar.add(historyMenu);

        mainFrame.setJMenuBar(menuBar);

        mainFrame.setSize(800, 600);
        mainFrame.setLocationRelativeTo(null); // Center the window
        mainFrame.setVisible(true);
    }
}
