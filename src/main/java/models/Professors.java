package models;

import java.sql.ResultSet;
import java.sql.SQLException;



public class Professors {
    private int id;
    private int userId;
    private String department;

    private Professors(int id, int userId, String department) {
        this.id = id;
        this.userId = userId;
        this.department = department;
    }

    public static Professors getInstance(ResultSet result) throws SQLException {
        int id = result.getInt("professor_id");
        int userId = result.getInt("user_id");
        String department = result.getString("department");

        return new Professors(id, userId, department);
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getDepartment() {
        return department;
    }
}

