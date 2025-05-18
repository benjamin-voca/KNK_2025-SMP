package services;

import models.Assignments;
import models.dto.CreateAssignmentDto;
import models.dto.UpdateAssignmentDto;
import repository.AssignmentRepository;

import java.util.List;

public class AssignmentService extends BaseService<Assignments, CreateAssignmentDto, UpdateAssignmentDto> {

    private final AssignmentRepository assignmentRepository;

    public AssignmentService() {
        super(new AssignmentRepository());
        this.assignmentRepository = new AssignmentRepository();
    }

    @Override
    public Assignments getById(int id) throws Exception {
        return super.getById(id);
    }

    @Override
    public Assignments create(CreateAssignmentDto createAssignmentDto) throws Exception {
        return super.create(createAssignmentDto);
    }

    @Override
    public Assignments update(UpdateAssignmentDto updateAssignmentDto) throws Exception {
        return super.update(updateAssignmentDto);
    }

    public List<String> getAssignmentsByProfessorId(int professorId) {
        return assignmentRepository.findAssignmentsByProfessorId(professorId);
    }
}
