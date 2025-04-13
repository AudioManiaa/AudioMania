package com.audiomania.estoque.controller;

import com.audiomania.estoque.model.ItemEstoque;
import com.audiomania.estoque.repository.EstoqueRepository;
import com.audiomania.estoque.service.EstoqueService;
import com.audiomania.estoque.view.RelatorioEstoqueView;

import java.util.List;

public class RelatorioController {
    private final RelatorioEstoqueView relatorioView;
    private final EstoqueService estoqueService;

    public RelatorioController() {
        this.relatorioView = new RelatorioEstoqueView();
        this.estoqueService = new EstoqueService();
    }

    public void iniciar() {
        int opcao;
        do {
            opcao = relatorioView.exibirMenuRelatorios();

            switch (opcao) {
                case 1:
                    gerarRelatorioGeral();
                    break;
                case 2:
                    gerarRelatorioBaixoEstoque();
                    break;
                case 3:
                    gerarRelatorioValorEstoque();
                    break;
                case 0:
                    relatorioView.exibirMensagem("Saindo do módulo de relatórios...");
                    break;
                default:
                    relatorioView.exibirMensagem("Opção inválida!");
            }
        } while (opcao != 0);
    }

    private void gerarRelatorioGeral() {
        List<ItemEstoque> itens = estoqueService.listarTodosItensEstoque();
        relatorioView.exibirRelatorioGeral(itens);
    }

    private void gerarRelatorioBaixoEstoque() {
        List<ItemEstoque> itensBaixo = estoqueService.listarItensBaixoEstoque();
        relatorioView.exibirRelatorioBaixoEstoque(itensBaixo);
    }

    private void gerarRelatorioValorEstoque() {
        List<ItemEstoque> itens = estoqueService.listarTodosItensEstoque();

        double valorTotal = 0.0;
        for (ItemEstoque item : itens) {
            double valorItem = item.getProduto().getPrecoCompra().doubleValue() * item.getQuantidade();
            valorTotal += valorItem;}

        relatorioView.exibirRelatorioValorEstoque(itens, valorTotal);
    }
}