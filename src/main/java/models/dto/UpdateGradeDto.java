package models.dto;

public class UpdateGradeDto {
    private int id;
    private float grade;

    public UpdateGradeDto(int id, float grade) {
        this.id = id;
        this.grade = grade;
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
}
