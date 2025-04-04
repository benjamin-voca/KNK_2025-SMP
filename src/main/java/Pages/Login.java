package Pages;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class Login {
    private Button loginButton;

    public Scene createLoginScene() {
        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter your username");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter your password");

        loginButton = new Button("Log In");

        VBox loginLayout = new VBox(10);
        loginLayout.getChildren().addAll(usernameField, passwordField, loginButton);

        Scene loginScene = new Scene(loginLayout, 300, 200);

        return loginScene;
    }

    public Button getLoginButton() {
        return loginButton;
    }

}
