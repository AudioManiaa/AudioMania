package com.audiomania.service;

import com.audiomania.entities.ClienteEntity;
import com.audiomania.repository.ClienteRepository;
import java.time.LocalDate;
import java.util.List;

public class ClienteService {

    private static ClienteRepository repository = new ClienteRepository();

    public static ClienteEntity buscarPorId(Integer id) {
        return repository.buscarPorId(id);
    }

    public static ClienteEntity buscarPorCpf(String cpf) {
        return repository.buscarPorCpf(cpf);
    }

    public static List<ClienteEntity> listarTodos() {
        return repository.listarTodos();
    }

    public static boolean cadastrarCliente(String nome, String cpf, String telefone, String endereco) {
        // Verificar se já existe cliente com o CPF informado
        if (repository.buscarPorCpf(cpf) != null) {
            return false;
        }

        ClienteEntity cliente = new ClienteEntity();
        cliente.setNome(nome);
        cliente.setCpf(cpf);
        cliente.setTelefone(telefone);
        cliente.setEndereco(endereco);
        cliente.setDataDeCadastro(LocalDate.now());

        return repository.salvar(cliente);
    }

    public static boolean atualizarCliente(Integer id, String nome, String telefone, String endereco) {
        ClienteEntity cliente = repository.buscarPorId(id);
        if (cliente == null) {
            return false;
        }

        cliente.setNome(nome);
        cliente.setTelefone(telefone);
        cliente.setEndereco(endereco);

        return repository.atualizar(cliente);
    }

    public static boolean excluirCliente(Integer id) {
        return repository.excluir(id);
    }

    public static void fecharRecursos() {
        repository.fechar();
    }
}