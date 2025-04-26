package services;

import models.Grades;
import models.dto.CreateGradeDto;
import models.dto.UpdateGradeDto;
import repository.GradeRepository;

public class GradeService extends BaseService<Grades, CreateGradeDto, UpdateGradeDto> {

    public GradeService() {
        super(new GradeRepository());
    }

    @Override
    public Grades getById(int id) throws Exception {
        return super.getById(id);
    }

    @Override
    public Grades create(CreateGradeDto createGradeDto) throws Exception {
        return super.create(createGradeDto);
    }

    @Override
    public Grades update(UpdateGradeDto updateGradeDto) throws Exception {
        return super.update(updateGradeDto);
    }
}