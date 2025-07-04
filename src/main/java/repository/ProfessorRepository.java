package repository;


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

    public Professors findByProfessorNumber(String professorNumber) {
        String query = "SELECT * FROM professors WHERE professor_number = ?";

        try {
            ensureConnection();
            PreparedStatement pstm = this.connection.prepareStatement(query);
            pstm.setString(1, professorNumber);
            ResultSet res = pstm.executeQuery();
            if (res.next()) {
                return fromResultSet(res);
            } else {
                System.out.println("No professor found for professor_number=" + professorNumber);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Professors create(CreateProfessorDto dto) {
        String query = """
                INSERT INTO professors (user_id, department)
                VALUES (?, ?)
                """;

        try {
            ensureConnection();
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
            ensureConnection();
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