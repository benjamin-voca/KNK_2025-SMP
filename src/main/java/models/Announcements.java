package models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class Announcements {
    private int announcement_id;
    private int course_id;
    private String title;
    private String content;
    private Timestamp created_at;

    private Announcements(int announcement_id, int course_id, String title, String content, Timestamp created_at) {
        this.announcement_id = announcement_id;
        this.course_id = course_id;
        this.title = title;
        this.content = content;
        this.created_at = created_at;
    }

    public static Announcements getInstance(ResultSet result) throws SQLException {
        int announcement_id = result.getInt("announcement_id");
        int course_id = result.getInt("course_id");
        String title = result.getString("title");
        String content = result.getString("content");
        Timestamp created_at = result.getTimestamp("created_at");
        return new Announcements(announcement_id, course_id, title, content, created_at);
    }

    public int getAnnouncement_id() {
        return announcement_id;
    }

    public int getCourse_id() {
        return course_id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }
}
