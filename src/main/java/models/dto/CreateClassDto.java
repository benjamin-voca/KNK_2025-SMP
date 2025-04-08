package models.dto;

public class CreateClassDto {
    private String className;
    private int courseId;
    private String schedule;

    public CreateClassDto(String className, int courseId, String schedule) {
        this.className = className;
        this.courseId = courseId;
        this.schedule = schedule;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }
}
