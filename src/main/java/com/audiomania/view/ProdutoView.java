package com.audiomania.view;

import com.audiomania.controller.ProdutoController;
import com.audiomania.entities.ProdutoEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class ProdutoView {
    private final Scanner scanner;
    private final ProdutoController controller;

    public ProdutoView() {
        this.scanner = new Scanner(System.in);
        this.controller = new ProdutoController();
    }

    public void iniciarGerenciamento() {
        boolean sair = false;

        while (!sair) {
            System.out.println("\n===== GERENCIAMENTO DE PRODUTOS =====\n");
            System.out.println("1. Listar Produtos");
            System.out.println("2. Cadastrar Produto");
            System.out.println("3. Atualizar Produto");
            System.out.println("4. Excluir Produto");
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
                    listarProdutos();
                    break;
                case 2:
                    cadastrarProduto();
                    break;
                case 3:
                    atualizarProduto();
                    break;
                case 4:
                    excluirProduto();
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

    private void listarProdutos() {
        List<ProdutoEntity> produtos = controller.listarProdutos();

        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto cadastrado.");
            return;
        }

        System.out.println("\n=== LISTA DE PRODUTOS ===\n");
        System.out.printf("%-5s | %-30s | %-10s | %-10s | %-20s | %-20s\n",
                "ID", "NOME", "PREÇO", "ESTOQUE", "CATEGORIA", "MARCA");
        System.out.println("--------------------------------------------------------------------------------------------");

        for (ProdutoEntity produto : produtos) {
            System.out.printf("%-5d | %-30s | R$ %-8.2f | %-10d | %-20s | %-20s\n",
                    produto.getId(),
                    produto.getNome(),
                    produto.getPreco(),
                    produto.getQuantidadeEstoque(),
                    produto.getCategoria(),
                    produto.getMarca());
        }
    }

    private void cadastrarProduto() {
        System.out.println("\n=== CADASTRO DE PRODUTO ===\n");

        System.out.print("Nome: ");
        String nome = scanner.nextLine();

        System.out.print("Descrição: ");
        String descricao = scanner.nextLine();

        System.out.print("Preço: ");
        BigDecimal preco;
        try {
            preco = new BigDecimal(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Preço inválido.");
            return;
        }

        System.out.print("Quantidade em Estoque: ");
        int estoque;
        try {
            estoque = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Quantidade inválida.");
            return;
        }

        System.out.print("Categoria: ");
        String categoria = scanner.nextLine();

        System.out.print("Marca: ");
        String marca = scanner.nextLine();

        boolean sucesso = controller.cadastrarProduto(nome, descricao, preco, estoque, categoria, marca);

        if (sucesso) {
            System.out.println("Produto cadastrado com sucesso!");
        } else {
            System.out.println("Erro ao cadastrar produto.");
        }
    }

    private void atualizarProduto() {
        System.out.println("\n=== ATUALIZAÇÃO DE PRODUTO ===\n");

        listarProdutos();

        System.out.print("\nDigite o ID do produto que deseja atualizar: ");
        int id;
        try {
            id = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("ID inválido.");
            return;
        }

        ProdutoEntity produto = controller.buscarPorId(id);
        if (produto == null) {
            System.out.println("Produto não encontrado.");
            return;
        }

        System.out.println("\nDados atuais do produto:");
        System.out.println("Nome: " + produto.getNome());
        System.out.println("Descrição: " + produto.getDescricao());
        System.out.println("Preço: R$ " + produto.getPreco());
        System.out.println("Estoque: " + produto.getQuantidadeEstoque());
        System.out.println("Categoria: " + produto.getCategoria());
        System.out.println("Marca: " + produto.getMarca());

        System.out.println("\nDigite os novos dados (deixe em branco para manter o valor atual):");

        System.out.print("Novo Nome: ");
        String nome = scanner.nextLine();
        if (!nome.isBlank()) {
            produto.setNome(nome);
        }

        System.out.print("Nova Descrição: ");
        String descricao = scanner.nextLine();
        if (!descricao.isBlank()) {
            produto.setDescricao(descricao);
        }

        System.out.print("Novo Preço: ");
        String precoStr = scanner.nextLine();
        if (!precoStr.isBlank()) {
            try {
                produto.setPreco(new BigDecimal(precoStr));
            } catch (NumberFormatException e) {
                System.out.println("Preço inválido. Este campo não será atualizado.");
            }
        }

        System.out.print("Nova Quantidade em Estoque: ");
        String estoqueStr = scanner.nextLine();
        if (!estoqueStr.isBlank()) {
            try {
                produto.setQuantidadeEstoque(Integer.parseInt(estoqueStr));
            } catch (NumberFormatException e) {
                System.out.println("Quantidade inválida. Este campo não será atualizado.");
            }
        }

        System.out.print("Nova Categoria: ");
        String categoria = scanner.nextLine();
        if (!categoria.isBlank()) {
            produto.setCategoria(categoria);
        }

        System.out.print("Nova Marca: ");
        String marca = scanner.nextLine();
        if (!marca.isBlank()) {
            produto.setMarca(marca);
        }

        boolean sucesso = controller.atualizarProduto(produto);

        if (sucesso) {
            System.out.println("Produto atualizado com sucesso!");
        } else {
            System.out.println("Erro ao atualizar produto.");
        }
    }

    private void excluirProduto() {
        System.out.println("\n=== EXCLUSÃO DE PRODUTO ===\n");

        listarProdutos();

        System.out.print("\nDigite o ID do produto que deseja excluir: ");
        int id;
        try {
            id = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("ID inválido.");
            return;
        }

        ProdutoEntity produto = controller.buscarPorId(id);
        if (produto == null) {
            System.out.println("Produto não encontrado.");
            return;
        }

        System.out.println("\nDados do produto a ser excluído:");
        System.out.println("Nome: " + produto.getNome());
        System.out.println("Descrição: " + produto.getDescricao());
        System.out.println("Preço: R$ " + produto.getPreco());
        System.out.println("Estoque: " + produto.getQuantidadeEstoque());

        System.out.print("\nTem certeza que deseja excluir este produto? (S/N): ");
        String confirmacao = scanner.nextLine();

        if (confirmacao.equalsIgnoreCase("S")) {
            boolean sucesso = controller.excluirProduto(id);

            if (sucesso) {
                System.out.println("Produto excluído com sucesso!");
            } else {
                System.out.println("Erro ao excluir produto. Pode haver vendas relacionadas a este produto.");
            }
        } else {
            System.out.println("Operação cancelada.");
        }
    }
}