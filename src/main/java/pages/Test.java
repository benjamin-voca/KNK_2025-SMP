package pages;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Test extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        // Load the FXML file
        Parent root = FXMLLoader.load(getClass().getResource("/views/index.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Welcome");

        // Load and set the logo icon
        Image icon = new Image(getClass().getResourceAsStream("/images/logo.png")); // Adjust path to your logo
        stage.getIcons().add(icon);

        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}