package models.dto;

import java.sql.Timestamp;

public class CreateAnnouncementDto {
    private int courseId;
    private String title;
    private String content;
    private Timestamp createdAt;

    public CreateAnnouncementDto(int courseId, String title, String content, Timestamp createdAt) {
        this.courseId = courseId;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
