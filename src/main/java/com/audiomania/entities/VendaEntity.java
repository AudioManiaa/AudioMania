package com.audiomania.entities;

import java.math.BigDecimal;
import java.time.LocalDate;
import jakarta.persistence.*;

@Entity
@Table(name = "VENDA")
public class VendaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_Venda")
    private Integer id;

    @Column(name = "Data")
    private LocalDate data;

    @Column(name = "Valor_Total")
    private BigDecimal valorTotal;

    @Column(name = "Forma_Pagamento")
    private String formaPagamento;

    @Column(name = "Desconto")
    private BigDecimal desconto;

    // Relacionamento Many-to-One com Cliente
    @ManyToOne
    @JoinColumn(name = "Id_Cliente", nullable = false)
    private ClienteEntity cliente;

    // Relacionamento Many-to-One com Funcionário
    @ManyToOne
    @JoinColumn(name = "Id_Funcionario", nullable = false)
    private FuncionarioEntity funcionario;

    // Relacionamento Many-to-One com Produto
    @ManyToOne
    @JoinColumn(name = "Id_Produto", nullable = false)
    private ProdutoEntity produto;

    @Column(name = "Quantidade")
    private Integer quantidade;

    // Construtores, getters e setters

    public VendaEntity() {
    }

    // Getters e Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public BigDecimal getDesconto() {
        return desconto;
    }

    public void setDesconto(BigDecimal desconto) {
        this.desconto = desconto;
    }

    public ClienteEntity getCliente() {
        return cliente;
    }

    public void setCliente(ClienteEntity cliente) {
        this.cliente = cliente;
    }

    public FuncionarioEntity getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(FuncionarioEntity funcionario) {
        this.funcionario = funcionario;
    }

    public ProdutoEntity getProduto() {
        return produto;
    }

    public void setProduto(ProdutoEntity produto) {
        this.produto = produto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }
}