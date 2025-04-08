package models.dto;

import java.util.Date;

public class CreateAssignmentDto {
    private int courseId;
    private String title;
    private String description;
    private Date dueDate;

    public CreateAssignmentDto(int courseId, String title, String description, Date dueDate) {
        this.courseId = courseId;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }
}
