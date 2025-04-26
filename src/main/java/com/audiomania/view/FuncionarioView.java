package com.audiomania.view;

import java.util.List;
import java.util.Scanner;
import com.audiomania.controller.SistemaController;
import com.audiomania.entities.FuncionarioEntity;

public class FuncionarioView {
    private final Scanner scanner;
    private final SistemaController controller;

    public FuncionarioView() {
        scanner = new Scanner(System.in);
        controller = new SistemaController();
    }

    /**
     * Inicia o menu de gerenciamento de funcionários
     */
    public void iniciarGerenciamento() {
        boolean sair = false;

        while (!sair) {
            System.out.println("\n===== GERENCIAMENTO DE FUNCIONÁRIOS =====\n");
            System.out.println("1. Listar Funcionários");
            System.out.println("2. Cadastrar Funcionário");
            System.out.println("3. Atualizar Funcionário");
            System.out.println("4. Excluir Funcionário");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("\nEscolha uma opção: ");

            int opcao = -1;
            try {
                opcao = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Opção inválida!");
                continue;
            }

            switch (opcao) {
                case 1:
                    listarFuncionarios();
                    break;
                case 2:
                    cadastrarFuncionario();
                    break;
                case 3:
                    atualizarFuncionario();
                    break;
                case 4:
                    excluirFuncionario();
                    break;
                case 0:
                    sair = true;
                    break;
                default:
                    System.out.println("Opção inválida!");
            }

            if (!sair) {
                System.out.print("\nPressione ENTER para continuar...");
                scanner.nextLine();
            }
        }
    }

    private void listarFuncionarios() {
        List<FuncionarioEntity> funcionarios = controller.listarFuncionarios();

        if (funcionarios.isEmpty()) {
            System.out.println("Nenhum funcionário cadastrado.");
            return;
        }

        System.out.println("\n=== LISTA DE FUNCIONÁRIOS ===\n");
        System.out.printf("%-5s | %-30s | %-15s | %-20s | %-15s\n",
                "ID", "NOME", "CPF", "CARGO", "TELEFONE");
        System.out.println("----------------------------------------------------------------------------------------------");

        for (FuncionarioEntity funcionario : funcionarios) {
            System.out.printf("%-5d | %-30s | %-15s | %-20s | %-15s\n",
                    funcionario.getId(),
                    funcionario.getNome(),
                    funcionario.getCpf(),
                    funcionario.getCargo(),
                    funcionario.getTelefone());
        }
    }

    private void cadastrarFuncionario() {
        System.out.println("\n=== CADASTRO DE FUNCIONÁRIO ===\n");

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
            System.out.println("Funcionário cadastrado com sucesso!");
        } else {
            System.out.println("Erro ao cadastrar funcionário. CPF já existe ou ocorreu um problema no banco de dados.");
        }
    }

    private void atualizarFuncionario() {
        System.out.println("\n=== ATUALIZAÇÃO DE FUNCIONÁRIO ===\n");

        // Listar funcionários para referência
        listarFuncionarios();

        System.out.print("\nDigite o ID do funcionário que deseja atualizar: ");
        int id;
        try {
            id = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("ID inválido.");
            return;
        }

        System.out.print("Nome: ");
        String nome = scanner.nextLine();

        System.out.print("Cargo: ");
        String cargo = scanner.nextLine();

        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();

        System.out.print("Nova senha (deixe em branco para manter a atual): ");
        String senha = scanner.nextLine();

        boolean sucesso = controller.atualizarFuncionario(id, nome, cargo, telefone, senha);

        if (sucesso) {
            System.out.println("Funcionário atualizado com sucesso!");
        } else {
            System.out.println("Erro ao atualizar funcionário. ID não encontrado ou ocorreu um problema no banco de dados.");
        }
    }

    private void excluirFuncionario() {
        System.out.println("\n=== EXCLUSÃO DE FUNCIONÁRIO ===\n");

        // Listar funcionários para referência
        listarFuncionarios();

        System.out.print("\nDigite o ID do funcionário que deseja excluir: ");
        int id;
        try {
            id = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("ID inválido.");
            return;
        }

        System.out.print("Tem certeza que deseja excluir este funcionário? (S/N): ");
        String confirmacao = scanner.nextLine();

        if (confirmacao.equalsIgnoreCase("S")) {
            boolean sucesso = controller.excluirFuncionario(id);

            if (sucesso) {
                System.out.println("Funcionário excluído com sucesso!");
            } else {
                System.out.println("Erro ao excluir funcionário. ID não encontrado ou ocorreu um problema no banco de dados.");
            }
        } else {
            System.out.println("Operação cancelada.");
        }
    }

    public void fechar() {
        scanner.close();
    }
}