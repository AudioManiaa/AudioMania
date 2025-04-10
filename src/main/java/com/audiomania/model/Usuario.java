package com.audiomania.model;

public class Usuario {
    private int id;
    private String login;
    private String senha;
    private int nivelAcesso; // 1: básico, 2: intermediário, 3: administrador

    public Usuario(int id, String login, String senha, int nivelAcesso) {
        this.id = id;
        this.login = login;
        this.senha = senha;
        this.nivelAcesso = nivelAcesso;
    }

    public int getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getSenha() {
        return senha;
    }

    public int getNivelAcesso() {
        return nivelAcesso;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setNivelAcesso(int nivelAcesso) {
        this.nivelAcesso = nivelAcesso;
    }
}