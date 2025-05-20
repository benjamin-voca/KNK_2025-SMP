package models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.math.BigDecimal;

public class Submissions {
    private int id;
    private int assignmentId;
    private int studentId;
    private Timestamp submittedAt;
    private String content;
    private BigDecimal grade;
    private String feedback;
    private String status;

    private Submissions(int id, int assignmentId, int studentId, Timestamp submittedAt, String content, BigDecimal grade, String feedback, String status) {
        this.id = id;
        this.assignmentId = assignmentId;
        this.studentId = studentId;
        this.submittedAt = submittedAt;
        this.content = content;
        this.grade = grade;
        this.feedback = feedback;
        this.status = status;
    }

    public static Submissions getInstance(ResultSet result) throws SQLException {
        int id = result.getInt("id");
        int assignmentId = result.getInt("assignment_id");
        int studentId = result.getInt("student_id");
        Timestamp submittedAt = result.getTimestamp("submitted_at");
        String content = result.getString("content");
        BigDecimal grade = result.getBigDecimal("grade");
        String feedback = result.getString("feedback");
        String status = result.getString("status");
        return new Submissions(id, assignmentId, studentId, submittedAt, content, grade, feedback, status);
    }

    public int getId() { return id; }
    public int getAssignmentId() { return assignmentId; }
    public int getStudentId() { return studentId; }
    public Timestamp getSubmittedAt() { return submittedAt; }
    public String getContent() { return content; }
    public BigDecimal getGrade() { return grade; }
    public String getFeedback() { return feedback; }
    public String getStatus() { return status; }
}