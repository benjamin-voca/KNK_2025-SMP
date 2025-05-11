package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.User;
import models.dto.UpdateUserDto;
import repository.UserRepository;
import utilities.PasswordHasher;
import utilities.SessionManager;

public class ModifyController {
    @FXML private TextField emailProfile;
    @FXML private TextField newPasswordProfile;
    @FXML private TextField confirmNewPasswordProfile;
    @FXML private TextField currentPasswordField;

    private Stage profileStage;
    private Stage modifyStage;
    private final UserRepository userRepository = new UserRepository();

    public void setPreviousStages(Stage profilePage, Stage modifyPage) {
        this.profileStage = profilePage;
        this.modifyStage = modifyPage;
    }

    @FXML
    public void initialize() {
        // Load current user email
        User currentUser = SessionManager.getCurrentUser();
        if (currentUser != null) {
            emailProfile.setText(currentUser.getEmail());
        }
    }

    @FXML
    private void saveInfo(ActionEvent event) {
        try {
            User currentUser = SessionManager.getCurrentUser();
            if (currentUser == null) {
                showAlert("Error", "No user session found");
                return;
            }

            // Verify current password
            if (!PasswordHasher.validatePassword(currentPasswordField.getText(), currentUser.getPasswordHash())) {
                showAlert("Error", "Current password is incorrect");
                return;
            }

            // Get new values
            String newEmail = emailProfile.getText().trim();
            String newPassword = newPasswordProfile.getText();
            String confirmPassword = confirmNewPasswordProfile.getText();

            // Validate new password if changed
            if (!newPassword.isEmpty()) {
                if (!newPassword.equals(confirmPassword)) {
                    showAlert("Error", "New passwords don't match");
                    return;
                }
                if (newPassword.length() < 6) {
                    showAlert("Error", "Password must be at least 6 characters");
                    return;
                }
            }

            // Create DTO with updates
            UpdateUserDto updateDto = new UpdateUserDto(
                    currentUser.getId(),
                    newEmail,
                    newPassword.isEmpty() ? currentUser.getPasswordHash() : PasswordHasher.hash(newPassword)
            );

            // Debug before update
            System.out.println("Attempting update with DTO: " + updateDto);

            // Update using repository method
            User updatedUser = userRepository.update(updateDto);

            // Debug after update
            if (updatedUser != null) {
                System.out.println("Update successful - New email: " + updatedUser.getEmail());

                // Verify password was actually changed
                boolean passwordChanged = !newPassword.isEmpty();
                if (passwordChanged) {
                    boolean passwordMatches = PasswordHasher.validatePassword(
                            newPassword,
                            updatedUser.getPasswordHash()
                    );
                    System.out.println("Password verification: " + passwordMatches);
                }

                // Update session
                SessionManager.setCurrentUser(updatedUser);
                showAlert("Success", "Profile updated successfully");
                returnToProfile(event);
            } else {
                // Check database directly if update really failed
                User dbUser = userRepository.getById(currentUser.getId());
                if (dbUser != null &&
                        dbUser.getEmail().equals(newEmail) &&
                        (newPassword.isEmpty() ||
                                PasswordHasher.validatePassword(newPassword, dbUser.getPasswordHash()))) {
                    // Update actually succeeded despite null return
                    SessionManager.setCurrentUser(dbUser);
                    showAlert("Success", "Profile updated successfully");
                    returnToProfile(event);
                } else {
                    showAlert("Error", "Failed to update profile");
                }
            }

        } catch (Exception e) {
            showAlert("Error", "An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void returnToProfile(ActionEvent event) {
        if (profileStage != null) {
            profileStage.show();
        }
        if (modifyStage != null) {
            modifyStage.close();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void changeProfilePicture(ActionEvent event) {

    }
}