package repository;

import models.Enrollments;
import models.dto.CreateEnrollmentDto;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EnrollmentRepository extends BaseRepository<Enrollments, CreateEnrollmentDto, Void> {

    public EnrollmentRepository() {
        super("enrollments");
    }

    @Override
    public Enrollments fromResultSet(ResultSet result) throws SQLException {
        return Enrollments.getInstance(result);
    }

    @Override
    public Enrollments create(CreateEnrollmentDto dto) {
        String query = "INSERT INTO enrollments (student_id, course_id) VALUES (?, ?)";

        try {
            PreparedStatement pstm = this.connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pstm.setInt(1, dto.getStudentId());
            pstm.setInt(2, dto.getCourseId());
            pstm.execute();

            ResultSet keys = pstm.getGeneratedKeys();
            if (keys.next()) {
                return this.getById(keys.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Enrollments update(Void dto) {
        throw new UnsupportedOperationException("Update operation is not supported for Enrollments.");
    }
}
