package com.audiomania.estoque.util;

import com.audiomania.estoque.model.ItemEstoque;
import com.audiomania.estoque.model.Produto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class EstoqueUtil {

    /**
     * Calcula o valor total de um item de estoque (quantidade * preço).
     *
     * @param item Item de estoque
     * @return Valor total do item
     */
    public static BigDecimal calcularValorTotalItem(ItemEstoque item) {
        return item.getProduto().getPrecoCompra()
                .multiply(BigDecimal.valueOf(item.getQuantidade()))
                .setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Calcula o valor total do estoque.
     *
     * @param itens Lista de itens de estoque
     * @return Valor total do estoque
     */
    public static BigDecimal calcularValorTotalEstoque(List<ItemEstoque> itens) {
        BigDecimal total = BigDecimal.ZERO;

        for (ItemEstoque item : itens) {
            total = total.add(calcularValorTotalItem(item));
        }

        return total.setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Calcula a margem de lucro de um produto.
     *
     * @param produto Produto
     * @return Percentual de margem de lucro
     */
    public static BigDecimal calcularMargemLucro(Produto produto) {
        if (produto.getPrecoCompra().compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }

        BigDecimal lucro = produto.getPrecoVenda().subtract(produto.getPrecoCompra());
        return lucro.divide(produto.getPrecoCompra(), 4, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100))
                .setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Formata uma data no padrão dd/MM/yyyy.
     *
     * @param data Data a ser formatada
     * @return String com a data formatada
     */
    public static String formatarData(LocalDate data) {
        if (data == null) {
            return "";
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return data.format(formatter);
    }

    /**
     * Verifica se um produto está em baixo estoque.
     *
     * @param item Item de estoque
     * @return true se o estoque estiver abaixo do mínimo, false caso contrário
     */
    public static boolean isEstoqueBaixo(ItemEstoque item) {
        return item.getQuantidade() <= item.getQuantidadeMinima();
    }

    /**
     * Calcula a quantidade ideal para nova compra de um item em baixo estoque.
     *
     * @param item Item de estoque
     * @return Quantidade sugerida para compra
     */
    public static int calcularQuantidadeParaCompra(ItemEstoque item) {
        if (!isEstoqueBaixo(item)) {
            return 0;
        }

        // Política simples: repor para 2x o estoque mínimo
        return (item.getQuantidadeMinima() * 2) - item.getQuantidade();
    }
}