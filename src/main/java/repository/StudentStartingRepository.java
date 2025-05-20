package repository;

import database.DB_Connector;
import models.StartingStudent;
import models.dto.CreateStartingStudentDto;
import models.dto.UpdateStartingStudentDto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StudentStartingRepository {

    public StartingStudent save(CreateStartingStudentDto dto) {
        String sql = "INSERT INTO student_starting (name, surname, address, age, gpa_transcript, ethnicity, extra_credit_document, test_score, acceptance_test_score, program) " +
                "VALUES (?, ?, ?, ?, ?, ?::ethnicity_type, ?, ?, ?, ?::program_type) RETURNING id";
        try (Connection conn = DB_Connector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, dto.getName());
            stmt.setString(2, dto.getSurname());
            stmt.setString(3, dto.getAddress());
            stmt.setInt(4, dto.getAge());
            stmt.setString(5, dto.getGpaTranscript());
            stmt.setString(6, dto.getEthnicity());
            stmt.setString(7, dto.getExtraCreditDocument());
            stmt.setDouble(8, dto.getTestScore());
            stmt.setInt(9, dto.getAcceptanceTestScore());
            stmt.setString(10, dto.getProgram());
            ResultSet rs = stmt.executeQuery();
            StartingStudent student = new StartingStudent();
            if (rs.next()) {
                student.setId(rs.getInt("id"));
            }
            student.setName(dto.getName());
            student.setSurname(dto.getSurname());
            student.setAddress(dto.getAddress());
            student.setAge(dto.getAge());
            student.setGpaTranscript(dto.getGpaTranscript());
            student.setEthnicity(dto.getEthnicity());
            student.setExtraCreditDocument(dto.getExtraCreditDocument());
            student.setTestScore(dto.getTestScore());
            student.setAcceptanceTestScore(dto.getAcceptanceTestScore());
            student.setProgram(dto.getProgram());
            return student;
        } catch (SQLException e) {
            throw new RuntimeException("Error saving student: " + e.getMessage());
        }
    }

    public Optional<StartingStudent> findById(int id) {
        String sql = "SELECT * FROM student_starting WHERE id = ?";
        try (Connection conn = DB_Connector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                StartingStudent student = new StartingStudent();
                student.setId(rs.getInt("id"));
                student.setName(rs.getString("name"));
                student.setSurname(rs.getString("surname"));
                student.setAddress(rs.getString("address"));
                student.setAge(rs.getInt("age"));
                student.setGpaTranscript(rs.getString("gpa_transcript"));
                student.setEthnicity(rs.getString("ethnicity"));
                student.setExtraCreditDocument(rs.getString("extra_credit_document"));
                student.setTestScore(rs.getDouble("test_score"));
                student.setAcceptanceTestScore(rs.getInt("acceptance_test_score"));
                student.setProgram(rs.getString("program"));
                return Optional.of(student);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Error finding student by ID: " + e.getMessage());
        }
    }

    public List<StartingStudent> findAll() {
        List<StartingStudent> students = new ArrayList<>();
        String sql = "SELECT * FROM student_starting";
        try (Connection conn = DB_Connector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                StartingStudent student = new StartingStudent();
                student.setId(rs.getInt("id"));
                student.setName(rs.getString("name"));
                student.setSurname(rs.getString("surname"));
                student.setAddress(rs.getString("address"));
                student.setAge(rs.getInt("age"));
                student.setGpaTranscript(rs.getString("gpa_transcript"));
                student.setEthnicity(rs.getString("ethnicity"));
                student.setExtraCreditDocument(rs.getString("extra_credit_document"));
                student.setTestScore(rs.getDouble("test_score"));
                student.setAcceptanceTestScore(rs.getInt("acceptance_test_score"));
                student.setProgram(rs.getString("program"));
                students.add(student);
            }
            return students;
        } catch (SQLException e) {
            throw new RuntimeException("Error finding all students: " + e.getMessage());
        }
    }

    public boolean update(UpdateStartingStudentDto dto) {
        String sql = "UPDATE student_starting SET name = ?, surname = ?, address = ?, age = ?, gpa_transcript = ?, " +
                "ethnicity = ?::ethnicity_type, extra_credit_document = ?, test_score = ?, acceptance_test_score = ?, " +
                "program = ?::program_type WHERE id = ?";
        try (Connection conn = DB_Connector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, dto.getName());
            stmt.setString(2, dto.getSurname());
            stmt.setString(3, dto.getAddress());
            stmt.setInt(4, dto.getAge());
            stmt.setString(5, dto.getGpaTranscript());
            stmt.setString(6, dto.getEthnicity());
            stmt.setString(7, dto.getExtraCreditDocument());
            stmt.setDouble(8, dto.getTestScore());
            stmt.setInt(9, dto.getAcceptanceTestScore());
            stmt.setString(10, dto.getProgram());
            stmt.setInt(11, dto.getId());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error updating student: " + e.getMessage());
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM student_starting WHERE id = ?";
        try (Connection conn = DB_Connector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting student: " + e.getMessage());
        }
    }
}