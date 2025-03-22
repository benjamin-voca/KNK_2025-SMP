module com.example.knkproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    exports pages;


    opens com.example.knkproject to javafx.fxml;
    exports com.example.knkproject;
}