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

    public static BigDecimal calcularValorTotalEstoque() {
        return itensEstoque.stream()
                .map(ItemEstoque::getValorTotalEstoque)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // Método para carregar dados de exemplo
    public static void carregarDadosExemplo() {
        // Criar categorias
        Categoria c1 = new Categoria(null, "AUT", "Auto-Falantes", "Alto falantes para sons automotivos");
        Categoria c2 = new Categoria(null, "MOD", "Módulos Amplificadores", "Módulos de potência para som automotivo");
        Categoria c3 = new Categoria(null, "SUB", "Subwoofers", "Subwoofers para sons automotivos");
        Categoria c4 = new Categoria(null, "CAB", "Cabos e Acessórios", "Cabos, conectores e acessórios para instalação");
        Categoria c5 = new Categoria(null, "MUL", "Multimídia", "Painéis multimídia, DVD, receptores");

        adicionarCategoria(c1);
        adicionarCategoria(c2);
        adicionarCategoria(c3);
        adicionarCategoria(c4);
        adicionarCategoria(c5);

        // Criar produtos
        Produto p1 = new Produto(null, "AF001", "Auto-Falante 6\" 120W", "Par de auto-falantes de 6 polegadas, 120W RMS",
                new BigDecimal("80.00"), new BigDecimal("189.90"), "JBL", c1);
        Produto p2 = new Produto(null, "MOD001", "Módulo Amplificador 800W 4 Canais", "Módulo amplificador 800W RMS, 4 canais",
                new BigDecimal("350.00"), new BigDecimal("699.90"), "Taramps", c2);
        Produto p3 = new Produto(null, "SUB001", "Subwoofer 12\" 500W", "Subwoofer de 12 polegadas, 500W RMS, bobina dupla",
                new BigDecimal("250.00"), new BigDecimal("499.90"), "Selenium", c3);
        Produto p4 = new Produto(null, "CAB001", "Kit Cabo RCA 5M", "Kit de cabos RCA blindados 5 metros",
                new BigDecimal("25.00"), new BigDecimal("59.90"), "Tech One", c4);
        Produto p5 = new Produto(null, "MUL001", "Central Multimídia 7\" Android", "Central multimídia com tela 7 polegadas, Android Auto, Bluetooth",
                new BigDecimal("550.00"), new BigDecimal("1299.90"), "Pioneer", c5);

        adicionarProduto(p1);
        adicionarProduto(p2);
        adicionarProduto(p3);
        adicionarProduto(p4);
        adicionarProduto(p5);

        // Criar itens de estoque
        ItemEstoque i1 = new ItemEstoque(null, p1, 15, 5, "Prateleira A1", LocalDate.now().minusDays(30), LocalDate.now().minusDays(2));
        ItemEstoque i2 = new ItemEstoque(null, p2, 8, 10, "Prateleira B2", LocalDate.now().minusDays(15), LocalDate.now().minusDays(5));
        ItemEstoque i3 = new ItemEstoque(null, p3, 5, 3, "Prateleira C3", LocalDate.now().minusDays(45), null);
        ItemEstoque i4 = new ItemEstoque(null, p4, 50, 20, "Gaveta D1", LocalDate.now().minusDays(60), LocalDate.now().minusDays(1));
        ItemEstoque i5 = new ItemEstoque(null, p5, 2, 3, "Estante E1", LocalDate.now().minusDays(90), LocalDate.now().minusDays(10));

        adicionarItemEstoque(i1);
        adicionarItemEstoque(i2);
        adicionarItemEstoque(i3);
        adicionarItemEstoque(i4);
        adicionarItemEstoque(i5);
    }
}