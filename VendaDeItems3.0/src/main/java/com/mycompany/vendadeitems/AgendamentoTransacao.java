package com.mycompany.vendadeitems;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AgendamentoTransacao {

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        executor.execute(() -> {
            try {
                executarTransacao(1, 150.75);
            } catch (SQLException e) {
                System.err.println("Erro ao executar transação 1: " + e.getMessage());
            }
        });

        executor.execute(() -> {
            try {
                executarTransacao(2, 200.50);
            } catch (SQLException e) {
                System.err.println("Erro ao executar transação 2: " + e.getMessage());
            }
        });

        executor.shutdown();
    }

    private static void executarTransacao(int usuarioId, double valor) throws SQLException {
        try (Connection connection = Database.getConnection()) {
            connection.setAutoCommit(false);

            LocalDateTime inicio = LocalDateTime.now();
            System.out.println("[" + inicio + "] Transação iniciada para usuário " + usuarioId);

            try (PreparedStatement stmt = connection.prepareStatement(
                    "INSERT INTO venda (data_venda, id_usuario, total) VALUES (?, ?, ?)")
            ) {
                stmt.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
                stmt.setInt(2, usuarioId);
                stmt.setDouble(3, valor);
                stmt.executeUpdate();

                connection.commit();
                LocalDateTime fim = LocalDateTime.now();
                System.out.println("[" + fim + "] Transação concluída com sucesso para usuário " + usuarioId);
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
        }
    }
}
