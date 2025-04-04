package models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Student {
    private int student_id;
    private int user_id;
    private String student_number;
    private int year_of_study;

    public Student(int student_id, int user_id, String student_number, int year_of_study) {
        this.student_id = student_id;
        this.user_id = user_id;
        this.student_number = student_number;
        this.year_of_study = year_of_study;
    }

    public static Student getInstance(ResultSet result) throws SQLException {
        int student_id = result.getInt("student_id");
        int user_id = result.getInt("user_id");
        String student_number = result.getString("student_number");
        int year_of_study = result.getInt("year_of_study");
        return new Student(student_id, user_id, student_number, year_of_study);
    }

    public int getStudent_id() {
        return student_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getStudent_number() {
        return student_number;
    }

    public int getYear_of_study() {
        return year_of_study;
    }
}
