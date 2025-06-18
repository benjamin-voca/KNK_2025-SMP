package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utilities.SceneLocator;

import java.io.IOException;

public class IndexController {
    @FXML
    private void goToLogIn(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(SceneLocator.LOGIN));
        Parent root = loader.load();

        Stage logInStage = new Stage();
        logInStage.setScene(new Scene(root));
        logInStage.setTitle("Log In as Student");
        logInStage.show();

        LogInController controller = loader.getController();
        Stage indexStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        controller.setPreviousStages(indexStage, logInStage);
    }

    @FXML
    private void goToLoginProfessor(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(SceneLocator.LOGIN_PROFESSOR));
        Parent root = loader.load();

        Stage logInStage = new Stage();
        logInStage.setScene(new Scene(root));
        logInStage.setTitle("Log In as Professor");
        logInStage.show();

        LogInProfessorController controller = loader.getController();
        Stage indexStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        controller.setPreviousStages(indexStage, logInStage);
    }

    @FXML
    private void goToLoginAssessor(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(SceneLocator.ASSESSOR_LOGIN));
        Parent root = loader.load();

        Stage logInStage = new Stage();
        logInStage.setScene(new Scene(root));
        logInStage.setTitle("Log In as Assessor");
        logInStage.show();

        AssessorLoginController controller = loader.getController();
        Stage indexStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        controller.setPreviousStages(indexStage, logInStage);
    }

    @FXML
    private void closeIndex(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    private void goToRegisterPage(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(SceneLocator.REGISTER_PAGE));
        Parent root = loader.load();

        Stage registerStage = new Stage();
        registerStage.setScene(new Scene(root));
        registerStage.setTitle("Apply for registration");
        registerStage.show();

        RegisterPageController controller = loader.getController();
        Stage indexStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        controller.setPreviousStages(indexStage, registerStage);
    }
}
