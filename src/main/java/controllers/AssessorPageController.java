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

import java.io.File;
import java.util.List;

public class AssessorPageController {

    @FXML private TilePane studentCardsContainer;
    @FXML private Label errorLabel;

    private final StudentStartingRepository repository = new StudentStartingRepository();
    private static final double MAX_IMAGE_WIDTH = 800.0;
    private static final double MAX_IMAGE_HEIGHT = 600.0;
    private static final double ZOOM_FACTOR = 1.3; // 30% zoom in
    private static final double DEFAULT_ZOOM = 1.0; // Original size

    @FXML
    public void initialize() {
        try {
            List<StartingStudent> students = repository.findAll();
            System.out.println("Fetched " + students.size() + " student records.");
            for (StartingStudent student : students) {
                System.out.println("Student ID: " + student.getId() + ", Name: " + student.getName() +
                        ", Surname: " + student.getSurname() + ", Program: " + student.getProgram() +
                        ", Transcript: " + student.getGpaTranscript() + ", Extra Doc: " + student.getExtraCreditDocument());
            }
            if (students.isEmpty()) {
                errorLabel.setText("No student records found.");
                errorLabel.setTextFill(Color.RED);
                errorLabel.setVisible(true);
                return;
            }

            for (StartingStudent student : students) {
                HBox card = createStudentCard(student);
                studentCardsContainer.getChildren().add(card);
                System.out.println("Added card for Student ID: " + student.getId());
            }
        } catch (RuntimeException e) {
            errorLabel.setText("Error loading student data: " + e.getMessage());
            errorLabel.setTextFill(Color.RED);
            errorLabel.setVisible(true);
            e.printStackTrace();
        }
    }

    private HBox createStudentCard(StartingStudent student) {
        HBox card = new HBox();
        card.setSpacing(10);
        card.getStyleClass().add("card");
        card.setAlignment(javafx.geometry.Pos.CENTER);
        card.setPrefWidth(250.0); // Updated to match wider cards
        card.setMinWidth(250.0);

        VBox studentVBox = new VBox(5);
        HBox idBox = new HBox(5);
        Label idLabel = new Label("ID:");
        idLabel.getStyleClass().add("label");
        Label studentIdLabel = new Label("" + student.getId());
        studentIdLabel.getStyleClass().add("label");
        idBox.getChildren().addAll(idLabel, studentIdLabel);

        HBox nameBox = new HBox(5);
        Label fullNameLabel = new Label("Full Name:");
        fullNameLabel.getStyleClass().add("label");
        String fullName = (student.getName() != null ? student.getName() : "N/A") + " " +
                (student.getSurname() != null ? student.getSurname() : "N/A");
        Label studentNameLabel = new Label(fullName.trim());
        studentNameLabel.getStyleClass().add("label");
        nameBox.getChildren().addAll(fullNameLabel, studentNameLabel);

        HBox programBox = new HBox(5);
        Label programLabel = new Label("Program:");
        programLabel.getStyleClass().add("label");
        Label studentProgramLabel = new Label(student.getProgram() != null ? student.getProgram() : "N/A");
        studentProgramLabel.getStyleClass().add("label");
        programBox.getChildren().addAll(programLabel, studentProgramLabel);

        HBox gradeBox = new HBox(5);
        Label gradeAverageLabel = new Label("GPA:");
        gradeAverageLabel.getStyleClass().add("label");
        TextField gradeAverageField = new TextField();
        gradeAverageField.getStyleClass().add("text-field");
        gradeAverageField.setPromptText("3.5–5.0");
        gradeBox.getChildren().addAll(gradeAverageLabel, gradeAverageField);

        HBox pointsBox = new HBox(5);
        Label extraPointsLabel = new Label("Extra Points:");
        extraPointsLabel.getStyleClass().add("label");
        TextField extraPointsField = new TextField();
        extraPointsField.getStyleClass().add("text-field");
        extraPointsField.setPromptText("0–10");
        pointsBox.getChildren().addAll(extraPointsLabel, extraPointsField);

        Button viewGradeButton = new Button("View Transcript");
        viewGradeButton.getStyleClass().add("btn-blue");
        boolean gradeFileExists = student.getGpaTranscript() != null && new File(student.getGpaTranscript()).exists();
        viewGradeButton.setDisable(!gradeFileExists);
        System.out.println("Student ID: " + student.getId() + ", Grade Transcript: " + student.getGpaTranscript() + ", Exists: " + gradeFileExists);
        viewGradeButton.setOnAction(event -> openImageWindow(student.getGpaTranscript(), "Grade Transcript"));

        Button viewExtraDocButton = null;
        boolean extraDocExists = student.getExtraCreditDocument() != null && new File(student.getExtraCreditDocument()).exists();
        if (extraDocExists) {
            viewExtraDocButton = new Button("View Extra Document");
            viewExtraDocButton.getStyleClass().add("btn-blue");
            viewExtraDocButton.setDisable(!extraDocExists);
            System.out.println("Student ID: " + student.getId() + ", Extra Credit Document: " + student.getExtraCreditDocument() + ", Exists: " + extraDocExists);
            viewExtraDocButton.setOnAction(event -> openImageWindow(student.getExtraCreditDocument(), "Extra Credit Document"));
        }

        Button closeButton = new Button("Complete");
        closeButton.getStyleClass().add("btn-green");
        closeButton.setDisable(true);

        // Validation for Complete button
        BooleanBinding isInputValid = Bindings.createBooleanBinding(() -> {
            String gradeText = gradeAverageField.getText();
            String pointsText = extraPointsField.getText();
            if (gradeText.isEmpty() || pointsText.isEmpty()) {
                return false;
            }
            if (!gradeText.matches("\\d*\\.?\\d*") || !pointsText.matches("\\d*\\.?\\d*")) {
                return false;
            }
            try {
                double grade = Double.parseDouble(gradeText);
                double points = Double.parseDouble(pointsText);
                return grade >= 3.5 && grade <= 5.0 && points >= 0 && points <= 10;
            } catch (NumberFormatException e) {
                return false;
            }
        }, gradeAverageField.textProperty(), extraPointsField.textProperty());

        closeButton.disableProperty().bind(isInputValid.not());

        // Input validation with error messages
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

        closeButton.setOnAction(event -> {
            studentCardsContainer.getChildren().remove(card);
            System.out.println("Removed card for Student ID: " + student.getId());
        });

        if (extraDocExists) {
            studentVBox.getChildren().addAll(idBox, nameBox, programBox, gradeBox, pointsBox,
                    viewGradeButton, viewExtraDocButton, closeButton);
        } else {
            studentVBox.getChildren().addAll(idBox, nameBox, programBox, gradeBox, pointsBox,
                    viewGradeButton, closeButton);
        }

        card.getChildren().add(studentVBox);
        return card;
    }

