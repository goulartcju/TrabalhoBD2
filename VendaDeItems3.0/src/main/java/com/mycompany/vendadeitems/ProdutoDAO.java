package com.mycompany.vendadeitems;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class ProdutoDAO {

    private static final String SQL_INSERT = "INSERT INTO produto (nome, descricao, preco, estoque) VALUES (?, ?, ?, ?)";

    public boolean inserirProduto(Produto produto, Connection conexao) {
        // Validação dos dados
        if (produto.getPreco() <= 0 || produto.getEstoque() < 0) {
            JOptionPane.showMessageDialog(null, "Preço e estoque devem ser valores válidos.");
            return false;
        }

        try (PreparedStatement stmt = conexao.prepareStatement(SQL_INSERT)) {
            // Configura os parâmetros da instrução SQL
            stmt.setString(1, produto.getNome());
            stmt.setString(2, produto.getDescricao());
            stmt.setDouble(3, produto.getPreco());
            stmt.setInt(4, produto.getEstoque());

            // Executa a instrução SQL
            int linhasAfetadas = stmt.executeUpdate();

            // Verifica se o INSERT foi bem-sucedido
            if (linhasAfetadas > 0) {
                JOptionPane.showMessageDialog(null, "Produto inserido com sucesso!");
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Falha ao inserir o produto.");
                return false;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao inserir o produto: " + e.getMessage());
            return false;
        }
    }
}