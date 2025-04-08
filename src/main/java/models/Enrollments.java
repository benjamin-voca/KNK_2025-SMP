package models;


import java.sql.ResultSet;
import java.sql.SQLException;


public class Enrollments {
    private int enrollment_id;
    private int student_id;
    private int course_id;

    public Enrollments(int enrollment_id, int student_id, int course_id) {
        this.enrollment_id = enrollment_id;
        this.student_id = student_id;
        this.course_id = course_id;
    }

    public static Enrollments getInstance(ResultSet result) throws SQLException {
        int enrollment_id = result.getInt("enrollment_id");
        int student_id = result.getInt("student_id");
        int professor_id = result.getInt("professor_id");

        return new Enrollments(enrollment_id,student_id, professor_id);
    }

    public int getEnrollment_id() {
        return enrollment_id;
    }

    public int getStudent_id() {
        return student_id;
    }

    public int getCourse_id() {
        return course_id;
    }
}
