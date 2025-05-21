package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utilities.SceneLocator;
import utilities.SessionManager;

import java.io.IOException;

public class LogOutController {
    private Stage homeStage;
    private Stage logOutStage;

    public void setPreviousStages(Stage homePage, Stage logOutPage) {
        this.homeStage = homePage;
        this.logOutStage = logOutPage;
    }

    @FXML
    private void goToIndex(ActionEvent event) throws IOException {
        SessionManager.clearSession();

        FXMLLoader loader = new FXMLLoader(getClass().getResource(SceneLocator.INDEX));
        Parent root = loader.load();

        Stage indexStage = new Stage();
        indexStage.setScene(new Scene(root));
        indexStage.setTitle("Welcome");
        indexStage.show();

        if (homeStage != null) {
            homeStage.close();
        }

        if (logOutStage != null) {
            logOutStage.close();
        }
    }

    @FXML
    private void closeLogOut(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
