package models.dto;

public class UpdateStudentDto {
    private int student_id;
    private String studentNumber;
    private int yearOfStudy;

    public UpdateStudentDto(int student_id, String studentNumber, int yearOfStudy) {
        this.student_id = student_id;
        this.studentNumber = studentNumber;
        this.yearOfStudy = yearOfStudy;
    }

    public int getStudent_id() {
        return student_id;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public int getYearOfStudy() {
        return yearOfStudy;
    }

    public void setYearOfStudy(int yearOfStudy) {
        this.yearOfStudy = yearOfStudy;
    }
}
