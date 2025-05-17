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
import utilities.SessionManager;
import database.DB_Connector;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OthersProfessorController {

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

        List<String> courses = new ArrayList<>();
        String query = "SELECT c.course_name, c.course_code " +
                "FROM courses c " +
                "JOIN professors p ON c.professor_id = p.id " +
                "WHERE p.user_id = ?";
        try (Connection conn = DB_Connector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, user.getId());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                courses.add(rs.getString("course_name") + " (" + rs.getString("course_code") + ")");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        coursesListView.getItems().setAll(courses);
    }

    private void loadGrades() {
        User user = SessionManager.getCurrentUser();
        if (user == null) return;

        List<String> grades = new ArrayList<>();
        String query = "SELECT u.first_name, u.last_name, c.course_name, g.grade " +
                "FROM grades g " +
                "JOIN students s ON g.student_id = s.student_id " +
                "JOIN users u ON s.user_id = u.user_id " +
                "JOIN courses c ON g.course_id = c.id " +
                "JOIN professors p ON c.professor_id = p.id " +
                "WHERE p.user_id = ?";
        try (Connection conn = DB_Connector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, user.getId());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                grades.add(rs.getString("first_name") + " " + rs.getString("last_name") +
                        " - " + rs.getString("course_name") + ": " + rs.getDouble("grade"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        gradesListView.getItems().setAll(grades);
    }

    private void loadAssignments() {
        User user = SessionManager.getCurrentUser();
        if (user == null) return;

        List<String> assignments = new ArrayList<>();
        String query = "SELECT a.title, a.due_date, c.course_name " +
                "FROM assignments a " +
                "JOIN courses c ON a.course_id = c.id " +
                "JOIN professors p ON c.professor_id = p.id " +
                "WHERE p.user_id = ?";
        try (Connection conn = DB_Connector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, user.getId());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                assignments.add(rs.getString("title") + " (" + rs.getString("course_name") +
                        ") - Due: " + rs.getTimestamp("due_date"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        assignmentsListView.getItems().setAll(assignments);
    }

    private void loadAnnouncements() {
        User user = SessionManager.getCurrentUser();
        if (user == null) return;

        List<String> announcements = new ArrayList<>();
        String query = "SELECT a.title, a.created_at, c.course_name " +
                "FROM announcements a " +
                "JOIN courses c ON a.course_id = c.id " +
                "JOIN professors p ON c.professor_id = p.id " +
                "WHERE p.user_id = ?";
        try (Connection conn = DB_Connector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, user.getId());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                announcements.add(rs.getString("title") + " (" + rs.getString("course_name") +
                        ") - Posted: " + rs.getTimestamp("created_at"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        announcementsListView.getItems().setAll(announcements);
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/professorhomepage.fxml"));
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/professorprofile.fxml"));
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