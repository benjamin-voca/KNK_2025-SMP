package repository;

import database.DB_Connector;
import models.Submissions;
import models.dto.CreateSubmissionDto;
import models.dto.UpdateSubmissionDto;

import java.math.BigDecimal;
import java.sql.*;

public class SubmissionsRepository extends BaseRepository<Submissions, CreateSubmissionDto, UpdateSubmissionDto> {

    public SubmissionsRepository() {
        super("submissions");
    }

    @Override
    public Submissions fromResultSet(ResultSet result) throws SQLException {
        return Submissions.getInstance(result);
    }

    @Override
    public Submissions create(CreateSubmissionDto dto) {
        String query = """
                INSERT INTO submissions (
                    assignment_id, student_id, submitted_at, content, grade, feedback
                ) VALUES (?, ?, ?, ?, ?, ?)
                """;

        try {
            PreparedStatement pstm = this.connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pstm.setInt(1, dto.getAssignmentId());
            pstm.setInt(2, dto.getStudentId());
            pstm.setTimestamp(3, dto.getSubmittedAt());
            pstm.setString(4, dto.getContent());
            pstm.setBigDecimal(5, BigDecimal.valueOf(dto.getGrade()));
            pstm.setString(6, dto.getFeedback());

            pstm.executeUpdate();

            ResultSet res = pstm.getGeneratedKeys();
            if (res.next()) {
                int submissionId = res.getInt(1);
                return this.getById(submissionId);
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
                SET grade = ?, feedback = ?
                WHERE submission_id = ?
                """;

        try {
            PreparedStatement pstm = this.connection.prepareStatement(query);
            pstm.setBigDecimal(1, BigDecimal.valueOf(dto.getGrade()));
            pstm.setString(2, dto.getFeedback());
            pstm.setInt(3, dto.getId());

            int updated = pstm.executeUpdate();
            if (updated == 1) {
                return this.getById(dto.getId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
