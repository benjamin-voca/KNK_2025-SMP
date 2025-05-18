package repository;

import database.DB_Connector;
import models.Courses;
import models.dto.CreateCourseDto;
import models.dto.UpdateCourseDto;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseRepository extends BaseRepository<Courses, CreateCourseDto, UpdateCourseDto> {

    public CourseRepository() {
        super("courses");
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
    public Courses fromResultSet(ResultSet result) throws SQLException {
        return Courses.getInstance(result);
    }

    @Override
    public Courses create(CreateCourseDto dto) {
        String query = """
                INSERT INTO courses (course_name, course_code, professor_id)
                VALUES (?, ?, ?)
                """;

        try (Connection conn = getValidConnection();
             PreparedStatement pstm = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            pstm.setString(1, dto.getCourseName());
            pstm.setString(2, dto.getCourseCode());
            pstm.setInt(3, dto.getProfessorId());

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
    public Courses update(UpdateCourseDto dto) {
        String query = """
                UPDATE courses
                SET course_name = ?
                WHERE id = ?
                """;

        try (Connection conn = getValidConnection();
             PreparedStatement pstm = conn.prepareStatement(query)) {
            pstm.setString(1, dto.getCourseName());
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

    public List<Courses> fetchCoursesForStudent(int studentId) {
        List<Courses> courses = new ArrayList<>();
        String query = "SELECT c.id AS course_id, c.course_name, c.course_code, c.professor_id " +
                "FROM courses c " +
                "JOIN enrollments e ON c.id = e.course_id " +
                "WHERE e.student_id = ?";

        try (Connection conn = getValidConnection();
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, studentId);
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    courses.add(Courses.getInstance(result));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return courses;
    }

    public List<String> findCoursesByProfessorId(int professorId) {
        List<String> courses = new ArrayList<>();
        String query = "SELECT c.course_name, c.course_code " +
                "FROM courses c " +
                "JOIN professors p ON c.professor_id = p.id " +
                "WHERE p.user_id = ?";
        try (Connection conn = DB_Connector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, professorId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                courses.add(rs.getString("course_name") + " (" + rs.getString("course_code") + ")");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }
}