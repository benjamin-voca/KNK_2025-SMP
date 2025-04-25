package repository;

import database.DB_Connector;
import models.Classes;
import models.dto.CreateClassDto;
import models.dto.UpdateClassDto;

import java.sql.*;

public class ClassRepository extends BaseRepository<Classes, CreateClassDto, UpdateClassDto> {

    public ClassRepository() {
        super("classes");
    }

    @Override
    public Classes fromResultSet(ResultSet result) throws SQLException {
        return Classes.getInstance(result);
    }

    @Override
    public Classes create(CreateClassDto dto) {
        String query = """
                INSERT INTO classes (class_name, course_id, schedule)
                VALUES (?, ?, ?)
                """;

        try {
            PreparedStatement pstm = this.connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pstm.setString(1, dto.getClassName());
            pstm.setInt(2, dto.getCourseId());
            pstm.setString(3, dto.getSchedule());

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
    public Classes update(UpdateClassDto dto) {
        String query = """
                UPDATE classes
                SET class_name = ?, schedule = ?
                WHERE id = ?
                """;

        try {
            PreparedStatement pstm = this.connection.prepareStatement(query);
            pstm.setString(1, dto.getClassName());
            pstm.setString(2, dto.getSchedule());
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
