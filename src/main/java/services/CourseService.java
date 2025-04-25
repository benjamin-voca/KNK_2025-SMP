package services;

import models.Courses;
import models.dto.CreateCourseDto;
import models.dto.UpdateCourseDto;
import repository.CourseRepository;

public class CourseService {
    private CourseRepository courseRepository;

    public CourseService() {
        this.courseRepository = new CourseRepository();
    }

    public Courses getById(int id) throws Exception {
        if (id <= 0) {
            throw new Exception("Invalid ID provided.");
        }

        Courses course = this.courseRepository.getById(id);
        if (course == null) {
            throw new Exception("Course with ID: " + id + " does not exist.");
        }

        return course;
    }

    public Courses create(CreateCourseDto createCourseDto) throws Exception {
        if (createCourseDto.getCourseName().isEmpty() || createCourseDto.getCourseCode().isEmpty() || createCourseDto.getProfessorId() <= 0) {
            throw new Exception("All fields are required.");
        }

        Courses course = this.courseRepository.create(createCourseDto);
        if (course == null) {
            throw new Exception("Course could not be created.");
        }

        return course;
    }

    public Courses update(UpdateCourseDto updateCourseDto) throws Exception {
        if (updateCourseDto.getId() <= 0) {
            throw new Exception("Invalid course ID.");
        }

        Courses existingCourse = this.getById(updateCourseDto.getId());
        if (existingCourse == null) {
            throw new Exception("Course with ID: " + updateCourseDto.getId() + " does not exist.");
        }

        Courses updatedCourse = this.courseRepository.update(updateCourseDto);
        if (updatedCourse == null) {
            throw new Exception("Course could not be updated.");
        }

        return updatedCourse;
    }
}
