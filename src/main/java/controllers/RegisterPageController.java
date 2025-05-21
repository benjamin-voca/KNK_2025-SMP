package controllers;

import database.DB_Connector;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class RegisterPageController {

    @FXML private TextField nameField;
    @FXML private TextField surnameField;
    @FXML private TextField addressField;
    @FXML private TextField ageField;
    @FXML private ComboBox<String> ethnicityCombo;
    @FXML private ComboBox<String> programCombo;
    @FXML private ComboBox<String> municipalityCombo;
    @FXML private Button fileButton1;
    @FXML private Button fileButton2;
    @FXML private Label fileLabel1;
    @FXML private Label fileLabel2;
    @FXML private Label errorLabel;
    @FXML private Button registerButton;

    private File selectedFile1;
    private File selectedFile2;


    private static final Path GRADE_TRANSCRIPTS_DIR = Paths.get("uploads/grade_transcripts");
    private static final Path EXTRA_DOCS_DIR = Paths.get("uploads/extra_point_documents");


    private static final List<String> VALID_ETHNICITIES = Arrays.asList(
            "Shqiptar", "Serb", "Boshnjak", "Romë", "Ashkali", "Egjiptian"
    );
    private static final List<String> VALID_PROGRAMS = Arrays.asList(
            "IKS", "EAR", "EEN", "TIK"
    );
    private static final List<String> VALID_MUNICIPALITIES = Arrays.asList(
            "Peje", "Prizren", "Prishtine", "Mitrovicë", "Gjakovë", "Ferizaj", "Gjilan"
    );

    @FXML
    public void initialize() {

        try {
            Files.createDirectories(GRADE_TRANSCRIPTS_DIR);
            Files.createDirectories(EXTRA_DOCS_DIR);
        } catch (IOException e) {
            errorLabel.setText("Error creating upload directories: " + e.getMessage());
            errorLabel.setTextFill(Color.RED);
            errorLabel.setVisible(true);
            e.printStackTrace();
        }


        if (ethnicityCombo == null || programCombo == null || municipalityCombo == null) {
            System.err.println("ComboBox not initialized: ethnicity=" + ethnicityCombo +
                    ", program=" + programCombo + ", municipality=" + municipalityCombo);
        }


        ageField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                ageField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });


        fileButton1.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select Transkripta e notave");
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png")
            );
            File file = fileChooser.showOpenDialog(fileButton1.getScene().getWindow());
            if (file != null) {
                String fileName = file.getName().toLowerCase();
                if (fileName.endsWith(".jpg") || fileName.endsWith(".png")) {
                    selectedFile1 = file;
                    fileLabel1.setText(file.getName());
                    errorLabel.setVisible(false);
                    System.out.println("Selected File 1: " + selectedFile1.getAbsolutePath());
                } else {
                    errorLabel.setText("Nuk suportohet ky lloj i fjallit");
                    errorLabel.setTextFill(Color.RED);
                    errorLabel.setVisible(true);
                    selectedFile1 = null;
                    fileLabel1.setText("No file selected");
                }
            }
        });


        fileButton2.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select Dokumentacione ekstra");
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png")
            );
            File file = fileChooser.showOpenDialog(fileButton2.getScene().getWindow());
            if (file != null) {
                String fileName = file.getName().toLowerCase();
                if (fileName.endsWith(".jpg") || fileName.endsWith(".png")) {
                    selectedFile2 = file;
                    fileLabel2.setText(file.getName());
                    errorLabel.setVisible(false);
                    System.out.println("Selected File 2: " + selectedFile2.getAbsolutePath());
                } else {
                    errorLabel.setText("Nuk suportohet ky lloj i fjallit");
                    errorLabel.setTextFill(Color.RED);
                    errorLabel.setVisible(true);
                    selectedFile2 = null;
                    fileLabel2.setText("No file selected");
                }
            }
        });
    }

    @FXML
    private void handleRegister() {
        String street = addressField.getText() != null ? addressField.getText().trim() : "";
        String municipality = municipalityCombo != null ? municipalityCombo.getValue() : null;
        String name = nameField.getText() != null ? nameField.getText().trim() : "";
        String surname = surnameField.getText() != null ? surnameField.getText().trim() : "";
        String ageText = ageField.getText() != null ? ageField.getText().trim() : "";
        String ethnicity = ethnicityCombo != null ? ethnicityCombo.getValue() : null;
        String program = programCombo != null ? programCombo.getValue() : null;





        if (name.isEmpty() || surname.isEmpty() || street.isEmpty() || ageText.isEmpty() ||
                ethnicity == null || program == null || municipality == null ||
                selectedFile1 == null || selectedFile2 == null) {
            errorLabel.setText("Ju lutemi plotësoni të gjitha fushat dhe zgjidhni të dy skedarët");
            errorLabel.setTextFill(Color.RED);
            errorLabel.setVisible(true);
            return;
        }


        if (!VALID_MUNICIPALITIES.contains(municipality)) {
            errorLabel.setText("Komuna e pavlefshme: " + municipality);
            errorLabel.setTextFill(Color.RED);
            errorLabel.setVisible(true);
            return;
        }


        String address = street + ", " + municipality;
        if (address.length() > 50) {
            errorLabel.setText("Adresa është shumë e gjatë (maksimum 50 karaktere)");
            errorLabel.setTextFill(Color.RED);
            errorLabel.setVisible(true);
            return;
        }

        int age;
        try {
            age = Integer.parseInt(ageText);
            if (age < 18) {
                errorLabel.setText("Mosha duhet të jetë 18 ose më e madhe");
                errorLabel.setTextFill(Color.RED);
                errorLabel.setVisible(true);
                return;
            }
        } catch (NumberFormatException e) {
            errorLabel.setText("Mosha duhet të jetë një numër i vlefshëm");
            errorLabel.setTextFill(Color.RED);
            errorLabel.setVisible(true);
            return;
        }


        if (!VALID_ETHNICITIES.contains(ethnicity)) {
            errorLabel.setText("Etniciteti i pavlefshëm: " + ethnicity);
            errorLabel.setTextFill(Color.RED);
            errorLabel.setVisible(true);
            return;
        }
        if (!VALID_PROGRAMS.contains(program)) {
            errorLabel.setText("Programi i pavlefshëm: " + program);
            errorLabel.setTextFill(Color.RED);
            errorLabel.setVisible(true);
            return;
        }


        String transcriptPath = null;
        String extraDocPath = null;
        try {
            if (selectedFile1 != null) {
                String extension = selectedFile1.getName().substring(selectedFile1.getName().lastIndexOf("."));
                String uniqueFileName = UUID.randomUUID() + extension;
                Path destination = GRADE_TRANSCRIPTS_DIR.resolve(uniqueFileName);
                Files.copy(selectedFile1.toPath(), destination);
                transcriptPath = destination.toString().replace("\\", "/");
            }
            if (selectedFile2 != null) {
                String extension = selectedFile2.getName().substring(selectedFile2.getName().lastIndexOf("."));
                String uniqueFileName = UUID.randomUUID() + extension;
                Path destination = EXTRA_DOCS_DIR.resolve(uniqueFileName);
                Files.copy(selectedFile2.toPath(), destination);
                extraDocPath = destination.toString().replace("\\", "/");
            }
        } catch (IOException e) {
            errorLabel.setText("Error uploading files: " + e.getMessage());
            errorLabel.setTextFill(Color.RED);
            errorLabel.setVisible(true);
            e.printStackTrace();
            return;
        }


        try (Connection conn = DB_Connector.getConnection()) {
            String sql = "INSERT INTO student_starting (name, surname, address, age, gpa_transcript, " +
                    "ethnicity, extra_credit_document, test_score, acceptance_test_score, program) " +
                    "VALUES (?, ?, ?, ?, ?, ?::ethnicity_type, ?, ?, ?, ?::program_type)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, surname);
            stmt.setString(3, address);
            stmt.setInt(4, age);
            stmt.setString(5, transcriptPath);
            stmt.setString(6, ethnicity);
            stmt.setString(7, extraDocPath);
            stmt.setDouble(8, 0.0);
            stmt.setInt(9, 0);
            stmt.setString(10, program);


            errorLabel.setText("Regjistrimi ka përfunduar, ju lutem prisni!");
            errorLabel.setTextFill(Color.GREEN);
            errorLabel.setVisible(true);


            nameField.clear();
            surnameField.clear();
            addressField.clear();
            ageField.clear();
            ethnicityCombo.setValue(null);
            programCombo.setValue(null);
            if (municipalityCombo != null) {
                municipalityCombo.setValue(null);
            }
            fileLabel1.setText("No file selected");
            fileLabel2.setText("No file selected");
            selectedFile1 = null;
            selectedFile2 = null;

        } catch (SQLException e) {
            errorLabel.setText("Database error: " + e.getMessage());
            errorLabel.setTextFill(Color.RED);
            errorLabel.setVisible(true);
            e.printStackTrace();
        }
    }
}
