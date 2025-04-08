package models;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Courses {
    private int id;
    private String courseName;
    private String courseCode;
    private int professorId;

    private Courses(int id, String courseName, String courseCode, int professorId) {
        this.id = id;
        this.courseName = courseName;
        this.courseCode = courseCode;
        this.professorId = professorId;
    }

    public static Courses getInstance(ResultSet result) throws SQLException {
        int id = result.getInt("course_id");
        String courseName = result.getString("course_name");
        String courseCode = result.getString("course_code");
        int professorId = result.getInt("professor_id");

        return new Courses(id, courseName, courseCode, professorId);
    }

    public int getId() {
        return id;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public int getProfessorId() {
        return professorId;
    }
}


