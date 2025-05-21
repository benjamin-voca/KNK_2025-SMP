package controllers;

import models.StudentAccepted;
import services.TransferService;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import utilities.SceneLocator;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class TransferController {

    @FXML
    private AnchorPane sideBar;

    @FXML
    private HBox rootHBox;

    @FXML
    private TextField studentIdField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField currentProgramField;

    @FXML
    private ComboBox<String> targetProgramCombo;

    @FXML
    private Button submitButton;

    private boolean sidebarVisible = true;

    private List<Node> focusableNodes;

    private final TransferService transferService;

    public TransferController() {
        this.transferService = new TransferService();
    }

    @FXML
    public void initialize() {
        focusableNodes = Arrays.asList(
                studentIdField,
                nameField,
                currentProgramField,
                targetProgramCombo,
                submitButton
        );

        for (Node node : focusableNodes) {
            node.setOnKeyPressed(this::handleKeyNavigation);
        }

        String userId = transferService.getCurrentUserId();
        StudentAccepted student = transferService.getStudentById(userId);
        if (student != null) {
            studentIdField.setText(student.getId());
            nameField.setText(student.getName());
            currentProgramField.setText(student.getProgram());
        } else {
            studentIdField.setText("Error: User not found");
            nameField.setText("");
            currentProgramField.setText("");
            submitButton.setDisable(true);
        }

        String currentProgram = currentProgramField.getText();
        if (currentProgram != null && !currentProgram.isEmpty()) {
            targetProgramCombo.getItems().remove(currentProgram);
        }

        targetProgramCombo.setPromptText("Select Target Program");
    }

    @FXML
    private void handleKeyNavigation(KeyEvent event) {
        Node source = (Node) event.getSource();
        KeyCode code = event.getCode();

        if (source == targetProgramCombo) {
            if (code == KeyCode.L || code == KeyCode.LEFT) {
                selectPreviousComboItem();
                event.consume();
            } else if (code == KeyCode.R || code == KeyCode.RIGHT) {
                selectNextComboItem();
                event.consume();
            }
        }

        if (code == KeyCode.UP) {
            moveFocusUp(source);
            event.consume();
        } else if (code == KeyCode.DOWN) {
            moveFocusDown(source);
            event.consume();
        }
    }

    private void selectPreviousComboItem() {
        int currentIndex = targetProgramCombo.getSelectionModel().getSelectedIndex();
        if (currentIndex > 0) {
            targetProgramCombo.getSelectionModel().select(currentIndex - 1);
        } else {
            targetProgramCombo.getSelectionModel().select(targetProgramCombo.getItems().size() - 1);
        }
    }

    private void selectNextComboItem() {
        int currentIndex = targetProgramCombo.getSelectionModel().getSelectedIndex();
        if (currentIndex < targetProgramCombo.getItems().size() - 1) {
            targetProgramCombo.getSelectionModel().select(currentIndex + 1);
        } else {
            targetProgramCombo.getSelectionModel().select(0);
        }
    }

    private void moveFocusUp(Node currentNode) {
        int currentIndex = focusableNodes.indexOf(currentNode);
        if (currentIndex > 0) {
            focusableNodes.get(currentIndex - 1).requestFocus();
        } else {
            focusableNodes.get(focusableNodes.size() - 1).requestFocus();
        }
    }

    private void moveFocusDown(Node currentNode) {
        int currentIndex = focusableNodes.indexOf(currentNode);
        if (currentIndex < focusableNodes.size() - 1) {
            focusableNodes.get(currentIndex + 1).requestFocus();
        } else {
            focusableNodes.get(0).requestFocus();
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
        stage.setTitle("Your Profile");
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
        stage.setTitle("Transfer Request");
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
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        LogOutController controller = loader.getController();
        controller.setPreviousStages(currentStage, logOutStage);
    }

    @FXML
    private void submitTransferRequest() {
        String targetProgram = targetProgramCombo.getValue();

        if (targetProgram == null || targetProgram.isEmpty()) {
            System.out.println("Error: Please select a target program.");
            return;
        }

        boolean success = transferService.submitTransferRequest(
                studentIdField.getText(),
                nameField.getText(),
                currentProgramField.getText(),
                targetProgram
        );

        if (success) {
            System.out.println("Transfer Request Submitted Successfully:");
            System.out.println("Student ID: " + studentIdField.getText());
            System.out.println("Name: " + nameField.getText());
            System.out.println("Current Program: " + currentProgramField.getText());
            System.out.println("Target Program: " + targetProgram);
            targetProgramCombo.getSelectionModel().clearSelection();
        } else {
            System.out.println("Error: Failed to submit transfer request.");
        }
    }
}