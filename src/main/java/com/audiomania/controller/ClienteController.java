package com.audiomania.controller;

import com.audiomania.model.entities.ClienteEntity;
import com.audiomania.model.service.ClienteService;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ClienteController {

    /**
     * Busca um cliente pelo ID
     * @param id ID do cliente
     * @return O objeto ClienteEntity ou null se não encontrado
     */
    public ClienteEntity buscarPorId(Integer id) {
        try {
            return ClienteService.buscarPorId(id);
        } catch (Exception e) {
            System.err.println("Erro ao buscar cliente por ID: " + e.getMessage());
            return null;
        }
    }

    /**
     * Lista todos os clientes
     * @return Lista de clientes
     */
    public List<ClienteEntity> listarClientes() {
        try {
            return ClienteService.listarTodos();
        } catch (Exception e) {
            System.err.println("Erro ao listar clientes: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Busca clientes por termo (nome ou CPF)
     * @param termo Termo de busca
     * @return Lista de clientes filtrados
     */
    public List<ClienteEntity> buscarClientes(String termo) {
        try {
            List<ClienteEntity> todosClientes = ClienteService.listarTodos();

            if (termo == null || termo.trim().isEmpty()) {
                return todosClientes;
            }

            String termoLower = termo.toLowerCase().trim();

            return todosClientes.stream()
                    .filter(cliente -> filtrarPorTermo(cliente, termoLower))
                    .collect(Collectors.toList());

        } catch (Exception e) {
            System.err.println("Erro ao buscar clientes: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Formata cliente para exibição na tabela
     * @param cliente Cliente a ser formatado
     * @return Array de objetos para a tabela
     */
    public Object[] formatarClienteParaTabela(ClienteEntity cliente) {
        return new Object[]{
                cliente.getId(),
                cliente.getNome(),
                formatarCpf(cliente.getCpf()),
                cliente.getTelefone() != null ? cliente.getTelefone() : "",
                cliente.getEndereco() != null ? cliente.getEndereco() : "",
                cliente.getDataDeCadastro() != null ? cliente.getDataDeCadastro().toString() : ""
        };
    }

    /**
     * Cadastra um novo cliente com validações
     * @param nome Nome do cliente
     * @param cpf CPF do cliente
     * @param telefone Telefone do cliente
     * @param endereco Endereço do cliente
     * @return Status da operação
     */
    public String cadastrarCliente(String nome, String cpf, String telefone, String endereco) {

        // Validações de campos obrigatórios
        if (nome == null || nome.trim().isEmpty()) {
            return "NOME_VAZIO";
        }

        if (cpf == null || cpf.trim().isEmpty()) {
            return "CPF_VAZIO";
        }

        // Validação do CPF
        String cpfLimpo = limparCpf(cpf);
        if (!validarCpf(cpfLimpo)) {
            return "CPF_INVALIDO";
        }

        // Verificar se CPF já existe
        if (cpfJaExiste(cpfLimpo)) {
            return "CPF_EXISTE";
        }

        // Validação do telefone (se preenchido)
        if (telefone != null && !telefone.trim().isEmpty()) {
            String telefoneLimpo = limparTelefone(telefone);
            if (!validarTelefone(telefoneLimpo)) {
                return "TELEFONE_INVALIDO";
            }
            telefone = telefoneLimpo;
        }

        // Preparar dados para cadastro
        String nomeProcessado = nome.trim();
        String telefoneProcessado = (telefone != null && !telefone.trim().isEmpty()) ? telefone.trim() : null;
        String enderecoProcessado = (endereco != null && !endereco.trim().isEmpty()) ? endereco.trim() : null;

        // Tentar cadastrar
        try {
            boolean sucesso = ClienteService.cadastrarCliente(
                    nomeProcessado, cpfLimpo, telefoneProcessado, enderecoProcessado
            );

            return sucesso ? "SUCESSO" : "ERRO_BANCO";
        } catch (Exception e) {
            return "ERRO: " + e.getMessage();
        }
    }

    /**
     * Atualiza um cliente existente com validações
     * @param cliente Cliente a ser atualizado
     * @param nome Novo nome
     * @param telefone Novo telefone
     * @param endereco Novo endereço
     * @return Status da operação
     */
    public String atualizarCliente(ClienteEntity cliente, String nome, String telefone, String endereco) {

        // Validação de campo obrigatório
        if (nome == null || nome.trim().isEmpty()) {
            return "NOME_VAZIO";
        }

        // Validação do telefone (se preenchido)
        if (telefone != null && !telefone.trim().isEmpty()) {
            String telefoneLimpo = limparTelefone(telefone);
            if (!validarTelefone(telefoneLimpo)) {
                return "TELEFONE_INVALIDO";
            }
            telefone = telefoneLimpo;
        }

        // Preparar dados para atualização
        String nomeProcessado = nome.trim();
        String telefoneProcessado = (telefone != null && !telefone.trim().isEmpty()) ? telefone.trim() : null;
        String enderecoProcessado = (endereco != null && !endereco.trim().isEmpty()) ? endereco.trim() : null;

        // Tentar atualizar
        try {
            boolean sucesso = ClienteService.atualizarCliente(
                    cliente.getId(), nomeProcessado, telefoneProcessado, enderecoProcessado
            );

            return sucesso ? "SUCESSO" : "ERRO_BANCO";
        } catch (Exception e) {
            return "ERRO: " + e.getMessage();
        }
    }

    /**
     * Preenche os campos da tela de edição com dados do cliente
     * @param cliente Cliente a ser editado
     * @param nomeField Campo nome
     * @param cpfField Campo CPF
     * @param telefoneField Campo telefone
     * @param enderecoField Campo endereço
     */
    public void preencherCamposEdicao(ClienteEntity cliente, JTextField nomeField,
                                      JTextField cpfField, JTextField telefoneField, JTextField enderecoField) {

        nomeField.setText(cliente.getNome());
        cpfField.setText(formatarCpf(cliente.getCpf()));
        telefoneField.setText(cliente.getTelefone() != null ? cliente.getTelefone() : "");
        enderecoField.setText(cliente.getEndereco() != null ? cliente.getEndereco() : "");
    }

    /**
     * Exclui um cliente pelo ID
     * @param id ID do cliente
     * @return true se excluído com sucesso, false caso contrário
     */
    public boolean excluirCliente(Integer id) {
        try {
            return ClienteService.excluirCliente(id);
        } catch (Exception e) {
            System.err.println("Erro ao excluir cliente: " + e.getMessage());
            return false;
        }
    }

    // Métodos auxiliares privados para validações e formatações

    /**
     * Filtra cliente por termo de busca
     * @param cliente Cliente a ser filtrado
     * @param termo Termo de busca em minúsculas
     * @return true se o cliente corresponde ao termo
     */
    private boolean filtrarPorTermo(ClienteEntity cliente, String termo) {
        String nome = cliente.getNome() != null ? cliente.getNome().toLowerCase() : "";
        String cpf = cliente.getCpf() != null ? cliente.getCpf().replaceAll("[^0-9]", "") : "";

        return nome.contains(termo) || cpf.contains(termo.replaceAll("[^0-9]", ""));
    }

    /**
     * Remove formatação do CPF
     * @param cpf CPF com ou sem formatação
     * @return CPF apenas com números
     */
    private String limparCpf(String cpf) {
        return cpf.replaceAll("[^0-9]", "");
    }

    /**
     * Valida formato do CPF
     * @param cpf CPF apenas com números
     * @return true se válido
     */
    private boolean validarCpf(String cpf) {
        return cpf.matches("\\d{11}");
    }

    /**
     * Formata CPF para exibição
     * @param cpf CPF apenas com números
     * @return CPF formatado (000.000.000-00)
     */
    private String formatarCpf(String cpf) {
        if (cpf == null || cpf.length() != 11) {
            return cpf;
        }
        return cpf.substring(0, 3) + "." +
                cpf.substring(3, 6) + "." +
                cpf.substring(6, 9) + "-" +
                cpf.substring(9, 11);
    }

    /**
     * Verifica se CPF já existe no sistema
     * @param cpf CPF apenas com números
     * @return true se já existe
     */
    private boolean cpfJaExiste(String cpf) {
        try {
            return ClienteService.buscarPorCpf(cpf) != null;
        } catch (Exception e) {
            System.err.println("Erro ao verificar CPF existente: " + e.getMessage());
            return false;
        }
    }

    /**
     * Remove formatação do telefone
     * @param telefone Telefone com ou sem formatação
     * @return Telefone apenas com números
     */
    private String limparTelefone(String telefone) {
        return telefone.replaceAll("[^0-9]", "");
    }

    /**
     * Valida formato do telefone
     * @param telefone Telefone apenas com números
     * @return true se válido (10 ou 11 dígitos)
     */
    private boolean validarTelefone(String telefone) {
        return telefone.matches("\\d{10,11}");
    }
}