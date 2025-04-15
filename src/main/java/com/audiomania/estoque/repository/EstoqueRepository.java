package com.audiomania.estoque.repository;

import com.audiomania.estoque.model.Categoria;
import com.audiomania.estoque.model.ItemEstoque;
import com.audiomania.estoque.model.Produto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EstoqueRepository {
    private static final List<Categoria> categorias = new ArrayList<>();
    private static final List<Produto> produtos = new ArrayList<>();
    private static final List<ItemEstoque> itensEstoque = new ArrayList<>();
    private static Long nextCategoriaId = 1L;
    private static Long nextProdutoId = 1L;
    private static Long nextItemId = 1L;

    // Métodos para categorias
    public static List<Categoria> listarCategorias() {
        return new ArrayList<>(categorias);
    }

    public static Categoria buscarCategoriaPorId(Long id) {
        return categorias.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public static void adicionarCategoria(Categoria categoria) {
        if (categoria.getId() == null) {
            categoria.setId(nextCategoriaId++);
        }
        categorias.add(categoria);
    }

    // Métodos para produtos
    public static List<Produto> listarProdutos() {
        return new ArrayList<>(produtos);
    }

    public static Produto buscarProdutoPorId(Long id) {
        return produtos.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public static Produto buscarProdutoPorCodigo(String codigo) {
        return produtos.stream()
                .filter(p -> p.getCodigo().equalsIgnoreCase(codigo))
                .findFirst()
                .orElse(null);
    }

    public static List<Produto> buscarProdutosPorCategoria(Long categoriaId) {
        return produtos.stream()
                .filter(p -> p.getCategoria() != null && p.getCategoria().getId().equals(categoriaId))
                .collect(Collectors.toList());
    }

    public static void adicionarProduto(Produto produto) {
        if (produto.getId() == null) {
            produto.setId(nextProdutoId++);
        }
        produtos.add(produto);
    }

    public static boolean atualizarProduto(Produto produto) {
        if (produto.getId() == null) {
            return false;
        }

        for (int i = 0; i < produtos.size(); i++) {
            if (produtos.get(i).getId().equals(produto.getId())) {
                produtos.set(i, produto);
                return true;
            }
        }
        return false;
    }

    public static boolean removerProduto(Long id) {
        return produtos.removeIf(p -> p.getId().equals(id));
    }

    // Métodos para itens de estoque
    public static List<ItemEstoque> listarItensEstoque() {
        return new ArrayList<>(itensEstoque);
    }

    public static ItemEstoque buscarItemPorId(Long id) {
        return itensEstoque.stream()
                .filter(i -> i.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public static List<ItemEstoque> buscarItensPorProduto(Long produtoId) {
        return itensEstoque.stream()
                .filter(i -> i.getProduto().getId().equals(produtoId))
                .collect(Collectors.toList());
    }

    public static List<ItemEstoque> buscarEstoqueBaixo() {
        return itensEstoque.stream()
                .filter(ItemEstoque::isEstoqueBaixo)
                .collect(Collectors.toList());
    }

    public static void adicionarItemEstoque(ItemEstoque item) {
        if (item.getId() == null) {
            item.setId(nextItemId++);
        }
        itensEstoque.add(item);
    }

    public static boolean atualizarItemEstoque(ItemEstoque item) {
        if (item.getId() == null) {
            return false;
        }

        for (int i = 0; i < itensEstoque.size(); i++) {
            if (itensEstoque.get(i).getId().equals(item.getId())) {
                itensEstoque.set(i, item);
                return true;
            }
        }
        return false;
    }

    public static boolean removerItemEstoque(Long id) {
        return itensEstoque.removeIf(i -> i.getId().equals(id));
    }

    public static boolean registrarEntrada(Long idItem, int quantidade) {
        ItemEstoque item = buscarItemPorId(idItem);
        if (item != null && quantidade > 0) {
            item.setQuantidade(item.getQuantidade() + quantidade);
            item.setDataUltimaEntrada(LocalDate.now());
            return true;
        }
        return false;
    }

    public static boolean registrarSaida(Long idItem, int quantidade) {
        ItemEstoque item = buscarItemPorId(idItem);
        if (item != null && item.getQuantidade() >= quantidade && quantidade > 0) {
            item.setQuantidade(item.getQuantidade() - quantidade);
            item.setDataUltimaSaida(LocalDate.now());
            return true;
        }
        return false;
    }

    public static boolean atualizarCategoria(Categoria categoria) {
        for (int i = 0; i < categorias.size(); i++) {
            if (categorias.get(i).getId().equals(categoria.getId())) {
                categorias.set(i, categoria);
                return true;
            }
        }
        return false;
    }

    public static boolean removerCategoria(Long id) {
        return categorias.removeIf(categoria -> categoria.getId().equals(id));
    }


    public static BigDecimal calcularValorTotalEstoque() {
        return itensEstoque.stream()
                .map(ItemEstoque::getValorTotalEstoque)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}