package services;

import models.Courses;
import models.dto.CreateCourseDto;
import models.dto.UpdateCourseDto;
import repository.CourseRepository;

public class CourseService extends BaseService<Courses, CreateCourseDto, UpdateCourseDto> {

    public CourseService() {
        super(new CourseRepository());
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
}
