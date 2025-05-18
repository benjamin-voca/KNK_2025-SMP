package repository;

import database.DB_Connector;
import models.Assignments;
import models.dto.CreateAssignmentDto;
import models.dto.UpdateAssignmentDto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    public List<String> findAssignmentsByProfessorId(int professorId) {
        List<String> assignments = new ArrayList<>();
        String query = "SELECT a.title, a.due_date, c.course_name " +
                "FROM assignments a " +
                "JOIN courses c ON a.course_id = c.id " +
                "JOIN professors p ON c.professor_id = p.id " +
                "WHERE p.user_id = ?";
        try (Connection conn = DB_Connector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, professorId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                assignments.add(rs.getString("title") + " (" + rs.getString("course_name") +
                        ") - Due: " + rs.getTimestamp("due_date"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return assignments;
    }
}
