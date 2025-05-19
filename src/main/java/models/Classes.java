package models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class Classes {
    private int id;
    private String className;
    private int courseId;
    private Timestamp schedule;
    private String location;
    private String classType;
    private Integer duration;

    private Classes(int id, String className, int courseId, Timestamp schedule, String location, String classType, Integer duration) {
        this.id = id;
        this.className = className;
        this.courseId = courseId;
        this.schedule = schedule;
        this.location = location;
        this.classType = classType;
        this.duration = duration;
    }

    public static Classes getInstance(ResultSet result) throws SQLException {
        int id = result.getInt("id");
        String className = result.getString("class_name");
        int courseId = result.getInt("course_id");
        Timestamp schedule = result.getTimestamp("schedule");
        String location = result.getString("location");
        String classType = result.getString("class_type");
        Integer duration = result.getInt("duration");
        if (result.wasNull()) {
            duration = null;
        }
        return new Classes(id, className, courseId, schedule, location, classType, duration);
    }

    public int getId() { return id; }
    public String getClassName() { return className; }
    public int getCourseId() { return courseId; }
    public Timestamp getSchedule() { return schedule; }
    public String getLocation() { return location; }
    public String getClassType() { return classType; }
    public Integer getDuration() { return duration; }
}