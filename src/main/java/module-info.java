module com.example.knkproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.knkproject to javafx.fxml;
    exports com.example.knkproject;
}