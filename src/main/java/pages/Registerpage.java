package pages;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;

public class Registerpage extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/registerpage.fxml"));
        Scene scene = new Scene(loader.load(), 800, 700);
        primaryStage.setTitle("Registration Page");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        Image icon = new Image(getClass().getResourceAsStream("/images/logo.png"));
        primaryStage.getIcons().add(icon);
        primaryStage.show();
    }
}