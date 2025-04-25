package models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentRejected {
    private int id;
    private String name;
    private String surname;
    private String address;
    private int age;
    private double gpa;
    private String ethnicity;
    private int extraCredit;
    private double testScore;
    private int acceptanceTestScore;
    private String programIntended;

    private StudentRejected(int id, String name, String surname, String address, int age, double gpa, String ethnicity, int extraCredit, double testScore, int acceptanceTestScore, String programIntended) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.age = age;
        this.gpa = gpa;
        this.ethnicity = ethnicity;
        this.extraCredit = extraCredit;
        this.testScore = testScore;
        this.acceptanceTestScore = acceptanceTestScore;
        this.programIntended = programIntended;
    }

    private StudentRejected getInstance(ResultSet result) throws SQLException {
        int id = result.getInt("id");
        String name = result.getString("name");
        String surname = result.getString("surname");
        String address = result.getString("address");
        int age = result.getInt("age");
        double gpa = result.getDouble("gpa");
        String ethnicity = result.getString("ethnicity");
        int extraCredit = result.getInt("extra_credit");
        double testScore = result.getDouble("test_score");
        int acceptanceTestScore = result.getInt("acceptance_test_score");
        String programIntended = result.getString("program_intended");
        return new StudentRejected(id ,name , surname , address , age , gpa , ethnicity , extraCredit , testScore , acceptanceTestScore , programIntended);
    }

    public int getId() {
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

    public double getGpa() {
        return gpa;
    }

    public String getEthnicity() {
        return ethnicity;
    }

    public int getExtraCredit() {
        return extraCredit;
    }

    public double getTestScore() {
        return testScore;
    }

    public double getAcceptanceTestScore() {
        return acceptanceTestScore;
    }

    public String getProgramIntended() {
        return programIntended;
    }
}
