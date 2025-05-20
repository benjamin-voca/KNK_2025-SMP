package models.dto;

import java.sql.Timestamp;
import java.math.BigDecimal;

public class CreateSubmissionDto {
    private int assignmentId;
    private int studentId;
    private Timestamp submittedAt;
    private String content;
    private BigDecimal grade;
    private String feedback;
    private String status;

    public CreateSubmissionDto(int assignmentId, int studentId, Timestamp submittedAt, String content, BigDecimal grade, String feedback, String status) {
        this.assignmentId = assignmentId;
        this.studentId = studentId;
        this.submittedAt = submittedAt;
        this.content = content;
        this.grade = grade;
        this.feedback = feedback;
        this.status = status;
    }

    public int getAssignmentId() { return assignmentId; }
    public void setAssignmentId(int assignmentId) { this.assignmentId = assignmentId; }
    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }
    public Timestamp getSubmittedAt() { return submittedAt; }
    public void setSubmittedAt(Timestamp submittedAt) { this.submittedAt = submittedAt; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public BigDecimal getGrade() { return grade; }
    public void setGrade(BigDecimal grade) { this.grade = grade; }
    public String getFeedback() { return feedback; }
    public void setFeedback(String feedback) { this.feedback = feedback; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}