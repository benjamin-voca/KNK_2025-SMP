package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB_Connector {
    private static Connection connection = null;
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/projekti_knk";
    private static final String USER = "postgres";
    private static final String PASSWORD = "123";

    public static synchronized Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                throw e;
            }
        }
        return connection;
    }

    public static synchronized void closeConnection() {
        if (connection != null) {
            try {
                if (!connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                connection = null;
            }
        }
    }
}