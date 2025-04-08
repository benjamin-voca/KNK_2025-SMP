package models.dto;

public class UpdateClassDto {
    private int id;
    private String className;
    private String schedule;

    public UpdateClassDto(int id, String className, String schedule) {
        this.id = id;
        this.className = className;
        this.schedule = schedule;
    }

    public int getId() {
        return id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }
}
