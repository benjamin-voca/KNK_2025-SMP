package pages;
import database.DB_Connector;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import utilities.Clock;

import java.sql.*;

public class homepage {
    public Scene createDashboardScene() {
        Button dashboardButton = new Button("Dashboard Button");

        Clock clock = new Clock(200, 50, 100, 100);

        Pane dashboardLayout = new Pane();
        dashboardLayout.getChildren().addAll(dashboardButton, clock);

        Scene dashboardScene = new Scene(dashboardLayout, 700, 700);

        dashboardButton.setOnAction(e -> {
            System.out.println("Dashboard button clicked!");
        });

        return dashboardScene;
    }
}
