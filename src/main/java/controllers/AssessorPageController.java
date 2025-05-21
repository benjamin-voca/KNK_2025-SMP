package controllers;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import models.StartingStudent;
import repository.StudentStartingRepository;
//import utilities.SceneLocator;

import java.io.File;
//import java.io.IOException;
import java.util.List;

public class AssessorPageController {

    @FXML private TilePane studentCardsContainer;
    @FXML private Label errorLabel;
    @FXML private Button finishButton;

    private final StudentStartingRepository repository = new StudentStartingRepository();
    private static final double MAX_IMAGE_WIDTH = 800.0;
    private static final double MAX_IMAGE_HEIGHT = 600.0;
    private static final double ZOOM_FACTOR = 1.3;
    private static final double DEFAULT_ZOOM = 1.0;

    @FXML
    public void initialize() {
        try {
            List<StartingStudent> students = repository.findAll();

            for (StartingStudent student : students) {

            }
            if (students.isEmpty()) {
                errorLabel.setText("No student records found.");
                errorLabel.setTextFill(Color.RED);
                errorLabel.setVisible(true);
                finishButton.setDisable(false);
                return;
            }

            finishButton.disableProperty().bind(Bindings.isNotEmpty(studentCardsContainer.getChildren()));

            for (StartingStudent student : students) {
                HBox card = createStudentCard(student);
                studentCardsContainer.getChildren().add(card);

            }
        } catch (RuntimeException e) {
            errorLabel.setText("Error loading student data: " + e.getMessage());
            errorLabel.setTextFill(Color.RED);
            errorLabel.setVisible(true);
            e.printStackTrace();
        }
    }

    //@FXML
    //private void handleFinishButton() throws IOException {
    //    SceneLocator.getInstance().switchToRegistrationRecords();
    //}

