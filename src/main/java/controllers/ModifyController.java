package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.User;
import models.dto.UpdateUserDto;
import repository.UserRepository;
import utilities.PasswordHasher;
import utilities.SessionManager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class ModifyController {
    @FXML private TextField emailProfile;
    @FXML private TextField newPasswordProfile;
    @FXML private TextField confirmNewPasswordProfile;
    @FXML private TextField currentPasswordField;
    @FXML private ImageView profilePicture;

    private Stage profileStage;
    private Stage modifyStage;
    private final UserRepository userRepository = new UserRepository();
    private static final String UPLOAD_DIR = "uploads/profile_pictures/";
    private static final String DEFAULT_PICTURE_PATH = UPLOAD_DIR + "default_profile.png";

    public void setPreviousStages(Stage profilePage, Stage modifyPage) {
        this.profileStage = profilePage;
        this.modifyStage = modifyPage;
    }

    @FXML
    public void initialize() {
        User currentUser = SessionManager.getCurrentUser();
        if (currentUser != null) {
            emailProfile.setText(currentUser.getEmail());
            loadProfilePicture(currentUser.getProfilePicturePath());
        }
    }

    private void loadProfilePicture(String picturePath) {
        try {
            if (picturePath != null && !picturePath.isEmpty()) {
                File imageFile = new File(picturePath);
                if (imageFile.exists()) {
                    profilePicture.setImage(new Image(imageFile.toURI().toString()));
                    return;
                }
            }
            File defaultImageFile = new File(DEFAULT_PICTURE_PATH);
            if (defaultImageFile.exists()) {
                profilePicture.setImage(new Image(defaultImageFile.toURI().toString()));
            } else {
                System.err.println("Default profile picture not found at: " + DEFAULT_PICTURE_PATH);
            }
        } catch (Exception e) {
            System.err.println("Failed to load profile picture: " + e.getMessage());
        }
    }

    @FXML
    private void changeProfilePicture(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Profile Picture");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );

        File file = fileChooser.showOpenDialog(modifyStage);
        if (file != null) {
            try {
                Image image = new Image(file.toURI().toString());
                profilePicture.setImage(image);

                User currentUser = SessionManager.getCurrentUser();
                if (currentUser == null) {
                    showAlert("Error", "No user session found");
                    return;
                }

                Path uploadPath = Paths.get(UPLOAD_DIR);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                String fileName = currentUser.getId() + "_" + file.getName();
                Path targetPath = uploadPath.resolve(fileName);
                Files.copy(file.toPath(), targetPath, StandardCopyOption.REPLACE_EXISTING);
                String profilePicturePath = targetPath.toString();

                String currentPicturePath = currentUser.getProfilePicturePath();
                if (currentPicturePath != null &&
                        !currentPicturePath.isEmpty() &&
                        !currentPicturePath.equals(DEFAULT_PICTURE_PATH)) {
                    Path oldPicturePath = Paths.get(currentPicturePath);
                    if (Files.exists(oldPicturePath)) {
                        try {
                            Files.delete(oldPicturePath);
                            System.out.println("Deleted previous profile picture: " + currentPicturePath);
                        } catch (IOException e) {
                            System.err.println("Failed to delete previous profile picture: " + e.getMessage());
                        }
                    }
                }

                System.out.println("Profile picture saved to: " + profilePicturePath);

                if (!Files.exists(targetPath)) {
                    showAlert("Error", "Failed to save profile picture to filesystem");
                    return;
                }

                UpdateUserDto updateDto = new UpdateUserDto(
                        currentUser.getId(),
                        currentUser.getEmail(),
                        currentUser.getPasswordHash(),
                        profilePicturePath
                );

                User updatedUser = userRepository.update(updateDto);
                System.out.println("Profile picture update result: " + (updatedUser != null ? "Success" : "Failed"));

                if (updatedUser != null) {
                    SessionManager.setCurrentUser(updatedUser);
                    showAlert("Success", "Profile picture updated successfully");
                } else {
                    // Fallback: Check database directly
                    User dbUser = userRepository.getById(currentUser.getId());
                    if (dbUser != null && profilePicturePath.equals(dbUser.getProfilePicturePath())) {
                        SessionManager.setCurrentUser(dbUser);
                        showAlert("Success", "Profile picture updated successfully (via fallback)");
                    } else {
                        showAlert("Error", "Failed to update profile picture");
                        System.err.println("Profile picture update failed. DB user: " + dbUser);
                    }
                }
            } catch (IOException e) {
                showAlert("Error", "Failed to save profile picture: " + e.getMessage());
                e.printStackTrace();
            } catch (Exception e) {
                showAlert("Error", "An error occurred: " + e.getMessage());
                e.printStackTrace();
            }
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

            if (!PasswordHasher.validatePassword(currentPasswordField.getText(), currentUser.getPasswordHash())) {
                showAlert("Error", "Current password is incorrect");
                return;
            }

            String newEmail = emailProfile.getText().trim();
            String newPassword = newPasswordProfile.getText();
            String confirmPassword = confirmNewPasswordProfile.getText();

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

            UpdateUserDto updateDto = new UpdateUserDto(
                    currentUser.getId(),
                    newEmail,
                    newPassword.isEmpty() ? currentUser.getPasswordHash() : PasswordHasher.hash(newPassword),
                    currentUser.getProfilePicturePath()
            );

            User updatedUser = userRepository.update(updateDto);
            System.out.println("Update result: " + (updatedUser != null ? "Success" : "Failed"));

            if (updatedUser != null) {
                SessionManager.setCurrentUser(updatedUser);
                showAlert("Success", "Profile updated successfully");
                returnToProfile(event);
            } else {
                User dbUser = userRepository.getById(currentUser.getId());
                if (dbUser != null &&
                        dbUser.getEmail().equals(newEmail) &&
                        (newPassword.isEmpty() ||
                                PasswordHasher.validatePassword(newPassword, dbUser.getPasswordHash()))) {
                    SessionManager.setCurrentUser(dbUser);
                    showAlert("Success", "Profile updated successfully (via fallback)");
                    returnToProfile(event);
                } else {
                    showAlert("Error", "Failed to update profile");
                    System.err.println("Update failed. DB user: " + dbUser);
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
}