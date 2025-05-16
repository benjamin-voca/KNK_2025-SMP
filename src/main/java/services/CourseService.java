package services;

import models.Courses;
import models.dto.CreateCourseDto;
import models.dto.UpdateCourseDto;
import repository.CourseRepository;

import java.util.List;

public class CourseService extends BaseService<Courses, CreateCourseDto, UpdateCourseDto> {

    private final CourseRepository courseRepository;

    public CourseService() {
        super(new CourseRepository());
        this.courseRepository = (CourseRepository) super.repository;
    }

    @Override
    public Courses getById(int id) throws Exception {
        return super.getById(id);
    }

    @Override
    public Courses create(CreateCourseDto createCourseDto) throws Exception {
        return super.create(createCourseDto);
    }

    @Override
    public Courses update(UpdateCourseDto updateCourseDto) throws Exception {
        return super.update(updateCourseDto);
    }

    public List<Courses> fetchCoursesForStudent(int studentId) {
        return courseRepository.fetchCoursesForStudent(studentId);
    }
}