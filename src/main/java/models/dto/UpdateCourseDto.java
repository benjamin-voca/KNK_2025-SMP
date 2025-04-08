package models.dto;

public class UpdateCourseDto {
    private int id;
    private String courseName;

    public UpdateCourseDto(int id, String courseName) {
        this.id = id;
        this.courseName = courseName;
    }

    public int getId() {
        return id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
}
