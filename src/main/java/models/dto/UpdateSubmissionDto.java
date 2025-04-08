package models.dto;

import java.sql.Timestamp;

public class UpdateSubmissionDto {
    private int id;
    private float grade;
    private String feedback;

    public UpdateSubmissionDto(int id, float grade, String feedback) {
        this.id = id;
        this.grade = grade;
        this.feedback = feedback;
    }

    public int getId() {
        return id;
    }

    public float getGrade() {
        return grade;
    }

    public void setGrade(float grade) {
        this.grade = grade;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
