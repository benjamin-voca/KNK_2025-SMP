package models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String passwordHash;
    private String profilePicturePath;

    private User(int id, String firstName, String lastName, String email, String passwordHash, String profilePicturePath) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.passwordHash = passwordHash;
        this.profilePicturePath = profilePicturePath != null ? profilePicturePath : "uploads/profile_pictures/default_profile.png";
    }

    public static User getInstance(ResultSet result) throws SQLException {
        int id = result.getInt("user_id");
        String firstName = result.getString("first_name");
        String lastName = result.getString("last_name");
        String email = result.getString("email");
        String passwordHash = result.getString("password_hash");
        String profilePicturePath = result.getString("profile_picture_path");
        return new User(id, firstName, lastName, email, passwordHash, profilePicturePath);
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getProfilePicturePath() {
        return profilePicturePath;
    }

    public void setProfilePicturePath(String profilePicturePath) {
        this.profilePicturePath = profilePicturePath;
    }
}
