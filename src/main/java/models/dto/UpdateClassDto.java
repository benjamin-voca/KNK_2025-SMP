package models.dto;

import java.sql.Timestamp;

public class UpdateClassDto {
    private int id;
    private String className;
    private Timestamp schedule;
    private String location;
    private String classType;
    private Integer duration;

    public UpdateClassDto(int id, String className, Timestamp schedule, String location, String classType, Integer duration) {
        this.id = id;
        this.className = className;
        this.schedule = schedule;
        this.location = location;
        this.classType = classType;
        this.duration = duration;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getClassName() { return className; }
    public void setClassName(String className) { this.className = className; }
    public Timestamp getSchedule() { return schedule; }
    public void setSchedule(Timestamp schedule) { this.schedule = schedule; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public String getClassType() { return classType; }
    public void setClassType(String classType) { this.classType = classType; }
    public Integer getDuration() { return duration; }
    public void setDuration(Integer duration) { this.duration = duration; }
}