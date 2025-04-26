package com.audiomania.controller;

import com.audiomania.entities.FuncionarioEntity;
import com.audiomania.service.FuncionarioService;

public class SistemaController {

    /**
     * Faz a autenticação do funcionário
     * @param cpf CPF do funcionário
     * @param senha Senha do funcionário
     * @return O objeto FuncionarioEntity se autenticado, null caso contrário
     */
    public FuncionarioEntity realizarLogin(String cpf, String senha) {
        return FuncionarioService.autenticar(cpf, senha);
    }

    /**
     * Cadastra um novo funcionário
     * @param nome Nome do funcionário
     * @param cpf CPF do funcionário
     * @param cargo Cargo do funcionário
     * @param telefone Telefone do funcionário
     * @param senha Senha do funcionário
     * @return true se cadastrado com sucesso, false caso contrário
     */
    public boolean cadastrarFuncionario(String nome, String cpf, String cargo,
                                        String telefone, String senha) {
        return FuncionarioService.cadastrarFuncionario(nome, cpf, cargo, telefone, senha);
    }
}