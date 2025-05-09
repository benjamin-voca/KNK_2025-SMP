package repository;

import database.DB_Connector;
import models.Student;
import models.dto.CreateStudentDto;
import models.dto.UpdateStudentDto;

import java.sql.*;

public class StudentRepository extends BaseRepository<Student, CreateStudentDto, UpdateStudentDto> {

    public StudentRepository() {
        super("students");
    }

    @Override
    public Student fromResultSet(ResultSet result) throws SQLException {
        return Student.getInstance(result);
    }

    public Student findByStudentNumber(String studentNumber) {
        String query = "SELECT * FROM students WHERE student_number = ?";

        try {
            PreparedStatement pstm = this.connection.prepareStatement(query);
            pstm.setString(1, studentNumber);

            ResultSet result = pstm.executeQuery();
            if (result.next()) {
                return fromResultSet(result);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Student create(CreateStudentDto dto) {
        String query = """
                INSERT INTO students (user_id, student_number, year_of_study)
                VALUES (?, ?, ?)
                """;

        try {
            PreparedStatement pstm = this.connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pstm.setInt(1, getUserIdForNewStudent());
            pstm.setString(2, dto.getStudent_number());
            pstm.setInt(3, dto.getYear_of_study());

            pstm.executeUpdate();

            ResultSet res = pstm.getGeneratedKeys();
            if (res.next()) {
                int studentId = res.getInt(1);
                return this.getById(studentId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Student update(UpdateStudentDto dto) {
        String query = """
                UPDATE students
                SET student_number = ?, year_of_study = ?
                WHERE student_id = ?
                """;

        try {
            PreparedStatement pstm = this.connection.prepareStatement(query);
            pstm.setString(1, dto.getStudentNumber());
            pstm.setInt(2, dto.getYearOfStudy());
            pstm.setInt(3, dto.getStudent_id());

            int updated = pstm.executeUpdate();
            if (updated == 1) {
                return this.getById(dto.getStudent_id());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    private int getUserIdForNewStudent() {
        return 1;
    }
}
