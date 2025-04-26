package com.audiomania.estoque;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProdutoRepository {
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USUARIO = "postgres";
    private static final String SENHA = "Ee2310";

    private Connection obterConexao() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }

    // Métodos CRUD
    public void adicionarProduto(Produto produto) {
        String sql = "INSERT INTO PRODUTO (Nome, Descricao, Preco, Quantidade_Estoque, Categoria, Marca) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, produto.getNome());
            stmt.setString(2, produto.getDescricao());
            stmt.setBigDecimal(3, produto.getPreco());
            stmt.setInt(4, produto.getQuantidade_Estoque());
            stmt.setString(5, produto.getCategoria());
            stmt.setString(6, produto.getMarca());

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        produto.setId_Produto(rs.getInt(1));
                    }
                }
                System.out.println("Produto adicionado com sucesso! ID: " + produto.getId_Produto());
            }
        } catch (SQLException e) {
            System.out.println("Erro ao adicionar produto: " + e.getMessage());
        }
    }

    public Produto buscarProdutoPorId(int id) {
        String sql = "SELECT * FROM PRODUTO WHERE Id_Produto = ?";

        try (Connection conn = obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Produto(
                            rs.getInt("Id_Produto"),
                            rs.getString("Nome"),
                            rs.getString("Descricao"),
                            rs.getBigDecimal("Preco"),
                            rs.getInt("Quantidade_Estoque"),
                            rs.getString("Categoria"),
                            rs.getString("Marca")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar produto: " + e.getMessage());
        }
        return null;
    }

    public List<Produto> listarTodosProdutos() {
        List<Produto> produtos = new ArrayList<>();
        String sql = "SELECT * FROM PRODUTO";

        try (Connection conn = obterConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                produtos.add(new Produto(
                        rs.getInt("Id_Produto"),
                        rs.getString("Nome"),
                        rs.getString("Descricao"),
                        rs.getBigDecimal("Preco"),
                        rs.getInt("Quantidade_Estoque"),
                        rs.getString("Categoria"),
                        rs.getString("Marca")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar produtos: " + e.getMessage());
        }
        return produtos;
    }

    public void atualizarProduto(Produto produto) {
        String sql = "UPDATE PRODUTO SET Nome = ?, Descricao = ?, Preco = ?, " +
                "Quantidade_Estoque = ?, Categoria = ?, Marca = ? WHERE Id_Produto = ?";

        try (Connection conn = obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, produto.getNome());
            stmt.setString(2, produto.getDescricao());
            stmt.setBigDecimal(3, produto.getPreco());
            stmt.setInt(4, produto.getQuantidade_Estoque());
            stmt.setString(5, produto.getCategoria());
            stmt.setString(6, produto.getMarca());
            stmt.setInt(7, produto.getId_Produto());

            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Produto atualizado com sucesso!");
            } else {
                System.out.println("Nenhum produto encontrado com o ID: " + produto.getId_Produto());
            }
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar produto: " + e.getMessage());
        }
    }

    public void removerProduto(int id) {
        String sql = "DELETE FROM PRODUTO WHERE Id_Produto = ?";

        try (Connection conn = obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Produto removido com sucesso!");
            } else {
                System.out.println("Nenhum produto encontrado com o ID: " + id);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao remover produto: " + e.getMessage());
        }
    }

    public void exibirMenu() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== GERENCIADOR DE PRODUTOS ===");
            System.out.println("1. Adicionar Produto");
            System.out.println("2. Buscar Produto por ID");
            System.out.println("3. Listar Todos os Produtos");
            System.out.println("4. Atualizar Produto");
            System.out.println("5. Remover Produto");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    System.out.print("Nome: ");
                    String nome = scanner.nextLine();
                    System.out.print("Descrição: ");
                    String descricao = scanner.nextLine();
                    System.out.print("Preço: ");
                    BigDecimal preco = new BigDecimal(scanner.nextLine());
                    System.out.print("Quantidade em Estoque: ");
                    int qtdEstoque = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Categoria: ");
                    String categoria = scanner.nextLine();
                    System.out.print("Marca: ");
                    String marca = scanner.nextLine();

                    Produto novoProduto = new Produto(nome, descricao, preco, qtdEstoque, categoria, marca);
                    this.adicionarProduto(novoProduto);
                    break;

                case 2:
                    System.out.print("ID do Produto: ");
                    int idBusca = scanner.nextInt();
                    Produto produto = this.buscarProdutoPorId(idBusca);
                    if (produto != null) {
                        System.out.println(produto);
                    } else {
                        System.out.println("Produto não encontrado.");
                    }
                    break;

                case 3:
                    List<Produto> produtos = this.listarTodosProdutos();
                    if (produtos.isEmpty()) {
                        System.out.println("Nenhum produto cadastrado.");
                    } else {
                        for (Produto p : produtos) {
                            System.out.println(p);
                        }
                    }
                    break;

                case 4:
                    System.out.print("ID do Produto a atualizar: ");
                    int idAtualizar = scanner.nextInt();
                    scanner.nextLine();

                    Produto produtoAtualizar = this.buscarProdutoPorId(idAtualizar);
                    if (produtoAtualizar != null) {
                        System.out.print("Novo Nome (" + produtoAtualizar.getNome() + "): ");
                        String novoNome = scanner.nextLine();
                        if (!novoNome.trim().isEmpty()) {
                            produtoAtualizar.setNome(novoNome);
                        }

                        System.out.print("Nova Descrição (" + produtoAtualizar.getDescricao() + "): ");
                        String novaDescricao = scanner.nextLine();
                        if (!novaDescricao.trim().isEmpty()) {
                            produtoAtualizar.setDescricao(novaDescricao);
                        }

                        System.out.print("Novo Preço (" + produtoAtualizar.getPreco() + "): ");
                        String precoStr = scanner.nextLine();
                        if (!precoStr.trim().isEmpty()) {
                            produtoAtualizar.setPreco(new BigDecimal(precoStr));
                        }

                        System.out.print("Nova Quantidade em Estoque (" + produtoAtualizar.getQuantidade_Estoque() + "): ");
                        String qtdStr = scanner.nextLine();
                        if (!qtdStr.trim().isEmpty()) {
                            produtoAtualizar.setQuantidade_Estoque(Integer.parseInt(qtdStr));
                        }

                        System.out.print("Nova Categoria (" + produtoAtualizar.getCategoria() + "): ");
                        String novaCat = scanner.nextLine();
                        if (!novaCat.trim().isEmpty()) {
                            produtoAtualizar.setCategoria(novaCat);
                        }

                        System.out.print("Nova Marca (" + produtoAtualizar.getMarca() + "): ");
                        String novaMarca = scanner.nextLine();
                        if (!novaMarca.trim().isEmpty()) {
                            produtoAtualizar.setMarca(novaMarca);
                        }

                        this.atualizarProduto(produtoAtualizar);
                    } else {
                        System.out.println("Produto não encontrado.");
                    }
                    break;

                case 5:
                    System.out.print("ID do Produto a remover: ");
                    int idRemover = scanner.nextInt();
                    this.removerProduto(idRemover);
                    break;

                case 0:
                    System.out.println("Voltando ao menu principal.");
                    scanner.close();
                    return;

                default:
                    System.out.println("Opção inválida!");
            }
        }
    }
}