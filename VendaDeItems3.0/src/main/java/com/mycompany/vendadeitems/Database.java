package com.mycompany.vendadeitems;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Database {

    // Caminho absoluto para o arquivo de configurações
    private static final String CONFIG_PATH = "C:\\Users\\HP\\Downloads\\VendaDeItems4.0\\VendaDeItems3.0\\VendaDeItems3.0\\config.properties";

    /**
     * Obtém uma conexão com o banco de dados usando as credenciais passadas como parâmetros.
     *
     * @param user  O nome de usuário do banco de dados.
     * @param senha A senha do banco de dados.
     * @return Uma conexão com o banco de dados.
     * @throws SQLException Se ocorrer um erro ao obter a conexão.
     */
    public static Connection getConnection(String user, String senha) throws SQLException {
        Properties prop = new Properties();
        try (InputStream input = new FileInputStream(CONFIG_PATH)) {
            prop.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao carregar configurações do banco de dados.", e);
        }

        String url = prop.getProperty("db.url");

        if (url == null || user == null || senha == null ||
            user.trim().isEmpty() || senha.trim().isEmpty()) {
            throw new SQLException("Usuário, senha ou URL não podem ser nulos ou vazios.");
        }
        return DriverManager.getConnection(url, user, senha);
    }

    /**
     * Obtém uma conexão com o banco de dados lendo as credenciais (user e senha)
     * diretamente do arquivo de configurações.
     *
     * @return Uma conexão com o banco de dados.
     * @throws SQLException Se ocorrer um erro ao obter a conexão.
     */
    public static Connection getConnection() throws SQLException {
        Properties prop = new Properties();
        try (InputStream input = new FileInputStream(CONFIG_PATH)) {
            prop.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao carregar configurações do banco de dados.", e);
        }

        String url = prop.getProperty("db.url");
        String user = prop.getProperty("db.user");
        String senha = prop.getProperty("db.password");

        if (url == null || user == null || senha == null ||
            user.trim().isEmpty() || senha.trim().isEmpty()) {
            throw new SQLException("Usuário, senha ou URL não podem ser nulos ou vazios.");
        }
        return DriverManager.getConnection(url, user, senha);
    }

    /**
     * Método para testar a conexão com o banco de dados.
     */
    public static void testarConexao() {
        try (Connection conexao = getConnection()) {
            if (conexao != null) {
                System.out.println("Conexão com o banco de dados estabelecida com sucesso!");
            } else {
                System.out.println("Falha ao estabelecer conexão com o banco de dados.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        testarConexao();
    }
}
