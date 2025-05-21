package controllers;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import models.Notifications;
import services.NotificationService;
import utilities.SceneLocator;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

public class ProfessorHomePageController {

    @FXML
    private AnchorPane sideBar;

    @FXML
    private HBox rootHBox;

    @FXML
    private VBox notificationsContainer;

    private boolean sidebarVisible = true;

    private final NotificationService notificationService = new NotificationService();

    @FXML
    public void initialize() {
        loadNotifications();
    }

    private void loadNotifications() {
        notificationsContainer.getChildren().clear();

        List<Notifications> notifications = notificationService.fetchNotifications();

        for (Notifications notification : notifications) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(SceneLocator.NOTIFICATIONS));
                VBox notificationItem = loader.load();

                Label titleLabel = (Label) notificationItem.lookup("#titleLabel");
                Label dateLabel = (Label) notificationItem.lookup("#dateLabel");
                Label contentLabel = (Label) notificationItem.lookup("#contentLabel");

                titleLabel.setText(notification.getTitle());
                dateLabel.setText(new SimpleDateFormat("MMM dd, yyyy HH:mm").format(notification.getCreatedAt()));
                contentLabel.setText(notification.getContent());

                notificationsContainer.getChildren().add(notificationItem);
            } catch (IOException e) {
                e.printStackTrace();
            }
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

    @FXML
    private void goToProfessorProfile(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(SceneLocator.PROFESSOR_PROFILE));
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Professor's Profile");
        stage.show();

        Stage currentStage = (Stage)((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }

    @FXML
    private void goToLogOutProfessor(ActionEvent event) throws IOException {
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
}
