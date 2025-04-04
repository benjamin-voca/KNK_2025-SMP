package Pages;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import Utilities.Clock;

public class Homepage {
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
