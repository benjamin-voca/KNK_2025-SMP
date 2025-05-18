package pages;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Registerpage extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println(getClass().getResource("/utilities/Up_frame.png"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/registerpage.fxml"));
        Scene scene = new Scene(loader.load(), 800, 700);
        primaryStage.setTitle("Registration Page");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}