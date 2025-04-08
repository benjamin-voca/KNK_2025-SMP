package models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Classes {
    private int id;
    private String className;
    private int courseId;
    private String schedule;

    private Classes(int id, String className, int courseId, String schedule) {
        this.id = id;
        this.className = className;
        this.courseId = courseId;
        this.schedule = schedule;
    }

    public static Classes getInstance(ResultSet result) throws SQLException {
        int id = result.getInt("class_id");
        String className = result.getString("class_name");
        int courseId = result.getInt("course_id");
        String schedule = result.getString("schedule");

        return new Classes(id, className, courseId, schedule);
    }

    public int getId() {
        return id;
    }

    public String getClassName() {
        return className;
    }

    public int getCourseId() {
        return courseId;
    }

    public String getSchedule() {
        return schedule;
    }
}
