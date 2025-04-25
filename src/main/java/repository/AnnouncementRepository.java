package repository;

import database.DB_Connector;
import models.Announcements;
import models.dto.CreateAnnouncementDto;
import models.dto.UpdateAnnouncementDto;

import java.sql.*;

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
}
