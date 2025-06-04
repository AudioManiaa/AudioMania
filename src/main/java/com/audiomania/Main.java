package com.audiomania;

import com.audiomania.view.*;
import com.audiomania.model.entities.FuncionarioEntity;
import com.audiomania.model.service.*;
import com.audiomania.controller.StyleController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Menu Principal em Swing com StyleController
public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            StyleController styleController = new StyleController();
            LoginView loginView = new LoginView();
            FuncionarioEntity funcionarioLogado = loginView.iniciarLogin();

            if (funcionarioLogado != null) {
                JFrame frame = new JFrame("AudioMania - Menu Principal");
                styleController.estilizarJanela(frame, null);

                JPanel panel = new JPanel();
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
                styleController.estilizarPanel(panel, Color.DARK_GRAY);

                JLabel titulo = new JLabel("Menu Principal");
                styleController.estilizarTitulo(titulo, Color.WHITE, 32);
                titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
                panel.add(titulo);

                panel.add(Box.createVerticalStrut(30));

                // Opções de menu como botões
                JButton btnFuncionario = new JButton("Funcionário");
                JButton btnGerenciarFuncionarios = new JButton("Gerenciar Funcionários");
                JButton btnProdutos = new JButton("Produtos");
                JButton btnVendas = new JButton("Vendas");
                JButton btnClientes = new JButton("Clientes");
                JButton btnHistorico = new JButton("Histórico");
                JButton btnSair = new JButton("Sair");

                JButton[] botoes = {
                    btnFuncionario, btnGerenciarFuncionarios, btnProdutos,
                    btnVendas, btnClientes, btnHistorico, btnSair
                };

                for (JButton btn : botoes) {
                    styleController.estilizarBotaoGrande(btn, new Color(60,60,60), Color.WHITE, 18);
                    btn.setAlignmentX(Component.CENTER_ALIGNMENT);
                    panel.add(Box.createVerticalStrut(10));
                    panel.add(btn);
                }

                // Ações dos botões
                btnFuncionario.addActionListener(e -> {
                    JOptionPane.showMessageDialog(frame,
                        "Funcionário logado:\nNome: " + funcionarioLogado.getNome() +
                        "\nCPF: " + funcionarioLogado.getCpf() +
                        "\nCargo: " + funcionarioLogado.getCargo() +
                        "\nTelefone: " + funcionarioLogado.getTelefone(),
                        "Informações do Funcionário",
                        JOptionPane.INFORMATION_MESSAGE
                    );
                });

                btnGerenciarFuncionarios.addActionListener(e -> {
                    new FuncionarioView().iniciarGerenciamento();
                });

                btnProdutos.addActionListener(e -> {
                    new ProdutoView().iniciarGerenciamento();
                });

                btnVendas.addActionListener(e -> {
                    new VendaView(funcionarioLogado).iniciarGerenciamento();
                });

                btnClientes.addActionListener(e -> {
                    new ClienteView().iniciarGerenciamento();
                });

                btnHistorico.addActionListener(e -> {
                    new HistoricoView().menuHistorico();
                });

                btnSair.addActionListener(e -> {
                    frame.dispose();
                    encerrar(loginView);
                });

                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setContentPane(panel);
                frame.setSize(350, 550);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            } else {
                loginView.fechar();
                encerrar(loginView);
            }
        });
    }

    // Fecha tudo corretamente
    private static void encerrar(LoginView loginView) {
        if (loginView != null) loginView.fechar();
        FuncionarioService.fecharRecursos();
        ClienteService.fecharRecursos();
        ProdutoService.fecharRecursos();
        VendaService.fecharRecursos();
        System.exit(0);
    }
}