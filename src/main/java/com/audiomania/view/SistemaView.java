package com.audiomania.view;

import com.audiomania.controller.SistemaController;
import com.audiomania.model.Usuario;
import java.util.Scanner;

public class SistemaView {
    private Scanner scanner;
    private SistemaController controller;

    public SistemaView() {
        scanner = new Scanner(System.in);
        controller = new SistemaController();
    }

    /**
     * Inicia a aplicação de loginpqp
     * @return Usuário autenticado ou null em caso de saída
     */
    public Usuario iniciarLogin() {
        boolean sair = false;
        Usuario usuarioLogado = null;

        while (!sair && usuarioLogado == null) {
            exibirMenuLogin();
            int opcao = lerOpcao();

            switch (opcao) {
                case 1:
                    usuarioLogado = fazerLogin();
                    break;
                case 2:
                    cadastrarNovoUsuario();
                    break;
                case 0:
                    sair = true;
                    System.out.println("Saindo do sistema. Obrigado!");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }

        return usuarioLogado;
    }

    private void exibirMenuLogin() {
        System.out.println("\n========= SISTEMA AUDIO MANIA =========");
        System.out.println("1. Login");
        System.out.println("2. Registrar novo usuário");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opção: ");
    }

    private int lerOpcao() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private Usuario fazerLogin() {
        System.out.println("\n--- Login ---");
        System.out.print("Login: ");
        String login = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        Usuario usuario = controller.realizarLogin(login, senha);

        if (usuario != null) {
            System.out.println("Login realizado com sucesso!");
            return usuario;
        } else {
            System.out.println("Login ou senha incorretos!");
            return null;
        }
    }

    private void cadastrarNovoUsuario() {
        System.out.println("\n--- Cadastro de Novo Usuário ---");
        System.out.print("Login: ");
        String login = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();
        System.out.print("Nível de Acesso (1-Básico, 2-Intermediário, 3-Admin): ");

        int nivelAcesso;
        try {
            nivelAcesso = Integer.parseInt(scanner.nextLine());
            if (nivelAcesso < 1 || nivelAcesso > 3) {
                System.out.println("Nível de acesso inválido!");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Nível de acesso inválido!");
            return;
        }

        boolean sucesso = controller.cadastrarUsuario(login, senha, nivelAcesso);

        if (sucesso) {
            System.out.println("Usuário cadastrado com sucesso!");
        } else {
            System.out.println("Erro: Login já existe!");
        }
    }

    /**
     * Fecha o scanner pra acessar o menu
     */
    public void fechar() {
        scanner.close();
    }
}

