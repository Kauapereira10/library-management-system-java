package util;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionFactory {

    private static final String URL =
        "jdbc:mysql://localhost:3306/library_db?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "0402";

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // carrega o driver
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao conectar com o banco de dados", e);
        }
    }
}
