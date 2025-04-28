package com.audiomania.entities;

import java.time.LocalDate;
import jakarta.persistence.*;

@Entity
@Table(name = "FUNCIONARIO")
public class FuncionarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_Funcionario")
    private Integer id;

    @Column(name = "Nome", nullable = false, length = 100)
    private String nome;

    @Column(name = "CPF", nullable = false, length = 14, unique = true)
    private String cpf;

    @Column(name = "Cargo", length = 50)
    private String cargo;

    @Column(name = "Telefone", length = 20)
    private String telefone;

    @Column(name = "Data_Admissao")
    private LocalDate dataAdmissao;

    @Column(name = "Senha", nullable = true, length = 100)
    private String senha;


    public FuncionarioEntity() {
    }

    public FuncionarioEntity(String nome, String cpf, String cargo, String telefone, LocalDate dataAdmissao) {
        this.nome = nome;
        this.cpf = cpf;
        this.cargo = cargo;
        this.telefone = telefone;
        this.dataAdmissao = dataAdmissao;
    }

    public FuncionarioEntity(String nome, String cpf, String cargo, String telefone, LocalDate dataAdmissao, String senha) {
        this.nome = nome;
        this.cpf = cpf;
        this.cargo = cargo;
        this.telefone = telefone;
        this.dataAdmissao = dataAdmissao;
        this.senha = senha;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public LocalDate getDataAdmissao() {
        return dataAdmissao;
    }

    public void setDataAdmissao(LocalDate dataAdmissao) {
        this.dataAdmissao = dataAdmissao;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}