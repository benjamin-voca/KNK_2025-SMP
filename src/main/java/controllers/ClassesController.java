package controllers;

import javafx.animation.TranslateTransition;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import models.Student;
import models.dto.ClassViewDto;
import services.ClassService;
import utilities.SessionManager;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

public class ClassesController {

    @FXML
    private HBox rootHBox;

    @FXML
    private AnchorPane sideBar;

    @FXML
    private Button toggleButton;

    @FXML
    private TableView<ClassViewDto> classesTable;

    @FXML
    private TableColumn<ClassViewDto, String> classNameColumn;

    @FXML
    private TableColumn<ClassViewDto, String> courseNameColumn;

    @FXML
    private TableColumn<ClassViewDto, String> scheduleColumn;

    @FXML
    private TableColumn<ClassViewDto, String> locationColumn;

    @FXML
    private TableColumn<ClassViewDto, String> classTypeColumn;

    @FXML
    private TableColumn<ClassViewDto, String> durationColumn;

    private boolean sidebarVisible = true;

    private final ClassService classService = new ClassService();

    @FXML
    public void initialize() {
        Student student = SessionManager.getCurrentStudent();
        if (student != null) {
            loadClasses(student.getId());
        } else {
            classesTable.getItems().clear();
        }

        setupClassesTable();
    }

    private void setupClassesTable() {
        classNameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getClassName()));
        courseNameColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCourseName()));
        scheduleColumn.setCellValueFactory(data -> {
            Timestamp schedule = data.getValue().getSchedule();
            if (schedule != null) {
                return new SimpleStringProperty(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(schedule));
            }
            return new SimpleStringProperty("");
        });
        locationColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getLocation() != null ? data.getValue().getLocation() : ""));
        classTypeColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getClassType() != null ? data.getValue().getClassType() : ""));
        durationColumn.setCellValueFactory(data -> {
            Integer duration = data.getValue().getDuration();
            return new SimpleStringProperty(duration != null ? duration + " min" : "");
        });
    }

    private void loadClasses(int studentId) {
        List<ClassViewDto> classes = classService.fetchClassesForStudent(studentId);
        classesTable.getItems().setAll(classes);
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/homepage.fxml"));
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/profile.fxml"));
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/studentcourses.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Registered Courses");
        stage.show();
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }

    @FXML
    private void goToTransfer(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/transfer.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Transfer Request");
        stage.show();
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
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
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        controller.setPreviousStages(currentStage, logOutStage);
    }
}