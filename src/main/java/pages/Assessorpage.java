package pages;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Assessorpage extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/AssessorPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 765, 704);
        primaryStage.setMinHeight(420);
        primaryStage.setMinWidth(620);
        primaryStage.setTitle("Assessor Dashboard");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

