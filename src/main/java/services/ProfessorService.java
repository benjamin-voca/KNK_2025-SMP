package services;

import models.Professors;
import models.dto.CreateProfessorDto;
import models.dto.UpdateProfessorDto;
import repository.ProfessorRepository;

public class ProfessorService extends BaseService<Professors, CreateProfessorDto, UpdateProfessorDto> {

    public ProfessorService() {
        super(new ProfessorRepository());
    }

    @Override
    public Professors getById(int id) throws Exception {
        return super.getById(id);
    }

    @Override
    public Professors create(CreateProfessorDto createProfessorDto) throws Exception {
        return super.create(createProfessorDto);
    }

    @Override
    public Professors update(UpdateProfessorDto updateProfessorDto) throws Exception {
        return super.update(updateProfessorDto);
    }
}