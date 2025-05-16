package repository;

import database.DB_Connector;
import models.Notifications;
import models.dto.CreateNotificationDto;
import models.dto.UpdateNotificationDto;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NotificationRepository extends BaseRepository<Notifications, CreateNotificationDto, UpdateNotificationDto> {

    public NotificationRepository() {
        super("notifications");
    }

    private Connection getValidConnection() throws SQLException {
        Connection conn = this.connection;
        if (conn == null || conn.isClosed()) {
            try {
                Field connectionField = DB_Connector.class.getDeclaredField("connection");
                connectionField.setAccessible(true);
                connectionField.set(null, null);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new SQLException("Failed to reset DB_Connector connection", e);
            }
            this.connection = DB_Connector.getConnection();
            conn = this.connection;
            if (conn == null || conn.isClosed()) {
                throw new SQLException("Unable to establish a valid database connection");
            }
        }
        return conn;
    }

    @Override
    public Notifications fromResultSet(ResultSet result) throws SQLException {
        return Notifications.getInstance(result);
    }

    @Override
    public Notifications create(CreateNotificationDto dto) {
        String query = """
                INSERT INTO notifications (title, content)
                VALUES (?, ?)
                """;

        try (Connection conn = getValidConnection();
             PreparedStatement pstm = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            pstm.setString(1, dto.getTitle());
            pstm.setString(2, dto.getContent());

            pstm.execute();

            try (ResultSet res = pstm.getGeneratedKeys()) {
                if (res.next()) {
                    int id = res.getInt(1);
                    return this.getById(id);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Notifications update(UpdateNotificationDto dto) {
        String query = """
                UPDATE notifications
                SET title = ?, content = ?
                WHERE id = ?
                """;

        try (Connection conn = getValidConnection();
             PreparedStatement pstm = conn.prepareStatement(query)) {
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

    public List<Notifications> fetchNotifications() {
        List<Notifications> notifications = new ArrayList<>();
        String query = "SELECT * FROM notifications ORDER BY created_at DESC";

        try (Connection conn = getValidConnection();
             PreparedStatement statement = conn.prepareStatement(query);
             ResultSet result = statement.executeQuery()) {
            while (result.next()) {
                notifications.add(Notifications.getInstance(result));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return notifications;
    }
}