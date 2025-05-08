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

public class ProfileController {

    @FXML
    private AnchorPane sideBar;

    @FXML
    private HBox rootHBox;

    private boolean sidebarVisible = true;

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
}
