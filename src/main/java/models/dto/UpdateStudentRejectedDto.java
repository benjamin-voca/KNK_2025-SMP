package models.dto;

public class UpdateStudentRejectedDto {
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

    public UpdateStudentRejectedDto(int id, String name, String surname, String address, int age, double gpa, String ethnicity, int extraCredit, double testScore, int acceptanceTestScore, String programIntended) {
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

    public int getAcceptanceTestScore() {
        return acceptanceTestScore;
    }

    public String getProgramIntended() {
        return programIntended;
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

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }

    public void setEthnicity(String ethnicity) {
        this.ethnicity = ethnicity;
    }

    public void setExtraCredit(int extraCredit) {
        this.extraCredit = extraCredit;
    }

    public void setTestScore(double testScore) {
        this.testScore = testScore;
    }

    public void setAcceptanceTestScore(int acceptanceTestScore) {
        this.acceptanceTestScore = acceptanceTestScore;
    }

    public void setProgramIntended(String programIntended) {
        this.programIntended = programIntended;
    }
}
