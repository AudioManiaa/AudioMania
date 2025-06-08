package com.audiomania.controller;

import com.audiomania.model.entities.FuncionarioEntity;
import com.audiomania.model.service.FuncionarioService;
import java.util.List;

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
        FuncionarioEntity novoFuncionario = new FuncionarioEntity();
        novoFuncionario.setNome(nome);
        novoFuncionario.setCpf(cpf);
        novoFuncionario.setCargo(cargo);
        novoFuncionario.setTelefone(telefone);
        novoFuncionario.setSenha(senha);
        return FuncionarioService.cadastrarFuncionario(novoFuncionario);
    }

    /**
     * Lista todos os funcionários
     * @return Lista de funcionários
     */
    public List<FuncionarioEntity> listarFuncionarios() {
        return FuncionarioService.listarTodos();
    }

    /**
     * Atualiza os dados de um funcionário
     * @param id ID do funcionário
     * @param nome Novo nome
     * @param cpf Novo CPF
     * @param cargo Novo cargo
     * @param telefone Novo telefone
     * @param senha Nova senha (opcional)
     * @return true se atualizado com sucesso, false caso contrário
     */
    public boolean atualizarFuncionario(Integer id, String nome, String cpf, String cargo,
                                        String telefone, String senha) {
        return FuncionarioService.atualizarFuncionario(id, nome, cpf, cargo, telefone, senha);
    }

    /**
     * Exclui um funcionário pelo ID
     * @param id ID do funcionário
     * @return true se excluído com sucesso, false caso contrário
     */
    public boolean excluirFuncionario(Integer id) {
        return FuncionarioService.excluirFuncionario(id);
    }
}