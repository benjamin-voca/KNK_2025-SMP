package services;

import models.Student;
import models.dto.CreateStudentDto;
import models.dto.UpdateStudentDto;
import repository.StudentRepository;

public class StudentService extends BaseService<Student, CreateStudentDto, UpdateStudentDto> {

    public StudentService() {
        super(new StudentRepository());
    }

    @Override
    public Student getById(int id) throws Exception {
        return super.getById(id);
    }

    @Override
    public Student create(CreateStudentDto createStudentDto) throws Exception {
        return super.create(createStudentDto);
    }

    @Override
    public Student update(UpdateStudentDto updateStudentDto) throws Exception {
        return super.update(updateStudentDto);
    }
}