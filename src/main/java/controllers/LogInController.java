package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import models.Student;
import models.User;
import repository.UserRepository;
import services.StudentService;
import exceptions.AuthenticationException;
import utilities.SessionManager;

import java.io.IOException;

public class LogInController {
    @FXML
    private TextField studentNumberField;

    @FXML
    private PasswordField passwordField;

    private Stage indexStage;
    private Stage logInStage;
    private final StudentService studentService = new StudentService();
    private final UserRepository userRepository = new UserRepository();

    public void setPreviousStages(Stage indexPage, Stage logInPage) {
        this.indexStage = indexPage;
        this.logInStage = logInPage;
    }

    @FXML
    private void goToHomePage(ActionEvent event) throws IOException {
        String studentNumber = studentNumberField.getText();
        String password = passwordField.getText();

        try {
            Student student = studentService.login(studentNumber, password);
            User user = userRepository.findById(student.getUserId());

            SessionManager.setCurrentUser(user);
            SessionManager.setCurrentStudent(student);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/homepage.fxml"));
            Parent root = loader.load();

            Stage homePageStage = new Stage();
            homePageStage.setScene(new Scene(root));
            homePageStage.setTitle("Home Page");
            homePageStage.show();

            if (indexStage != null) indexStage.close();
            if (logInStage != null) logInStage.close();

        } catch (AuthenticationException e) {
            showAlert("Login Failed", "Invalid student number or password");
        } catch (Exception e) {
            showAlert("Error", "An error occurred during login");
            e.printStackTrace();
        }
    }

    @FXML
    private void closeLogIn(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}