package models.dto;

public class UpdateStudentAcceptedDto {
    private String id;
    private String name;
    private String surname;
    private String address;
    private int age;
    private String status;
    private String program;

    public UpdateStudentAcceptedDto(String id, String name, String surname, String address, int age, String status, String program) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.age = age;
        this.status = status;
        this.program = program;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getAddress() {
        return address;
    }

    public int getAge() {
        return age;
    }

    public String getStatus() {
        return status;
    }

    public String getProgram() {
        return program;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setProgram(String program) {
        this.program = program;
    }
}

