package models.dto;

public class CreateStudentDto {
    private String student_number;
    private int year_of_study;

    public CreateStudentDto(String student_number, int year_of_study) {
        this.student_number = student_number;
        this.year_of_study = year_of_study;
    }

    public String getStudent_number() {
        return student_number;
    }

    public void setStudent_number(String student_number) {
        this.student_number = student_number;
    }

    public int getYear_of_study() {
        return year_of_study;
    }

    public void setYear_of_study(int year_of_study) {
        this.year_of_study = year_of_study;
    }
}