    private HBox createStudentCard(StartingStudent student) {
        HBox card = new HBox();
        card.setSpacing(10);
        card.getStyleClass().add("card");
        card.setAlignment(javafx.geometry.Pos.CENTER);
        card.setPrefWidth(250.0);
        card.setMinWidth(250.0);

        VBox studentVBox = new VBox(5);
        HBox idBox = new HBox(5);
        idBox.setStyle("-fx-padding: 5 0 0 0;");
        Label idLabel = new Label("ID:");
        idLabel.getStyleClass().add("label");
        Label studentIdLabel = new Label("" + student.getId());
        studentIdLabel.getStyleClass().add("label");
        idBox.getChildren().addAll(idLabel, studentIdLabel);

        HBox nameBox = new HBox(5);
        nameBox.setStyle("-fx-padding: 5 0 0 0;");
        Label fullNameLabel = new Label("Full Name:");
        fullNameLabel.getStyleClass().add("label");
        String fullName = (student.getName() != null ? student.getName() : "N/A") + " " +
                (student.getSurname() != null ? student.getSurname() : "N/A");
        Label studentNameLabel = new Label(fullName.trim());
        studentNameLabel.getStyleClass().add("label");
        nameBox.getChildren().addAll(fullNameLabel, studentNameLabel);

        HBox programBox = new HBox(5);
        programBox.setStyle("-fx-padding: 5 0 0 0;");
        Label programLabel = new Label("Program:");
        programLabel.getStyleClass().add("label");
        Label studentProgramLabel = new Label(student.getProgram() != null ? student.getProgram() : "N/A");
        studentProgramLabel.getStyleClass().add("label");
        programBox.getChildren().addAll(programLabel, studentProgramLabel);

        HBox gradeBox = new HBox(5);
        gradeBox.setStyle("-fx-padding: 5 0 0 0;");
        Label gradeAverageLabel = new Label("Grade Average:");
        gradeAverageLabel.getStyleClass().add("label");
        TextField gradeAverageField = new TextField();
        gradeAverageField.getStyleClass().add("text-field");
        gradeAverageField.setPromptText("3.5–5.0");
        gradeBox.getChildren().addAll(gradeAverageLabel, gradeAverageField);

        HBox pointsBox = new HBox(5);
        pointsBox.setStyle("-fx-padding: 5 0 0 0;");
        Label extraPointsLabel = new Label("Extra Points:");
        extraPointsLabel.getStyleClass().add("label");
        TextField extraPointsField = new TextField();
        extraPointsField.getStyleClass().add("text-field");
        extraPointsField.setPromptText("0–10");
        pointsBox.getChildren().addAll(extraPointsLabel, extraPointsField);

        HBox testScoreBox = new HBox(5);
        testScoreBox.setStyle("-fx-padding: 5 0 0 0;");
        Label testScoreLabel = new Label("Test Score:");
        testScoreLabel.getStyleClass().add("label");
        TextField testScoreField = new TextField();
        testScoreField.getStyleClass().add("text-field");
        testScoreField.setPromptText("0–100");
        testScoreBox.getChildren().addAll(testScoreLabel, testScoreField);

        HBox acceptanceScoreBox = new HBox(5);
        acceptanceScoreBox.setStyle("-fx-padding: 5 0 0 0;");
        Label acceptanceTestScoreLabel = new Label("Acceptance Score:");
        acceptanceTestScoreLabel.getStyleClass().add("label");
        TextField acceptanceTestScoreField = new TextField();
        acceptanceTestScoreField.getStyleClass().add("text-field");
        acceptanceTestScoreField.setPromptText("0–100");
        acceptanceScoreBox.getChildren().addAll(acceptanceTestScoreLabel, acceptanceTestScoreField);

        Button viewGradeButton = new Button("View Transcript");
        viewGradeButton.getStyleClass().add("btn-blue");
        boolean gradeFileExists = student.getGpaTranscript() != null && new File(student.getGpaTranscript()).exists();
        viewGradeButton.setDisable(!gradeFileExists);
        viewGradeButton.setOnAction(event -> openImageWindow(student.getGpaTranscript(), "Grade Transcript"));

        Button viewExtraDocButton = null;
        boolean extraDocExists = student.getExtraCreditDocument() != null && new File(student.getExtraCreditDocument()).exists();
        if (extraDocExists) {
            viewExtraDocButton = new Button("View Extra Document");
            viewExtraDocButton.getStyleClass().add("btn-blue");
            viewExtraDocButton.setDisable(!extraDocExists);
            viewExtraDocButton.setOnAction(event -> openImageWindow(student.getExtraCreditDocument(), "Extra Credit Document"));
        }

        Button closeButton = new Button("Complete");
        closeButton.getStyleClass().add("btn-green");
        closeButton.setDisable(true);

        BooleanBinding isInputValid = Bindings.createBooleanBinding(() -> {
                    String gradeText = gradeAverageField.getText();
                    String pointsText = extraPointsField.getText();
                    String testScoreText = testScoreField.getText();
                    String acceptanceScoreText = acceptanceTestScoreField.getText();
                    if (gradeText.isEmpty() || pointsText.isEmpty() || testScoreText.isEmpty() || acceptanceScoreText.isEmpty()) {
                        return false;
                    }
                    if (!gradeText.matches("\\d*\\.?\\d*") || !pointsText.matches("\\d*\\.?\\d*") ||
                            !testScoreText.matches("\\d*\\.?\\d*") || !acceptanceScoreText.matches("\\d*\\.?\\d*")) {
                        return false;
                    }
                    try {
                        double grade = Double.parseDouble(gradeText);
                        double points = Double.parseDouble(pointsText);
                        double testScore = Double.parseDouble(testScoreText);
                        int acceptanceScore = Integer.parseInt(acceptanceScoreText);
                        return grade >= 3.5 && grade <= 5.0 &&
                                points >= 0 && points <= 10 &&
                                testScore >= 0 && testScore <= 100 &&
                                acceptanceScore >= 0 && acceptanceScore <= 100;
                    } catch (NumberFormatException e) {
                        return false;
                    }
                }, gradeAverageField.textProperty(), extraPointsField.textProperty(),
                testScoreField.textProperty(), acceptanceTestScoreField.textProperty());

        closeButton.disableProperty().bind(isInputValid.not());

        gradeAverageField.textProperty().addListener((obs, old, newValue) -> {
            try {
                double grade = Double.parseDouble(newValue);
                if (grade < 3.5 || grade > 5.0) {
                    errorLabel.setText("Grade Average must be between 3.5 and 5.0");
                    errorLabel.setVisible(true);
                } else {
                    errorLabel.setVisible(false);
                }
            } catch (NumberFormatException e) {
                if (!newValue.isEmpty()) {
                    errorLabel.setText("Grade Average must be a number");
                    errorLabel.setVisible(true);
                } else {
                    errorLabel.setVisible(false);
                }
            }
        });

        extraPointsField.textProperty().addListener((obs, old, newValue) -> {
            try {
                double points = Double.parseDouble(newValue);
                if (points < 0 || points > 10) {
                    errorLabel.setText("Extra Points must be between 0 and 10");
                    errorLabel.setVisible(true);
                } else {
                    errorLabel.setVisible(false);
                }
            } catch (NumberFormatException e) {
                if (!newValue.isEmpty()) {
                    errorLabel.setText("Extra Points must be a number");
                    errorLabel.setVisible(true);
                } else {
                    errorLabel.setVisible(false);
                }
            }
        });

        testScoreField.textProperty().addListener((obs, old, newValue) -> {
            try {
                double testScore = Double.parseDouble(newValue);
                if (testScore < 0 || testScore > 100) {
                    errorLabel.setText("Test Score must be between 0 and 100");
                    errorLabel.setVisible(true);
                } else {
                    errorLabel.setVisible(false);
                }
            } catch (NumberFormatException e) {
                if (!newValue.isEmpty()) {
                    errorLabel.setText("Test Score must be a number");
                    errorLabel.setVisible(true);
                } else {
                    errorLabel.setVisible(false);
                }
            }
        });

        acceptanceTestScoreField.textProperty().addListener((obs, old, newValue) -> {
            try {
                int acceptanceScore = Integer.parseInt(newValue);
                if (acceptanceScore < 0 || acceptanceScore > 100) {
                    errorLabel.setText("Acceptance Test Score must be between 0 and 100");
                    errorLabel.setVisible(true);
                } else {
                    errorLabel.setVisible(false);
                }
            } catch (NumberFormatException e) {
                if (!newValue.isEmpty()) {
                    errorLabel.setText("Acceptance Test Score must be an integer");
                    errorLabel.setVisible(true);
                } else {
                    errorLabel.setVisible(false);
                }
            }
        });

        closeButton.setOnAction(event -> {
            try {
                double gradeAverage = Double.parseDouble(gradeAverageField.getText());
                double extraPoints = Double.parseDouble(extraPointsField.getText());
                double testScore = Double.parseDouble(testScoreField.getText());
                int acceptanceTestScore = Integer.parseInt(acceptanceTestScoreField.getText());


                student.setGradeAverage(gradeAverage);
                student.setExtraPoints(extraPoints);
                student.setTestScore(testScore);
                student.setAcceptanceTestScore(acceptanceTestScore);
                repository.updateStudent(student);


                repository.createRequest(student.getId(), true, false);

                studentCardsContainer.getChildren().remove(card);

            } catch (NumberFormatException e) {
                errorLabel.setText("Invalid input format");
                errorLabel.setTextFill(Color.RED);
                errorLabel.setVisible(true);

            } catch (RuntimeException e) {
                errorLabel.setText("Error saving data: " + e.getMessage());
                errorLabel.setTextFill(Color.RED);
                errorLabel.setVisible(true);

            }
        });

        if (extraDocExists) {
            studentVBox.getChildren().addAll(idBox, nameBox, programBox, gradeBox, pointsBox,
                    testScoreBox, acceptanceScoreBox, viewGradeButton, viewExtraDocButton, closeButton);
        } else {
            studentVBox.getChildren().addAll(idBox, nameBox, programBox, gradeBox, pointsBox,
                    testScoreBox, acceptanceScoreBox, viewGradeButton, closeButton);
        }

        card.getChildren().add(studentVBox);
        return card;
    }

