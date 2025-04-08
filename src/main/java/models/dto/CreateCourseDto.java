package models.dto;

public class CreateCourseDto {
    private String courseName;
    private String courseCode;
    private int professorId;

    public CreateCourseDto(String courseName, String courseCode, int professorId) {
        this.courseName = courseName;
        this.courseCode = courseCode;
        this.professorId = professorId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public int getProfessorId() {
        return professorId;
    }

    public void setProfessorId(int professorId) {
        this.professorId = professorId;
    }
}
