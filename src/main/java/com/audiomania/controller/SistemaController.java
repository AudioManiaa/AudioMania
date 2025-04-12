package com.audiomania.controller;

import com.audiomania.model.Usuario;
import com.audiomania.service.UsuarioService;

public class SistemaController {

    /**
     * Faz a autenticação do usuário
     * @param login Nome de usuário
     * @param senha Senha do usuário
     * @return O objeto Usuario se autenticado, null caso contrário
     */
    public Usuario realizarLogin(String login, String senha) {
        return UsuarioService.autenticar(login, senha);
    }

    /**
     * Cadastra um novo usuário
     * @param login Nome de usuário
     * @param senha Senha do usuário
     * @param nivelAcesso Nível de permissão
     * @return true se cadastrado com sucesso, false se o login já existir
     */
    public boolean cadastrarUsuario(String login, String senha, int nivelAcesso) {
        return UsuarioService.cadastrarUsuario(login, senha, nivelAcesso);
    }
}