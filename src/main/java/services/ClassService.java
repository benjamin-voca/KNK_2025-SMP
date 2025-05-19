package services;

import models.Classes;
import models.dto.CreateClassDto;
import models.dto.UpdateClassDto;
import models.dto.ClassViewDto;
import repository.ClassRepository;

import java.util.List;

public class ClassService extends BaseService<Classes, CreateClassDto, UpdateClassDto> {

    private final ClassRepository classRepository;

    public ClassService() {
        super(new ClassRepository());
        this.classRepository = (ClassRepository) super.repository;
    }

    @Override
    public Classes getById(int id) throws Exception {
        return super.getById(id);
    }

    @Override
    public Classes create(CreateClassDto createClassDto) throws Exception {
        return super.create(createClassDto);
    }

    @Override
    public Classes update(UpdateClassDto updateClassDto) throws Exception {
        return super.update(updateClassDto);
    }

    public List<ClassViewDto> fetchClassesForStudent(int studentId) {
        return classRepository.fetchClassesForStudent(studentId);
    }
}