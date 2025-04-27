package com.audiomania.estoque;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class VendaRepository {
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USUARIO = "postgres";
    private static final String SENHA = "Ee2310";
    private Scanner scanner;
    private ProdutoRepository produtoRepository;

    public VendaRepository() {
        this.scanner = new Scanner(System.in);
        this.produtoRepository = new ProdutoRepository();
    }

    /**
     * Busca todas as vendas no banco de dados
     *
     * @return Lista de vendas
     */
    public List<Venda> buscarTodas() {
        List<Venda> vendas = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(URL, USUARIO, SENHA);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM VENDA ORDER BY Id_Venda")) {

            while (rs.next()) {
                Venda venda = mapearResultSetParaVenda(rs);
                vendas.add(venda);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar vendas: " + e.getMessage());
        }

        return vendas;
    }

    /**
     * Busca uma venda pelo seu ID
     *
     * @param id ID da venda
     * @return Venda encontrada ou null se não existir
     */
    public Venda buscarPorId(int id) {
        Venda venda = null;

        try (Connection conn = DriverManager.getConnection(URL, USUARIO, SENHA);
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM VENDA WHERE Id_Venda = ?")) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                venda = mapearResultSetParaVenda(rs);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar venda: " + e.getMessage());
        }

        return venda;
    }

    /**
     * Salva uma nova venda no banco de dados
     *
     * @param venda Venda a ser salva
     * @return true se salvo com sucesso, false caso contrário
     */
    public boolean salvar(Venda venda) {
        String sql = "INSERT INTO VENDA (Data, Valor_Total, Forma_Pagamento, Desconto, Id_Cliente, " +
                "Id_Funcionario, Id_Produto, Quantidade) VALUES (?, ?, ?, ?, ?, ?, ?, ?) RETURNING Id_Venda";

        try (Connection conn = DriverManager.getConnection(URL, USUARIO, SENHA);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, Date.valueOf(venda.getData()));
            stmt.setBigDecimal(2, venda.getValorTotal());
            stmt.setString(3, venda.getFormaPagamento());
            stmt.setBigDecimal(4, venda.getDesconto() != null ? venda.getDesconto() : BigDecimal.ZERO);
            stmt.setInt(5, venda.getIdCliente());
            stmt.setInt(6, venda.getIdFuncionario());
            stmt.setInt(7, venda.getIdProduto());
            stmt.setInt(8, venda.getQuantidade());

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                venda.setId(rs.getInt("Id_Venda"));
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao salvar venda: " + e.getMessage());
        }

        return false;
    }

    /**
     * Atualiza uma venda existente
     *
     * @param venda Venda com os dados atualizados
     * @return true se atualizado com sucesso, false caso contrário
     */
    public boolean atualizar(Venda venda) {
        String sql = "UPDATE VENDA SET Data = ?, Valor_Total = ?, Forma_Pagamento = ?, " +
                "Desconto = ?, Id_Cliente = ?, Id_Funcionario = ?, Id_Produto = ?, Quantidade = ? " +
                "WHERE Id_Venda = ?";

        try (Connection conn = DriverManager.getConnection(URL, USUARIO, SENHA);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, Date.valueOf(venda.getData()));
            stmt.setBigDecimal(2, venda.getValorTotal());
            stmt.setString(3, venda.getFormaPagamento());
            stmt.setBigDecimal(4, venda.getDesconto() != null ? venda.getDesconto() : BigDecimal.ZERO);
            stmt.setInt(5, venda.getIdCliente());
            stmt.setInt(6, venda.getIdFuncionario());
            stmt.setInt(7, venda.getIdProduto());
            stmt.setInt(8, venda.getQuantidade());
            stmt.setInt(9, venda.getId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar venda: " + e.getMessage());
            return false;
        }
    }

    /**
     * Remove uma venda do banco de dados
     *
     * @param id ID da venda a ser removida
     * @return true se removido com sucesso, false caso contrário
     */
    public boolean remover(int id) {
        String sql = "DELETE FROM VENDA WHERE Id_Venda = ?";

        try (Connection conn = DriverManager.getConnection(URL, USUARIO, SENHA);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao remover venda: " + e.getMessage());
            return false;
        }
    }

    /**
     * Exibe o menu de gerenciamento de vendas e processa as opções escolhidas pelo usuário
     */
    public void exibirMenu() {
        boolean voltarMenu = false;

        while (!voltarMenu) {
            System.out.println("\n======= GERENCIAMENTO DE VENDAS =======");
            System.out.println("1. Listar todas as vendas");
            System.out.println("2. Buscar venda pelo ID");
            System.out.println("3. Registrar nova venda");
            System.out.println("4. Atualizar venda existente");
            System.out.println("5. Cancelar venda");
            System.out.println("0. Voltar ao menu principal");
            System.out.print("Escolha uma opção: ");

            int opcao = lerInteiro();

            switch (opcao) {
                case 1:
                    listarVendas();
                    break;
                case 2:
                    buscarVendaPorId();
                    break;
                case 3:
                    registrarVenda();
                    break;
                case 4:
                    atualizarVenda();
                    break;
                case 5:
                    cancelarVenda();
                    break;
                case 0:
                    voltarMenu = true;
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    /**
     * Lista todas as vendas cadastradas
     */
    private void listarVendas() {
        System.out.println("\n--- LISTA DE VENDAS ---");
        List<Venda> vendas = buscarTodas();

        if (vendas.isEmpty()) {
            System.out.println("Nenhuma venda registrada.");
            return;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        for (Venda venda : vendas) {
            System.out.println("ID: " + venda.getId());
            System.out.println("Data: " + venda.getData().format(formatter));
            System.out.println("ID do Cliente: " + venda.getIdCliente());
            System.out.println("ID do Funcionário: " + venda.getIdFuncionario());
            System.out.println("ID do Produto: " + venda.getIdProduto());

            // Buscar nome do produto
            Produto produto = produtoRepository.buscarProdutoPorId(venda.getIdProduto());
            if (produto != null) {
                System.out.println("Produto: " + produto.getNome());
            }

            System.out.println("Quantidade: " + venda.getQuantidade());
            System.out.println("Valor Total: R$ " + venda.getValorTotal());
            System.out.println("Forma de Pagamento: " + venda.getFormaPagamento());
            if (venda.getDesconto() != null && venda.getDesconto().compareTo(BigDecimal.ZERO) > 0) {
                System.out.println("Desconto: R$ " + venda.getDesconto());
            }
            System.out.println("------------------------------");
        }
    }

    /**
     * Busca e exibe uma venda pelo ID
     */
    private void buscarVendaPorId() {
        System.out.print("Digite o ID da venda: ");
        int id = lerInteiro();

        Venda venda = buscarPorId(id);
        if (venda != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            System.out.println("\nVenda encontrada:");
            System.out.println("ID: " + venda.getId());
            System.out.println("Data: " + venda.getData().format(formatter));
            System.out.println("ID do Cliente: " + venda.getIdCliente());
            System.out.println("ID do Funcionário: " + venda.getIdFuncionario());
            System.out.println("ID do Produto: " + venda.getIdProduto());

            // Buscar nome do produto
            Produto produto = produtoRepository.buscarProdutoPorId(venda.getIdProduto());
            if (produto != null) {
                System.out.println("Produto: " + produto.getNome());
            }

            System.out.println("Quantidade: " + venda.getQuantidade());
            System.out.println("Valor Total: R$ " + venda.getValorTotal());
            System.out.println("Forma de Pagamento: " + venda.getFormaPagamento());
            if (venda.getDesconto() != null && venda.getDesconto().compareTo(BigDecimal.ZERO) > 0) {
                System.out.println("Desconto: R$ " + venda.getDesconto());
            }
        } else {
            System.out.println("Venda não encontrada.");
        }
    }

    /**
     * Registra uma nova venda
     */
    private void registrarVenda() {
        System.out.println("\n--- REGISTRAR NOVA VENDA ---");

        // Listar produtos disponíveis
        List<Produto> produtos = produtoRepository.listarTodosProdutos();
        if (produtos.isEmpty()) {
            System.out.println("Não há produtos cadastrados para venda.");
            return;
        }

        System.out.println("\nProdutos disponíveis:");
        for (Produto p : produtos) {
            if (p.getQuantidade_Estoque() > 0) {
                System.out.println("ID: " + p.getId_Produto() + " | Nome: " + p.getNome() +
                        " | Preço: R$ " + p.getPreco() +
                        " | Estoque: " + p.getQuantidade_Estoque());
            }
        }

        System.out.print("\nID do produto vendido: ");
        int produtoId = lerInteiro();

        Produto produto = produtoRepository.buscarProdutoPorId(produtoId);
        if (produto == null) {
            System.out.println("Produto não encontrado.");
            return;
        }

        if (produto.getQuantidade_Estoque() <= 0) {
            System.out.println("Produto sem estoque disponível.");
            return;
        }

        System.out.print("Quantidade vendida: ");
        int quantidade = lerInteiro();

        if (quantidade <= 0) {
            System.out.println("Quantidade inválida.");
            return;
        }

        if (quantidade > produto.getQuantidade_Estoque()) {
            System.out.println("Estoque insuficiente. Disponível: " + produto.getQuantidade_Estoque());
            return;
        }

        System.out.print("ID do cliente: ");
        int idCliente = lerInteiro();

        System.out.print("ID do funcionário: ");
        int idFuncionario = lerInteiro();

        System.out.print("Forma de pagamento: ");
        String formaPagamento = scanner.nextLine();

        System.out.print("Desconto (0 para nenhum): ");
        BigDecimal desconto = new BigDecimal(scanner.nextLine());

        // Calcular valor da venda
        BigDecimal valorTotal = produto.getPreco().multiply(new BigDecimal(quantidade));
        // Aplicar desconto se houver
        if (desconto != null && desconto.compareTo(BigDecimal.ZERO) > 0) {
            valorTotal = valorTotal.subtract(desconto);
            if (valorTotal.compareTo(BigDecimal.ZERO) < 0) {
                valorTotal = BigDecimal.ZERO;
            }
        }

        Venda venda = new Venda();
        venda.setData(LocalDate.now());
        venda.setIdProduto(produtoId);
        venda.setQuantidade(quantidade);
        venda.setValorTotal(valorTotal);
        venda.setIdCliente(idCliente);
        venda.setIdFuncionario(idFuncionario);
        venda.setFormaPagamento(formaPagamento);
        venda.setDesconto(desconto);

        // Atualizar estoque
        produto.setQuantidade_Estoque(produto.getQuantidade_Estoque() - quantidade);
        produtoRepository.atualizarProduto(produto);

        if (salvar(venda)) {
            System.out.println("Venda registrada com sucesso! ID: " + venda.getId());
            System.out.println("Valor total: R$ " + valorTotal);
        } else {
            System.out.println("Erro ao registrar venda.");
            // Reverter estoque
            produto.setQuantidade_Estoque(produto.getQuantidade_Estoque() + quantidade);
            produtoRepository.atualizarProduto(produto);
        }
    }

    /**
     * Atualiza os dados de uma venda existente
     */
    private void atualizarVenda() {
        System.out.print("Digite o ID da venda a ser atualizada: ");
        int id = lerInteiro();

        Venda venda = buscarPorId(id);
        if (venda == null) {
            System.out.println("Venda não encontrada.");
            return;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        System.out.println("\n--- ATUALIZAR VENDA ---");
        System.out.println("Venda atual:");
        System.out.println("ID: " + venda.getId());
        System.out.println("Data: " + venda.getData().format(formatter));
        System.out.println("ID do Cliente: " + venda.getIdCliente());
        System.out.println("ID do Funcionário: " + venda.getIdFuncionario());
        System.out.println("ID do Produto: " + venda.getIdProduto());

        // Buscar nome do produto
        Produto produto = produtoRepository.buscarProdutoPorId(venda.getIdProduto());
        if (produto != null) {
            System.out.println("Produto: " + produto.getNome());
        }

        System.out.println("Quantidade: " + venda.getQuantidade());
        System.out.println("Valor Total: R$ " + venda.getValorTotal());
        System.out.println("Forma de Pagamento: " + venda.getFormaPagamento());
        System.out.println("Desconto: R$ " + (venda.getDesconto() != null ? venda.getDesconto() : "0.00"));

        System.out.println("\nDigite os novos dados (deixe em branco para manter o valor atual):");

        System.out.print("Nova forma de pagamento [" + venda.getFormaPagamento() + "]: ");
        String formaPagamento = scanner.nextLine();
        if (!formaPagamento.isBlank()) {
            venda.setFormaPagamento(formaPagamento);
        }

        System.out.print("Novo desconto [" + (venda.getDesconto() != null ? venda.getDesconto() : "0.00") + "]: ");
        String descontoStr = scanner.nextLine();
        if (!descontoStr.isBlank()) {
            BigDecimal desconto = new BigDecimal(descontoStr);
            venda.setDesconto(desconto);

            // Recalcular valor total com novo desconto
            BigDecimal valorSemDesconto = produto.getPreco().multiply(new BigDecimal(venda.getQuantidade()));
            BigDecimal valorTotal = valorSemDesconto.subtract(desconto);
            if (valorTotal.compareTo(BigDecimal.ZERO) < 0) {
                valorTotal = BigDecimal.ZERO;
            }
            venda.setValorTotal(valorTotal);
        }

        if (atualizar(venda)) {
            System.out.println("Venda atualizada com sucesso!");
        } else {
            System.out.println("Erro ao atualizar venda.");
        }
    }

    /**
     * Cancela uma venda e devolve o produto ao estoque
     */
    private void cancelarVenda() {
        System.out.print("Digite o ID da venda a ser cancelada: ");
        int id = lerInteiro();

        Venda venda = buscarPorId(id);
        if (venda == null) {
            System.out.println("Venda não encontrada.");
            return;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        System.out.println("\n--- CANCELAR VENDA ---");
        System.out.println("Venda a ser cancelada:");
        System.out.println("ID: " + venda.getId());
        System.out.println("Data: " + venda.getData().format(formatter));
        System.out.println("ID do Cliente: " + venda.getIdCliente());
        System.out.println("ID do Funcionário: " + venda.getIdFuncionario());
        System.out.println("ID do Produto: " + venda.getIdProduto());

        // Buscar nome do produto
        Produto produto = produtoRepository.buscarProdutoPorId(venda.getIdProduto());
        String nomeProduto = produto != null ? produto.getNome() : "Produto não encontrado";

        System.out.println("Produto: " + nomeProduto);
        System.out.println("Quantidade: " + venda.getQuantidade());
        System.out.println("Valor Total: R$ " + venda.getValorTotal());
        System.out.println("Forma de Pagamento: " + venda.getFormaPagamento());

        System.out.print("\nConfirma o cancelamento desta venda? (S/N): ");
        String confirmacao = scanner.nextLine();

        if (confirmacao.equalsIgnoreCase("S")) {
            // Devolve o produto ao estoque
            if (produto != null) {
                produto.setQuantidade_Estoque(produto.getQuantidade_Estoque() + venda.getQuantidade());
                produtoRepository.atualizarProduto(produto);
            }

            if (remover(id)) {
                System.out.println("Venda cancelada com sucesso!");
                System.out.println("Os produtos foram devolvidos ao estoque.");
            } else {
                System.out.println("Erro ao cancelar venda.");

                // Reverte a devolução ao estoque
                if (produto != null) {
                    produto.setQuantidade_Estoque(produto.getQuantidade_Estoque() - venda.getQuantidade());
                    produtoRepository.atualizarProduto(produto);
                }
            }
        } else {
            System.out.println("Operação cancelada pelo usuário.");
        }
    }

    /**
     * Converte um ResultSet em um objeto Venda
     *
     * @param rs ResultSet da consulta
     * @return Objeto Venda
     */
    private Venda mapearResultSetParaVenda(ResultSet rs) throws SQLException {
        Venda venda = new Venda();
        venda.setId(rs.getInt("Id_Venda"));
        venda.setData(rs.getDate("Data").toLocalDate());
        venda.setValorTotal(rs.getBigDecimal("Valor_Total"));
        venda.setFormaPagamento(rs.getString("Forma_Pagamento"));
        venda.setDesconto(rs.getBigDecimal("Desconto"));
        venda.setIdCliente(rs.getInt("Id_Cliente"));
        venda.setIdFuncionario(rs.getInt("Id_Funcionario"));
        venda.setIdProduto(rs.getInt("Id_Produto"));
        venda.setQuantidade(rs.getInt("Quantidade"));
        return venda;
    }

    /**
     * Lê um número inteiro da entrada do usuário, tratando exceções
     *
     * @return Número inteiro lido
     */
    private int lerInteiro() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Entrada inválida. Digite um número inteiro: ");
            }
        }
    }
}