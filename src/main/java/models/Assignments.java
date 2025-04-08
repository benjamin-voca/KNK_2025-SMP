package models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Assignments {
    private int id;
    private int courseId;
    private String title;
    private String description;
    private Date dueDate;

    private Assignments(int id, int courseId, String title, String description, Date dueDate) {
        this.id = id;
        this.courseId = courseId;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
    }

    public static Assignments getInstance(ResultSet result) throws SQLException {
        int id = result.getInt("assignment_id");
        int courseId = result.getInt("course_id");
        String title = result.getString("title");
        String description = result.getString("description");
        Date dueDate = result.getDate("due_date");
        return new Assignments(id, courseId, title, description, dueDate);
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

    public String getDescription() {
        return description;
    }

    public Date getDueDate() {
        return dueDate;
    }
}
