package com.audiomania.estoque.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Produto {
    private Long id;
    private String codigo;
    private String nome;
    private String descricao;
    private BigDecimal precoCompra;
    private BigDecimal precoVenda;
    private String fabricante;
    private Categoria categoria;

    // Construtores
    public Produto() {
    }

    public Produto(Long id, String codigo, String nome, String descricao,
                   BigDecimal precoCompra, BigDecimal precoVenda,
                   String fabricante, Categoria categoria) {
        this.id = id;
        this.codigo = codigo;
        this.nome = nome;
        this.descricao = descricao;
        this.precoCompra = precoCompra;
        this.precoVenda = precoVenda;
        this.fabricante = fabricante;
        this.categoria = categoria;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getPrecoCompra() {
        return precoCompra;
    }

    public void setPrecoCompra(BigDecimal precoCompra) {
        this.precoCompra = precoCompra;
    }

    public BigDecimal getPrecoVenda() {
        return precoVenda;
    }

    public void setPrecoVenda(BigDecimal precoVenda) {
        this.precoVenda = precoVenda;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    // Cálculo de margem de lucro
    public BigDecimal getMargemLucro() {
        if (precoCompra == null || precoCompra.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        return precoVenda.subtract(precoCompra).divide(precoCompra, 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Produto produto = (Produto) o;
        return Objects.equals(id, produto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Produto{" +
                "id=" + id +
                ", codigo='" + codigo + '\'' +
                ", nome='" + nome + '\'' +
                ", precoVenda=" + precoVenda +
                ", categoria=" + (categoria != null ? categoria.getNome() : "N/A") +
                '}';
    }
}