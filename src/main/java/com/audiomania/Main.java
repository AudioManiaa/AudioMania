package com.audiomania;

import java.util.ArrayList;
import java.util.List;

import com.audiomania.model.Usuario;
import com.audiomania.service.MenuService;
import com.audiomania.service.MenuService.OpcaoMenu;
import com.audiomania.view.LoginView;

public class Main {
    public static void main(String[] args) {
        LoginView view = new LoginView();
        Usuario usuarioLogado = view.iniciarLogin();

        if (usuarioLogado != null) {

            //INICIOFUNMENU
            // Criando a lista de opções do menu
            List<OpcaoMenu> opcoes = new ArrayList<>();
            
            // Adicionando as opções do menu

            opcoes.add(new OpcaoMenu("Usuario", scanner -> {
                System.out.println("Usuário logado: " + usuarioLogado.getLogin());
                System.out.println("Nível de acesso: " + usuarioLogado.getNivelAcesso());
            }));
            
            opcoes.add(new OpcaoMenu("Estoque", scanner -> {
                System.out.println("Funcionalidade de Estoque");
                // Implementar lógica de estoque
            }));
            
            opcoes.add(new OpcaoMenu("Orçamentos de Serviços", scanner -> {
                System.out.println("Funcionalidade de Orçamentos");
                // Implementar lógica de orçamentos
            }));
            
            opcoes.add(new OpcaoMenu("Clientes", scanner -> {
                System.out.println("Funcionalidade de Clientes");

                //aplicar ClientesView
            }));
            
            opcoes.add(new OpcaoMenu("Historico", scanner -> {
                System.out.println("Funcionalidade de Histórico");
                // Implementar lógica de histórico
            }));

            opcoes.add(new OpcaoMenu("Gerenciar Funcionarios", scanner -> {
                System.out.println("Funcionalidade de Registro de novo Funcionario");
                //aplicar funcionalidade, usar @Funcionario e @SistemaController

            }));
            
            // Exibindo o menu
            MenuService.criarMenu("Menu Principal", opcoes);
            //FIMFUNMENU
        }

        view.fechar();
    }
}
