package controllers;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import models.Student;
import models.dto.CreateSubmissionDto;
import models.dto.SubmissionViewDto;
import services.SubmissionsService;
import utilities.SceneLocator;
import utilities.SessionManager;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

public class UploadSubmissionController {

    @FXML
    private HBox rootHBox;

    @FXML
    private AnchorPane sideBar;

    @FXML
    private Button toggleButton;

    @FXML
    private ComboBox<SubmissionViewDto> assignmentComboBox;

    @FXML
    private TextArea contentTextArea;

    private boolean sidebarVisible = true;

    private final SubmissionsService submissionsService = new SubmissionsService();

    @FXML
    public void initialize() {
        Student student = SessionManager.getCurrentStudent();
        if (student != null) {
            loadAssignments(student.getId());
        } else {
            assignmentComboBox.getItems().clear();
            contentTextArea.setDisable(true);
            Alert alert = new Alert(Alert.AlertType.ERROR, "No student logged in.");
            alert.showAndWait();
        }
    }

    private void loadAssignments(int studentId) {
        List<SubmissionViewDto> assignments = submissionsService.fetchAvailableAssignmentsForStudent(studentId);
        assignmentComboBox.getItems().setAll(assignments);
        assignmentComboBox.setCellFactory(lv -> new ListCell<SubmissionViewDto>() {
            @Override
            protected void updateItem(SubmissionViewDto item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getAssignmentTitle() + " (" + item.getCourseName() + ")");
            }
        });
        assignmentComboBox.setButtonCell(new ListCell<SubmissionViewDto>() {
            @Override
            protected void updateItem(SubmissionViewDto item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getAssignmentTitle() + " (" + item.getCourseName() + ")");
            }
        });
    }

    @FXML
    private void submitSubmission(ActionEvent event) {
        Student student = SessionManager.getCurrentStudent();
        SubmissionViewDto selectedAssignment = assignmentComboBox.getSelectionModel().getSelectedItem();
        String content = contentTextArea.getText().trim();

        if (student == null || selectedAssignment == null || content.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please select an assignment and enter content.");
            alert.showAndWait();
            return;
        }

        CreateSubmissionDto dto = new CreateSubmissionDto(
                selectedAssignment.getId(),
                student.getId(),
                new Timestamp(System.currentTimeMillis()),
                content,
                null,
                null,
                "Submitted"
        );

        try {
            submissionsService.create(dto);
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Submission uploaded successfully.");
            alert.showAndWait();
            goToProfile(event);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Failed to upload submission: " + e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    private void cancelSubmission(ActionEvent event) throws IOException {
        goToProfile(event);
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
    private void goToHomePage(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(SceneLocator.HOMEPAGE));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Home Page");
        stage.show();
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }

    @FXML
    private void goToProfile(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(SceneLocator.PROFILE));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Profile");
        stage.show();
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }

    @FXML
    private void goToStudentCourses(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(SceneLocator.STUDENT_COURSES));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Registered Courses");
        stage.show();
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }

    @FXML
    private void goToClasses(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(SceneLocator.CLASSES));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Classes");
        stage.show();
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }

    @FXML
    private void goToTransfer(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(SceneLocator.TRANSFER));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Transfer");
        stage.show();
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
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
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        controller.setPreviousStages(currentStage, logOutStage);
    }
}