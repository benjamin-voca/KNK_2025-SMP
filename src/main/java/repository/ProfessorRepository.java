package repository;

import database.DB_Connector;
import models.Professors;
import models.dto.CreateProfessorDto;
import models.dto.UpdateProfessorDto;

import java.sql.*;

public class ProfessorRepository extends BaseRepository<Professors, CreateProfessorDto, UpdateProfessorDto> {

    public ProfessorRepository() {
        super("professors");
    }

    @Override
    public Professors fromResultSet(ResultSet result) throws SQLException {
        return Professors.getInstance(result);
    }

    @Override
    public Professors create(CreateProfessorDto dto) {
        String query = """
                INSERT INTO professors (user_id, department)
                VALUES (?, ?)
                """;

        try {
            PreparedStatement pstm = this.connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pstm.setInt(1, getUserIdForNewProfessor());
            pstm.setString(2, dto.getDepartment());

            pstm.executeUpdate();

            ResultSet res = pstm.getGeneratedKeys();
            if (res.next()) {
                int professorId = res.getInt(1);
                return this.getById(professorId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Professors update(UpdateProfessorDto dto) {
        String query = """
                UPDATE professors
                SET department = ?
                WHERE professor_id = ?
                """;

        try {
            PreparedStatement pstm = this.connection.prepareStatement(query);
            pstm.setString(1, dto.getDepartment());
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

    private int getUserIdForNewProfessor() {
        return 1;
    }
}
