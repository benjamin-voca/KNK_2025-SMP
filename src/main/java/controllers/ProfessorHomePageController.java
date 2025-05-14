package controllers;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class ProfessorHomePageController {

    @FXML
    private AnchorPane sideBar;

    @FXML
    private HBox rootHBox;

    private boolean sidebarVisible = true;

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
    private void goToManageStudents(ActionEvent event) {
        System.out.println("Navigating to Manage Students...");
    }

    @FXML
    private void goToSchedule(ActionEvent event) {
        System.out.println("Navigating to Manage Students...");
    }

    @FXML
    private void goToProfProfile(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/professorprofile.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Your Profile");
        stage.show();

        Stage currentStage = (Stage)((Node) event.getSource()).getScene().getWindow();
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
        Stage homeStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        controller.setPreviousStages(homeStage, logOutStage);
    }
}
