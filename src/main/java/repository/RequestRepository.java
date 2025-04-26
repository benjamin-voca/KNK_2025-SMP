package repository;

import models.Request;
import models.dto.CreateRequestDto;
import models.dto.UpdateRequestDto;

import java.sql.ResultSet;
import java.sql.*;

public class RequestRepository extends BaseRepository<Request, CreateRequestDto, UpdateRequestDto>{
    public RequestRepository(){
        super("request");
    }

    public Request fromResultSet(ResultSet result) throws SQLException {
        return  Request.getInstance(result);
    }

    @Override
    public Request create(CreateRequestDto dto) {
        String query = """
                INSERT INTO request (student_id , request_time , accepted , repeat)
                VALUES (?, ?, ? , ?)
                """;

        try {
            PreparedStatement pstm = this.connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pstm.setInt(1, dto.getStudent_id());
            pstm.setDate(2, (java.sql.Date) dto.getRequest_time());
            pstm.setBoolean(3, dto.isAccepted());
            pstm.setBoolean(4, dto.isRepeat());

            pstm.executeUpdate();

            ResultSet res = pstm.getGeneratedKeys();
            if (res.next()) {
                int studentId = res.getInt(1);
                return this.getById(studentId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Request update(UpdateRequestDto dto) {
        String query = """
                UPDATE request
                SET accepted = ?
                WHERE student_id = ?
                """;

        try {
            PreparedStatement pstm = this.connection.prepareStatement(query);
            pstm.setBoolean(1, dto.isAccepted());
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
