package Pages;

import Database.DB_Connector;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.SQLException;

public class MainApp extends Application {
    public void start(Stage primaryStage) {
        Login loginPage = new Login();
        Homepage dashboardPage = new Homepage();

        Scene loginScene = loginPage.createLoginScene();
        Scene dashboardScene = dashboardPage.createDashboardScene();

        primaryStage.setScene(loginScene);
        primaryStage.setTitle("Login Page");
        primaryStage.show();

        loginPage.getLoginButton().setOnAction(e -> {

            if(checkDBConnection()) {
                primaryStage.setScene(dashboardScene);
            } else {
                System.out.println("Failed to connect!");
            }
        });
    }

    private boolean checkDBConnection() {
        try{
            Connection conn = DB_Connector.getConnection();
            if(conn != null && !conn.isClosed()) {
                System.out.println("Database connected successfully!");
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Database connection failed: " + e.getMessage());
        }
        return false;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
