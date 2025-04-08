package models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class Submissions {
    private int id;
    private int assignmentId;
    private int studentId;
    private Timestamp submittedAt;
    private String content;
    private float grade;
    private String feedback;

    private Submissions(int id, int assignmentId, int studentId, Timestamp submittedAt, String content, float grade, String feedback) {
        this.id = id;
        this.assignmentId = assignmentId;
        this.studentId = studentId;
        this.submittedAt = submittedAt;
        this.content = content;
        this.grade = grade;
        this.feedback = feedback;
    }

    public static Submissions getInstance(ResultSet result) throws SQLException {
        int id = result.getInt("submission_id");
        int assignmentId = result.getInt("assignment_id");
        int studentId = result.getInt("student_id");
        Timestamp submittedAt = result.getTimestamp("submitted_at");
        String content = result.getString("content");
        float grade = result.getFloat("grade");
        String feedback = result.getString("feedback");
        return new Submissions(id, assignmentId, studentId, submittedAt, content, grade, feedback);
    }

    public int getId() {
        return id;
    }

    public int getAssignmentId() {
        return assignmentId;
    }

    public int getStudentId() {
        return studentId;
    }

    public Timestamp getSubmittedAt() {
        return submittedAt;
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
