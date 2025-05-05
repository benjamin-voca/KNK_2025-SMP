module com.example.knkproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens controllers to javafx.fxml;

    exports pages;
    exports utilities;
    exports models;
    exports database;
    exports controllers;


    opens com.example.knkproject to javafx.fxml;
    exports com.example.knkproject;
}