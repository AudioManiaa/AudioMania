package com.audiomania.estoque.util;

import com.audiomania.estoque.model.ItemEstoque;
import com.audiomania.estoque.model.Produto;

import java.math.BigDecimal;

public class ValidadorEstoque {

    /**
     * Valida um item de estoque.
     *
     * @param item Item de estoque a ser validado
     * @throws IllegalArgumentException Se houver problemas na validação
     */
    public static void validarItemEstoque(ItemEstoque item) {
        if (item == null) {
            throw new IllegalArgumentException("O item de estoque não pode ser nulo");
        }

        if (item.getProduto() == null) {
            throw new IllegalArgumentException("O produto do item de estoque não pode ser nulo");
        }

        if (item.getQuantidadeAtual() < 0) {
            throw new IllegalArgumentException("A quantidade não pode ser negativa");
        }

        if (item.getEstoqueMinimo() < 0) {
            throw new IllegalArgumentException("A quantidade mínima não pode ser negativa");
        }

        validarProduto(item.getProduto());
    }

    /**
     * Valida um produto.
     *
     * @param produto Produto a ser validado
     * @throws IllegalArgumentException Se houver problemas na validação
     */
    public static void validarProduto(Produto produto) {
        if (produto == null) {
            throw new IllegalArgumentException("O produto não pode ser nulo");
        }

        if (produto.getSku() == null || produto.getSku().isEmpty()) {
            throw new IllegalArgumentException("O código do produto não pode ser vazio");
        }

        if (produto.getNome() == null || produto.getNome().isEmpty()) {
            throw new IllegalArgumentException("O nome do produto não pode ser vazio");
        }

        if (produto.getPrecoCompra() == null || produto.getPrecoCompra().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("O preço de compra deve ser maior ou igual a zero");
        }

        if (produto.getPrecoVenda() == null || produto.getPrecoVenda().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("O preço de venda deve ser maior ou igual a zero");
        }

        if (produto.getCategoria() == null) {
            throw new IllegalArgumentException("A categoria do produto não pode ser nula");
        }
    }
}