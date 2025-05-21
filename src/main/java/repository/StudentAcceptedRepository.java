package repository;

import models.StudentAccepted;
import models.dto.CreateStudentAcceptedDto;
import models.dto.UpdateStudentAcceptedDto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class StudentAcceptedRepository extends BaseRepository<StudentAccepted, CreateStudentAcceptedDto, UpdateStudentAcceptedDto>{
    public StudentAcceptedRepository(){
        super("student_accepted");
    }

    public StudentAccepted fromResultSet(ResultSet result) throws SQLException {
        return StudentAccepted.getInstance(result);
    }

    @Override
    public StudentAccepted create(CreateStudentAcceptedDto dto) {
        String query = """
                INSERT INTO student_accepted (name , surname , address , age , status , program)
                VALUES (?, ?, ? , ?, ? ,?)
                """;

        try {
            PreparedStatement pstm = this.connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pstm.setString(1, dto.getName());
            pstm.setString(2, dto.getSurname());
            pstm.setString(3, dto.getAddress());
            pstm.setInt(4, dto.getAge());
            pstm.setString(5, dto.getStatus());
            pstm.setString(6, dto.getProgram());

            pstm.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public StudentAccepted update(UpdateStudentAcceptedDto dto) {
        String query = """
                UPDATE student_accepted
                SET status = ?
                WHERE id = ?
                """;

        try {
            PreparedStatement pstm = this.connection.prepareStatement(query);
            pstm.setString(1, dto.getStatus());
            pstm.setString(2, dto.getId());

            int updated = pstm.executeUpdate();
            if (updated == 1) {
                return this.getById(Integer.parseInt(dto.getId()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public StudentAccepted findById(String id) {
        String query = "SELECT * FROM student_accepted WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return fromResultSet(rs);
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
