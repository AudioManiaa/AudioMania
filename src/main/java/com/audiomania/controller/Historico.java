package com.audiomania.controller;

import com.audiomania.entities.ClienteEntity;
import com.audiomania.entities.FuncionarioEntity;
import com.audiomania.model.Cliente;
import com.audiomania.model.Funcionario;
import com.audiomania.repository.ClienteRepository;
import com.audiomania.repository.FuncionarioRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Historico {

    private List<Cliente> historicoClientes = new ArrayList<>();
    private List<Funcionario> historicoFuncionarios = new ArrayList<>();
    private Map<Cliente, List<String>> historicoCompras = new HashMap<>();
    private Scanner scanner = new Scanner(System.in);

    // Repositórios para acesso ao banco de dados
    private ClienteRepository clienteRepository;
    private FuncionarioRepository funcionarioRepository;

    public Historico() {
        // Inicializa os repositórios
        this.clienteRepository = new ClienteRepository();
        this.funcionarioRepository = new FuncionarioRepository();

        carregarDadosDoBanco();
    }

    private void carregarDadosDoBanco() {
        try {
            // Limpar listas atuais
            historicoClientes.clear();
            historicoFuncionarios.clear();
            historicoCompras.clear();

            // Carregar clientes do banco de dados
            List<ClienteEntity> clientesEntity = clienteRepository.listarTodos();
            if (clientesEntity != null && !clientesEntity.isEmpty()) {
                for (ClienteEntity entity : clientesEntity) {
                    Cliente cliente = new Cliente(
                            entity.getNome(),
                            entity.getCpf(),
                            entity.getTelefone(),
                            entity.getEndereco()
                    );
                    historicoClientes.add(cliente);
                }
                System.out.println("Clientes carregados do banco de dados: " + historicoClientes.size());
            } else {
                System.out.println("Nenhum cliente encontrado no banco de dados.");
            }

            // Carregar funcionários do banco de dados
            List<FuncionarioEntity> funcionariosEntity = funcionarioRepository.listarTodos();
            if (funcionariosEntity != null && !funcionariosEntity.isEmpty()) {
                for (FuncionarioEntity entity : funcionariosEntity) {
                    Funcionario funcionario = new Funcionario(
                            entity.getNome(),
                            entity.getCpf(),
                            entity.getTelefone(),
                            "",
                            entity.getSenha()
                    );
                    historicoFuncionarios.add(funcionario);
                }
                System.out.println("Funcionários carregados do banco de dados: " + historicoFuncionarios.size());
            } else {
                System.out.println("Nenhum funcionário encontrado no banco de dados.");
            }

        } catch (Exception e) {
            System.out.println("Erro ao carregar dados do banco: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void exibirHistoricoClientes() {
        if (historicoClientes.isEmpty()) {
            System.out.println("Nenhum cliente no histórico.");
            return;
        }
        System.out.println("\n--- Histórico de Clientes ---");
        for (int i = 0; i < historicoClientes.size(); i++) {
            System.out.print((i + 1) + ". ");
            historicoClientes.get(i).exibirCliente();
        }
    }

    public void exibirHistoricoFuncionarios() {
        if (historicoFuncionarios.isEmpty()) {
            System.out.println("Nenhum funcionário no histórico.");
            return;
        }
        System.out.println("\n--- Histórico de Funcionários ---");
        for (int i = 0; i < historicoFuncionarios.size(); i++) {
            System.out.print((i + 1) + ". ");
            historicoFuncionarios.get(i).exibirFuncionario();
        }
    }

    public void exibirHistoricoCompras(Cliente cliente) {
        if (cliente == null) {
            System.out.println("Cliente inválido.");
            return;
        }

        if (!historicoCompras.containsKey(cliente) || historicoCompras.get(cliente).isEmpty()) {
            System.out.println("Nenhuma compra registrada para o cliente: " + cliente.getNome());
            return;
        }
        System.out.println("\n--- Histórico de Compras do Cliente: " + cliente.getNome() + " ---");
        List<String> compras = historicoCompras.get(cliente);
        for (int i = 0; i < compras.size(); i++) {
            System.out.println((i + 1) + ". " + compras.get(i));
            System.out.println("------------------------");
        }
    }

    public void adicionarCliente(Cliente cliente) {
        if (cliente != null && !historicoClientes.contains(cliente)) {
            historicoClientes.add(cliente);
        }
    }

    public void adicionarFuncionario(Funcionario funcionario) {
        if (funcionario != null && !historicoFuncionarios.contains(funcionario)) {
            historicoFuncionarios.add(funcionario);
        }
    }

    public void adicionarCompra(Cliente cliente, String detalhesCompra) {
        if (cliente != null && detalhesCompra != null) {
            if (!historicoCompras.containsKey(cliente)) {
                historicoCompras.put(cliente, new ArrayList<>());
            }
            historicoCompras.get(cliente).add(detalhesCompra);
        }
    }

    private Cliente selecionarCliente() {
        if (historicoClientes.isEmpty()) {
            System.out.println("Não há clientes cadastrados no histórico.");
            return null;
        }

        System.out.println("\n--- Selecione um Cliente ---");
        for (int i = 0; i < historicoClientes.size(); i++) {
            Cliente c = historicoClientes.get(i);
            System.out.println((i + 1) + ". " + c.getNome() + " (CPF: " + c.getCpf() + ")");
        }

        System.out.print("Digite o número do cliente (0 para cancelar): ");
        try {
            int escolha = scanner.nextInt();
            scanner.nextLine(); // Limpar o buffer

            if (escolha == 0) {
                return null;
            }

            if (escolha < 1 || escolha > historicoClientes.size()) {
                System.out.println("Opção inválida!");
                return null;
            }

            return historicoClientes.get(escolha - 1);
        } catch (Exception e) {
            System.out.println("Entrada inválida. Por favor, digite um número.");
            scanner.nextLine(); // Limpar o buffer em caso de erro
            return null;
        }
    }

    public void menuHistorico() {
        while (true) {
            System.out.println("\n--- Menu de Histórico ---");
            System.out.println("1. Exibir Histórico de Clientes");
            System.out.println("2. Exibir Histórico de Funcionários");
            System.out.println("3. Exibir Histórico de Compras de um Cliente");
            System.out.println("0. Sair");

            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    //exibirHistoricoClientes();
                    break;
                case 2:
                    //exibirHistoricoFuncionarios();
                    break;
                case 3:
                    //exibirHistoricoCompras(null);
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }
}