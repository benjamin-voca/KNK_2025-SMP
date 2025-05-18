package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
        import javafx.stage.FileChooser;
import java.io.File;

public class RegisterPageController {

    @FXML private TextField nameField;
    @FXML private TextField surnameField;
    @FXML private TextField addressField;
    @FXML private TextField ageField;
    @FXML private ComboBox<String> ethnicityCombo;
    @FXML private ComboBox<String> programCombo;
    @FXML private Button fileButton1;
    @FXML private Button fileButton2;
    @FXML private Label fileLabel1;
    @FXML private Label fileLabel2;
    @FXML private Label errorLabel;
    @FXML private Button registerButton;

    private File selectedFile1;
    private File selectedFile2;

    @FXML
    public void initialize() {
        // Set up file chooser for Document 1
        fileButton1.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select Document 1");
            File file = fileChooser.showOpenDialog(fileButton1.getScene().getWindow());
            if (file != null) {
                selectedFile1 = file;
                fileLabel1.setText(file.getName());
            }
        });

        // Set up file chooser for Document 2
        fileButton2.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select Document 2");
            File file = fileChooser.showOpenDialog(fileButton2.getScene().getWindow());
            if (file != null) {
                selectedFile2 = file;
                fileLabel2.setText(file.getName());
            }
        });
    }

    @FXML
    private void handleRegister() {
        String name = nameField.getText();
        String surname = surnameField.getText();
        String address = addressField.getText();
        String ageText = ageField.getText();
        String ethnicity = ethnicityCombo.getValue();
        String program = programCombo.getValue();

        // Validation
        if (name.isEmpty() || surname.isEmpty() || address.isEmpty() || ageText.isEmpty() || ethnicity == null || program == null || selectedFile1 == null || selectedFile2 == null) {
            errorLabel.setText("Please fill in all fields and select both files");
            errorLabel.setVisible(true);
            return;
        }

        try {
            int age = Integer.parseInt(ageText);
            if (age < 18) {
                errorLabel.setText("Age must be 18 or older");
                errorLabel.setVisible(true);
                return;
            }
        } catch (NumberFormatException e) {
            errorLabel.setText("Age must be a valid number");
            errorLabel.setVisible(true);
            return;
        }

        errorLabel.setVisible(false);
        // Process registration (e.g., save to database)
        System.out.println("Registration: Name=" + name + ", Surname=" + surname + ", Address=" + address +
                ", Age=" + ageText + ", Ethnicity=" + ethnicity + ", Program=" + program +
                ", File1=" + selectedFile1.getName() + ", File2=" + selectedFile2.getName());
        // Add logic to handle registration (e.g., save to database, transition to next scene)
    }
}