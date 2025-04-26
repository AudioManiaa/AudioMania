package com.audiomania;

import java.util.ArrayList;
import java.util.List;

import com.audiomania.entities.FuncionarioEntity;
import com.audiomania.service.FuncionarioService;
import com.audiomania.service.MenuService;
import com.audiomania.service.MenuService.OpcaoMenu;
import com.audiomania.view.LoginView;

public class Main {
    public static void main(String[] args) {
        LoginView view = new LoginView();
        FuncionarioEntity funcionarioLogado = view.iniciarLogin();

        if (funcionarioLogado != null) {
            List<OpcaoMenu> opcoes = new ArrayList<>();

            opcoes.add(new OpcaoMenu("Funcionário", scanner -> {
                System.out.println("Funcionário logado: " + funcionarioLogado.getNome());
                System.out.println("CPF: " + funcionarioLogado.getCpf());
                System.out.println("Cargo: " + funcionarioLogado.getCargo());
                System.out.println("Telefone: " + funcionarioLogado.getTelefone());
            }));

            opcoes.add(new OpcaoMenu("Estoque", scanner -> {
                System.out.println("Funcionalidade de Estoque");
                //Implementar lógica de estoque
            }));

            opcoes.add(new OpcaoMenu("Orçamentos de Serviços", scanner -> {
                System.out.println("Funcionalidade de Orçamentos");
                //Implementar lógica de orçamentos
            }));

            opcoes.add(new OpcaoMenu("Clientes", scanner -> {
                System.out.println("Funcionalidade de Clientes");
                //Implementar lógica de clientes
            }));

            opcoes.add(new OpcaoMenu("Histórico", scanner -> {
                System.out.println("Funcionalidade de Histórico");
                //Implementar lógica de histórico
            }));

            MenuService.criarMenu("Menu Principal", opcoes);
        }

        view.fechar();
        // Fechar recursos do sistema
        FuncionarioService.fecharRecursos();
    }
}