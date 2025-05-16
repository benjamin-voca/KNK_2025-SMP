package models.dto;

import java.util.Date;

public class CreateNotificationDto {
    private String title;
    private String content;
    private Date createdAt;

    public CreateNotificationDto(String title, String content, Date createdAt) {
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
