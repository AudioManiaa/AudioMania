package com.audiomania.estoque.controller;

import com.audiomania.estoque.model.Categoria;
import com.audiomania.estoque.model.ItemEstoque;
import com.audiomania.estoque.model.Produto;
import com.audiomania.estoque.repository.EstoqueRepository;
import com.audiomania.estoque.view.EstoqueView;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class EstoqueController {
    private final EstoqueView view;

    public EstoqueController() {
        this.view = new EstoqueView();
    }

    public void iniciarSistema() {
        System.out.println("Inicializando sistema de estoque da AudioMania Som Automotivo...");

        boolean executando = true;

        while (executando) {
            int opcao = view.exibirMenuPrincipal();

            switch (opcao) {
                case 0:
                    executando = false;
                    System.out.println("\nSistema finalizado. Obrigado por utilizar o Sistema de Estoque AudioMania!");
                    break;

                case 1:
                    listarTodosItens();
                    break;

                case 2:
                    menuConsulta();
                    break;

                case 3:
                    registrarEntrada();
                    break;

                case 4:
                    registrarSaida();
                    break;

                case 5:
                    menuRelatorios();
                    break;

                default:
                    System.out.println("\nOpção inválida! Por favor, tente novamente.");
                    break;
            }
        }
    }

    public void listarTodosItens() {
        List<ItemEstoque> itens = EstoqueRepository.listarItensEstoque();
        view.exibirListaItensEstoque(itens);
    }

    public void menuConsulta() {
        boolean voltarMenu = false;

        while (!voltarMenu) {
            int opcao = view.exibirMenuConsulta();

            switch (opcao) {
                case 0:
                    voltarMenu = true;
                    break;

                case 1:
                    consultarItemPorId();
                    break;

                case 2:
                    consultarPorCodigoProduto();
                    break;

                case 3:
                    consultarEstoqueBaixo();
                    break;

                default:
                    System.out.println("\nOpção inválida! Por favor, tente novamente.");
                    break;
            }
        }
    }

    public void consultarItemPorId() {
        Long id = view.solicitarId("item");

        if (id != null) {
            ItemEstoque item = EstoqueRepository.buscarItemPorId(id);
            view.exibirDetalhesItemEstoque(item);
        } else {
            System.out.println("\nID inválido!");
        }
    }

    public void consultarPorCodigoProduto() {
        String codigo = view.solicitarCodigoProduto();

        if (codigo != null && !codigo.isEmpty()) {
            Produto produto = EstoqueRepository.buscarProdutoPorCodigo(codigo);

            if (produto != null) {
                List<ItemEstoque> itens = EstoqueRepository.buscarItensPorProduto(produto.getId());

                if (itens != null && !itens.isEmpty()) {
                    System.out.println("\nProduto encontrado em estoque:");
                    view.exibirListaItensEstoque(itens);

                    // Mostrar detalhes do primeiro item encontrado
                    if (!itens.isEmpty()) {
                        view.exibirDetalhesItemEstoque(itens.get(0));
                    }
                } else {
                    System.out.println("\nO produto existe, mas não está em estoque atualmente.");
                }
            } else {
                System.out.println("\nProduto não encontrado com o código informado.");
            }
        } else {
            System.out.println("\nCódigo inválido!");
        }
    }

    public void consultarEstoqueBaixo() {
        List<ItemEstoque> itensBaixo = EstoqueRepository.buscarEstoqueBaixo();

        if (itensBaixo != null && !itensBaixo.isEmpty()) {
            System.out.println("\nItens com estoque abaixo do mínimo:");
            view.exibirListaItensEstoque(itensBaixo);
        } else {
            System.out.println("\nNão há itens com estoque abaixo do mínimo.");
        }
    }

    public void registrarEntrada() {
        Long id = view.solicitarId("item para entrada");

        if (id == null) {
            System.out.println("\nID inválido!");
            return;
        }

        ItemEstoque item = EstoqueRepository.buscarItemPorId(id);

        if (item == null) {
            System.out.println("\nItem não encontrado no estoque!");
            return;
        }

        view.exibirDetalhesItemEstoque(item);
        int quantidade = view.solicitarQuantidade("entrada");

        if (quantidade <= 0) {
            System.out.println("\nQuantidade inválida! A quantidade deve ser um número positivo.");
            return;
        }

        boolean sucesso = EstoqueRepository.registrarEntrada(id, quantidade);

        if (sucesso) {
            System.out.println("\nEntrada registrada com sucesso!");
            view.exibirDetalhesItemEstoque(EstoqueRepository.buscarItemPorId(id));
        } else {
            System.out.println("\nFalha ao registrar entrada!");
        }
    }

    public void registrarSaida() {
        Long id = view.solicitarId("item para saída");

        if (id == null) {
            System.out.println("\nID inválido!");
            return;
        }

        ItemEstoque item = EstoqueRepository.buscarItemPorId(id);

        if (item == null) {
            System.out.println("\nItem não encontrado no estoque!");
            return;
        }

        view.exibirDetalhesItemEstoque(item);
        int quantidade = view.solicitarQuantidade("saída");

        if (quantidade <= 0) {
            System.out.println("\nQuantidade inválida! A quantidade deve ser um número positivo.");
            return;
        }

        if (quantidade > item.getQuantidade()) {
            System.out.println("\nQuantidade insuficiente em estoque! Disponível: " + item.getQuantidade());
            return;
        }

        boolean sucesso = EstoqueRepository.registrarSaida(id, quantidade);

        if (sucesso) {
            System.out.println("\nSaída registrada com sucesso!");
            view.exibirDetalhesItemEstoque(EstoqueRepository.buscarItemPorId(id));
        } else {
            System.out.println("\nFalha ao registrar saída!");
        }
    }



    public void menuRelatorios() {
        boolean voltarMenu = false;

        while (!voltarMenu) {
            int opcao = view.exibirMenuRelatorios();

            switch (opcao) {
                case 0:
                    voltarMenu = true;
                    break;

                case 1:
                    gerarRelatorioGeral();
                    break;

                case 2:
                    gerarRelatorioEstoqueBaixo();
                    break;

                case 3:
                    gerarRelatorioValorEstoque();
                    break;

                default:
                    System.out.println("\nOpção inválida! Por favor, tente novamente.");
                    break;
            }
        }
    }

    public void gerarRelatorioGeral() {
        List<ItemEstoque> itens = EstoqueRepository.listarItensEstoque();
        view.exibirRelatorioGeral(itens);
    }

    public void gerarRelatorioEstoqueBaixo() {
        List<ItemEstoque> itensBaixo = EstoqueRepository.buscarEstoqueBaixo();
        view.exibirRelatorioBaixoEstoque(itensBaixo);
    }

    public void gerarRelatorioValorEstoque() {
        System.out.println("\n===== RELATÓRIO DE VALOR DE ESTOQUE - AUDIOMANIA SOM AUTOMOTIVO =====");

        // Calcular valor total do estoque
        System.out.println("Valor total em estoque: " +
                NumberFormat.getCurrencyInstance(new Locale("pt", "BR"))
                        .format(EstoqueRepository.calcularValorTotalEstoque()));

        // Estatísticas por categoria
        System.out.println("\nValor por categoria:");
        for (Categoria categoria : EstoqueRepository.listarCategorias()) {
            BigDecimal valorCategoria = BigDecimal.ZERO;
            int quantidadeCategoria = 0;

            List<Produto> produtosCategoria = EstoqueRepository.buscarProdutosPorCategoria(categoria.getId());

            for (Produto produto : produtosCategoria) {
                for (ItemEstoque item : EstoqueRepository.buscarItensPorProduto(produto.getId())) {
                    valorCategoria = valorCategoria.add(item.getValorTotalEstoque());
                    quantidadeCategoria += item.getQuantidade();
                }
            }

            System.out.printf("%-25s | %8d itens | %s%n",
                    categoria.getNome(),
                    quantidadeCategoria,
                    NumberFormat.getCurrencyInstance(new Locale("pt", "BR")).format(valorCategoria));
        }
    }
}