package models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String passwordHash;

    private User(int id, String firstName, String lastName, String email, String passwordHash) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.passwordHash = passwordHash;
    }

    public static User getInstance(ResultSet result) throws SQLException {
        int id = result.getInt("user_id");
        String firstName = result.getString("first_name");
        String lastName = result.getString("last_name");
        String email = result.getString("email");
        String passwordHash = result.getString("password_hash");
        return new User(id, firstName, lastName, email, passwordHash);
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
}
