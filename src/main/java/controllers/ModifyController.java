package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class ModifyController {
    private Stage profileStage;
    private Stage modifyStage;

    public void setPreviousStages(Stage profilePage, Stage modifyPage) {
        this.profileStage = profilePage;
        this.modifyStage = modifyPage;
    }

    @FXML
    private void changeProfilePicture(ActionEvent event) {

    }
}
