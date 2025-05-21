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
        return User.getInstance(result);
    }

    public User create(CreateUserDto userDto) {
        String query = """
            INSERT INTO users (first_name, last_name, email, password_hash, profile_picture_path)
            VALUES (?, ?, ?, ?, ?)
            """;

        try {
            ensureConnection();
            PreparedStatement pstm = this.connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pstm.setString(1, userDto.getFirst_name());
            pstm.setString(2, userDto.getLast_name());
            pstm.setString(3, userDto.getEmail());
            pstm.setString(4, userDto.getPassword_hash());
            pstm.setString(5, userDto.getProfile_picture_path() != null ?
                    userDto.getProfile_picture_path() :
                    "uploads/profile_pictures/default_profile.png");
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

    public User findById(int userId) {
        String query = "SELECT * FROM users WHERE user_id = ?";
        System.out.println("Executing query: " + query + " with user_id=" + userId);

        try {
            ensureConnection();
            PreparedStatement pstm = this.connection.prepareStatement(query);
            pstm.setInt(1, userId);
            ResultSet result = pstm.executeQuery();
            if (result.next()) {
                return fromResultSet(result);
            } else {
                System.out.println("No user found for user_id=" + userId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public User getById(int userId) {
        String query = "SELECT * FROM users WHERE user_id = ?";
        try {
            ensureConnection();
            PreparedStatement pstm = this.connection.prepareStatement(query);
            pstm.setInt(1, userId);
            ResultSet result = pstm.executeQuery();
            if (result.next()) {
                return fromResultSet(result);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public User getByEmail(String email) {
        String query = "SELECT * FROM users WHERE email = ?";
        try {
            ensureConnection();
            PreparedStatement pstm = this.connection.prepareStatement(query);
            pstm.setString(1, email);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                return this.fromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User update(UpdateUserDto userDto) {
        String query = """
            UPDATE users 
            SET email = ?, password_hash = ?, profile_picture_path = ?
            WHERE user_id = ?
            """;

        try {
            ensureConnection();
            PreparedStatement pstm = this.connection.prepareStatement(query);
            pstm.setString(1, userDto.getEmail());
            pstm.setString(2, userDto.getPassword_hash());
            pstm.setString(3, userDto.getProfile_picture_path());
            pstm.setInt(4, userDto.getUser_id());

            int updated = pstm.executeUpdate();
            if (updated == 1) {
                User updatedUser = this.getById(userDto.getUser_id());
                if (updatedUser != null) {
                    return updatedUser;
                } else {
                    System.out.println("Updated user retrieval failed");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public User findAssessor() throws SQLException {
        String query = "SELECT * FROM users WHERE user_id = ?";
        try (Connection conn = DB_Connector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, 3); // Assessor's user_id
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return User.getInstance(rs);
            }
            return null;
        }
    }
}