package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
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

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/index.fxml"));
        Parent root = loader.load();

        Stage loginStage = new Stage();
        loginStage.setScene(new Scene(root));
        loginStage.show();

        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }

    @FXML
    private void closeLogOut(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