    private void openImageWindow(String filePath, String title) {
        if (filePath == null || !new File(filePath).exists()) {
            errorLabel.setText("File not found: " + (filePath != null ? filePath : "No file"));
            errorLabel.setTextFill(Color.RED);
            errorLabel.setVisible(true);
            return;
        }

        try {
            File file = new File(filePath);
            Image image = new Image(file.toURI().toString());
            if (image.isError()) {
                errorLabel.setText("Error loading image: " + filePath);
                errorLabel.setTextFill(Color.RED);
                errorLabel.setVisible(true);
                return;
            }

            double width = Math.min(image.getWidth(), MAX_IMAGE_WIDTH);
            double height = Math.min(image.getHeight(), MAX_IMAGE_HEIGHT);
            if (image.getWidth() > MAX_IMAGE_WIDTH || image.getHeight() > MAX_IMAGE_HEIGHT) {
                double scale = Math.min(MAX_IMAGE_WIDTH / image.getWidth(), MAX_IMAGE_HEIGHT / image.getHeight());
                width = image.getWidth() * scale;
                height = image.getHeight() * scale;
            }

            ImageView imageView = new ImageView(image);
            imageView.setPreserveRatio(true);
            imageView.setFitWidth(width);
            imageView.setFitHeight(height);

            final boolean[] isZoomed = {false};
            imageView.setOnMouseClicked(event -> {
                if (isZoomed[0]) {
                    imageView.setScaleX(DEFAULT_ZOOM);
                    imageView.setScaleY(DEFAULT_ZOOM);
                    isZoomed[0] = false;
                } else {
                    imageView.setScaleX(ZOOM_FACTOR);
                    imageView.setScaleY(ZOOM_FACTOR);
                    isZoomed[0] = true;
                }
            });

            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(new VBox(imageView), width, height));
            stage.setResizable(false);
            stage.show();

        } catch (Exception e) {
            errorLabel.setText("Error opening image: " + e.getMessage());
            errorLabel.setTextFill(Color.RED);
            errorLabel.setVisible(true);
            e.printStackTrace();
        }
    }
}