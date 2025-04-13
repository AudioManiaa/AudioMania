package com.audiomania.estoque.view;

import com.audiomania.estoque.model.ItemEstoque;

import java.util.List;
import java.util.Scanner;

public class EstoqueView {
    private final Scanner scanner;

    public EstoqueView() {
        this.scanner = new Scanner(System.in);
    }

    public int exibirMenuPrincipal() {
        System.out.println("\n===== SISTEMA DE CONTROLE DE ESTOQUE =====");
        System.out.println("1. Listar itens");
        System.out.println("2. Consultar item");
        System.out.println("3. Adicionar entrada");
        System.out.println("4. Registrar saída");
        System.out.println("5. Itens em baixa");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opção: ");

        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public void exibirListaItensEstoque(List<ItemEstoque> itens) {
        System.out.println("\n===== LISTA DE ITENS EM ESTOQUE =====");
        if (itens.isEmpty()) {
            System.out.println("Não há itens cadastrados no estoque.");
            return;
        }

        System.out.printf("%-5s %-20s %-30s %-10s %-15s %-15s\n",
                "ID", "CÓDIGO", "PRODUTO", "QTDE", "MÍNIMO", "LOCALIZAÇÃO");

        for (ItemEstoque item : itens) {
            System.out.printf("%-5d %-20s %-30s %-10d %-15d %-15s\n",
                    item.getId(),
                    item.getProduto().getCodigo(),
                    item.getProduto().getNome(),
                    item.getQuantidade(),
                    item.getQuantidadeMinima(),
                    item.getLocalizacao());
        }
    }

    public void exibirDetalhesItemEstoque(ItemEstoque item) {
        if (item == null) {
            System.out.println("Item não encontrado!");
            return;
        }

        System.out.println("\n===== DETALHES DO ITEM DE ESTOQUE =====");
        System.out.println("ID: " + item.getId());
        System.out.println("Produto: " + item.getProduto().getNome());
        System.out.println("Código: " + item.getProduto().getCodigo());
        System.out.println("Quantidade em estoque: " + item.getQuantidade());
        System.out.println("Quantidade mínima: " + item.getQuantidadeMinima());
        System.out.println("Localização: " + item.getLocalizacao());
        System.out.println("Preço de compra: R$ " + item.getProduto().getPrecoCompra());
        System.out.println("Preço de venda: R$ " + item.getProduto().getPrecoVenda());

        if (item.getDataUltimaEntrada() != null) {
            System.out.println("Última entrada: " + item.getDataUltimaEntrada());
        }

        if (item.getDataUltimaSaida() != null) {
            System.out.println("Última saída: " + item.getDataUltimaSaida());
        }

        if (item.isEstoqueBaixo()) {
            System.out.println("ALERTA: Este item está com estoque abaixo do mínimo!");
        }
    }

    public void exibirItensBaixoEstoque(List<ItemEstoque> itens) {
        System.out.println("\n===== ITENS COM ESTOQUE ABAIXO DO MÍNIMO =====");

        if (itens.isEmpty()) {
            System.out.println("Não há itens com estoque abaixo do mínimo.");
            return;
        }

        System.out.printf("%-5s %-20s %-30s %-10s %-15s\n",
                "ID", "CÓDIGO", "PRODUTO", "ATUAL", "MÍNIMO");

        for (ItemEstoque item : itens) {
            System.out.printf("%-5d %-20s %-30s %-10d %-15d\n",
                    item.getId(),
                    item.getProduto().getCodigo(),
                    item.getProduto().getNome(),
                    item.getQuantidade(),
                    item.getQuantidadeMinima());
        }
    }

    public Long solicitarIdProduto() {
        System.out.print("Informe o ID do produto: ");
        try {
            return Long.parseLong(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("ID inválido! Informe um número.");
            return solicitarIdProduto();
        }
    }

    public Long solicitarIdItemEstoque() {
        System.out.print("Informe o ID do item de estoque: ");
        try {
            return Long.parseLong(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("ID inválido! Informe um número.");
            return solicitarIdItemEstoque();
        }
    }

    public int solicitarQuantidade(String tipo) {
        System.out.print("Informe a quantidade para " + tipo + ": ");
        try {
            int quantidade = Integer.parseInt(scanner.nextLine().trim());
            if (quantidade <= 0) {
                System.out.println("A quantidade deve ser maior que zero!");
                return solicitarQuantidade(tipo);
            }
            return quantidade;
        } catch (NumberFormatException e) {
            System.out.println("Quantidade inválida! Informe um número.");
            return solicitarQuantidade(tipo);
        }
    }

    public void exibirMensagem(String mensagem) {
        System.out.println("\n" + mensagem);
    }
}