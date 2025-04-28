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