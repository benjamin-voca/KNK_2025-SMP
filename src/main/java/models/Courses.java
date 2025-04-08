package models;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Courses {
    private int course_id;
    private String course_name;
    private String course_code;
    private int professor_id;



    private Courses(int courses_id,String course_name , String course_code, int professor_id) {
        this.course_id = courses_id;
        this.course_name = course_name;
        this.course_code = course_code;
        this.professor_id = professor_id;
    }
    public static Courses getInstance(ResultSet result) throws SQLException {
        int course_id = result.getInt("course_id");
        String course_name = result.getString("course_name");
        String course_code = result.getString("course_code");
        int professor_id = result.getInt("professor_id");

        return new Courses(course_id, course_name, course_code, professor_id);
    }
    public int getCourses_id() {
        return course_id;
    }

    public String getCourse_name() {
        return course_name;
    }

    public String getCourse_code() {
        return course_code;
    }

    public int getProfessor_id() {
        return professor_id;
    }
}
