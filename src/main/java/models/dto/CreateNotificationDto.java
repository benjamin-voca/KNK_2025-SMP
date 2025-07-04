package models.dto;

public class CreateNotificationDto {
    private String title;
    private String content;

    public CreateNotificationDto(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}