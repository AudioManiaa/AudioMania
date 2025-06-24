package com.audiomania.controller;

import com.audiomania.model.entities.ProdutoEntity;
import com.audiomania.model.service.ProdutoService;

import javax.swing.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        try {
            return ProdutoService.listarTodos();
        } catch (Exception e) {
            System.err.println("Erro ao listar produtos: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Busca produtos por termo e categoria
     * @param termo Termo de busca (nome ou descrição)
     * @param categoria Categoria selecionada
     * @return Lista de produtos filtrados
     */
    public List<ProdutoEntity> buscarProdutos(String termo, String categoria) {
        try {
            List<ProdutoEntity> todosProdutos = ProdutoService.listarTodos();

            return todosProdutos.stream()
                    .filter(produto -> filtrarPorTermo(produto, termo))
                    .filter(produto -> filtrarPorCategoria(produto, categoria))
                    .collect(Collectors.toList());

        } catch (Exception e) {
            System.err.println("Erro ao buscar produtos: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Lista todas as categorias únicas para o filtro
     * @return Lista de categorias
     */
    public List<String> listarCategorias() {
        try {
            List<ProdutoEntity> produtos = ProdutoService.listarTodos();
            List<String> categorias = new ArrayList<>();
            categorias.add("Todas");

            produtos.stream()
                    .map(ProdutoEntity::getCategoria)
                    .filter(categoria -> categoria != null && !categoria.trim().isEmpty())
                    .distinct()
                    .sorted()
                    .forEach(categorias::add);

            return categorias;
        } catch (Exception e) {
            System.err.println("Erro ao listar categorias: " + e.getMessage());
            List<String> categorias = new ArrayList<>();
            categorias.add("Todas");
            return categorias;
        }
    }

    /**
     * Lista categorias para o cadastro (com item vazio)
     * @return Lista de categorias para cadastro
     */
    public List<String> listarCategoriasParaCadastro() {
        try {
            List<ProdutoEntity> produtos = ProdutoService.listarTodos();
            List<String> categorias = new ArrayList<>();
            categorias.add(""); // Item vazio

            produtos.stream()
                    .map(ProdutoEntity::getCategoria)
                    .filter(categoria -> categoria != null && !categoria.trim().isEmpty())
                    .distinct()
                    .sorted()
                    .forEach(categorias::add);

            return categorias;
        } catch (Exception e) {
            System.err.println("Erro ao listar categorias para cadastro: " + e.getMessage());
            List<String> categorias = new ArrayList<>();
            categorias.add("");
            return categorias;
        }
    }

    /**
     * Formata produto para exibição na tabela
     * @param produto Produto a ser formatado
     * @return Array de objetos para a tabela
     */
    public Object[] formatarProdutoParaTabela(ProdutoEntity produto) {
        return new Object[]{
                produto.getId(),
                produto.getNome(),
                produto.getDescricao(),
                produto.getCategoria(),
                produto.getMarca(),
                String.format("R$ %.2f", produto.getPreco()),
                produto.getQuantidadeEstoque()
        };
    }

    /**
     * Adiciona nova categoria ao combo box
     * @param novaCategoria Nome da nova categoria
     * @param categoriaCombo Combo box de categorias
     * @return Status da operação ("ADICIONADA", "EXISTE")
     */
    public String adicionarNovaCategoria(String novaCategoria, JComboBox<String> categoriaCombo) {
        // Verificar se a categoria já existe
        for (int i = 0; i < categoriaCombo.getItemCount(); i++) {
            String item = categoriaCombo.getItemAt(i);
            if (item != null && novaCategoria.equalsIgnoreCase(item)) {
                return "EXISTE";
            }
        }

        // Adicionar nova categoria
        categoriaCombo.addItem(novaCategoria);
        return "ADICIONADA";
    }

    /**
     * Cadastra um novo produto com validações
     * @param nome Nome do produto
     * @param descricao Descrição do produto
     * @param categoria Categoria do produto
     * @param marca Marca do produto
     * @param precoStr Preço como string
     * @param estoqueStr Estoque como string
     * @return Status da operação
     */
    public String cadastrarProduto(String nome, String descricao, String categoria,
                                   String marca, String precoStr, String estoqueStr) {

        // Validações de campos obrigatórios
        if (nome == null || nome.trim().isEmpty()) {
            return "NOME_VAZIO";
        }

        if (precoStr == null || precoStr.trim().isEmpty()) {
            return "PRECO_VAZIO";
        }

        if (estoqueStr == null || estoqueStr.trim().isEmpty()) {
            return "ESTOQUE_VAZIO";
        }

        // Validação e conversão do preço
        BigDecimal preco;
        try {
            preco = new BigDecimal(precoStr.trim().replace(",", "."));
            if (preco.compareTo(BigDecimal.ZERO) < 0) {
                return "PRECO_INVALIDO";
            }
        } catch (NumberFormatException e) {
            return "PRECO_INVALIDO";
        }

        // Validação e conversão do estoque
        Integer estoque;
        try {
            estoque = Integer.parseInt(estoqueStr.trim());
            if (estoque < 0) {
                return "ESTOQUE_INVALIDO";
            }
        } catch (NumberFormatException e) {
            return "ESTOQUE_INVALIDO";
        }

        // Preparar dados para cadastro
        String nomeProcessado = nome.trim();
        String descricaoProcessada = (descricao != null && !descricao.trim().isEmpty()) ? descricao.trim() : null;
        String categoriaProcessada = (categoria != null && !categoria.trim().isEmpty()) ? categoria.trim() : null;
        String marcaProcessada = (marca != null && !marca.trim().isEmpty()) ? marca.trim() : null;

        // Tentar cadastrar
        try {
            boolean sucesso = ProdutoService.cadastrarProduto(
                    nomeProcessado, descricaoProcessada, preco, estoque, categoriaProcessada, marcaProcessada
            );

            return sucesso ? "SUCESSO" : "ERRO_BANCO";
        } catch (Exception e) {
            return "ERRO: " + e.getMessage();
        }
    }

    /**
     * Atualiza um produto existente com validações
     * @param produto Produto a ser atualizado
     * @param nome Novo nome
     * @param descricao Nova descrição
     * @param categoria Nova categoria
     * @param marca Nova marca
     * @param precoStr Novo preço como string
     * @param estoqueStr Novo estoque como string
     * @return Status da operação
     */
    public String atualizarProduto(ProdutoEntity produto, String nome, String descricao,
                                   String categoria, String marca, String precoStr, String estoqueStr) {

        // Validações de campos obrigatórios
        if (nome == null || nome.trim().isEmpty()) {
            return "NOME_VAZIO";
        }

        if (precoStr == null || precoStr.trim().isEmpty()) {
            return "PRECO_VAZIO";
        }

        if (estoqueStr == null || estoqueStr.trim().isEmpty()) {
            return "ESTOQUE_VAZIO";
        }

        // Validação e conversão do preço
        BigDecimal preco;
        try {
            preco = new BigDecimal(precoStr.trim().replace(",", "."));
            if (preco.compareTo(BigDecimal.ZERO) < 0) {
                return "PRECO_INVALIDO";
            }
        } catch (NumberFormatException e) {
            return "PRECO_INVALIDO";
        }

        // Validação e conversão do estoque
        Integer estoque;
        try {
            estoque = Integer.parseInt(estoqueStr.trim());
            if (estoque < 0) {
                return "ESTOQUE_INVALIDO";
            }
        } catch (NumberFormatException e) {
            return "ESTOQUE_INVALIDO";
        }

        // Atualizar dados do produto
        produto.setNome(nome.trim());
        produto.setDescricao((descricao != null && !descricao.trim().isEmpty()) ? descricao.trim() : null);
        produto.setCategoria((categoria != null && !categoria.trim().isEmpty()) ? categoria.trim() : null);
        produto.setMarca((marca != null && !marca.trim().isEmpty()) ? marca.trim() : null);
        produto.setPreco(preco);
        produto.setQuantidadeEstoque(estoque);

        // Tentar atualizar
        try {
            boolean sucesso = ProdutoService.atualizarProduto(produto);
            return sucesso ? "SUCESSO" : "ERRO_BANCO";
        } catch (Exception e) {
            return "ERRO: " + e.getMessage();
        }
    }

    /**
     * Preenche os campos da tela de edição com dados do produto
     * @param produto Produto a ser editado
     * @param nomeField Campo nome
     * @param descricaoArea Campo descrição
     * @param categoriaCombo Combo categoria
     * @param marcaField Campo marca
     * @param precoField Campo preço
     * @param estoqueField Campo estoque
     */
    public void preencherCamposEdicao(ProdutoEntity produto, JTextField nomeField,
                                      JTextArea descricaoArea, JComboBox<String> categoriaCombo,
                                      JTextField marcaField, JTextField precoField, JTextField estoqueField) {

        nomeField.setText(produto.getNome());
        descricaoArea.setText(produto.getDescricao() != null ? produto.getDescricao() : "");
        marcaField.setText(produto.getMarca() != null ? produto.getMarca() : "");
        precoField.setText(produto.getPreco().toString());
        estoqueField.setText(produto.getQuantidadeEstoque().toString());

        // Definir categoria no combo
        if (produto.getCategoria() != null && !produto.getCategoria().trim().isEmpty()) {
            categoriaCombo.setSelectedItem(produto.getCategoria());
        } else {
            categoriaCombo.setSelectedIndex(0); // Item vazio
        }
    }

    /**
     * Exclui um produto pelo ID
     * @param id ID do produto
     * @return true se excluído com sucesso, false caso contrário
     */
    public boolean excluirProduto(Integer id) {
        try {
            return ProdutoService.excluirProduto(id);
        } catch (Exception e) {
            System.err.println("Erro ao excluir produto: " + e.getMessage());
            return false;
        }
    }

    // Métodos auxiliares privados para filtros

    /**
     * Filtra produto por termo de busca
     * @param produto Produto a ser filtrado
     * @param termo Termo de busca
     * @return true se o produto corresponde ao termo
     */
    private boolean filtrarPorTermo(ProdutoEntity produto, String termo) {
        if (termo == null || termo.trim().isEmpty()) {
            return true;
        }

        String termoLower = termo.toLowerCase();
        String nome = produto.getNome() != null ? produto.getNome().toLowerCase() : "";
        String descricao = produto.getDescricao() != null ? produto.getDescricao().toLowerCase() : "";

        return nome.contains(termoLower) || descricao.contains(termoLower);
    }

    /**
     * Filtra produto por categoria
     * @param produto Produto a ser filtrado
     * @param categoria Categoria selecionada
     * @return true se o produto corresponde à categoria
     */
    private boolean filtrarPorCategoria(ProdutoEntity produto, String categoria) {
        if (categoria == null || categoria.equals("Todas")) {
            return true;
        }

        return produto.getCategoria() != null && produto.getCategoria().equals(categoria);
    }
}