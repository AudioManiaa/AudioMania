package com.audiomania.service;

import java.time.LocalDate;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.DriverManager;

import com.audiomania.entities.FuncionarioEntity;

public class FuncionarioService {

    private static Connection getConnection() throws Exception {
        return DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/postgres",
                "postgres",
                "Ee2310");
    }

    public static FuncionarioEntity autenticar(String cpf, String senha) {
        try {
            Connection conn = getConnection();

            String sql = "SELECT Id_Funcionario, Nome, CPF, Cargo, Telefone, Data_Admissao FROM FUNCIONARIO WHERE CPF = ? AND Senha = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, cpf);
            stmt.setString(2, senha);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                FuncionarioEntity funcionario = new FuncionarioEntity();
                funcionario.setId(rs.getInt("Id_Funcionario"));
                funcionario.setNome(rs.getString("Nome"));
                funcionario.setCpf(rs.getString("CPF"));
                funcionario.setCargo(rs.getString("Cargo"));
                funcionario.setTelefone(rs.getString("Telefone"));

                // Se a data de admissão estiver no ResultSet
                java.sql.Date dataSQL = rs.getDate("Data_Admissao");
                if (dataSQL != null) {
                    funcionario.setDataAdmissao(dataSQL.toLocalDate());
                }

                rs.close();
                stmt.close();
                conn.close();

                return funcionario;
            }

            rs.close();
            stmt.close();
            conn.close();

            return null;
        } catch (Exception e) {
            System.out.println("Erro ao autenticar: " + e.getMessage());
            return null;
        }
    }

    public static boolean cadastrarFuncionario(String nome, String cpf, String cargo,
                                               String telefone, String senha) {
        try {
            Connection conn = getConnection();

            String checkSql = "SELECT COUNT(*) FROM FUNCIONARIO WHERE CPF = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setString(1, cpf);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                rs.close();
                checkStmt.close();
                conn.close();
                return false;
            }

            String sql = "INSERT INTO FUNCIONARIO (Nome, CPF, Cargo, Telefone, Data_Admissao, Senha) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nome);
            stmt.setString(2, cpf);
            stmt.setString(3, cargo);
            stmt.setString(4, telefone);
            stmt.setDate(5, java.sql.Date.valueOf(LocalDate.now()));
            stmt.setString(6, senha);

            int linhasAfetadas = stmt.executeUpdate();

            stmt.close();
            conn.close();

            return linhasAfetadas > 0;
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar funcionário: " + e.getMessage());
            return false;
        }
    }

    public static void fecharRecursos() {

    }
}