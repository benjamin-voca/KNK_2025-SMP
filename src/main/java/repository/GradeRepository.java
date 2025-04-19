package repository;

import database.DB_Connector;
import models.Grades;
import models.dto.CreateGradeDto;
import models.dto.UpdateGradeDto;

import java.sql.*;

public class GradeRepository extends BaseRepository<Grades, CreateGradeDto, UpdateGradeDto> {

    public GradeRepository() {
        super("grades");
    }

    @Override
    public Grades fromResultSet(ResultSet result) throws SQLException {
        return Grades.getInstance(result);
    }

    @Override
    public Grades create(CreateGradeDto dto) {
        String query = """
                INSERT INTO grades (student_id, course_id, grade)
                VALUES (?, ?, ?)
                """;

        try {
            PreparedStatement pstm = this.connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pstm.setInt(1, dto.getStudentId());
            pstm.setInt(2, dto.getCourseId());
            pstm.setFloat(3, dto.getGrade());

            pstm.executeUpdate();

            ResultSet res = pstm.getGeneratedKeys();
            if (res.next()) {
                int gradeId = res.getInt(1);
                return this.getById(gradeId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Grades update(UpdateGradeDto dto) {
        String query = """
                UPDATE grades
                SET grade = ?
                WHERE grade_id = ?
                """;

        try {
            PreparedStatement pstm = this.connection.prepareStatement(query);
            pstm.setFloat(1, dto.getGrade());
            pstm.setInt(2, dto.getId());

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
