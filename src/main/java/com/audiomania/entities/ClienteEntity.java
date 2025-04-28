package com.audiomania.entities;

import java.time.LocalDate;
import jakarta.persistence.*;

    @Entity
    @Table(name = "CLIENTE")
    public class ClienteEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "Id_Cliente")
        private Integer id;

        @Column(name = "Nome", nullable = false, length = 100)
        private String nome;

        @Column(name = "CPF", nullable = false, length = 14, unique = true)
        private String cpf;

        @Column(name = "Telefone", length = 20)
        private String telefone;

        @Column(name = "Endereco", length = 255)
        private String endereco;

        @Column(name = "DataDeCadastro")
        private LocalDate dataDeCadastro;

        public ClienteEntity() {
        }

        public ClienteEntity(String nome, String cpf, String telefone, String endereco, LocalDate dataDeCadastro) {
            this.nome = nome;
            this.cpf = cpf;
            this.telefone = telefone;
            this.endereco = endereco;
            this.dataDeCadastro = dataDeCadastro;
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

        public String getTelefone() {
            return telefone;
        }

        public void setTelefone(String telefone) {
            this.telefone = telefone;
        }

        public String getEndereco() {
            return endereco;
        }

        public void setEndereco(String endereco) {
            this.endereco = endereco;
        }

        public LocalDate getDataDeCadastro() {
            return dataDeCadastro;
        }

        public void setDataDeCadastro(LocalDate dataDeCadastro) {
            this.dataDeCadastro = dataDeCadastro;
        }
    }

