package com.audiomania.estoque.view;

import com.audiomania.estoque.model.ItemEstoque;
import com.audiomania.estoque.repository.EstoqueRepository;

import java.util.List;
import java.util.Scanner;

public class ConsultaEstoqueView {
    private final Scanner scanner;

    public ConsultaEstoqueView() {
        this.scanner = new Scanner(System.in);
    }

    public void exibir() {
        int opcao;
        do {
            opcao = exibirMenuConsulta();

            switch (opcao) {
                case 1:
                    consultarPorId();
                    break;
                case 2:
                    consultarPorCodigoProduto();
                    break;
                case 3:
                    consultarEstoqueBaixo();
                    break;
                case 0:
                    System.out.println("Voltando ao menu anterior...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    private int exibirMenuConsulta() {
        System.out.println("\n===== CONSULTA DE ESTOQUE =====");
        System.out.println("1. Consultar por ID");
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

    private void consultarPorId() {
        System.out.print("Informe o ID do item de estoque: ");
        try {
            Long id = Long.parseLong(scanner.nextLine().trim());
            ItemEstoque item = EstoqueRepository.buscarPorId(id);

            if (item != null) {
                exibirDetalhesItemEstoque(item);
            } else {
                System.out.println("Item de estoque não encontrado!");
            }
        } catch (NumberFormatException e) {
            System.out.println("ID inválido! Informe um número.");
        }
    }

    private void consultarPorCodigoProduto() {
        System.out.print("Informe o código do produto: ");
        String codigo = scanner.nextLine().trim();

        boolean encontrado = false;
        for (ItemEstoque item : EstoqueRepository.listarTodos()) {
            if (item.getProduto().getCodigo().equalsIgnoreCase(codigo)) {
                exibirDetalhesItemEstoque(item);
                encontrado = true;
                break;
            }
        }

        if (!encontrado) {
            System.out.println("Produto não encontrado no estoque!");
        }
    }

    private void consultarEstoqueBaixo() {
        List<ItemEstoque> itensBaixo = EstoqueRepository.buscarEstoqueBaixo();

        if (itensBaixo.isEmpty()) {
            System.out.println("Não há itens com estoque abaixo do mínimo.");
            return;
        }

        System.out.println("\n===== ITENS COM ESTOQUE ABAIXO DO MÍNIMO =====");
        System.out.printf("%-5s %-20s %-30s %-10s %-15s\n",
                "ID", "CÓDIGO", "PRODUTO", "ATUAL", "MÍNIMO");

        for (ItemEstoque item : itensBaixo) {
            System.out.printf("%-5d %-20s %-30s %-10d %-15d\n",
                    item.getId(),
                    item.getProduto().getCodigo(),
                    item.getProduto().getNome(),
                    item.getQuantidade(),
                    item.getQuantidadeMinima());
        }
    }

    private void exibirDetalhesItemEstoque(ItemEstoque item) {
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
}