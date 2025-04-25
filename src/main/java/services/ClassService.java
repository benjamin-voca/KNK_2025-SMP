package services;

import models.Classes;
import models.dto.CreateClassDto;
import models.dto.UpdateClassDto;
import repository.ClassRepository;

public class ClassService {
    private ClassRepository classRepository;

    public ClassService() {
        this.classRepository = new ClassRepository();
    }

    public Classes getById(int id) throws Exception {
        if (id <= 0) {
            throw new Exception("Invalid ID provided.");
        }

        Classes classObj = this.classRepository.getById(id);
        if (classObj == null) {
            throw new Exception("Class with ID: " + id + " does not exist.");
        }

        return classObj;
    }

    public Classes create(CreateClassDto createClassDto) throws Exception {
        if (createClassDto.getClassName().isEmpty() || createClassDto.getCourseId() <= 0 || createClassDto.getSchedule().isEmpty()) {
            throw new Exception("All fields are required.");
        }

        Classes classObj = this.classRepository.create(createClassDto);
        if (classObj == null) {
            throw new Exception("Class could not be created.");
        }

        return classObj;
    }

    public Classes update(UpdateClassDto updateClassDto) throws Exception {
        if (updateClassDto.getId() <= 0) {
            throw new Exception("Invalid class ID.");
        }

        Classes existingClass = this.getById(updateClassDto.getId());
        if (existingClass == null) {
            throw new Exception("Class with ID: " + updateClassDto.getId() + " does not exist.");
        }

        Classes updatedClass = this.classRepository.update(updateClassDto);
        if (updatedClass == null) {
            throw new Exception("Class could not be updated.");
        }

        return updatedClass;
    }
}
