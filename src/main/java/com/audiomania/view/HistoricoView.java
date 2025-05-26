package com.audiomania.view;

import com.audiomania.model.entities.ClienteEntity;
import com.audiomania.model.entities.FuncionarioEntity;
import com.audiomania.model.entities.VendaEntity;
import com.audiomania.model.repository.ClienteRepository;
import com.audiomania.model.repository.FuncionarioRepository;
import com.audiomania.model.repository.VendaRepository;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class HistoricoView {

    private List<ClienteEntity> historicoClientes = new ArrayList<>();
    private List<FuncionarioEntity> historicoFuncionarios = new ArrayList<>();
    private Map<ClienteEntity, List<VendaEntity>> historicoCompras = new HashMap<>();
    private Scanner scanner = new Scanner(System.in);

    // Repositórios para acesso ao banco de dados
    private ClienteRepository clienteRepository;
    private FuncionarioRepository funcionarioRepository;
    private VendaRepository vendaRepository;

    public HistoricoView() {  // CORREÇÃO: Alterado de Historico para HistoricoView
        // Inicializa os repositórios
        this.clienteRepository = new ClienteRepository();
        this.funcionarioRepository = new FuncionarioRepository();
        this.vendaRepository = new VendaRepository();

        carregarDadosDoBanco();
    }

    private void carregarDadosDoBanco() {
        try {
            // Limpar listas atuais
            historicoClientes.clear();
            historicoFuncionarios.clear();
            historicoCompras.clear();

            // Carregar clientes do banco de dados
            List<ClienteEntity> clientes = clienteRepository.listarTodos();
            if (clientes != null && !clientes.isEmpty()) {
                historicoClientes.addAll(clientes);
                System.out.println("Clientes carregados do banco de dados: " + historicoClientes.size());
            } else {
                System.out.println("Nenhum cliente encontrado no banco de dados.");
            }

            // Carregar funcionários do banco de dados
            List<FuncionarioEntity> funcionarios = funcionarioRepository.listarTodos();
            if (funcionarios != null && !funcionarios.isEmpty()) {
                historicoFuncionarios.addAll(funcionarios);
                System.out.println("Funcionários carregados do banco de dados: " + historicoFuncionarios.size());
            } else {
                System.out.println("Nenhum funcionário encontrado no banco de dados.");
            }

            // Carregar vendas e organizá-las por cliente
            List<VendaEntity> vendas = vendaRepository.listarTodos();
            if (vendas != null && !vendas.isEmpty()) {
                for (VendaEntity venda : vendas) {
                    ClienteEntity cliente = venda.getCliente();
                    if (!historicoCompras.containsKey(cliente)) {
                        historicoCompras.put(cliente, new ArrayList<>());
                    }
                    historicoCompras.get(cliente).add(venda);
                }
                System.out.println("Vendas carregadas e organizadas por cliente.");
            } else {
                System.out.println("Nenhuma venda encontrada no banco de dados.");
            }

        } catch (Exception e) {
            System.out.println("Erro ao carregar dados do banco: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void exibirHistoricoClientes() {
        if (historicoClientes.isEmpty()) {
            System.out.println("Nenhum cliente no histórico.");
            return;
        }
        System.out.println("\n--- Histórico de Clientes ---");
        for (int i = 0; i < historicoClientes.size(); i++) {
            ClienteEntity cliente = historicoClientes.get(i);
            System.out.println((i + 1) + ". Nome: " + cliente.getNome());
            System.out.println("   CPF: " + cliente.getCpf());
            System.out.println("   Telefone: " + cliente.getTelefone());
            System.out.println("   Endereço: " + cliente.getEndereco());
            System.out.println("------------------------");
        }
    }

    public void exibirHistoricoFuncionarios() {
        if (historicoFuncionarios.isEmpty()) {
            System.out.println("Nenhum funcionário no histórico.");
            return;
        }
        System.out.println("\n--- Histórico de Funcionários ---");
        for (int i = 0; i < historicoFuncionarios.size(); i++) {
            FuncionarioEntity funcionario = historicoFuncionarios.get(i);
            System.out.println((i + 1) + ". Nome: " + funcionario.getNome());
            System.out.println("   CPF: " + funcionario.getCpf());
            System.out.println("   Telefone: " + funcionario.getTelefone());
            System.out.println("   Cargo: " + funcionario.getCargo());
            System.out.println("------------------------");
        }
    }

    public void exibirHistoricoCompras(ClienteEntity cliente) {
        if (cliente == null) {
            System.out.println("Cliente inválido.");
            return;
        }

        if (!historicoCompras.containsKey(cliente) || historicoCompras.get(cliente).isEmpty()) {
            System.out.println("Nenhuma compra registrada para o cliente: " + cliente.getNome());
            return;
        }

        System.out.println("\n--- Histórico de Compras do Cliente: " + cliente.getNome() + " ---");
        List<VendaEntity> compras = historicoCompras.get(cliente);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        for (int i = 0; i < compras.size(); i++) {
            VendaEntity venda = compras.get(i);
            System.out.println((i + 1) + ". Data: " + venda.getData().format(formatter));
            System.out.println("   Produto: " + venda.getProduto().getNome());
            System.out.println("   Quantidade: " + venda.getQuantidade());
            System.out.println("   Valor Total: R$ " + venda.getValorTotal());
            System.out.println("   Forma de Pagamento: " + venda.getFormaPagamento());
            System.out.println("   Atendido por: " + venda.getFuncionario().getNome());
            System.out.println("------------------------");
        }
    }

    private ClienteEntity selecionarCliente() {
        if (historicoClientes.isEmpty()) {
            System.out.println("Não há clientes cadastrados no histórico.");
            return null;
        }

        System.out.println("\n--- Selecione um Cliente ---");
        for (int i = 0; i < historicoClientes.size(); i++) {
            ClienteEntity c = historicoClientes.get(i);
            System.out.println((i + 1) + ". " + c.getNome() + " (CPF: " + c.getCpf() + ")");
        }

        System.out.print("Digite o número do cliente (0 para cancelar): ");
        try {
            int escolha = scanner.nextInt();
            scanner.nextLine();

            if (escolha == 0) {
                return null;
            }

            if (escolha < 1 || escolha > historicoClientes.size()) {
                System.out.println("Opção inválida!");
                return null;
            }

            return historicoClientes.get(escolha - 1);
        } catch (Exception e) {
            System.out.println("Entrada inválida. Por favor, digite um número.");
            scanner.nextLine();
            return null;
        }
    }

    public void menuHistorico() {
        while (true) {
            System.out.println("\n======Menu de Histórico======");
            System.out.println("1. Exibir Histórico de Clientes");
            System.out.println("2. Exibir Histórico de Funcionários");
            System.out.println("3. Exibir Histórico de Compras de um Cliente");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            try {
                int opcao = scanner.nextInt();
                scanner.nextLine();

                switch (opcao) {
                    case 1:
                        exibirHistoricoClientes();
                        break;
                    case 2:
                        exibirHistoricoFuncionarios();
                        break;
                    case 3:
                        ClienteEntity clienteSelecionado = selecionarCliente();
                        if (clienteSelecionado != null) {
                            exibirHistoricoCompras(clienteSelecionado);
                        }
                        break;
                    case 0:
                        // Fechando os repositórios
                        clienteRepository.fechar();
                        funcionarioRepository.fechar();
                        vendaRepository.fechar();
                        System.out.println("Saindo do menu de histórico...");
                        return;
                    default:
                        System.out.println("Opção inválida.");
                }
            } catch (Exception e) {
                System.out.println("Entrada inválida. Por favor, digite um número.");
                scanner.nextLine();
            }

            System.out.println("\nPressione ENTER para continuar...");
            scanner.nextLine();
        }
    }
}