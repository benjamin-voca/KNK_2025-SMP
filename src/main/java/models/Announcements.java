package models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class Announcements {
    private int id;
    private int courseId;
    private String title;
    private String content;
    private Timestamp createdAt;

    private Announcements(int id, int courseId, String title, String content, Timestamp createdAt) {
        this.id = id;
        this.courseId = courseId;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
    }

    public static Announcements getInstance(ResultSet result) throws SQLException {
        int id = result.getInt("announcement_id");
        int courseId = result.getInt("course_id");
        String title = result.getString("title");
        String content = result.getString("content");
        Timestamp createdAt = result.getTimestamp("created_at");
        return new Announcements(id, courseId, title, content, createdAt);
    }

    public int getId() {
        return id;
    }

    public int getCourseId() {
        return courseId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }
}
