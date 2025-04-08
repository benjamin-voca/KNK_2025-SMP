package models;

import java.sql.ResultSet;
import java.sql.SQLException;



public class Grades {
    private int id;
    private int studentId;
    private int courseId;
    private float grade;

    public Grades(int id, int studentId, int courseId, float grade) {
        this.id = id;
        this.studentId = studentId;
        this.courseId = courseId;
        this.grade = grade;
    }

    public static Grades getInstance(ResultSet result) throws SQLException {
        int id = result.getInt("grade_id");
        int studentId = result.getInt("student_id");
        int courseId = result.getInt("course_id");
        float grade = result.getFloat("grade");

        return new Grades(id, studentId, courseId, grade);
    }

    public int getId() {
        return id;
    }

    public int getStudentId() {
        return studentId;
    }

    public int getCourseId() {
        return courseId;
    }

    public float getGrade() {
        return grade;
    }
}

