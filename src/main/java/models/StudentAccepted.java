package models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentAccepted {
    private String id;
    private String name;
    private String surname;
    private String address;
    private int age;
    private String status;
    private String program;

    public StudentAccepted(String id, String name, String surname, String address, int age, String status, String program) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.age = age;
        this.status = status;
        this.program = program;
    }

    public static StudentAccepted getInstance(ResultSet result) throws SQLException {
        String id = result.getString("id");
        String name = result.getString("name");
        String surname = result.getString("surname");
        String address = result.getString("address");
        int age = result.getInt("age");
        String status = result.getString("status");
        String program = result.getString("program");
        return new StudentAccepted(id ,name , surname , address , age , status , program);
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
}
