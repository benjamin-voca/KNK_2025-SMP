package repository;

import database.DB_Connector;
import models.Submissions;
import models.dto.CreateSubmissionDto;
import models.dto.SubmissionViewDto;
import models.dto.UpdateSubmissionDto;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SubmissionsRepository extends BaseRepository<Submissions, CreateSubmissionDto, UpdateSubmissionDto> {

    public SubmissionsRepository() {
        super("submissions");
    }

    private Connection getValidConnection() throws SQLException {
        Connection conn = this.connection;
        if (conn == null || conn.isClosed()) {
            try {
                java.lang.reflect.Field connectionField = DB_Connector.class.getDeclaredField("connection");
                connectionField.setAccessible(true);
                connectionField.set(null, null);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new SQLException("Failed to reset DB_Connector connection", e);
            }
            this.connection = DB_Connector.getConnection();
            conn = this.connection;
            if (conn == null || conn.isClosed()) {
                throw new SQLException("Unable to establish a valid database connection");
            }
        }
        return conn;
    }

    @Override
    public Submissions fromResultSet(ResultSet result) throws SQLException {
        return Submissions.getInstance(result);
    }

    @Override
    public Submissions create(CreateSubmissionDto dto) {
        String query = """
                INSERT INTO submissions (
                    assignment_id, student_id, submitted_at, content, grade, feedback, status
                ) VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection conn = getValidConnection();
             PreparedStatement pstm = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            pstm.setInt(1, dto.getAssignmentId());
            pstm.setInt(2, dto.getStudentId());
            pstm.setTimestamp(3, dto.getSubmittedAt());
            pstm.setString(4, dto.getContent());
            if (dto.getGrade() != null) {
                pstm.setBigDecimal(5, dto.getGrade());
            } else {
                pstm.setNull(5, Types.DECIMAL);
            }
            pstm.setString(6, dto.getFeedback());
            pstm.setString(7, dto.getStatus());

            pstm.executeUpdate();

            try (ResultSet res = pstm.getGeneratedKeys()) {
                if (res.next()) {
                    int submissionId = res.getInt(1);
                    return this.getById(submissionId);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Submissions update(UpdateSubmissionDto dto) {
        String query = """
                UPDATE submissions 
                SET grade = ?, feedback = ?, status = ?
                WHERE id = ?
                """;

        try (Connection conn = getValidConnection();
             PreparedStatement pstm = conn.prepareStatement(query)) {
            if (dto.getGrade() != null) {
                pstm.setBigDecimal(1, dto.getGrade());
            } else {
                pstm.setNull(1, Types.DECIMAL);
            }
            pstm.setString(2, dto.getFeedback());
            pstm.setString(3, dto.getStatus());
            pstm.setInt(4, dto.getId());

            int updated = pstm.executeUpdate();
            if (updated == 1) {
                return this.getById(dto.getId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<SubmissionViewDto> fetchSubmissionsForStudent(int studentId) {
        List<SubmissionViewDto> submissions = new ArrayList<>();
        String query = """
                SELECT s.id, a.title AS assignment_title, c.course_name, s.submitted_at, s.grade, s.feedback, s.status
                FROM submissions s
                JOIN assignments a ON s.assignment_id = a.id
                JOIN courses c ON a.course_id = c.id
                WHERE s.student_id = ?
                ORDER BY s.submitted_at DESC
                """;

        try (Connection conn = getValidConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, studentId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    SubmissionViewDto dto = new SubmissionViewDto();
                    dto.setId(rs.getInt("id"));
                    dto.setAssignmentTitle(rs.getString("assignment_title"));
                    dto.setCourseName(rs.getString("course_name"));
                    dto.setSubmittedAt(rs.getTimestamp("submitted_at"));
                    dto.setGrade(rs.getBigDecimal("grade"));
                    dto.setFeedback(rs.getString("feedback"));
                    dto.setStatus(rs.getString("status"));
                    submissions.add(dto);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return submissions;
    }

    public List<SubmissionViewDto> fetchAvailableAssignmentsForStudent(int studentId) {
        List<SubmissionViewDto> assignments = new ArrayList<>();
        String query = """
                SELECT a.id, a.title AS assignment_title, c.course_name
                FROM assignments a
                JOIN courses c ON a.course_id = c.id
                JOIN enrollments e ON c.id = e.course_id
                WHERE e.student_id = ?
                AND a.id NOT IN (
                    SELECT assignment_id FROM submissions WHERE student_id = ?
                )
                ORDER BY a.title
                """;

        try (Connection conn = getValidConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, studentId);
            stmt.setInt(2, studentId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    SubmissionViewDto dto = new SubmissionViewDto();
                    dto.setId(rs.getInt("id"));
                    dto.setAssignmentTitle(rs.getString("assignment_title"));
                    dto.setCourseName(rs.getString("course_name"));
                    assignments.add(dto);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return assignments;
    }
}