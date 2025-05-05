package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LogInController {
    private Stage indexStage;
    private Stage logInStage;

    public void setPreviousStages(Stage indexPage, Stage logInPage) {
        this.indexStage = indexPage;
        this.logInStage = logInPage;
    }

    @FXML
    private void goToHomePage(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/homepage.fxml"));
        Parent root = loader.load();

        Stage homePageStage = new Stage();
        homePageStage.setScene(new Scene(root));
        homePageStage.setTitle("Home Page");
        homePageStage.show();

        if (indexStage != null) {
            indexStage.close();
        }

        if (logInStage != null) {
            logInStage.close();
        }
    }

    @FXML
    private void closeLogIn(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
