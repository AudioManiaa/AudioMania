package com.audiomania.estoque.view;

import com.audiomania.estoque.model.Produto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class CadastroProdutoView {
    private final Scanner scanner;

    public CadastroProdutoView() {
        this.scanner = new Scanner(System.in);
    }

    public int exibirMenuPrincipal() {
        System.out.println("\n===== CADASTRO DE PRODUTOS =====");
        System.out.println("1. Listar todos os produtos");
        System.out.println("2. Cadastrar novo produto");
        System.out.println("3. Editar produto");
        System.out.println("4. Excluir produto");
        System.out.println("5. Buscar produto por nome");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opção: ");

        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public void exibirListaProdutos(List<Produto> produtos) {
        System.out.println("\n===== LISTA DE PRODUTOS =====");
        if (produtos.isEmpty()) {
            System.out.println("Não há produtos cadastrados.");
            return;
        }

        System.out.printf("%-5s %-15s %-30s %-15s %-20s\n",
                "ID", "CÓDIGO", "NOME", "PREÇO VENDA", "CATEGORIA");

        for (Produto produto : produtos) {
            System.out.printf("%-5d %-15s %-30s R$ %-15.2f %-20s\n",
                    produto.getId(),
                    produto.getCodigo(),
                    produto.getNome(),
                    produto.getPrecoVenda().doubleValue(),
                    produto.getCategoria().getNome());
        }
    }

    public Long solicitarId() {
        System.out.print("Informe o ID do produto: ");
        try {
            return Long.parseLong(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("ID inválido! Informe um número.");
            return solicitarId();
        }
    }

    public String solicitarCodigo() {
        System.out.print("Informe o código do produto: ");
        String codigo = scanner.nextLine().trim();
        if (codigo.isEmpty()) {
            System.out.println("O código não pode ser vazio!");
            return solicitarCodigo();
        }
        return codigo;
    }

    public String solicitarNome() {
        System.out.print("Informe o nome do produto: ");
        String nome = scanner.nextLine().trim();
        if (nome.isEmpty()) {
            System.out.println("O nome não pode ser vazio!");
            return solicitarNome();
        }
        return nome;
    }

    public String solicitarDescricao() {
        System.out.print("Informe a descrição do produto: ");
        return scanner.nextLine().trim();
    }

    public BigDecimal solicitarPrecoCompra() {
        System.out.print("Informe o preço de compra (R$): ");
        try {
            double preco = Double.parseDouble(scanner.nextLine().trim().replace(",", "."));
            if (preco < 0) {
                System.out.println("O preço não pode ser negativo!");
                return solicitarPrecoCompra();
            }
            return BigDecimal.valueOf(preco);
        } catch (NumberFormatException e) {
            System.out.println("Preço inválido! Informe um número.");
            return solicitarPrecoCompra();
        }
    }

    public BigDecimal solicitarPrecoVenda() {
        System.out.print("Informe o preço de venda (R$): ");
        try {
            double preco = Double.parseDouble(scanner.nextLine().trim().replace(",", "."));
            if (preco < 0) {
                System.out.println("O preço não pode ser negativo!");
                return solicitarPrecoVenda();
            }
            return BigDecimal.valueOf(preco);
        } catch (NumberFormatException e) {
            System.out.println("Preço inválido! Informe um número.");
            return solicitarPrecoVenda();
        }
    }

    public String solicitarFabricante() {
        System.out.print("Informe o fabricante do produto: ");
        return scanner.nextLine().trim();
    }

    public Long solicitarCategoria() {
        System.out.print("Informe o ID da categoria: ");
        try {
            return Long.parseLong(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("ID inválido! Informe um número.");
            return solicitarCategoria();
        }
    }

    public String solicitarNomeBusca() {
        System.out.print("Informe o nome ou parte do nome do produto: ");
        String nome = scanner.nextLine().trim();
        if (nome.isEmpty()) {
            System.out.println("O termo de busca não pode ser vazio!");
            return solicitarNomeBusca();
        }
        return nome;
    }

    public void exibirMensagem(String mensagem) {
        System.out.println("\n" + mensagem);
    }
}