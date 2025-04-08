package models;


import java.sql.ResultSet;
import java.sql.SQLException;


public class Enrollments {
    private int id;
    private int studentId;
    private int courseId;

    public Enrollments(int id, int studentId, int courseId) {
        this.id = id;
        this.studentId = studentId;
        this.courseId = courseId;
    }

    public static Enrollments getInstance(ResultSet result) throws SQLException {
        int id = result.getInt("enrollment_id");
        int studentId = result.getInt("student_id");
        int courseId = result.getInt("course_id");

        return new Enrollments(id, studentId, courseId);
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
}

