package services;

import models.Assignments;
import models.dto.CreateAssignmentDto;
import models.dto.UpdateAssignmentDto;
import repository.AssignmentRepository;

public class AssignmentService extends BaseService<Assignments, CreateAssignmentDto, UpdateAssignmentDto> {

    public AssignmentService() {
        super(new AssignmentRepository());
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
}
