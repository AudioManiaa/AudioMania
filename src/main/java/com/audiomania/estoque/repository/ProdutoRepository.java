package com.audiomania.estoque.repository;

import com.audiomania.estoque.model.Produto;
import com.audiomania.estoque.model.Categoria;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProdutoRepository {
    private static final Map<Long, Produto> produtos = new HashMap<>();
    private static Long sequence = 1L;

    // Método para inicializar alguns produtos de exemplo
    static {
        Categoria instrumentos = new Categoria(1L, "INST", "Instrumentos Musicais", "Instrumentos de diversos tipos");
        Categoria acessorios = new Categoria(2L, "ACES", "Acessórios", "Acessórios para instrumentos");
        Categoria som = new Categoria(3L, "SOM", "Equipamentos de Som", "Equipamentos de áudio e som");

        adicionar(new Produto(null, "G001", "Guitarra Stratocaster", "Guitarra elétrica modelo Stratocaster",
                new BigDecimal("1200.00"), new BigDecimal("2499.99"), "Fender", instrumentos));

        adicionar(new Produto(null, "V001", "Violão Acústico", "Violão acústico de 6 cordas",
                new BigDecimal("300.00"), new BigDecimal("599.99"), "Takamine", instrumentos));

        adicionar(new Produto(null, "P001", "Palheta Pack com 10", "Conjunto de palhetas médias",
                new BigDecimal("5.00"), new BigDecimal("19.99"), "Dunlop", acessorios));

        adicionar(new Produto(null, "A001", "Amplificador 100W", "Amplificador para guitarra 100W",
                new BigDecimal("500.00"), new BigDecimal("999.99"), "Marshall", som));
    }

    public static List<Produto> listarTodos() {
        return new ArrayList<>(produtos.values());
    }

    public static Produto buscarPorId(Long id) {
        return produtos.get(id);
    }

    public static List<Produto> buscarPorCategoria(Categoria categoria) {
        List<Produto> resultado = new ArrayList<>();
        for (Produto produto : produtos.values()) {
            if (produto.getCategoria().getId().equals(categoria.getId())) {
                resultado.add(produto);
            }
        }
        return resultado;
    }

    public static Produto adicionar(Produto produto) {
        if (produto.getId() == null) {
            produto.setId(sequence++);
        }
        produtos.put(produto.getId(), produto);
        return produto;
    }

    public static Produto atualizar(Produto produto) {
        if (produto.getId() == null || !produtos.containsKey(produto.getId())) {
            throw new IllegalArgumentException("Produto não encontrado para atualização");
        }
        produtos.put(produto.getId(), produto);
        return produto;
    }

    public static void remover(Long id) {
        if (!produtos.containsKey(id)) {
            throw new IllegalArgumentException("Produto não encontrado para remoção");
        }
        produtos.remove(id);
    }

    public static List<Produto> buscarPorNome(String nome) {
        List<Produto> resultado = new ArrayList<>();
        for (Produto produto : produtos.values()) {
            if (produto.getNome().toLowerCase().contains(nome.toLowerCase())) {
                resultado.add(produto);
            }
        }
        return resultado;
    }
}