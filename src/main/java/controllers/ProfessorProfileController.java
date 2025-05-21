package controllers;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import models.Professors;
import models.Student;
import models.User;
import utilities.SceneLocator;
import utilities.SessionManager;

import java.io.File;
import java.io.IOException;

public class ProfessorProfileController {

    @FXML
    private AnchorPane sideBar;

    @FXML
    private HBox rootHBox;

    private boolean sidebarVisible = true;

    @FXML
    private Label firstNameLabel;

    @FXML
    private Label lastNameLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private ImageView profilePicture;

    @FXML
    public void initialize() {
        User user = SessionManager.getCurrentUser();
        Professors professor = SessionManager.getCurrentProfessor();

        if (user != null && professor != null) {
            firstNameLabel.setText(user.getFirstName());
            lastNameLabel.setText(user.getLastName());
            emailLabel.setText(user.getEmail());
            loadProfilePicture(user.getProfilePicturePath());
        }
    }

    private void loadProfilePicture(String picturePath) {
        try {
            if (picturePath != null && !picturePath.isEmpty()) {
                File imageFile = new File(picturePath);
                if (imageFile.exists()) {
                    profilePicture.setImage(new Image(imageFile.toURI().toString()));
                    return;
                }
            }
            File defaultImage = new File("uploads/profile_pictures/default_profile.png");
            if (defaultImage.exists()) {
                profilePicture.setImage(new Image(defaultImage.toURI().toString()));
            } else {
                System.err.println("Default profile picture not found at: uploads/profile_pictures/default_profile.png");
            }
        } catch (Exception e) {
            System.err.println("Failed to load profile picture: " + e.getMessage());
        }
    }

    @FXML
    private void toggleSideBar() {
        double sidebarWidth = sideBar.getPrefWidth();
        Stage stage = (Stage) rootHBox.getScene().getWindow();
        double currentWidth = stage.getWidth();

        if (sidebarVisible) {
            TranslateTransition transition = new TranslateTransition(Duration.millis(300), sideBar);
            transition.setFromX(0);
            transition.setToX(-sidebarWidth);

            transition.setOnFinished(e -> {
                sideBar.setVisible(false);
                sideBar.setManaged(false);
            });

            transition.play();
            stage.setWidth(currentWidth - sidebarWidth);
        } else {
            sideBar.setVisible(true);
            sideBar.setManaged(true);
            sideBar.setTranslateX(-sidebarWidth);

            TranslateTransition transition = new TranslateTransition(Duration.millis(300), sideBar);
            transition.setToX(0);
            transition.play();

            stage.setWidth(currentWidth + sidebarWidth);
        }

        sidebarVisible = !sidebarVisible;
    }

    @FXML
    private void goToLogOut(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(SceneLocator.LOGOUT));
        Parent root = loader.load();

        Stage logOutStage = new Stage();
        logOutStage.setScene(new Scene(root));
        logOutStage.setTitle("Log Out");
        logOutStage.show();

        LogOutController controller = loader.getController();
        Stage homeStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        controller.setPreviousStages(homeStage, logOutStage);
    }

    @FXML
    private void goToModify(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(SceneLocator.MODIFY));
        Parent root = loader.load();

        Stage modifyStage = new Stage();
        modifyStage.setScene(new Scene(root));
        modifyStage.setTitle("Modify Info");
        modifyStage.show();

        ModifyController controller = loader.getController();
        Stage profileStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        controller.setPreviousStages(profileStage, modifyStage);
    }

    @FXML
    private void goToProfessorHomePage(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(SceneLocator.PROFESSOR_HOMEPAGE));
        Parent root = loader.load();

        Stage profileStage = new Stage();
        profileStage.setScene(new Scene(root));
        profileStage.setTitle("Professor's Homepage");
        profileStage.show();

        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }

    @FXML
    private void goToProfessorOther(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(SceneLocator.OTHER));
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Professor's Courses");
        stage.show();

        Stage currentStage = (Stage)((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }
}