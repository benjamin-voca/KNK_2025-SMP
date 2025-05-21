package controllers;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.StartingStudent;
import repository.StudentStartingRepository;

import java.util.Comparator;
import java.util.List;

public class RegistrationRecordsController {

    @FXML private TableView<StartingStudent> studentTable;
    @FXML private TableColumn<StartingStudent, Integer> idColumn;
    @FXML private TableColumn<StartingStudent, String> nameColumn;
    @FXML private TableColumn<StartingStudent, String> surnameColumn;
    @FXML private TableColumn<StartingStudent, String> addressColumn;
    @FXML private TableColumn<StartingStudent, Integer> ageColumn;
    @FXML private TableColumn<StartingStudent, Double> gpaColumn;
    @FXML private TableColumn<StartingStudent, String> ethnicityColumn;
    @FXML private TableColumn<StartingStudent, Double> testScoreColumn;
    @FXML private TableColumn<StartingStudent, Integer> acceptanceTestScoreColumn;
    @FXML private TableColumn<StartingStudent, String> programColumn;
    @FXML private TableColumn<StartingStudent, Double> extraPointsColumn;
    @FXML private TableColumn<StartingStudent, Double> scoreColumn;
    @FXML private TableColumn<StartingStudent, String> statusColumn;
    @FXML private Label errorLabel;
    @FXML private Label rejectionLabel;

    private final StudentStartingRepository repository = new StudentStartingRepository();

    @FXML
    public void initialize() {

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
        gpaColumn.setCellValueFactory(new PropertyValueFactory<>("gradeAverage"));
        ethnicityColumn.setCellValueFactory(new PropertyValueFactory<>("ethnicity"));
        testScoreColumn.setCellValueFactory(new PropertyValueFactory<>("testScore"));
        acceptanceTestScoreColumn.setCellValueFactory(new PropertyValueFactory<>("acceptanceTestScore"));
        programColumn.setCellValueFactory(new PropertyValueFactory<>("program"));
        extraPointsColumn.setCellValueFactory(new PropertyValueFactory<>("extraPoints"));
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("calculatedScore"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));


        studentTable.setRowFactory(tv -> new javafx.scene.control.TableRow<StartingStudent>() {
            @Override
            protected void updateItem(StartingStudent item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setStyle("");
                } else {
                    setStyle(item.getStatus().equals("Accepted") ? "-fx-background-color: #e6ffe6;" : "-fx-background-color: #ffe6e6;");
                }
            }
        });


        rejectionLabel.visibleProperty().bind(Bindings.createBooleanBinding(() -> {
            StartingStudent selected = studentTable.getSelectionModel().getSelectedItem();
            return selected != null && selected.getStatus().equals("Rejected");
        }, studentTable.getSelectionModel().selectedItemProperty()));


        try {
            List<StartingStudent> students = repository.findAll();

            if (students.isEmpty()) {
                errorLabel.setText("No student records found.");
                errorLabel.setVisible(true);
            } else {
                ObservableList<StartingStudent> studentList = FXCollections.observableArrayList(students);
                studentList.sort(Comparator.comparing(StartingStudent::getGradeAverage, Comparator.nullsLast(Comparator.reverseOrder())));
                studentTable.setItems(studentList);
            }
        } catch (RuntimeException e) {
            errorLabel.setText("Error loading student data: " + e.getMessage());
            errorLabel.setVisible(true);

            e.printStackTrace();
        }
    }
}