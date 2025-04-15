package com.audiomania.estoque.view;

import com.audiomania.estoque.model.ItemEstoque;
import com.audiomania.estoque.model.Produto;
import com.audiomania.estoque.repository.ProdutoRepository;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class EstoqueView {
    private final Scanner scanner;
    private final NumberFormat currencyFormatter;
    private final DateTimeFormatter dateFormatter;

    public EstoqueView() {
        this.scanner = new Scanner(System.in);
        this.currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        this.dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    }

    // Menu principal
    public int exibirMenuPrincipal() {
        System.out.println("\n===== SISTEMA DE CONTROLE DE ESTOQUE =====");
        System.out.println("1. Listar todos os itens em estoque");
        System.out.println("2. Consultar estoque");
        System.out.println("3. Adicionar entrada em estoque");
        System.out.println("4. Registrar saída em estoque");
        System.out.println("5. Relatórios");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opção: ");

        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    // Menu de consulta
    public int exibirMenuConsulta() {
        System.out.println("\n===== CONSULTA DE ESTOQUE =====");
        System.out.println("1. Consultar item por ID");
        System.out.println("2. Consultar por código do produto");
        System.out.println("3. Consultar itens com estoque baixo");
        System.out.println("0. Voltar");
        System.out.print("Escolha uma opção: ");

        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    // Menu de relatórios
    public int exibirMenuRelatorios() {
        System.out.println("\n===== RELATÓRIOS DE ESTOQUE =====");
        System.out.println("1. Relatório Geral de Estoque");
        System.out.println("2. Relatório de Itens com Estoque Baixo");
        System.out.println("3. Relatório de Valor do Estoque");
        System.out.println("0. Voltar");
        System.out.print("Escolha uma opção: ");

        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    // Métodos para exibir listas e detalhes de itens
    public void exibirListaItensEstoque(List<ItemEstoque> itens) {
        if (itens == null || itens.isEmpty()) {
            System.out.println("\nNenhum item encontrado no estoque.");
            return;
        }

        System.out.println("\n===== LISTA DE ITENS EM ESTOQUE - SOM AUTOMOTIVO =====");
        System.out.printf("%-5s | %-10s | %-30s | %-10s | %-15s | %-8s%n",
                "ID", "CÓDIGO", "PRODUTO", "QTDE", "FABRICANTE", "STATUS");
        System.out.println("-------------------------------------------------------------------------------------");

        for (ItemEstoque item : itens) {
            Produto produto = item.getProduto();
            System.out.printf("%-5d | %-10s | %-30s | %-10d | %-15s | %-8s%n",
                    item.getId(),
                    produto.getCodigo(),
                    produto.getNome(),
                    item.getQuantidade(),
                    produto.getFabricante(),
                    item.getStatusEstoque());
        }
    }

    public void exibirDetalhesItemEstoque(ItemEstoque item) {
        if (item == null) {
            System.out.println("\nItem não encontrado.");
            return;
        }

        Produto produto = item.getProduto();

        System.out.println("\n===== DETALHES DO ITEM =====");
        System.out.println("ID: " + item.getId());
        System.out.println("Produto: " + produto.getNome() + " (Código: " + produto.getCodigo() + ")");
        System.out.println("Categoria: " + produto.getCategoria().getNome());
        System.out.println("Fabricante: " + produto.getFabricante());
        System.out.println("Localização: " + item.getLocalizacao());
        System.out.println("Quantidade em estoque: " + item.getQuantidade());
        System.out.println("Quantidade mínima: " + item.getQuantidadeMinima());
        System.out.println("Status: " + item.getStatusEstoque());
        System.out.println("Preço de compra: " + currencyFormatter.format(produto.getPrecoCompra()));
        System.out.println("Preço de venda: " + currencyFormatter.format(produto.getPrecoVenda()));
        System.out.println("Margem de lucro: " + produto.getMargemLucro() + "%");
        System.out.println("Valor total em estoque: " + currencyFormatter.format(item.getValorTotalEstoque()));
        System.out.println("Última entrada: " + item.getDataUltimaEntradaFormatada());
        System.out.println("Última saída: " + item.getDataUltimaSaidaFormatada());
        System.out.println("Descrição: " + produto.getDescricao());
    }

    // Métodos para solicitar entradas do usuário
    public Long solicitarId(String tipo) {
        System.out.print("\nDigite o ID do " + tipo + ": ");
        try {
            return Long.parseLong(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public String solicitarCodigoProduto() {
        System.out.print("\nDigite o código do produto: ");
        return scanner.nextLine().trim();
    }

    public int solicitarQuantidade(String tipo) {
        System.out.print("\nDigite a quantidade para " + tipo + ": ");
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    // Menu de produtos
    public int exibirMenuProdutos() {
        System.out.println("\n===== MENU DE PRODUTOS =====");
        System.out.println("1. Cadastrar novo produto");
        System.out.println("2. Listar produtos");
        System.out.println("0. Voltar");
        System.out.print("Escolha uma opção: ");

        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    // Método para cadastrar um novo produto
    public Produto cadastrarProduto(ProdutoRepository produtoRepository) {
        System.out.println("\n===== CADASTRAR NOVO PRODUTO =====");

        System.out.print("Código do produto: ");
        String codigo = scanner.nextLine();

        System.out.print("Nome do produto: ");
        String nome = scanner.nextLine();

        System.out.print("Descrição: ");
        String descricao = scanner.nextLine();

        System.out.print("Preço de compra (R$): ");
        BigDecimal precoCompra = new BigDecimal(scanner.nextLine());

        System.out.print("Preço de venda (R$): ");
        BigDecimal precoVenda = new BigDecimal(scanner.nextLine());

        System.out.print("Fabricante/Marca: ");
        String fabricante = scanner.nextLine();

        System.out.print("Categoria: ");
        String categoria = scanner.nextLine();

        // Criar e salvar o produto
        Produto novoProduto = produtoRepository.criarProduto(
                codigo, nome, descricao, precoCompra, precoVenda, fabricante,
                new Categoria(null, "CAT" + System.currentTimeMillis(), categoria, ""));

        System.out.println("\nProduto cadastrado com sucesso!");
        System.out.println("ID do produto: " + novoProduto.getId());

        return novoProduto;
    }

    // Método para listar produtos
    public void listarProdutos(List<Produto> produtos) {
        if (produtos == null || produtos.isEmpty()) {
            System.out.println("\nNenhum produto cadastrado.");
            return;
        }

        System.out.println("\n===== LISTA DE PRODUTOS =====");
        System.out.printf("%-5s | %-10s | %-30s | %-12s | %-12s | %-15s%n",
                "ID", "CÓDIGO", "NOME", "PREÇO COMPRA", "PREÇO VENDA", "FABRICANTE");
        System.out.println("-------------------------------------------------------------------------------------");

        for (Produto produto : produtos) {
            System.out.printf("%-5s | %-10s | %-30s | %-12s | %-12s | %-15s%n",
                    produto.getId(),
                    produto.getCodigo(),
                    produto.getNome(),
                    currencyFormatter.format(produto.getPrecoCompra()),
                    currencyFormatter.format(produto.getPrecoVenda()),
                    produto.getFabricante());
        }
    }

    // Métodos para relatórios
    public void exibirRelatorioGeral(List<ItemEstoque> itens) {
        if (itens == null || itens.isEmpty()) {
            System.out.println("\nNenhum item encontrado no estoque.");
            return;
        }

        BigDecimal valorTotal = BigDecimal.ZERO;
        int totalItens = 0;
        int itensAbaixoMinimo = 0;

        System.out.println("\n===== RELATÓRIO GERAL DE ESTOQUE =====");
        System.out.println("Data do relatório: " + LocalDate.now().format(dateFormatter));
        System.out.println("\nITENS EM ESTOQUE:");

        System.out.printf("%-5s | %-10s | %-30s | %-8s | %-12s | %-12s%n",
                "ID", "CÓDIGO", "PRODUTO", "QTDE", "VALOR UNIT.", "VALOR TOTAL");
        System.out.println("--------------------------------------------------------------------------------------");

        for (ItemEstoque item : itens) {
            Produto produto = item.getProduto();
            BigDecimal valorTotalItem = item.getValorTotalEstoque();
            valorTotal = valorTotal.add(valorTotalItem);
            totalItens += item.getQuantidade();

            if (item.isEstoqueBaixo()) {
                itensAbaixoMinimo++;
            }

            System.out.printf("%-5d | %-10s | %-30s | %-8d | %-12s | %-12s%n",
                    item.getId(),
                    produto.getCodigo(),
                    produto.getNome(),
                    item.getQuantidade(),
                    currencyFormatter.format(produto.getPrecoCompra()),
                    currencyFormatter.format(valorTotalItem));
        }

        System.out.println("--------------------------------------------------------------------------------------");
        System.out.println("Total de produtos em estoque: " + totalItens);
        System.out.println("Valor total em estoque: " + currencyFormatter.format(valorTotal));
        System.out.println("Produtos abaixo do estoque mínimo: " + itensAbaixoMinimo);
    }

    public void exibirRelatorioBaixoEstoque(List<ItemEstoque> itensBaixo) {
        if (itensBaixo == null || itensBaixo.isEmpty()) {
            System.out.println("\nNão há itens com estoque baixo.");
            return;
        }

        System.out.println("\n===== RELATÓRIO DE ITENS COM ESTOQUE BAIXO =====");
        System.out.println("Data do relatório: " + LocalDate.now().format(dateFormatter));

        System.out.printf("%-5s | %-10s | %-30s | %-10s | %-10s | %-15s%n",
                "ID", "CÓDIGO", "PRODUTO", "ATUAL", "MÍNIMO", "NECESSÁRIO");
        System.out.println("--------------------------------------------------------------------------------------");

        for (ItemEstoque item : itensBaixo) {
            Produto produto = item.getProduto();
            int necessario = item.getQuantidadeMinima() - item.getQuantidade();

            System.out.printf("%-5d | %-10s | %-30s | %-10d | %-10d | %-15d%n",
                    item.getId(),
                    produto.getCodigo(),
                    produto.getNome(),
                    item.getQuantidade(),
                    item.getQuantidadeMinima(),
                    necessario);
        }

        System.out.println("--------------------------------------------------------------------------------------");
        System.out.println("Total de itens com estoque baixo: " + itensBaixo.size());
    }
}