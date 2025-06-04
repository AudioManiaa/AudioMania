package com.audiomania;

import java.awt.Color;
import java.util.Scanner;

import com.audiomania.view.HistoricoView;
import com.audiomania.model.entities.FuncionarioEntity;
import com.audiomania.view.ClienteView;
import com.audiomania.view.FuncionarioView;
import com.audiomania.view.LoginView;
import com.audiomania.view.ProdutoView;
import com.audiomania.view.VendaView;
import com.audiomania.model.service.FuncionarioService;
import com.audiomania.model.service.ClienteService;
import com.audiomania.model.service.ProdutoService;
import com.audiomania.model.service.VendaService;

import javax.swing.*;

import com.audiomania.controller.StyleController;


public class Main {
    public static void main(String[] args) {
        //Instanciando o StyleController
        StyleController styleController = new StyleController();

        //Estilizando a Janela
        styleController.estilizarJanela(new JFrame(), null);

        LoginView loginView = new LoginView();
        FuncionarioEntity funcionarioLogado = loginView.iniciarLogin();

        if (funcionarioLogado != null) {
            Scanner scanner = new Scanner(System.in);
            boolean sair = false;

            while (!sair) {
                System.out.println("\nMenu Principal:");
                System.out.println("1. Funcionário");
                System.out.println("2. Gerenciar Funcionários");
                System.out.println("3. Produtos");
                System.out.println("4. Vendas");
                System.out.println("5. Clientes");
                System.out.println("6. Histórico");
                System.out.println("7. Sair");
                System.out.print("Escolha uma opção: ");

                String opcao = scanner.nextLine();

                switch (opcao) {
                    case "1":
                        System.out.println("Funcionário logado: " + funcionarioLogado.getNome());
                        System.out.println("CPF: " + funcionarioLogado.getCpf());
                        System.out.println("Cargo: " + funcionarioLogado.getCargo());
                        System.out.println("Telefone: " + funcionarioLogado.getTelefone());
                        break;
                    case "2":
                        FuncionarioView funcionarioView = new FuncionarioView();
                        funcionarioView.iniciarGerenciamento();
                        break;
                    case "3":
                        ProdutoView produtoView = new ProdutoView();
                        produtoView.iniciarGerenciamento();
                        break;
                    case "4":
                        System.out.println("Gerenciamento de Vendas");
                        VendaView vendaView = new VendaView(funcionarioLogado);
                        vendaView.iniciarGerenciamento();
                        break;
                    case "5":
                        ClienteView clienteView = new ClienteView();
                        clienteView.iniciarGerenciamento();
                        break;
                    case "6":
                        System.out.println("Funcionalidade de Histórico");
                        HistoricoView historico = new HistoricoView();
                        historico.menuHistorico();
                        break;
                    case "7":
                        sair = true;
                        break;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                        break;
                }
            }

            scanner.close();
        }

        loginView.fechar();
        // Fechar recursos do sistema
        FuncionarioService.fecharRecursos();
        ClienteService.fecharRecursos();
        ProdutoService.fecharRecursos();
        VendaService.fecharRecursos();
    }
}