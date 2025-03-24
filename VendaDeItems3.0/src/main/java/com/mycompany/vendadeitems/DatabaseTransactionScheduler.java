package com.mycompany.vendadeitems;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DatabaseTransactionScheduler {
    
    // Função para executar a transação no banco de dados
    public void executeTransaction() {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            // Estabelecendo a conexão com o banco de dados usando as credenciais da classe Database
            conn = Database.getConnection();
            
            // SQL para chamar a função no banco de dados
            String sql = "SELECT execute_scheduled_transaction();";
            
            // Preparando e executando o comando SQL
            pstmt = conn.prepareStatement(sql);
            pstmt.execute();
            System.out.println("Transação executada com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao executar a transação: " + e.getMessage());
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.err.println("Erro ao fechar a conexão: " + e.getMessage());
            }
        }
    }

    // Método para agendar a execução da transação
    public void scheduleTransaction(long initialDelay, long period) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        // Agendar a tarefa
        scheduler.scheduleAtFixedRate(this::executeTransaction, initialDelay, period, TimeUnit.MILLISECONDS);
        System.out.println("Agendamento iniciado: A transação será executada a cada " + period + " milissegundos.");
    }
}
