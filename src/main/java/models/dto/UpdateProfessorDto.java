package models.dto;

public class UpdateProfessorDto {
    private int id;
    private int userId;
    private String department;

    public UpdateProfessorDto(int id, int userId, String department) {
        this.id = id;
        this.userId = userId;
        this.department = department;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
