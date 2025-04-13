package com.audiomania.estoque.controller;

import com.audiomania.estoque.model.Categoria;
import com.audiomania.estoque.model.Produto;
import com.audiomania.estoque.repository.ProdutoRepository;
import com.audiomania.estoque.view.CadastroProdutoView;

import java.math.BigDecimal;
import java.util.List;

public class ProdutoController {
    private final CadastroProdutoView produtoView;

    public ProdutoController() {
        this.produtoView = new CadastroProdutoView();
    }

    public void iniciar() {
        int opcao;
        do {
            opcao = produtoView.exibirMenuPrincipal();

            switch (opcao) {
                case 1:
                    listarTodosProdutos();
                    break;
                case 2:
                    cadastrarNovoProduto();
                    break;
                case 3:
                    editarProduto();
                    break;
                case 4:
                    excluirProduto();
                    break;
                case 5:
                    buscarProdutoPorNome();
                    break;
                case 0:
                    produtoView.exibirMensagem("Saindo do cadastro de produtos...");
                    break;
                default:
                    produtoView.exibirMensagem("Opção inválida!");
            }
        } while (opcao != 0);
    }

    private void listarTodosProdutos() {
        List<Produto> produtos = ProdutoRepository.listarTodos();
        produtoView.exibirListaProdutos(produtos);
    }

    private void cadastrarNovoProduto() {
        try {
            String codigo = produtoView.solicitarCodigo();
            String nome = produtoView.solicitarNome();
            String descricao = produtoView.solicitarDescricao();
            BigDecimal precoCompra = produtoView.solicitarPrecoCompra();
            BigDecimal precoVenda = produtoView.solicitarPrecoVenda();
            String fabricante = produtoView.solicitarFabricante();
            Long idCategoria = produtoView.solicitarCategoria();

            // Criando uma categoria simples para este exemplo
            Categoria categoria = new Categoria(idCategoria, "CAT"+idCategoria, "Categoria " + idCategoria, "");

            Produto novoProduto = new Produto(null, codigo, nome, descricao, precoCompra, precoVenda, fabricante, categoria);

            ProdutoRepository.adicionar(novoProduto);
            produtoView.exibirMensagem("Produto cadastrado com sucesso!");

        } catch (Exception e) {
            produtoView.exibirMensagem("Erro ao cadastrar produto: " + e.getMessage());
        }
    }

    private void editarProduto() {
        Long id = produtoView.solicitarId();
        Produto produto = ProdutoRepository.buscarPorId(id);

        if (produto == null) {
            produtoView.exibirMensagem("Produto não encontrado!");
            return;
        }

        try {
            String codigo = produtoView.solicitarCodigo();
            String nome = produtoView.solicitarNome();
            String descricao = produtoView.solicitarDescricao();
            BigDecimal precoCompra = produtoView.solicitarPrecoCompra();
            BigDecimal precoVenda = produtoView.solicitarPrecoVenda();
            String fabricante = produtoView.solicitarFabricante();
            Long idCategoria = produtoView.solicitarCategoria();

            // Mantendo a categoria atual ou criando uma nova se necessário
            Categoria categoria = produto.getCategoria();
            if (!categoria.getId().equals(idCategoria)) {
                categoria = new Categoria(idCategoria, "CAT"+idCategoria, "Categoria " + idCategoria, "");
            }

            produto.setCodigo(codigo);
            produto.setNome(nome);
            produto.setDescricao(descricao);
            produto.setPrecoCompra(precoCompra);
            produto.setPrecoVenda(precoVenda);
            produto.setFabricante(fabricante);
            produto.setCategoria(categoria);

            ProdutoRepository.atualizar(produto);
            produtoView.exibirMensagem("Produto atualizado com sucesso!");

        } catch (Exception e) {
            produtoView.exibirMensagem("Erro ao atualizar produto: " + e.getMessage());
        }
    }

    private void excluirProduto() {
        Long id = produtoView.solicitarId();

        try {
            ProdutoRepository.remover(id);
            produtoView.exibirMensagem("Produto removido com sucesso!");
        } catch (IllegalArgumentException e) {
            produtoView.exibirMensagem("Erro: " + e.getMessage());
        }
    }

    private void buscarProdutoPorNome() {
        String nome = produtoView.solicitarNomeBusca();

        List<Produto> resultados = ProdutoRepository.buscarPorNome(nome);

        if (resultados.isEmpty()) {
            produtoView.exibirMensagem("Nenhum produto encontrado com o nome: " + nome);
        } else {
            produtoView.exibirListaProdutos(resultados);
        }
    }
}