    private void openImageWindow(String filePath, String title) {
        if (filePath == null || !new File(filePath).exists()) {
            errorLabel.setText("File not found: " + (filePath != null ? filePath : "No file"));
            errorLabel.setTextFill(Color.RED);
            errorLabel.setVisible(true);
            System.out.println("Cannot open image window for: " + filePath);
            return;
        }

        try {
            File file = new File(filePath);
            Image image = new Image(file.toURI().toString());
            if (image.isError()) {
                errorLabel.setText("Error loading image: " + filePath);
                errorLabel.setTextFill(Color.RED);
                errorLabel.setVisible(true);
                System.out.println("Image loading error for: " + filePath);
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

            final boolean[] isZoomed = {false}; // Track zoom state
            imageView.setOnMouseClicked(event -> {
                if (isZoomed[0]) {
                    imageView.setScaleX(DEFAULT_ZOOM);
                    imageView.setScaleY(DEFAULT_ZOOM);
                    isZoomed[0] = false;
                    System.out.println("Zoomed out image: " + filePath + ", Zoom level: " + DEFAULT_ZOOM);
                } else {
                    imageView.setScaleX(ZOOM_FACTOR);
                    imageView.setScaleY(ZOOM_FACTOR);
                    isZoomed[0] = true;
                    System.out.println("Zoomed in image: " + filePath + ", Zoom level: " + ZOOM_FACTOR);
                }
            });

            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(new VBox(imageView), width, height));
            stage.setResizable(false);
            stage.show();

            System.out.println("Opened image window for: " + filePath + ", Size: " + width + "x" + height);
        } catch (Exception e) {
            errorLabel.setText("Error opening image: " + e.getMessage());
            errorLabel.setTextFill(Color.RED);
            errorLabel.setVisible(true);
            System.out.println("Error opening image window for: " + filePath + ", " + e.getMessage());
            e.printStackTrace();
        }
    }
}