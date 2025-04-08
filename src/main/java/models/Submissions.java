package models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class Submissions {
    private int submission_id;
    private int assignment_id;
    private int student_id;
    private Timestamp submitted_at;
    private String content;
    private float grade;
    private String feedback;

    private Submissions(int submission_id, int assignment_id, int student_id, Timestamp submitted_at, String content, float grade, String feedback) {
        this.submission_id = submission_id;
        this.assignment_id = assignment_id;
        this.student_id = student_id;
        this.submitted_at = submitted_at;
        this.content = content;
        this.grade = grade;
        this.feedback = feedback;
    }

    public static Submissions getInstance(ResultSet result) throws SQLException {
        int submission_id = result.getInt("submission_id");
        int assignment_id = result.getInt("assignment_id");
        int student_id = result.getInt("student_id");
        Timestamp submitted_at = result.getTimestamp("submitted_at");
        String content = result.getString("content");
        float grade = result.getFloat("grade");
        String feedback = result.getString("feedback");
        return new Submissions(submission_id, assignment_id, student_id, submitted_at, content, grade, feedback);
    }

    public int getSubmission_id() {
        return submission_id;
    }

    public int getAssignment_id() {
        return assignment_id;
    }

    public int getStudent_id() {
        return student_id;
    }

    public Timestamp getSubmitted_at() {
        return submitted_at;
    }

    public String getContent() {
        return content;
    }

    public float getGrade() {
        return grade;
    }

    public String getFeedback() {
        return feedback;
    }
}
