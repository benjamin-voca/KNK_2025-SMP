package controllers;

import javafx.animation.TranslateTransition;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import models.Student;
import models.User;
import models.dto.SubmissionViewDto;
import services.SubmissionsService;
import utilities.SessionManager;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

public class ProfileController {

    @FXML
    private AnchorPane sideBar;

    @FXML
    private HBox rootHBox;

    @FXML
    private Button toggleButton;

    @FXML
    private Label firstNameLabel;

    @FXML
    private Label lastNameLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private ImageView profilePicture;

    @FXML
    private TableView<SubmissionViewDto> submissionsTable;

    @FXML
    private TableColumn<SubmissionViewDto, String> assignmentColumn;

    @FXML
    private TableColumn<SubmissionViewDto, String> courseColumn;

    @FXML
    private TableColumn<SubmissionViewDto, String> submittedColumn;

    @FXML
    private TableColumn<SubmissionViewDto, String> gradeColumn;

    @FXML
    private TableColumn<SubmissionViewDto, String> feedbackColumn;

    @FXML
    private TableColumn<SubmissionViewDto, String> statusColumn;

    private boolean sidebarVisible = true;

    private final SubmissionsService submissionsService = new SubmissionsService();

    @FXML
    public void initialize() {
        User user = SessionManager.getCurrentUser();
        Student student = SessionManager.getCurrentStudent();

        if (user != null && student != null) {
            firstNameLabel.setText(user.getFirstName());
            lastNameLabel.setText(user.getLastName());
            emailLabel.setText(user.getEmail());
            loadProfilePicture(user.getProfilePicturePath());
            loadSubmissions(student.getId());
        } else {
            firstNameLabel.setText("No student logged in");
            lastNameLabel.setText("");
            emailLabel.setText("");
        }

        setupSubmissionsTable();
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
            File defaultImage = new File("Uploads/profile_pictures/default_profile.png");
            if (defaultImage.exists()) {
                profilePicture.setImage(new Image(defaultImage.toURI().toString()));
            } else {
                System.err.println("Default profile picture not found at: Uploads/profile_pictures/default_profile.png");
            }
        } catch (Exception e) {
            System.err.println("Failed to load profile picture: " + e.getMessage());
        }
    }

    private void setupSubmissionsTable() {
        assignmentColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getAssignmentTitle()));
        courseColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCourseName()));
        submittedColumn.setCellValueFactory(data -> {
            Timestamp submittedAt = data.getValue().getSubmittedAt();
            if (submittedAt != null) {
                return new SimpleStringProperty(new SimpleDateFormat("yyyy-MM-dd").format(submittedAt));
            }
            return new SimpleStringProperty("");
        });
        gradeColumn.setCellValueFactory(data -> {
            BigDecimal grade = data.getValue().getGrade();
            return new SimpleStringProperty(grade != null ? grade.toString() : "N/A");
        });
        feedbackColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getFeedback() != null ? data.getValue().getFeedback() : ""));
        statusColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getStatus()));
    }

    private void loadSubmissions(int studentId) {
        List<SubmissionViewDto> submissions = submissionsService.fetchSubmissionsForStudent(studentId);
        submissionsTable.getItems().setAll(submissions);
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/logout.fxml"));
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/modify.fxml"));
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
    private void goToHomePage(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/homepage.fxml"));
        Parent root = loader.load();
        Stage profileStage = new Stage();
        profileStage.setScene(new Scene(root));
        profileStage.setTitle("Home Page");
        profileStage.show();
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }

    @FXML
    private void goToStudentCourses(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/studentcourses.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Registered Courses");
        stage.show();
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }
}