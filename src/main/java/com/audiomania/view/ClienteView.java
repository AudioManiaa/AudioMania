package com.audiomania.view;

import com.audiomania.controller.ClienteController;
import com.audiomania.entities.ClienteEntity;
import java.util.List;
import java.util.Scanner;

public class ClienteView {
    private final Scanner scanner;
    private final ClienteController controller;

    public ClienteView() {
        scanner = new Scanner(System.in);
        controller = new ClienteController();
    }

    public void iniciarGerenciamento() {
        boolean sair = false;

        while (!sair) {
            System.out.println("\n===== GERENCIAMENTO DE CLIENTES =====\n");
            System.out.println("1. Listar Clientes");
            System.out.println("2. Cadastrar Cliente");
            System.out.println("3. Atualizar Cliente");
            System.out.println("4. Excluir Cliente");
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
                    listarClientes();
                    break;
                case 2:
                    cadastrarCliente();
                    break;
                case 3:
                    atualizarCliente();
                    break;
                case 4:
                    excluirCliente();
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

    private void listarClientes() {
        List<ClienteEntity> clientes = controller.listarClientes();

        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado.");
            return;
        }

        System.out.println("\n=== LISTA DE CLIENTES ===\n");
        System.out.printf("%-5s | %-30s | %-15s | %-15s | %-30s | %-12s\n",
                "ID", "NOME", "CPF", "TELEFONE", "ENDEREÇO", "DATA CADASTRO");
        System.out.println("------------------------------------------------------------------------------------------------------------");

        for (ClienteEntity cliente : clientes) {
            System.out.printf("%-5d | %-30s | %-15s | %-15s | %-30s | %-12s\n",
                    cliente.getId(),
                    cliente.getNome(),
                    cliente.getCpf(),
                    cliente.getTelefone(),
                    cliente.getEndereco(),
                    cliente.getDataDeCadastro());
        }
    }

    private void cadastrarCliente() {
        System.out.println("\n=== CADASTRO DE CLIENTE ===\n");

        System.out.print("Nome: ");
        String nome = scanner.nextLine();

        System.out.print("CPF: ");
        String cpf = scanner.nextLine();

        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();

        System.out.print("Endereço: ");
        String endereco = scanner.nextLine();

        boolean sucesso = controller.cadastrarCliente(nome, cpf, telefone, endereco);

        if (sucesso) {
            System.out.println("Cliente cadastrado com sucesso!");
        } else {
            System.out.println("Erro ao cadastrar cliente. CPF já existe ou ocorreu um problema no banco de dados.");
        }
    }

    private void atualizarCliente() {
        System.out.println("\n=== ATUALIZAÇÃO DE CLIENTE ===\n");

        listarClientes();

        System.out.print("\nDigite o ID do cliente que deseja atualizar: ");
        int id;
        try {
            id = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("ID inválido.");
            return;
        }

        System.out.print("Nome: ");
        String nome = scanner.nextLine();

        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();

        System.out.print("Endereço: ");
        String endereco = scanner.nextLine();

        boolean sucesso = controller.atualizarCliente(id, nome, telefone, endereco);

        if (sucesso) {
            System.out.println("Cliente atualizado com sucesso!");
        } else {
            System.out.println("Erro ao atualizar cliente. ID não encontrado ou ocorreu um problema no banco de dados.");
        }
    }

    private void excluirCliente() {
        System.out.println("\n=== EXCLUSÃO DE CLIENTE ===\n");

        listarClientes();

        System.out.print("\nDigite o ID do cliente que deseja excluir: ");
        int id;
        try {
            id = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("ID inválido.");
            return;
        }

        System.out.print("Tem certeza que deseja excluir este cliente? (S/N): ");
        String confirmacao = scanner.nextLine();

        if (confirmacao.equalsIgnoreCase("S")) {
            boolean sucesso = controller.excluirCliente(id);

            if (sucesso) {
                System.out.println("Cliente excluído com sucesso!");
            } else {
                System.out.println("Erro ao excluir cliente. ID não encontrado ou ocorreu um problema no banco de dados.");
            }
        } else {
            System.out.println("Operação cancelada.");
        }
    }
}