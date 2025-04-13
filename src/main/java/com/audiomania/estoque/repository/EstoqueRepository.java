package com.audiomania.estoque.repository;

import com.audiomania.estoque.model.ItemEstoque;
import com.audiomania.estoque.model.Produto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EstoqueRepository {
    private static final Map<Long, ItemEstoque> itensEstoque = new HashMap<>();
    private static Long sequence = 1L;

    // Inicialização com alguns itens de exemplo
    static {
        List<Produto> produtos = ProdutoRepository.listarTodos();
        for (Produto produto : produtos) {
            adicionar(new ItemEstoque(null, produto, 10, 5, "Prateleira A" + produto.getId()));
        }
    }

    public static List<ItemEstoque> listarTodos() {
        return new ArrayList<>(itensEstoque.values());
    }

    public static ItemEstoque buscarPorId(Long id) {
        return itensEstoque.get(id);
    }

    public static ItemEstoque buscarPorProduto(Produto produto) {
        for (ItemEstoque item : itensEstoque.values()) {
            if (item.getProduto().getId().equals(produto.getId())) {
                return item;
            }
        }
        return null;
    }

    public static ItemEstoque adicionar(ItemEstoque itemEstoque) {
        if (itemEstoque.getId() == null) {
            itemEstoque.setId(sequence++);
        }
        itensEstoque.put(itemEstoque.getId(), itemEstoque);
        return itemEstoque;
    }

    public static ItemEstoque atualizar(ItemEstoque itemEstoque) {
        if (itemEstoque.getId() == null || !itensEstoque.containsKey(itemEstoque.getId())) {
            throw new IllegalArgumentException("Item de estoque não encontrado para atualização");
        }
        itensEstoque.put(itemEstoque.getId(), itemEstoque);
        return itemEstoque;
    }

    public static void remover(Long id) {
        if (!itensEstoque.containsKey(id)) {
            throw new IllegalArgumentException("Item de estoque não encontrado para remoção");
        }
        itensEstoque.remove(id);
    }

    public static List<ItemEstoque> buscarEstoqueBaixo() {
        List<ItemEstoque> resultado = new ArrayList<>();
        for (ItemEstoque item : itensEstoque.values()) {
            if (item.isEstoqueBaixo()) {
                resultado.add(item);
            }
        }
        return resultado;
    }
}