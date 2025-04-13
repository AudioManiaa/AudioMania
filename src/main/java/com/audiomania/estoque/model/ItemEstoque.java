package com.audiomania.estoque.model;

import java.time.LocalDate;

public class ItemEstoque {
    private Long id;
    private Produto produto;
    private int quantidade;
    private int quantidadeMinima;
    private String localizacao;
    private LocalDate dataUltimaEntrada;
    private LocalDate dataUltimaSaida;

    public ItemEstoque() {
    }

    public ItemEstoque(Long id, Produto produto, int quantidade,
                       int quantidadeMinima, String localizacao) {
        this.id = id;
        this.produto = produto;
        this.quantidade = quantidade;
        this.quantidadeMinima = quantidadeMinima;
        this.localizacao = localizacao;
        this.dataUltimaEntrada = LocalDate.now();
    }

    public boolean isEstoqueBaixo() {
        return quantidade <= quantidadeMinima;
    }

    public void adicionarEstoque(int quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("A quantidade deve ser maior que zero");
        }
        this.quantidade += quantidade;
        this.dataUltimaEntrada = LocalDate.now();
    }

    public void removerEstoque(int quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("A quantidade deve ser maior que zero");
        }
        if (this.quantidade < quantidade) {
            throw new IllegalArgumentException("Quantidade insuficiente em estoque");
        }
        this.quantidade -= quantidade;
        this.dataUltimaSaida = LocalDate.now();
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public int getQuantidadeMinima() {
        return quantidadeMinima;
    }

    public void setQuantidadeMinima(int quantidadeMinima) {
        this.quantidadeMinima = quantidadeMinima;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public LocalDate getDataUltimaEntrada() {
        return dataUltimaEntrada;
    }

    public void setDataUltimaEntrada(LocalDate dataUltimaEntrada) {
        this.dataUltimaEntrada = dataUltimaEntrada;
    }

    public LocalDate getDataUltimaSaida() {
        return dataUltimaSaida;
    }

    public void setDataUltimaSaida(LocalDate dataUltimaSaida) {
        this.dataUltimaSaida = dataUltimaSaida;
    }

    @Override
    public String toString() {
        return "ItemEstoque{" +
                "produto=" + produto.getNome() +
                ", quantidade=" + quantidade +
                ", localizacao='" + localizacao + '\'' +
                '}';
    }
}