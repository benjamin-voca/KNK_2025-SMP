package repository;

import database.DB_Connector;
import models.Classes;
import models.dto.CreateClassDto;
import models.dto.UpdateClassDto;
import models.dto.ClassViewDto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClassRepository extends BaseRepository<Classes, CreateClassDto, UpdateClassDto> {

    public ClassRepository() {
        super("classes");
    }

    private Connection getValidConnection() throws SQLException {
        Connection conn = this.connection;
        if (conn == null || conn.isClosed()) {
            try {
                java.lang.reflect.Field connectionField = DB_Connector.class.getDeclaredField("connection");
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
    public Classes fromResultSet(ResultSet result) throws SQLException {
        return Classes.getInstance(result);
    }

    @Override
    public Classes create(CreateClassDto dto) {
        String query = """
                INSERT INTO classes (class_name, course_id, schedule, location, class_type, duration)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (Connection conn = getValidConnection();
             PreparedStatement pstm = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            pstm.setString(1, dto.getClassName());
            pstm.setInt(2, dto.getCourseId());
            pstm.setTimestamp(3, dto.getSchedule());
            pstm.setString(4, dto.getLocation());
            pstm.setString(5, dto.getClassType());
            if (dto.getDuration() != null) {
                pstm.setInt(6, dto.getDuration());
            } else {
                pstm.setNull(6, Types.INTEGER);
            }

            pstm.executeUpdate();

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
    public Classes update(UpdateClassDto dto) {
        String query = """
                UPDATE classes
                SET class_name = ?, schedule = ?, location = ?, class_type = ?, duration = ?
                WHERE id = ?
                """;

        try (Connection conn = getValidConnection();
             PreparedStatement pstm = conn.prepareStatement(query)) {
            pstm.setString(1, dto.getClassName());
            pstm.setTimestamp(2, dto.getSchedule());
            pstm.setString(3, dto.getLocation());
            pstm.setString(4, dto.getClassType());
            if (dto.getDuration() != null) {
                pstm.setInt(5, dto.getDuration());
            } else {
                pstm.setNull(5, Types.INTEGER);
            }
            pstm.setInt(6, dto.getId());

            int updated = pstm.executeUpdate();
            if (updated == 1) {
                return this.getById(dto.getId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<ClassViewDto> fetchClassesForStudent(int studentId) {
        List<ClassViewDto> classes = new ArrayList<>();
        String query = """
                SELECT c.id, c.class_name, co.course_name, c.schedule, c.location, c.class_type, c.duration
                FROM classes c
                JOIN courses co ON c.course_id = co.id
                JOIN enrollments e ON co.id = e.course_id
                WHERE e.student_id = ?
                ORDER BY c.schedule
                """;

        try (Connection conn = getValidConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, studentId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ClassViewDto dto = new ClassViewDto();
                    dto.setId(rs.getInt("id"));
                    dto.setClassName(rs.getString("class_name"));
                    dto.setCourseName(rs.getString("course_name"));
                    dto.setSchedule(rs.getTimestamp("schedule"));
                    dto.setLocation(rs.getString("location"));
                    dto.setClassType(rs.getString("class_type"));
                    int duration = rs.getInt("duration");
                    if (!rs.wasNull()) {
                        dto.setDuration(duration);
                    }
                    classes.add(dto);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return classes;
    }
}