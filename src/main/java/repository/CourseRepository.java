package repository;

import database.DB_Connector;
import models.Courses;
import models.dto.CreateCourseDto;
import models.dto.UpdateCourseDto;

import java.sql.*;

public class CourseRepository extends BaseRepository<Courses, CreateCourseDto, UpdateCourseDto> {

    public CourseRepository() {
        super("courses");
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

        try {
            PreparedStatement pstm = this.connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pstm.setString(1, dto.getCourseName());
            pstm.setString(2, dto.getCourseCode());
            pstm.setInt(3, dto.getProfessorId());

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

    @Override
    public Courses update(UpdateCourseDto dto) {
        String query = """
                UPDATE courses
                SET course_name = ?
                WHERE id = ?
                """;

        try {
            PreparedStatement pstm = this.connection.prepareStatement(query);
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
}
