module com.example.knkproject {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.knkproject to javafx.fxml;
    exports com.example.knkproject;
}