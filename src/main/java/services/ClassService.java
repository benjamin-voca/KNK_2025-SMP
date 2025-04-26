package services;

import models.Classes;
import models.dto.CreateClassDto;
import models.dto.UpdateClassDto;
import repository.ClassRepository;

public class ClassService extends BaseService<Classes, CreateClassDto, UpdateClassDto> {

    public ClassService() {
        super(new ClassRepository());
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
}
