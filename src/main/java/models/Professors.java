package models;

import java.sql.ResultSet;
import java.sql.SQLException;



public class Professors {
    private int professor_id;
    private int user_id;
    private String department;

    private Professors(int professor_id, int user_id, String department) {
        this.professor_id = professor_id;
        this.user_id = user_id;
        this.department = department;
    }
    public static Professors getInstance(ResultSet result) throws SQLException {
        int professor_id = result.getInt("professor_id");
        int user_id = result.getInt("user_id");
        String department = result.getString("department");

        return new Professors(professor_id, user_id, department);
    }
    public int getProfessor_id() {
        return professor_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getDepartment() {
        return department;
    }
}
