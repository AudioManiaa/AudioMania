package com.audiomania.estoque.controller;

import com.audiomania.estoque.model.ItemEstoque;
import com.audiomania.estoque.model.Produto;
import com.audiomania.estoque.service.EstoqueService;
import com.audiomania.estoque.view.ConsultaEstoqueView;
import com.audiomania.estoque.view.EstoqueView;

import java.util.List;

public class EstoqueController {
    private final EstoqueService estoqueService;
    private final EstoqueView estoqueView;

    public EstoqueController() {
        this.estoqueService = new EstoqueService();
        this.estoqueView = new EstoqueView();
    }

    public void iniciar() {
        int opcao;
        do {
            opcao = estoqueView.exibirMenuPrincipal();

            switch (opcao) {
                case 1:
                    listarTodosItens();
                    break;
                case 2:
                    consultarItem();
                    break;
                case 3:
                    adicionarEntrada();
                    break;
                case 4:
                    registrarSaida();
                    break;
                case 5:
                    verificarItensBaixoEstoque();
                    break;
                case 0:
                    estoqueView.exibirMensagem("Saindo do sistema de estoque...");
                    break;
                default:
                    estoqueView.exibirMensagem("Opção inválida!");
            }
        } while (opcao != 0);
    }

    private void listarTodosItens() {
        List<ItemEstoque> itens = estoqueService.listarTodosItensEstoque();
        estoqueView.exibirListaItensEstoque(itens);
    }

    private void consultarItem() {
        Long idProduto = estoqueView.solicitarIdProduto();
        Produto produto = new Produto();
        produto.setId(idProduto);

        ItemEstoque item = estoqueService.buscarItemEstoquePorProduto(produto);

        if (item != null) {
            estoqueView.exibirDetalhesItemEstoque(item);
        } else {
            estoqueView.exibirMensagem("Item não encontrado no estoque!");
        }
    }

    private void adicionarEntrada() {
        Long idItemEstoque = estoqueView.solicitarIdItemEstoque();
        int quantidade = estoqueView.solicitarQuantidade("entrada");

        try {
            estoqueService.adicionarQuantidade(idItemEstoque, quantidade);
            estoqueView.exibirMensagem("Entrada registrada com sucesso!");

            // Exibir detalhes atualizados
            ItemEstoque itemAtualizado = estoqueService.buscarItemEstoquePorId(idItemEstoque);
            estoqueView.exibirDetalhesItemEstoque(itemAtualizado);

        } catch (IllegalArgumentException e) {
            estoqueView.exibirMensagem("Erro: " + e.getMessage());
        }
    }

    private void registrarSaida() {
        Long idItemEstoque = estoqueView.solicitarIdItemEstoque();
        int quantidade = estoqueView.solicitarQuantidade("saída");

        try {
            estoqueService.removerQuantidade(idItemEstoque, quantidade);
            estoqueView.exibirMensagem("Saída registrada com sucesso!");

            // Exibir detalhes atualizados
            ItemEstoque itemAtualizado = estoqueService.buscarItemEstoquePorId(idItemEstoque);
            estoqueView.exibirDetalhesItemEstoque(itemAtualizado);

        } catch (IllegalArgumentException e) {
            estoqueView.exibirMensagem("Erro: " + e.getMessage());
        }
    }

    private void verificarItensBaixoEstoque() {
        List<ItemEstoque> itensBaixo = estoqueService.listarItensBaixoEstoque();

        if (itensBaixo.isEmpty()) {
            estoqueView.exibirMensagem("Não há itens com estoque baixo.");
        } else {
            estoqueView.exibirItensBaixoEstoque(itensBaixo);
        }
    }

    public void abrirConsultaEstoque() {
        ConsultaEstoqueView consultaView = new ConsultaEstoqueView();
        consultaView.exibir();
    }
}