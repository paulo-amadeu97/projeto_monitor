package factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    private static final String URL = "jdbc:mysql://localhost:3306/data_server";
    private static final String USER = "root";
    private static final String PASSWORD = "1234";

    public static Connection createConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            connection.setAutoCommit(true);

            return connection;
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException("Failed to create connection to the database", e);
        }
    }
}
