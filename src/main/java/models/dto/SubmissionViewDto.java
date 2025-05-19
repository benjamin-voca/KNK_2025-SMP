package models.dto;

import java.sql.Timestamp;
import java.math.BigDecimal;

public class SubmissionViewDto {
    private int id;
    private String assignmentTitle;
    private String courseName;
    private Timestamp submittedAt;
    private BigDecimal grade;
    private String feedback;
    private String status;

    public SubmissionViewDto() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getAssignmentTitle() { return assignmentTitle; }
    public void setAssignmentTitle(String assignmentTitle) { this.assignmentTitle = assignmentTitle; }
    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }
    public Timestamp getSubmittedAt() { return submittedAt; }
    public void setSubmittedAt(Timestamp submittedAt) { this.submittedAt = submittedAt; }
    public BigDecimal getGrade() { return grade; }
    public void setGrade(BigDecimal grade) { this.grade = grade; }
    public String getFeedback() { return feedback; }
    public void setFeedback(String feedback) { this.feedback = feedback; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}