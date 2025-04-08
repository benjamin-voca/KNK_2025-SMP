package models.dto;

public class CreateProfessorDto {
    private String department;

    public CreateProfessorDto(String department) {
        this.department = department;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
