package repository;

import database.DB_Connector;
import models.User;
import models.dto.CreateUserDto;
import models.dto.UpdateUserDto;

import java.sql.*;

public class UserRepository extends BaseRepository<User, CreateUserDto, UpdateUserDto> {

    public UserRepository() {
        super("users");
    }

    public User fromResultSet(ResultSet result) throws SQLException {
        return User.getInstance(result); // assuming this still applies
    }

    public User create(CreateUserDto userDto) {
        String query = """
                INSERT INTO users (first_name, last_name, email, password_hash)
                VALUES (?, ?, ?, ?)
                """;

        try {
            PreparedStatement pstm = this.connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pstm.setString(1, userDto.getFirst_name());
            pstm.setString(2, userDto.getLast_name());
            pstm.setString(3, userDto.getEmail());
            pstm.setString(4, userDto.getPassword_hash());
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

    public User update(UpdateUserDto userDto) {
        String query = """
                UPDATE users 
                SET email = ?, password_hash = ?
                WHERE id = ?
                """;

        try {
            PreparedStatement pstm = this.connection.prepareStatement(query);
            pstm.setString(1, userDto.getEmail());
            pstm.setString(2, userDto.getPassword_hash());
            pstm.setInt(3, userDto.getUser_id());

            int updated = pstm.executeUpdate();
            if (updated == 1) {
                return this.getById(userDto.getUser_id());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
