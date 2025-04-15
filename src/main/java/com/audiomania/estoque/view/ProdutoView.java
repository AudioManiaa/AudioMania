package com.audiomania.estoque.view;

import com.audiomania.estoque.model.Categoria;
import com.audiomania.estoque.model.Produto;
import com.audiomania.estoque.repository.EstoqueRepository;

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
        System.out.println("6. Gerenciar categorias"); // Nova opção para gerenciar categorias
        System.out.println("0. Voltar");
        System.out.print("Escolha uma opção: ");

        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    // Menu para gerenciamento de categorias
    public int exibirMenuCategorias() {
        System.out.println("\n===== GERENCIAMENTO DE CATEGORIAS =====");
        System.out.println("1. Listar todas as categorias");
        System.out.println("2. Cadastrar nova categoria");
        System.out.println("3. Editar categoria");
        System.out.println("4. Remover categoria");
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
        // Método mantido como estava...
    }

    // Exibir detalhes de um produto
    public void exibirDetalhesProduto(Produto produto) {
        // Método mantido como estava...
    }

    // Solicitar código do produto
    public String solicitarCodigoProduto() {
        // Método mantido como estava...
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

        // Verificar se há categorias cadastradas
        if (categorias == null || categorias.isEmpty()) {
            System.out.println("\nNão há categorias cadastradas.");
            if (confirmarCriarCategoria()) {
                Categoria novaCategoria = solicitarDadosCategoria();
                EstoqueRepository.adicionarCategoria(novaCategoria);
                System.out.println("\nCategoria cadastrada com sucesso!");

                // Atualizar a lista de categorias
                categorias = EstoqueRepository.listarCategorias();

                // Associar a categoria recém-criada ao produto
                produto.setCategoria(novaCategoria);
            } else {
                System.out.println("\nNão é possível cadastrar um produto sem categoria.");
                return null;
            }
        } else {
            // Exibir categorias disponíveis
            System.out.println("\nCategorias disponíveis:");
            for (Categoria categoria : categorias) {
                System.out.println(categoria.getId() + " - " + categoria.getNome());
            }

            // Adicionar opção para criar nova categoria
            System.out.println("0 - Criar nova categoria");

            // Solicitar categoria
            while (true) {
                System.out.print("Escolha o ID da categoria (ou 0 para criar nova): ");
                try {
                    Long categoriaId = Long.parseLong(scanner.nextLine().trim());

                    if (categoriaId == 0) {
                        // Criar nova categoria
                        Categoria novaCategoria = solicitarDadosCategoria();
                        EstoqueRepository.adicionarCategoria(novaCategoria);
                        System.out.println("\nCategoria cadastrada com sucesso!");
                        produto.setCategoria(novaCategoria);
                        break;
                    } else {
                        // Buscar categoria existente
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
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Por favor, digite um número válido.");
                }
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

    // Método para solicitar dados de categoria
    public Categoria solicitarDadosCategoria() {
        Categoria categoria = new Categoria();

        System.out.println("\n===== CADASTRO DE CATEGORIA =====");

        System.out.print("Nome da categoria: ");
        categoria.setNome(scanner.nextLine().trim());

        System.out.print("Descrição: ");
        categoria.setDescricao(scanner.nextLine().trim());

        return categoria;
    }

    // Confirmar criação de categoria
    public boolean confirmarCriarCategoria() {
        System.out.print("\nDeseja cadastrar uma nova categoria agora? (S/N): ");
        String resposta = scanner.nextLine().trim().toUpperCase();
        return resposta.equals("S");
    }

    // Solicitar ID da categoria
    public Long solicitarIdCategoria() {
        System.out.print("\nDigite o ID da categoria: ");
        try {
            return Long.parseLong(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    // Exibir lista de categorias
    public void exibirListaCategorias(List<Categoria> categorias) {
        if (categorias == null || categorias.isEmpty()) {
            System.out.println("\nNenhuma categoria encontrada.");
            return;
        }

        System.out.println("\n===== LISTA DE CATEGORIAS =====");
        System.out.printf("%-5s | %-30s | %-50s%n", "ID", "NOME", "DESCRIÇÃO");
        System.out.println("-------------------------------------------------------------------------");

        for (Categoria categoria : categorias) {
            System.out.printf("%-5d | %-30s | %-50s%n",
                    categoria.getId(),
                    categoria.getNome(),
                    categoria.getDescricao());
        }
    }

    // Confirmar exclusão (pode ser usado tanto para produtos quanto para categorias)
    public boolean confirmarExclusao(String tipo) {
        System.out.print("\nConfirmar exclusão d" + (tipo.equals("produto") ? "o" : "a") + " " + tipo + "? (S/N): ");
        String resposta = scanner.nextLine().trim().toUpperCase();
        return resposta.equals("S");
    }
}