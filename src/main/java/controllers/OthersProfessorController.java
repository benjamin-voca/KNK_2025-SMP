package controllers;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import models.User;
import services.AnnouncementService;
import services.AssignmentService;
import services.CourseService;
import services.GradeService;
import utilities.SceneLocator;
import utilities.SessionManager;

import java.io.IOException;

public class OthersProfessorController {

    private final CourseService courseService;
    private final GradeService gradeService;
    private final AssignmentService assignmentService;
    private final AnnouncementService announcementService;

    public OthersProfessorController() {
        this.courseService = new CourseService();
        this.gradeService = new GradeService();
        this.assignmentService = new AssignmentService();
        this.announcementService = new AnnouncementService();
    }

    @FXML
    private AnchorPane sideBar;

    @FXML
    private HBox rootHBox;

    @FXML
    private ListView<String> coursesListView;

    @FXML
    private ListView<String> gradesListView;

    @FXML
    private ListView<String> assignmentsListView;

    @FXML
    private ListView<String> announcementsListView;

    private boolean sidebarVisible = true;

    @FXML
    public void initialize() {
        loadCourses();
        loadGrades();
        loadAssignments();
        loadAnnouncements();
    }

    private void loadCourses() {
        User user = SessionManager.getCurrentUser();
        if (user == null) return;
        coursesListView.getItems().setAll(courseService.getCoursesByProfessorId(user.getId()));
    }

    private void loadGrades() {
        User user = SessionManager.getCurrentUser();
        if (user == null) return;
        gradesListView.getItems().setAll(gradeService.getGradesByProfessorId(user.getId()));
    }

    private void loadAssignments() {
        User user = SessionManager.getCurrentUser();
        if (user == null) return;
        assignmentsListView.getItems().setAll(assignmentService.getAssignmentsByProfessorId(user.getId()));
    }

    private void loadAnnouncements() {
        User user = SessionManager.getCurrentUser();
        if (user == null) return;
        announcementsListView.getItems().setAll(announcementService.getAnnouncementsByProfessorId(user.getId()));
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
    private void goToHomePageProfessor(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(SceneLocator.PROFESSOR_HOMEPAGE));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Professor's Homepage");
        stage.show();
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }

    @FXML
    private void goToProfileProfessor(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(SceneLocator.PROFESSOR_PROFILE));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Professor's Profile");
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