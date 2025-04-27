package com.audiomania;

import java.util.ArrayList;
import java.util.List;

import com.audiomania.controller.Historico;
import com.audiomania.entities.FuncionarioEntity;
import com.audiomania.estoque.ProdutoRepository;
import com.audiomania.model.Cliente;
import com.audiomania.repository.VendaRepository;
import com.audiomania.service.FuncionarioService;
import com.audiomania.service.MenuService;
import com.audiomania.service.MenuService.OpcaoMenu;
import com.audiomania.view.FuncionarioView;
import com.audiomania.view.LoginView;

public class Main {
    public static void main(String[] args) {
        LoginView loginView = new LoginView();
        FuncionarioEntity funcionarioLogado = loginView.iniciarLogin();

        if (funcionarioLogado != null) {
            List<OpcaoMenu> opcoes = new ArrayList<>();

            opcoes.add(new OpcaoMenu("Funcionário", scanner -> {
                System.out.println("Funcionário logado: " + funcionarioLogado.getNome());
                System.out.println("CPF: " + funcionarioLogado.getCpf());
                System.out.println("Cargo: " + funcionarioLogado.getCargo());
                System.out.println("Telefone: " + funcionarioLogado.getTelefone());
            }));

            // Nova opção para gerenciar funcionários com FuncionarioView
            opcoes.add(new OpcaoMenu("Gerenciar Funcionários", scanner -> {
                FuncionarioView funcionarioView = new FuncionarioView();
                funcionarioView.iniciarGerenciamento();
            }));

            opcoes.add(new OpcaoMenu("Produtos", scanner -> {
                System.out.println("");
                ProdutoRepository produtoRepository = new ProdutoRepository();
                produtoRepository.exibirMenu();
            }));

            opcoes.add(new OpcaoMenu("Vendas", scanner -> {
                System.out.println("Funcionalidade de Orçamentos");
                VendaRepository vendaRepository = new VendaRepository();
                vendaRepository.listarTodas();
            }));

            opcoes.add(new OpcaoMenu("Histórico", scanner -> {
                System.out.println("Funcionalidade de Histórico");
                Historico historico = new Historico();
                historico.menuHistorico();
            }));

            MenuService.criarMenu("Menu Principal", opcoes);
        }

        loginView.fechar();
        // Fechar recursos do sistema
        FuncionarioService.fecharRecursos();
    }
}