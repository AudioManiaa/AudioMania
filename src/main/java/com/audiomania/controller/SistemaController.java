package com.audiomania.controller;

import com.audiomania.model.entities.FuncionarioEntity;
import com.audiomania.model.service.FuncionarioService;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SistemaController {

    /**
     * Faz a autenticação do funcionário
     * @param cpf CPF do funcionário
     * @param senha Senha do funcionário
     * @return O objeto FuncionarioEntity se autenticado, null caso contrário
     */
    public FuncionarioEntity realizarLogin(String cpf, String senha) {
        try {
            if (cpf == null || cpf.trim().isEmpty() || senha == null || senha.trim().isEmpty()) {
                return null;
            }

            String cpfLimpo = limparCpf(cpf);
            return FuncionarioService.autenticar(cpfLimpo, senha.trim());
        } catch (Exception e) {
            System.err.println("Erro ao realizar login: " + e.getMessage());
            return null;
        }
    }

    /**
     * Busca um funcionário pelo ID
     * @param id ID do funcionário
     * @return O objeto FuncionarioEntity ou null se não encontrado
     */
    public FuncionarioEntity buscarFuncionarioPorId(Integer id) {
        try {
            List<FuncionarioEntity> funcionarios = FuncionarioService.listarTodos();
            return funcionarios.stream()
                    .filter(f -> f.getId().equals(id))
                    .findFirst()
                    .orElse(null);
        } catch (Exception e) {
            System.err.println("Erro ao buscar funcionário por ID: " + e.getMessage());
            return null;
        }
    }

    /**
     * Lista todos os funcionários
     * @return Lista de funcionários
     */
    public List<FuncionarioEntity> listarFuncionarios() {
        try {
            return FuncionarioService.listarTodos();
        } catch (Exception e) {
            System.err.println("Erro ao listar funcionários: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Busca funcionários por termo (nome, CPF ou cargo)
     * @param termo Termo de busca
     * @return Lista de funcionários filtrados
     */
    public List<FuncionarioEntity> buscarFuncionarios(String termo) {
        try {
            List<FuncionarioEntity> todosFuncionarios = FuncionarioService.listarTodos();

            if (termo == null || termo.trim().isEmpty()) {
                return todosFuncionarios;
            }

            String termoLower = termo.toLowerCase().trim();

            return todosFuncionarios.stream()
                    .filter(funcionario -> filtrarPorTermo(funcionario, termoLower))
                    .collect(Collectors.toList());

        } catch (Exception e) {
            System.err.println("Erro ao buscar funcionários: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Formata funcionário para exibição na tabela
     * @param funcionario Funcionário a ser formatado
     * @return Array de objetos para a tabela
     */
    public Object[] formatarFuncionarioParaTabela(FuncionarioEntity funcionario) {
        return new Object[]{
                funcionario.getId(),
                funcionario.getNome(),
                formatarCpf(funcionario.getCpf()),
                funcionario.getCargo(),
                funcionario.getTelefone() != null ? funcionario.getTelefone() : "",
                funcionario.getDataAdmissao() != null ? funcionario.getDataAdmissao().toString() : ""
        };
    }

    /**
     * Cadastra um novo funcionário com validações
     * @param nome Nome do funcionário
     * @param cpf CPF do funcionário
     * @param cargo Cargo do funcionário
     * @param telefone Telefone do funcionário
     * @param senha Senha do funcionário
     * @return Status da operação
     */
    public String cadastrarFuncionario(String nome, String cpf, String cargo, String telefone, String senha) {

        // Validações de campos obrigatórios
        if (nome == null || nome.trim().isEmpty()) {
            return "NOME_VAZIO";
        }

        if (cpf == null || cpf.trim().isEmpty()) {
            return "CPF_VAZIO";
        }

        if (cargo == null || cargo.trim().isEmpty()) {
            return "CARGO_VAZIO";
        }

        if (senha == null || senha.trim().isEmpty()) {
            return "SENHA_VAZIA";
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
        String cargoProcessado = cargo.trim();
        String senhaProcessada = senha.trim();
        String telefoneProcessado = (telefone != null && !telefone.trim().isEmpty()) ? telefone.trim() : null;

        // Criar entidade funcionário
        FuncionarioEntity novoFuncionario = new FuncionarioEntity();
        novoFuncionario.setNome(nomeProcessado);
        novoFuncionario.setCpf(cpfLimpo);
        novoFuncionario.setCargo(cargoProcessado);
        novoFuncionario.setTelefone(telefoneProcessado);
        novoFuncionario.setSenha(senhaProcessada);

        // Tentar cadastrar
        try {
            boolean sucesso = FuncionarioService.cadastrarFuncionario(novoFuncionario);
            return sucesso ? "SUCESSO" : "ERRO_BANCO";
        } catch (Exception e) {
            return "ERRO: " + e.getMessage();
        }
    }

    /**
     * Atualiza um funcionário existente com validações
     * @param id ID do funcionário
     * @param nome Novo nome
     * @param cpf Novo CPF
     * @param cargo Novo cargo
     * @param telefone Novo telefone
     * @param senha Nova senha (opcional)
     * @param alterarSenha Se deve alterar a senha
     * @return Status da operação
     */
    public String atualizarFuncionario(Integer id, String nome, String cpf, String cargo,
                                       String telefone, String senha, boolean alterarSenha) {

        // Validações de campos obrigatórios
        if (nome == null || nome.trim().isEmpty()) {
            return "NOME_VAZIO";
        }

        if (cargo == null || cargo.trim().isEmpty()) {
            return "CARGO_VAZIO";
        }

        // Validação do CPF
        String cpfLimpo = limparCpf(cpf);
        if (!validarCpf(cpfLimpo)) {
            return "CPF_INVALIDO";
        }

        // Validação da senha se marcou para alterar
        if (alterarSenha && (senha == null || senha.trim().isEmpty())) {
            return "SENHA_VAZIA_ALTERACAO";
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
        String cargoProcessado = cargo.trim();
        String telefoneProcessado = (telefone != null && !telefone.trim().isEmpty()) ? telefone.trim() : null;
        String senhaProcessada = (alterarSenha && senha != null && !senha.trim().isEmpty()) ? senha.trim() : null;

        // Tentar atualizar
        try {
            boolean sucesso = FuncionarioService.atualizarFuncionario(
                    id, nomeProcessado, cpfLimpo, cargoProcessado, telefoneProcessado, senhaProcessada
            );

            return sucesso ? "SUCESSO" : "ERRO_BANCO";
        } catch (Exception e) {
            return "ERRO: " + e.getMessage();
        }
    }

    /**
     * Preenche os campos da tela de edição com dados do funcionário
     * @param funcionario Funcionário a ser editado
     * @param idField Campo ID
     * @param nomeField Campo nome
     * @param cpfField Campo CPF
     * @param cargoField Campo cargo
     * @param telefoneField Campo telefone
     * @param senhaField Campo senha
     */
    public void preencherCamposEdicao(FuncionarioEntity funcionario, JTextField idField,
                                      JTextField nomeField, JTextField cpfField, JTextField cargoField,
                                      JTextField telefoneField, JPasswordField senhaField) {

        idField.setText(funcionario.getId().toString());
        nomeField.setText(funcionario.getNome());
        cpfField.setText(formatarCpf(funcionario.getCpf()));
        cargoField.setText(funcionario.getCargo());
        telefoneField.setText(funcionario.getTelefone() != null ? funcionario.getTelefone() : "");
        senhaField.setText(""); // Limpa o campo senha por segurança
    }

    /**
     * Exclui um funcionário pelo ID
     * @param id ID do funcionário
     * @return true se excluído com sucesso, false caso contrário
     */
    public boolean excluirFuncionario(Integer id) {
        try {
            return FuncionarioService.excluirFuncionario(id);
        } catch (Exception e) {
            System.err.println("Erro ao excluir funcionário: " + e.getMessage());
            return false;
        }
    }

    // Métodos auxiliares privados para validações e formatações

    /**
     * Filtra funcionário por termo de busca
     * @param funcionario Funcionário a ser filtrado
     * @param termo Termo de busca em minúsculas
     * @return true se o funcionário corresponde ao termo
     */
    private boolean filtrarPorTermo(FuncionarioEntity funcionario, String termo) {
        String nome = funcionario.getNome() != null ? funcionario.getNome().toLowerCase() : "";
        String cpf = funcionario.getCpf() != null ? funcionario.getCpf().replaceAll("[^0-9]", "") : "";
        String cargo = funcionario.getCargo() != null ? funcionario.getCargo().toLowerCase() : "";

        return nome.contains(termo) ||
                cpf.contains(termo.replaceAll("[^0-9]", "")) ||
                cargo.contains(termo);
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
            List<FuncionarioEntity> funcionarios = FuncionarioService.listarTodos();
            return funcionarios.stream()
                    .anyMatch(f -> f.getCpf().equals(cpf));
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