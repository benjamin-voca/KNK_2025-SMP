package pages;
import database.DB_Connector;
import java.sql.*;

public class homepage {
    public static void main(String[] args) {
        try {
            Connection conn = DB_Connector.getConnection();
            if(conn.isValid(1000)) {
                System.out.println("DB connected!");
            }

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
