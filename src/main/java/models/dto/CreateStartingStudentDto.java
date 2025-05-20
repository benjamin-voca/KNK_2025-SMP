package models.dto;

public class CreateStartingStudentDto {
    private String name;
    private String surname;
    private String address;
    private int age;
    private String gpaTranscript;
    private String ethnicity;
    private String extraCreditDocument;
    private double testScore;
    private int acceptanceTestScore;
    private String program;

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGpaTranscript() {
        return gpaTranscript;
    }

    public void setGpaTranscript(String gpaTranscript) {
        this.gpaTranscript = gpaTranscript;
    }

    public String getEthnicity() {
        return ethnicity;
    }

    public void setEthnicity(String ethnicity) {
        this.ethnicity = ethnicity;
    }

    public String getExtraCreditDocument() {
        return extraCreditDocument;
    }

    public void setExtraCreditDocument(String extraCreditDocument) {
        this.extraCreditDocument = extraCreditDocument;
    }

    public double getTestScore() {
        return testScore;
    }

    public void setTestScore(double testScore) {
        this.testScore = testScore;
    }

    public int getAcceptanceTestScore() {
        return acceptanceTestScore;
    }

    public void setAcceptanceTestScore(int acceptanceTestScore) {
        this.acceptanceTestScore = acceptanceTestScore;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }
}