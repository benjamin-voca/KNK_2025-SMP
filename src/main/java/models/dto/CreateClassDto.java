package models.dto;

import java.sql.Timestamp;

public class CreateClassDto {
    private String className;
    private int courseId;
    private Timestamp schedule;
    private String location;
    private String classType;
    private Integer duration;

    public CreateClassDto(String className, int courseId, Timestamp schedule, String location, String classType, Integer duration) {
        this.className = className;
        this.courseId = courseId;
        this.schedule = schedule;
        this.location = location;
        this.classType = classType;
        this.duration = duration;
    }

    public String getClassName() { return className; }
    public void setClassName(String className) { this.className = className; }
    public int getCourseId() { return courseId; }
    public void setCourseId(int courseId) { this.courseId = courseId; }
    public Timestamp getSchedule() { return schedule; }
    public void setSchedule(Timestamp schedule) { this.schedule = schedule; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public String getClassType() { return classType; }
    public void setClassType(String classType) { this.classType = classType; }
    public Integer getDuration() { return duration; }
    public void setDuration(Integer duration) { this.duration = duration; }
}