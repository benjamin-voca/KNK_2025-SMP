package models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Student {
    private int id;
    private int userId;
    private String studentNumber;
    private int yearOfStudy;

    private Student(int id, int userId, String studentNumber, int yearOfStudy) {
        this.id = id;
        this.userId = userId;
        this.studentNumber = studentNumber;
        this.yearOfStudy = yearOfStudy;
    }

    public static Student getInstance(ResultSet result) throws SQLException {
        int id = result.getInt("student_id");
        int userId = result.getInt("user_id");
        String studentNumber = result.getString("student_number");
        int yearOfStudy = result.getInt("year_of_study");
        return new Student(id, userId, studentNumber, yearOfStudy);
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public int getYearOfStudy() {
        return yearOfStudy;
    }
}
