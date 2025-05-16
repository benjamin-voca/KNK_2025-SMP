package models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Notifications {
    private int id;
    private String title;
    private String content;
    private Date createdAt;

    private Notifications(int id, String title, String content, Date createdAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
    }

    public static Notifications getInstance(ResultSet result) throws SQLException {
        int id = result.getInt("id");
        String title = result.getString("title");
        String content = result.getString("content");
        Date dueDate = result.getDate("created_at");
        return new Notifications(id, title, content, dueDate);
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Date getCreatedAt() {
        return createdAt;
    }
}
