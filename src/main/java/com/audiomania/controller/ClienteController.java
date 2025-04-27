package com.audiomania.controller;

import com.audiomania.entities.ClienteEntity;
import com.audiomania.service.ClienteService;
import java.util.List;

public class ClienteController {

    /**
     * Busca um cliente pelo ID
     * @param id ID do cliente
     * @return O objeto ClienteEntity ou null se não encontrado
     */
    public ClienteEntity buscarPorId(Integer id) {
        return ClienteService.buscarPorId(id);
    }


    public List<ClienteEntity> listarClientes() {
        return ClienteService.listarTodos();
    }

    /**
     * Cadastra um novo cliente
     * @param nome Nome do cliente
     * @param cpf CPF do cliente
     * @param telefone Telefone do cliente
     * @param endereco Endereço do cliente
     * @return true se cadastrado com sucesso, false caso contrário
     */
    public boolean cadastrarCliente(String nome, String cpf, String telefone, String endereco) {
        return ClienteService.cadastrarCliente(nome, cpf, telefone, endereco);
    }

    public boolean atualizarCliente(Integer id, String nome, String telefone, String endereco) {
        return ClienteService.atualizarCliente(id, nome, telefone, endereco);
    }

    public boolean excluirCliente(Integer id) {
        return ClienteService.excluirCliente(id);
    }
}