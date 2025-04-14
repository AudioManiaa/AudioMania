package com.audiomania.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import com.audiomania.controller.SistemaController;
import com.audiomania.model.Usuario;
import com.audiomania.service.MenuService;
import com.audiomania.service.MenuService.OpcaoMenu;

public class LoginView {
    private final Scanner scanner;
    private final SistemaController controller;

    public LoginView() {
        scanner = new Scanner(System.in);
        controller = new SistemaController();
    }

    /**
     * Inicia a aplicação de login
     * @return Usuário autenticado ou null em caso de saída
     */
    public Usuario iniciarLogin() {
        AtomicBoolean sair = new AtomicBoolean(false);
        AtomicReference<Usuario> usuarioLogadoRef = new AtomicReference<>(null);
        
        while (!sair.get() && usuarioLogadoRef.get() == null) {
            // Criando as opções do menu de login
            List<OpcaoMenu> opcoesLogin = new ArrayList<>();
            
            // Opção de login
            opcoesLogin.add(new OpcaoMenu("Login", s -> {
                Usuario usuario = fazerLogin();
                if (usuario != null) {
                    usuarioLogadoRef.set(usuario);
                }
            }));
            
            // Opção de cadastro
            opcoesLogin.add(new OpcaoMenu("Registrar novo usuário", s -> {
                cadastrarNovoUsuario();
            }));
            
            // Exibindo o menu usando o MenuService
            MenuService.criarMenu("========= SISTEMA AUDIO MANIA =========", opcoesLogin);
        }
        
        return usuarioLogadoRef.get();
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
