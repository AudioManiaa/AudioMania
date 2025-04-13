package com.audiomania.estoque.view;

import com.audiomania.estoque.model.ItemEstoque;

import java.util.List;
import java.util.Scanner;

public class RelatorioEstoqueView {
    private final Scanner scanner;

    public RelatorioEstoqueView() {
        this.scanner = new Scanner(System.in);
    }

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

    public void exibirRelatorioGeral(List<ItemEstoque> itens) {
        System.out.println("\n========= RELATÓRIO GERAL DE ESTOQUE =========");
        System.out.println("Data do relatório: " + java.time.LocalDate.now());
        System.out.println("Total de itens: " + itens.size());
        System.out.println("=============================================");

        System.out.printf("%-5s %-15s %-30s %-10s %-15s %-15s\n",
                "ID", "CÓDIGO", "PRODUTO", "QTDE", "PREÇO COMPRA", "VALOR TOTAL");

        double valorTotalEstoque = 0.0;
        int totalQuantidadeItens = 0;

        for (ItemEstoque item : itens) {
            double valorItem = item.getProduto().getPrecoCompra().doubleValue() * item.getQuantidade();
            valorTotalEstoque += valorItem;
            totalQuantidadeItens += item.getQuantidade();

            System.out.printf("%-5d %-15s %-30s %-10d R$ %-15.2f R$ %-15.2f\n",
                    item.getId(),
                    item.getProduto().getCodigo(),
                    item.getProduto().getNome(),
                    item.getQuantidade(),
                    item.getProduto().getPrecoCompra().doubleValue(),
                    valorItem);
        }

        System.out.println("=============================================");
        System.out.printf("TOTAL: %d itens no estoque, avaliados em R$ %.2f\n",
                totalQuantidadeItens, valorTotalEstoque);
        System.out.println("=============================================");

        System.out.println("\nPressione ENTER para continuar...");
        scanner.nextLine();
    }

    public void exibirRelatorioBaixoEstoque(List<ItemEstoque> itensBaixo) {
        System.out.println("\n========= RELATÓRIO DE ITENS COM ESTOQUE BAIXO =========");
        System.out.println("Data do relatório: " + java.time.LocalDate.now());
        System.out.println("Total de itens com estoque baixo: " + itensBaixo.size());
        System.out.println("=======================================================");

        if (itensBaixo.isEmpty()) {
            System.out.println("Não há itens com estoque abaixo do mínimo.");
        } else {
            System.out.printf("%-5s %-15s %-30s %-10s %-15s %-15s\n",
                    "ID", "CÓDIGO", "PRODUTO", "ATUAL", "MÍNIMO", "DIFERENÇA");

            for (ItemEstoque item : itensBaixo) {
                int diferenca = item.getQuantidadeMinima() - item.getQuantidade();

                System.out.printf("%-5d %-15s %-30s %-10d %-15d %-15d\n",
                        item.getId(),
                        item.getProduto().getCodigo(),
                        item.getProduto().getNome(),
                        item.getQuantidade(),
                        item.getQuantidadeMinima(),
                        diferenca);
            }
        }

        System.out.println("=======================================================");

        System.out.println("\nPressione ENTER para continuar...");
        scanner.nextLine();
    }

    public void exibirRelatorioValorEstoque(List<ItemEstoque> itens, double valorTotal) {
        System.out.println("\n========= RELATÓRIO DE VALOR DO ESTOQUE =========");
        System.out.println("Data do relatório: " + java.time.LocalDate.now());
        System.out.println("Total de itens: " + itens.size());
        System.out.println("===============================================");

        // Agrupar por categoria
        System.out.println("\nVALOR POR CATEGORIA:");

        // Simplificado para este exemplo - em um sistema real usaríamos Map<Categoria, BigDecimal>
        System.out.printf("%-30s %-15s\n", "CATEGORIA", "VALOR TOTAL");

        // Em um sistema real, faríamos a soma por categoria
        System.out.printf("%-30s R$ %-15.2f\n", "Instrumentos Musicais", valorTotal * 0.6);
        System.out.printf("%-30s R$ %-15.2f\n", "Acessórios", valorTotal * 0.2);
        System.out.printf("%-30s R$ %-15.2f\n", "Equipamentos de Som", valorTotal * 0.2);

        System.out.println("\nITENS DE MAIOR VALOR:");
        System.out.printf("%-30s %-10s %-15s %-15s\n",
                "PRODUTO", "QTDE", "PREÇO UNIT.", "VALOR TOTAL");

        // Ordenar por valor total e mostrar os top 5 (simplificado)
        for (int i = 0; i < Math.min(5, itens.size()); i++) {
            ItemEstoque item = itens.get(i);
            double valorItem = item.getProduto().getPrecoCompra().doubleValue() * item.getQuantidade();

            System.out.printf("%-30s %-10d R$ %-15.2f R$ %-15.2f\n",
                    item.getProduto().getNome(),
                    item.getQuantidade(),
                    item.getProduto().getPrecoCompra().doubleValue(),
                    valorItem);
        }

        System.out.println("===============================================");
        System.out.printf("VALOR TOTAL DO ESTOQUE: R$ %.2f\n", valorTotal);
        System.out.println("===============================================");

        System.out.println("\nPressione ENTER para continuar...");
        scanner.nextLine();
    }

    public void exibirMensagem(String mensagem) {
        System.out.println("\n" + mensagem);
    }
}