package com.audiomania;

import java.util.ArrayList;
import java.util.List;

import com.audiomania.controller.Historico;
import com.audiomania.controller.VendaController;
import com.audiomania.entities.FuncionarioEntity;
import com.audiomania.service.FuncionarioService;
import com.audiomania.service.MenuService;
import com.audiomania.service.MenuService.OpcaoMenu;
import com.audiomania.view.ClienteView;
import com.audiomania.view.FuncionarioView;
import com.audiomania.view.LoginView;
import com.audiomania.view.ProdutoView;
import com.audiomania.view.VendaView;
import com.audiomania.service.ClienteService;
import com.audiomania.service.FuncionarioService;
import com.audiomania.service.ProdutoService;
import com.audiomania.service.VendaService;

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

            opcoes.add(new OpcaoMenu("Gerenciar Funcionários", scanner -> {
                FuncionarioView funcionarioView = new FuncionarioView();
                funcionarioView.iniciarGerenciamento();
            }));

            opcoes.add(new OpcaoMenu("Produtos", scanner -> {
                ProdutoView produtoView = new ProdutoView();
                produtoView.iniciarGerenciamento();
            }));

            opcoes.add(new OpcaoMenu("Vendas", scanner -> {
                System.out.println("Gerenciamento de Vendas");
                VendaView vendaView = new VendaView(funcionarioLogado);
                vendaView.iniciarGerenciamento();
            }));

            opcoes.add(new OpcaoMenu("Clientes", scanner -> {
                ClienteView clienteView = new ClienteView();
                clienteView.iniciarGerenciamento();
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
        // Fechar recursos do sistema
        FuncionarioService.fecharRecursos();
        ClienteService.fecharRecursos();
        ProdutoService.fecharRecursos();
        VendaService.fecharRecursos();
    }
}