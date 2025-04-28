package com.audiomania.model;

import java.math.BigDecimal;

// Classe de modelo para representar um Produto
public class Produto {
    private int id_Produto;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private int quantidade_Estoque;
    private String categoria;
    private String marca;

    // Construtor sem ID (para novos produtos)
    public Produto(String nome, String descricao, BigDecimal preco, int quantidade_Estoque,
                   String categoria, String marca) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.quantidade_Estoque = quantidade_Estoque;
        this.categoria = categoria;
        this.marca = marca;
    }

    // Construtor com ID (para produtos existentes)
    public Produto(int id_Produto, String nome, String descricao, BigDecimal preco,
                   int quantidade_Estoque, String categoria, String marca) {
        this.id_Produto = id_Produto;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.quantidade_Estoque = quantidade_Estoque;
        this.categoria = categoria;
        this.marca = marca;
    }

    // Getters e Setters
    public int getId_Produto() { return id_Produto; }
    public void setId_Produto(int id_Produto) { this.id_Produto = id_Produto; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public BigDecimal getPreco() { return preco; }
    public void setPreco(BigDecimal preco) { this.preco = preco; }
    public int getQuantidade_Estoque() { return quantidade_Estoque; }
    public void setQuantidade_Estoque(int quantidade_Estoque) { this.quantidade_Estoque = quantidade_Estoque; }
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    @Override
    public String toString() {
        return "ID: " + id_Produto +
                ", Nome: " + nome +
                ", Descrição: " + descricao +
                ", Preço: " + preco +
                ", Estoque: " + quantidade_Estoque +
                ", Categoria: " + categoria +
                ", Marca: " + marca;
    }
}