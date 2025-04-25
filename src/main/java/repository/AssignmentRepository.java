package repository;

import database.DB_Connector;
import models.Assignments;
import models.dto.CreateAssignmentDto;
import models.dto.UpdateAssignmentDto;

import java.sql.*;

public class AssignmentRepository extends BaseRepository<Assignments, CreateAssignmentDto, UpdateAssignmentDto> {

    public AssignmentRepository() {
        super("assignments");
    }

    @Override
    public Assignments fromResultSet(ResultSet result) throws SQLException {
        return Assignments.getInstance(result);
    }

    @Override
    public Assignments create(CreateAssignmentDto dto) {
        String query = """
                INSERT INTO assignments (course_id, title, description, due_date)
                VALUES (?, ?, ?, ?)
                """;

        try {
            PreparedStatement pstm = this.connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pstm.setInt(1, dto.getCourseId());
            pstm.setString(2, dto.getTitle());
            pstm.setString(3, dto.getDescription());
            pstm.setDate(4, new java.sql.Date(dto.getDueDate().getTime()));

            pstm.execute();

            ResultSet res = pstm.getGeneratedKeys();
            if (res.next()) {
                int id = res.getInt(1);
                return this.getById(id);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Assignments update(UpdateAssignmentDto dto) {
        String query = """
                UPDATE assignments
                SET title = ?, description = ?, due_date = ?
                WHERE id = ?
                """;

        try {
            PreparedStatement pstm = this.connection.prepareStatement(query);
            pstm.setString(1, dto.getTitle());
            pstm.setString(2, dto.getDescription());
            pstm.setDate(3, new java.sql.Date(dto.getDueDate().getTime()));
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
}
