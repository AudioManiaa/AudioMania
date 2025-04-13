package com.audiomania.estoque.service;

import com.audiomania.estoque.model.ItemEstoque;
import com.audiomania.estoque.model.Produto;
import com.audiomania.estoque.repository.EstoqueRepository;
import com.audiomania.estoque.util.ValidadorEstoque;

import java.util.List;

public class EstoqueService {

    public List<ItemEstoque> listarTodosItensEstoque() {
        return EstoqueRepository.listarTodos();
    }

    public ItemEstoque buscarItemEstoquePorId(Long id) {
        return EstoqueRepository.buscarPorId(id);
    }

    public ItemEstoque buscarItemEstoquePorProduto(Produto produto) {
        return EstoqueRepository.buscarPorProduto(produto);
    }

    public ItemEstoque adicionarItemEstoque(ItemEstoque itemEstoque) {
        ValidadorEstoque.validarItemEstoque(itemEstoque);
        return EstoqueRepository.adicionar(itemEstoque);
    }

    public ItemEstoque atualizarItemEstoque(ItemEstoque itemEstoque) {
        ValidadorEstoque.validarItemEstoque(itemEstoque);
        return EstoqueRepository.atualizar(itemEstoque);
    }

    public void removerItemEstoque(Long id) {
        EstoqueRepository.remover(id);
    }

    public void adicionarQuantidade(Long idItemEstoque, int quantidade) {
        ItemEstoque item = EstoqueRepository.buscarPorId(idItemEstoque);
        if (item == null) {
            throw new IllegalArgumentException("Item de estoque não encontrado");
        }
        item.adicionarEstoque(quantidade);
        EstoqueRepository.atualizar(item);

        // Verificar se há necessidade de alerta de estoque
        AlertEstoque.verificarEstoque(item);
    }

    public void removerQuantidade(Long idItemEstoque, int quantidade) {
        ItemEstoque item = EstoqueRepository.buscarPorId(idItemEstoque);
        if (item == null) {
            throw new IllegalArgumentException("Item de estoque não encontrado");
        }
        item.removerEstoque(quantidade);
        EstoqueRepository.atualizar(item);

        // Verificar se há necessidade de alerta de estoque
        AlertEstoque.verificarEstoque(item);
    }

    public List<ItemEstoque> listarItensBaixoEstoque() {
        return EstoqueRepository.buscarEstoqueBaixo();
    }
}