package com.audiomania.controller;

import com.audiomania.model.Cliente;
import com.audiomania.model.Funcionario;

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


    public void exibirHistoricoClientes() {
        if (historicoClientes.isEmpty()) {
            System.out.println("Nenhum cliente no histórico.");
            return;
        }
        System.out.println("\n--- Histórico de Clientes ---");
        for (Cliente cliente : historicoClientes) {
            cliente.exibirCliente();
        }
    }

    public void exibirHistoricoFuncionarios() {
        if (historicoFuncionarios.isEmpty()) {
            System.out.println("Nenhum funcionário no histórico.");
            return;
        }
        System.out.println("\n--- Histórico de Funcionários ---");
        for (Funcionario funcionario : historicoFuncionarios) {
            funcionario.exibirFuncionario();
        }
    }

    public void exibirHistoricoCompras(Cliente cliente) {
        if (!historicoCompras.containsKey(cliente) || historicoCompras.get(cliente).isEmpty()) {
            System.out.println("Nenhuma compra registrada para o cliente: " + cliente.getNome());
            return;
        }
        System.out.println("\n--- Histórico de Compras do Cliente: " + cliente.getNome() + " ---");
        for (String compra : historicoCompras.get(cliente)) {
            System.out.println(compra);
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
                    exibirHistoricoClientes();
                    break;
                case 2:
                    exibirHistoricoFuncionarios();
                    break;
                case 3:
                    exibirHistoricoCompras(null);
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }
}
