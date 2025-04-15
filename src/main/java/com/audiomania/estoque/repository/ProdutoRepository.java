package com.audiomania.estoque.repository;

import com.audiomania.estoque.model.Categoria;
import com.audiomania.estoque.model.Produto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProdutoRepository {
    private Map<Long, Produto> produtos;
    private long proximoId;

    public ProdutoRepository() {
        this.produtos = new HashMap<>();
        this.proximoId = 1L;
    }

    // Salvar um produto no repositório
    public Produto salvar(Produto produto) {
        if (produto.getId() == null) {
            produto.setId(proximoId++);
        }
        produtos.put(produto.getId(), produto);
        return produto;
    }

    // Criar um novo produto
    public Produto criarProduto(String nome, String descricao, BigDecimal preco,
                                int quantidadeEstoque, String categoria, String marca) {

        // Cria uma categoria simples com o nome fornecido
        Categoria cat = new Categoria(proximoId, categoria, categoria, "");

        // Cria o produto (usando preço como preço de compra e venda para simplificar)
        Produto novoProduto = new Produto(null, "PROD" + proximoId, nome, descricao,
                preco, preco, marca, cat);

        return salvar(novoProduto);
    }

    // Buscar todos os produtos
    public List<Produto> buscarTodos() {
        return new ArrayList<>(produtos.values());
    }

    // Buscar um produto pelo ID
    public Produto buscarPorId(Long id) {
        return produtos.get(id);
    }
}

