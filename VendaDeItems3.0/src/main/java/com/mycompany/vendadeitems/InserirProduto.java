package com.mycompany.vendadeitems;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class InserirProduto {

    private static final String SQL_INSERT = "INSERT INTO produto (nome, descricao, preco, estoque) VALUES (?, ?, ?, ?)";

    public InserirProduto(String nome, String descricao, double preco, int estoque) {
        try (Connection conexao = Database.getConnection()) {
            conexao.setAutoCommit(false); // Desativa o auto-commit para controle manual

            try (PreparedStatement stmt = conexao.prepareStatement(SQL_INSERT)) {
                // Configura os parâmetros da instrução SQL
                stmt.setString(1, nome);
                stmt.setString(2, descricao);
                stmt.setDouble(3, preco);
                stmt.setInt(4, estoque);

                int linhasAfetadas = stmt.executeUpdate();

                if (linhasAfetadas > 0) {
                    conexao.commit(); // Confirma a transação
                    JOptionPane.showMessageDialog(null, "Produto inserido com sucesso!");
                } else {
                    conexao.rollback(); // Desfaz a operação em caso de falha
                    JOptionPane.showMessageDialog(null, "Falha ao inserir o produto.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException e) {
                conexao.rollback(); // Rollback em caso de erro
                System.err.println("Erro ao inserir o produto: " + e.getMessage());
                JOptionPane.showMessageDialog(null, "Erro ao inserir o produto: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}