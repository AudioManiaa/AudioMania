package com.audiomania.view;

import java.util.Scanner;
import com.audiomania.controller.SistemaController;
import com.audiomania.entities.FuncionarioEntity;

public class LoginView {
    private final Scanner scanner;
    private final SistemaController controller;

    public LoginView() {
        scanner = new Scanner(System.in);
        controller = new SistemaController();
    }

    /**
     * Inicia a aplicação de login
     * @return Funcionário autenticado ou null em caso de saída
     */
    public FuncionarioEntity iniciarLogin() {
        System.out.println("\n===== SISTEMA AUDIO MANIA =====\n");
        System.out.println("1. Login");
        System.out.println("2. Registrar novo funcionário");
        System.out.println("0. Sair");
        System.out.print("\nEscolha uma opção: ");

        int opcao = -1;
        try {
            opcao = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Opção inválida!");
            return null;
        }

        switch (opcao) {
            case 1:
                return fazerLogin();
            case 2:
                cadastrarNovoFuncionario();
                return null;
            case 0:
                return null;
            default:
                System.out.println("Opção inválida!");
                return null;
        }
    }

    private FuncionarioEntity fazerLogin() {
        System.out.println("\n--- Login de Funcionário ---");
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();

        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        FuncionarioEntity funcionario = controller.realizarLogin(cpf, senha);

        if (funcionario != null) {
            System.out.println("\nLogin realizado com sucesso!");
            return funcionario;
        } else {
            System.out.println("\nCPF ou senha incorretos!");
            return null;
        }
    }

    private void cadastrarNovoFuncionario() {
        System.out.println("\n--- Cadastro de Novo Funcionário ---");

        System.out.print("Nome: ");
        String nome = scanner.nextLine();

        System.out.print("CPF: ");
        String cpf = scanner.nextLine();

        System.out.print("Cargo: ");
        String cargo = scanner.nextLine();

        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();

        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        boolean sucesso = controller.cadastrarFuncionario(nome, cpf, cargo, telefone, senha);

        if (sucesso) {
            System.out.println("\nFuncionário cadastrado com sucesso!");
        } else {
            System.out.println("\nErro ao cadastrar funcionário!");
        }
    }


    public void fechar() {
        scanner.close();
    }
}