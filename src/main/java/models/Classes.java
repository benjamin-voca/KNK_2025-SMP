package models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Classes {
    private int gradeId;
    private int studentId;
    private int courseId;
    private float grade;

    private Classes(int gradeId, int studentId, int courseId, float grade) {
        this.gradeId = gradeId;
        this.studentId = studentId;
        this.courseId = courseId;
        this.grade = grade;
    }

    public static Classes getInstance(ResultSet result) throws SQLException {
        int gradeId = result.getInt("grade_id");
        int studentId = result.getInt("student_id");
        int courseId = result.getInt("course_id");
        float grade = result.getFloat("grade");
        return new Classes(gradeId, studentId, courseId, grade);
    }

    public int getGradeId() {
        return gradeId;
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
