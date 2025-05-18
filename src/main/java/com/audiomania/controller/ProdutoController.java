package com.audiomania.controller;

import com.audiomania.entities.ProdutoEntity;
import com.audiomania.service.ProdutoService;

import java.math.BigDecimal;
import java.util.List;

public class ProdutoController {

    /**
     * Busca um produto pelo ID
     * @param id ID do produto
     * @return O objeto ProdutoEntity ou null se não encontrado
     */
    public ProdutoEntity buscarPorId(Integer id) {
        return ProdutoService.buscarPorId(id);
    }

    /**
     * Lista todos os produtos
     * @return Lista de produtos
     */
    public List<ProdutoEntity> listarProdutos() {
        return ProdutoService.listarTodos();
    }

    /**
     * Cadastra um novo produto
     * @param nome Nome do produto
     * @param descricao Descrição do produto
     * @param preco Preço do produto
     * @param estoque Quantidade em estoque
     * @param categoria Categoria do produto
     * @param marca Marca do produto
     * @return true se cadastrado com sucesso, false caso contrário
     */
    public boolean cadastrarProduto(String nome, String descricao, BigDecimal preco,
                                    Integer estoque, String categoria, String marca) {
        return ProdutoService.cadastrarProduto(nome, descricao, preco, estoque, categoria, marca);
    }

    /**
     * Atualiza um produto existente
     * @param produto Produto com os dados atualizados
     * @return true se atualizado com sucesso, false caso contrário
     */
    public boolean atualizarProduto(ProdutoEntity produto) {
        return ProdutoService.atualizarProduto(produto);
    }

    /**
     * Exclui um produto pelo ID
     * @param id ID do produto
     * @return true se excluído com sucesso, false caso contrário
     */
    public boolean excluirProduto(Integer id) {
        return ProdutoService.excluirProduto(id);
    }
}