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
import models.Courses;
import models.Student;
import services.CourseService;
import utilities.SessionManager;

import java.io.IOException;
import java.util.List;

public class StudentCoursesController {
    @FXML
    private AnchorPane sideBar;

    @FXML
    private HBox rootHBox;

    private boolean sidebarVisible = true;

    @FXML
    private ListView<String> coursesListView;

    private final CourseService courseService = new CourseService();

    @FXML
    public void initialize() {
        Student student = SessionManager.getCurrentStudent();
        if (student != null) {
            int studentId = student.getId();
            List<Courses> courses = courseService.fetchCoursesForStudent(studentId);
            displayCourses(courses);
        } else {
            coursesListView.getItems().add("No student logged in.");
        }
    }

    private void displayCourses(List<Courses> courses) {
        for (Courses course : courses) {
            coursesListView.getItems().add(course.getCourseName() + " (" + course.getCourseCode() + ")");
        }
    }

    @FXML
    private void toggleSideBar() {
        double sidebarWidth = sideBar.getWidth();
        Stage stage = (Stage) rootHBox.getScene().getWindow();

        if (sidebarVisible) {
            TranslateTransition sidebarTransition = new TranslateTransition(Duration.millis(300), sideBar);
            sidebarTransition.setByX(-sidebarWidth);
            sidebarTransition.play();

            sidebarTransition.setOnFinished(event -> {
                sideBar.setVisible(false);
                sideBar.setManaged(false);
            });

            stage.setWidth(stage.getWidth() - sidebarWidth);
        } else {
            sideBar.setManaged(true);
            sideBar.setVisible(true);

            TranslateTransition sidebarTransition = new TranslateTransition(Duration.millis(300), sideBar);
            sidebarTransition.setByX(sidebarWidth);
            sidebarTransition.play();

            stage.setWidth(stage.getWidth() + sidebarWidth);
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
    private void goToProfile(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/profile.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Your Profile");
        stage.show();

        Stage currentStage = (Stage)((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }

    @FXML
    private void goToHomePage(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/homepage.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Home Page");
        stage.show();

        Stage currentStage = (Stage)((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }

    @FXML
    private void goToClasses(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/classes.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Classes");
        stage.show();
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }
}