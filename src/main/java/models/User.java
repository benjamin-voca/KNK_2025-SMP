package models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
    private int user_id;
    private String first_name;
    private String last_name;
    private String email;
    private String password_hash;

    private User(int user_id, String first_name, String last_name, String email, String password_hash) {
        this.user_id = user_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.password_hash = password_hash;
    }

    public static User getInstance(ResultSet result) throws SQLException {
        int user_id = result.getInt("user_id");
        String first_name = result.getString("first_name");
        String last_name = result.getString("last_name");
        String email = result.getString("email");
        String password_hash = result.getString("password_hash");
        return new User(user_id, first_name, last_name, email, password_hash);
    }

    public int getUser_id() {
        return user_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword_hash() {
        return password_hash;
    }
}
