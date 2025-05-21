package controllers;

import exceptions.AuthenticationException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import models.User;
import repository.UserRepository;
import services.LogInService;
import utilities.SessionManager;
import java.io.IOException;
import java.net.URL;

public class AssessorLoginController {

    @FXML
    private TextField assessorIdField;

    @FXML
    private PasswordField passwordField;

    private final LogInService logInService;
    private final UserRepository userRepository;
    private Stage indexStage;
    private Stage logInStage;

    public AssessorLoginController() {
        this.logInService = new LogInService();
        this.userRepository = new UserRepository();
    }

    public void setPreviousStages(Stage indexPage, Stage logInPage) {
        this.indexStage = indexPage;
        this.logInStage = logInPage;
    }

    @FXML
    private void handleLogin(ActionEvent event) {
        String assessorId = assessorIdField.getText();
        String password = passwordField.getText();

        try {
            boolean loginSuccessful = logInService.logInAssessor(assessorId, password);
            if (loginSuccessful) {
                User user = userRepository.findAssessor();
                if (user == null) {
                    showAlert("Error", "Assessor user not found in database");
                    return;
                }

                SessionManager.setCurrentUser(user);

                URL resourceUrl = getClass().getResource("/views/assessorpage.fxml");
                if (resourceUrl == null) {
                    showAlert("Error", "assessorpage.fxml not found at /views/assessorpage.fxml");
                    System.err.println("FXML file not found at /views/assessorpage.fxml");
                    return;
                }

                FXMLLoader loader = new FXMLLoader(resourceUrl);
                Parent root = loader.load();

                Stage assessorPageStage = new Stage();
                assessorPageStage.setScene(new Scene(root));
                assessorPageStage.setTitle("Assessor Page");
                assessorPageStage.show();

                if (indexStage != null) {
                    indexStage.close();
                }
                if (logInStage != null) {
                    logInStage.close();
                }
            }
        } catch (AuthenticationException e) {
            showAlert("Login Failed", e.getMessage());
        } catch (IOException e) {
            showAlert("Error", "Error navigating to assessor page: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            showAlert("Error", "An error occurred during login");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleBack(ActionEvent event) {
        try {
            URL resourceUrl = getClass().getResource("/views/index.fxml");
            if (resourceUrl == null) {
                showAlert("Error", "index.fxml not found at /views/index.fxml");
                System.err.println("FXML file not found at /views/index.fxml");
                return;
            }

            FXMLLoader loader = new FXMLLoader(resourceUrl);
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            showAlert("Error", "Error returning to main menu: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}