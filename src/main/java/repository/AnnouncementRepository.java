package repository;

import database.DB_Connector;
import models.Announcements;
import models.dto.CreateAnnouncementDto;
import models.dto.UpdateAnnouncementDto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AnnouncementRepository extends BaseRepository<Announcements, CreateAnnouncementDto, UpdateAnnouncementDto> {

    public AnnouncementRepository() {
        super("announcements");
    }

    @Override
    public Announcements fromResultSet(ResultSet result) throws SQLException {
        return Announcements.getInstance(result);
    }

    @Override
    public Announcements create(CreateAnnouncementDto dto) {
        String query = """
                INSERT INTO announcements (course_id, title, content, created_at)
                VALUES (?, ?, ?, ?)
                """;

        try {
            PreparedStatement pstm = this.connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pstm.setInt(1, dto.getCourseId());
            pstm.setString(2, dto.getTitle());
            pstm.setString(3, dto.getContent());
            pstm.setTimestamp(4, dto.getCreatedAt());

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
    public Announcements update(UpdateAnnouncementDto dto) {
        String query = """
                UPDATE announcements
                SET title = ?, content = ?
                WHERE id = ?
                """;

        try {
            PreparedStatement pstm = this.connection.prepareStatement(query);
            pstm.setString(1, dto.getTitle());
            pstm.setString(2, dto.getContent());
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

    public List<String> findAnnouncementsByProfessorId(int professorId) {
        List<String> announcements = new ArrayList<>();
        String query = "SELECT a.title, a.created_at, c.course_name " +
                "FROM announcements a " +
                "JOIN courses c ON a.course_id = c.id " +
                "JOIN professors p ON c.professor_id = p.id " +
                "WHERE p.user_id = ?";
        try (Connection conn = DB_Connector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, professorId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                announcements.add(rs.getString("title") + " (" + rs.getString("course_name") +
                        ") - Posted: " + rs.getTimestamp("created_at"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return announcements;
    }
}
