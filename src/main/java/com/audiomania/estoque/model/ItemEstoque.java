package com.audiomania.estoque.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class ItemEstoque {
    private Long id;
    private Produto produto;
    private int quantidade;
    private int quantidadeMinima;
    private String localizacao;
    private LocalDate dataUltimaEntrada;
    private LocalDate dataUltimaSaida;

    // Construtores
    public ItemEstoque() {
    }

    public ItemEstoque(Long id, Produto produto, int quantidade, int quantidadeMinima,
                       String localizacao, LocalDate dataUltimaEntrada, LocalDate dataUltimaSaida) {
        this.id = id;
        this.produto = produto;
        this.quantidade = quantidade;
        this.quantidadeMinima = quantidadeMinima;
        this.localizacao = localizacao;
        this.dataUltimaEntrada = dataUltimaEntrada;
        this.dataUltimaSaida = dataUltimaSaida;
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

    // Métodos de negócio
    public boolean isEstoqueBaixo() {
        return quantidade < quantidadeMinima;
    }

    public String getStatusEstoque() {
        if (quantidade <= 0) {
            return "ESGOTADO";
        } else if (isEstoqueBaixo()) {
            return "BAIXO";
        } else {
            return "NORMAL";
        }
    }

    public BigDecimal getValorTotalEstoque() {
        if (produto != null && produto.getPrecoCompra() != null) {
            return produto.getPrecoCompra().multiply(new BigDecimal(quantidade));
        }
        return BigDecimal.ZERO;
    }

    public String getDataUltimaEntradaFormatada() {
        return formatarData(dataUltimaEntrada);
    }

    public String getDataUltimaSaidaFormatada() {
        return formatarData(dataUltimaSaida);
    }

    private String formatarData(LocalDate data) {
        if (data == null) {
            return "N/A";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return data.format(formatter);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemEstoque that = (ItemEstoque) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "ItemEstoque{" +
                "id=" + id +
                ", produto=" + (produto != null ? produto.getNome() : "N/A") +
                ", quantidade=" + quantidade +
                ", status=" + getStatusEstoque() +
                '}';
    }
}