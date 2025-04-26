package repository;


import models.StudentRejected;
import models.dto.CreateStudentRejectedDto;
import models.dto.UpdateStudentRejectedDto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class StudentRejectedRepository extends BaseRepository<StudentRejected, CreateStudentRejectedDto, UpdateStudentRejectedDto>{
    public StudentRejectedRepository(){
        super("student_rejected");
    }

    public StudentRejected fromResultSet(ResultSet result) throws SQLException {
        return StudentRejected.getInstance(result);
    }

    @Override
    public StudentRejected create(CreateStudentRejectedDto dto) {
        String query = """
                INSERT INTO student_rejected (name , surname , address , age , gpa , ethnicity , extra_credit , test_score , acceptance_test_score , program_intended)
                VALUES ( ? , ? , ? , ?, ? , ? , ? , ? , ? , ? )
                """;

        try {
            PreparedStatement pstm = this.connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pstm.setString(1, dto.getName());
            pstm.setString(2, dto.getSurname());
            pstm.setString(3, dto.getAddress());
            pstm.setInt(4, dto.getAge());
            pstm.setDouble(5, dto.getGpa());
            pstm.setString(6, dto.getEthnicity());
            pstm.setInt(7, dto.getExtraCredit());
            pstm.setDouble(8, dto.getTestScore());
            pstm.setInt(9, dto.getAcceptanceTestScore());
            pstm.setString(10, dto.getProgramIntended());
            pstm.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public StudentRejected update(UpdateStudentRejectedDto dto) {
        String query = """
                UPDATE student_rejected
                SET program_intended = ?
                WHERE id = ?
                """;

        try {
            PreparedStatement pstm = this.connection.prepareStatement(query);
            pstm.setString(1, dto.getProgramIntended());
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
