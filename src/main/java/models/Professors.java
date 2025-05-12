package models;

import java.sql.ResultSet;
import java.sql.SQLException;



public class Professors {
    private int id;
    private int userId;
    private String professorNumber;
    private String department;

    private Professors(int id, int userId, String professorNumber, String department) {
        this.id = id;
        this.userId = userId;
        this.professorNumber = professorNumber;
        this.department = department;
    }

    public static Professors getInstance(ResultSet result) throws SQLException {
        int id = result.getInt("id");
        int userId = result.getInt("user_id");
        String professorNumber = result.getString("professor_number");
        String department = result.getString("department");

        return new Professors(id, userId, professorNumber, department);
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getProfessorNumber() {
        return professorNumber;
    }

    public String getDepartment() {
        return department;
    }
}

