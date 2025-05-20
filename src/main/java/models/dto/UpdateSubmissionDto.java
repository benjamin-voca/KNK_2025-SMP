package models.dto;

import java.math.BigDecimal;

public class UpdateSubmissionDto {
    private int id;
    private BigDecimal grade;
    private String feedback;
    private String status;

    public UpdateSubmissionDto(int id, BigDecimal grade, String feedback, String status) {
        this.id = id;
        this.grade = grade;
        this.feedback = feedback;
        this.status = status;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public BigDecimal getGrade() { return grade; }
    public void setGrade(BigDecimal grade) { this.grade = grade; }
    public String getFeedback() { return feedback; }
    public void setFeedback(String feedback) { this.feedback = feedback; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}