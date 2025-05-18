package com.audiomania.service;

import com.audiomania.entities.ProdutoEntity;
import com.audiomania.repository.ProdutoRepository;

import java.math.BigDecimal;
import java.util.List;

public class ProdutoService {

    private static ProdutoRepository repository = new ProdutoRepository();

    public static ProdutoEntity buscarPorId(Integer id) {
        return repository.buscarPorId(id);
    }

    public static List<ProdutoEntity> listarTodos() {
        return repository.listarTodos();
    }

    public static boolean cadastrarProduto(String nome, String descricao, BigDecimal preco,
                                           Integer estoque, String categoria, String marca) {
        ProdutoEntity produto = new ProdutoEntity();
        produto.setNome(nome);
        produto.setDescricao(descricao);
        produto.setPreco(preco);
        produto.setQuantidadeEstoque(estoque);
        produto.setCategoria(categoria);
        produto.setMarca(marca);

        return repository.salvar(produto);
    }

    public static boolean atualizarProduto(ProdutoEntity produto) {
        return repository.atualizar(produto);
    }

    public static boolean excluirProduto(Integer id) {
        return repository.excluir(id);
    }

    public static void fecharRecursos() {
        repository.fechar();
    }
}