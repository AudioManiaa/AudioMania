package com.audiomania.controller;

import com.audiomania.entities.VendaEntity;
import com.audiomania.service.VendaService;

import java.util.List;
import java.util.Scanner;

public class VendaController {

    private VendaService vendaService = new VendaService();
    private Scanner scanner = new Scanner(System.in);

    public void menu() {
        int opcao;
        do {
            System.out.println("\n--- Sistema de Vendas ---");
            System.out.println("1 - Cadastrar Venda");
            System.out.println("2 - Listar Vendas");
            System.out.println("0 - Sair 8===D");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // consumir o enter

            switch (opcao) {
                case 1:
                    cadastrarVenda();
                    break;
                case 2:
                    listarVendas();
                    break;
                case 0:
                    System.out.println("Encerrando...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private void cadastrarVenda() {
        VendaEntity venda = new VendaEntity();

        System.out.print("Data da venda (YYYY-MM-DD): ");
        venda.setData(java.time.LocalDate.parse(scanner.nextLine()));

        System.out.print("Valor total: ");
        venda.setValorTotal(new java.math.BigDecimal(scanner.nextLine()));

        System.out.print("Forma de pagamento: ");
        venda.setFormaPagamento(scanner.nextLine());

        System.out.print("Desconto: ");
        venda.setDesconto(new java.math.BigDecimal(scanner.nextLine()));

        System.out.print("ID do Cliente: ");
        venda.setIdCliente(scanner.nextInt());

        System.out.print("ID do Funcionário: ");
        venda.setIdFuncionario(scanner.nextInt());

        System.out.print("ID do Produto: ");
        venda.setIdProduto(scanner.nextInt());

        System.out.print("Quantidade: ");
        venda.setQuantidade(scanner.nextInt());
        scanner.nextLine(); // consumir enter

        vendaService.salvarVenda(venda);
        System.out.println("Venda cadastrada com sucesso!");
    }

    private void listarVendas() {
        List<VendaEntity> vendas = vendaService.listarVendas();
        for (VendaEntity venda : vendas) {
            System.out.println("\nID Venda: " + venda.getIdVenda());
            System.out.println("Data: " + venda.getData());
            System.out.println("Valor Total: " + venda.getValorTotal());
            System.out.println("Forma de Pagamento: " + venda.getFormaPagamento());
            System.out.println("Desconto: " + venda.getDesconto());
            System.out.println("ID Cliente: " + venda.getIdCliente());
            System.out.println("ID Funcionário: " + venda.getIdFuncionario());
            System.out.println("ID Produto: " + venda.getIdProduto());
            System.out.println("Quantidade: " + venda.getQuantidade());
            System.out.println("---------------------------");
        }
    }
}
