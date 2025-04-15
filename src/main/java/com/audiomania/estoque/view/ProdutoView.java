package com.audiomania.estoque.view;

import com.audiomania.estoque.model.Categoria;
import com.audiomania.estoque.model.Produto;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class ProdutoView {
    private final Scanner scanner;
    private final NumberFormat currencyFormatter;

    public ProdutoView() {
        this.scanner = new Scanner(System.in);
        this.currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
    }

    // Menu para gerenciamento de produtos
    public int exibirMenuProdutos() {
        System.out.println("\n===== GERENCIAMENTO DE PRODUTOS =====");
        System.out.println("1. Listar todos os produtos");
        System.out.println("2. Buscar produto por código");
        System.out.println("3. Cadastrar novo produto");
        System.out.println("4. Editar produto existente");
        System.out.println("5. Remover produto");
        System.out.println("0. Voltar");
        System.out.print("Escolha uma opção: ");

        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    // Exibir lista de produtos
    public void exibirListaProdutos(List<Produto> produtos) {
        if (produtos == null || produtos.isEmpty()) {
            System.out.println("\nNenhum produto encontrado.");
            return;
        }

        System.out.println("\n===== LISTA DE PRODUTOS =====");
        System.out.printf("%-5s | %-10s | %-30s | %-15s | %-15s | %-15s%n",
                "ID", "CÓDIGO", "NOME", "CATEGORIA", "MARCA", "PREÇO");
        System.out.println("-----------------------------------------------------------------------------------------");

        for (Produto produto : produtos) {
            System.out.printf("%-5d | %-10s | %-30s | %-15s | %-15s | %-15s%n",
                    produto.getId(),
                    produto.getCodigo(),
                    produto.getNome(),
                    produto.getCategoria().getNome(),
                    produto.getFabricante(),
                    currencyFormatter.format(produto.getPrecoVenda()));
        }
    }

    // Exibir detalhes de um produto
    public void exibirDetalhesProduto(Produto produto) {
        if (produto == null) {
            System.out.println("\nProduto não encontrado.");
            return;
        }

        System.out.println("\n===== DETALHES DO PRODUTO =====");
        System.out.println("ID: " + produto.getId());
        System.out.println("Código: " + produto.getCodigo());
        System.out.println("Nome: " + produto.getNome());
        System.out.println("Descrição: " + produto.getDescricao());
        System.out.println("Categoria: " + produto.getCategoria().getNome());
        System.out.println("Marca: " + produto.getFabricante());
        System.out.println("Preço de compra: " + currencyFormatter.format(produto.getPrecoCompra()));
        System.out.println("Preço de venda: " + currencyFormatter.format(produto.getPrecoVenda()));
        System.out.println("Margem de lucro: " + produto.getMargemLucro() + "%");
    }

    // Solicitar código do produto
    public String solicitarCodigoProduto() {
        System.out.print("\nDigite o código do produto: ");
        return scanner.nextLine().trim();
    }
    // Método para cadastrar um novo produto
    public Produto solicitarDadosProduto(List<Categoria> categorias) {
        Produto produto = new Produto();

        System.out.println("\n===== CADASTRO DE PRODUTO =====");

        System.out.print("Código do produto: ");
        produto.setCodigo(scanner.nextLine().trim());

        System.out.print("Nome do produto: ");
        produto.setNome(scanner.nextLine().trim());

        System.out.print("Descrição: ");
        produto.setDescricao(scanner.nextLine().trim());

        System.out.print("Marca: ");
        produto.setFabricante(scanner.nextLine().trim());

        // Exibir categorias disponíveis
        System.out.println("\nCategorias disponíveis:");
        for (Categoria categoria : categorias) {
            System.out.println(categoria.getId() + " - " + categoria.getNome());
        }

        // Solicitar categoria
        while (true) {
            System.out.print("ID da categoria: ");
            try {
                Long categoriaId = Long.parseLong(scanner.nextLine().trim());
                boolean categoriaEncontrada = false;

                for (Categoria categoria : categorias) {
                    if (categoria.getId().equals(categoriaId)) {
                        produto.setCategoria(categoria);
                        categoriaEncontrada = true;
                        break;
                    }
                }

                if (categoriaEncontrada) {
                    break;
                } else {
                    System.out.println("Categoria não encontrada. Tente novamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor, digite um número válido.");
            }
        }

        // Solicitar preços
        while (true) {
            System.out.print("Preço de compra (R$): ");
            try {
                BigDecimal precoCompra = new BigDecimal(scanner.nextLine().trim().replace(",", "."));
                produto.setPrecoCompra(precoCompra);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Por favor, digite um valor válido (Ex: 99.90).");
            }
        }

        while (true) {
            System.out.print("Preço de venda (R$): ");
            try {
                BigDecimal precoVenda = new BigDecimal(scanner.nextLine().trim().replace(",", "."));
                produto.setPrecoVenda(precoVenda);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Por favor, digite um valor válido (Ex: 149.90).");
            }
        }

        return produto;
    }



