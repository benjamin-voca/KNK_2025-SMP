package models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Classes {
    private int grade_id;
    private int student_id;
    private int course_id;
    private float grade;

    private Classes(int grade_id, int student_id, int course_id, float grade) {
        this.grade_id = grade_id;
        this.student_id = student_id;
        this.course_id = course_id;
        this.grade = grade;
    }

    public static Classes getInstance(ResultSet result) throws SQLException {
        int grade_id = result.getInt("grade_id");
        int student_id = result.getInt("student_id");
        int course_id = result.getInt("course_id");
        float grade = result.getFloat("grade");
        return new Classes(grade_id, student_id, course_id, grade);
    }

    public int getGrade_id() {
        return grade_id;
    }

    public int getStudent_id() {
        return student_id;
    }

    public int getCourse_id() {
        return course_id;
    }

    public float getGrade() {
        return grade;
    }
}
