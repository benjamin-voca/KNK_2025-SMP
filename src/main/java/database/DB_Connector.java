package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB_Connector {
    private static Connection connection = null;
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/projekti_knk";
    private static final String USER = "postgres";
    private static final String PASSWORD = "123";
    public static Connection getConnection() {
        if(connection == null){
            try {
                connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return connection;
    }
}
