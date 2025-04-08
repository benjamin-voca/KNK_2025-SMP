package models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Assignments {
    private int assignment_id;
    private int course_id;
    private String title;
    private String description;
    private Date due_date;

    private Assignments(int assignment_id, int course_id, String title, String description, Date due_date) {
        this.assignment_id = assignment_id;
        this.course_id = course_id;
        this.title = title;
        this.description = description;
        this.due_date = due_date;
    }

    public static Assignments getInstance(ResultSet result) throws SQLException {
        int assignment_id = result.getInt("assignment_id");
        int course_id = result.getInt("course_id");
        String title = result.getString("title");
        String description = result.getString("description");
        Date due_date = result.getDate("due_date");
        return new Assignments(assignment_id, course_id, title, description, due_date);
    }

    public int getAssignment_id() {
        return assignment_id;
    }

    public int getCourse_id() {
        return course_id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Date getDue_date() {
        return due_date;
    }
}
