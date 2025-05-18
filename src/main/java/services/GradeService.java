package services;

import models.Grades;
import models.dto.CreateGradeDto;
import models.dto.UpdateGradeDto;
import repository.GradeRepository;

import java.util.List;

public class GradeService extends BaseService<Grades, CreateGradeDto, UpdateGradeDto> {

    private final GradeRepository gradeRepository;

    public GradeService() {
        super(new GradeRepository());
        this.gradeRepository = new GradeRepository();
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

    public List<String> getGradesByProfessorId(int professorId) {
        return gradeRepository.findGradesByProfessorId(professorId);
    }
}