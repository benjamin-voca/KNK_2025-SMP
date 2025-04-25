package services;

import models.Assignments;
import models.dto.CreateAssignmentDto;
import models.dto.UpdateAssignmentDto;
import repository.AssignmentRepository;

public class AssignmentService {
    private AssignmentRepository assignmentRepository;

    public AssignmentService() {
        this.assignmentRepository = new AssignmentRepository();
    }

    public Assignments getById(int id) throws Exception {
        if (id <= 0) {
            throw new Exception("Invalid ID provided.");
        }

        Assignments assignment = this.assignmentRepository.getById(id);
        if (assignment == null) {
            throw new Exception("Assignment with ID: " + id + " does not exist.");
        }

        return assignment;
    }

    public Assignments create(CreateAssignmentDto createAssignmentDto) throws Exception {
        if (createAssignmentDto.getCourseId() <= 0 || createAssignmentDto.getTitle().isEmpty() ||
                createAssignmentDto.getDescription().isEmpty() || createAssignmentDto.getDueDate() == null) {
            throw new Exception("All fields are required.");
        }

        Assignments assignment = this.assignmentRepository.create(createAssignmentDto);
        if (assignment == null) {
            throw new Exception("Assignment could not be created.");
        }

        return assignment;
    }

    public Assignments update(UpdateAssignmentDto updateAssignmentDto) throws Exception {
        if (updateAssignmentDto.getId() <= 0) {
            throw new Exception("Invalid assignment ID.");
        }

        Assignments existingAssignment = this.getById(updateAssignmentDto.getId());
        if (existingAssignment == null) {
            throw new Exception("Assignment with ID: " + updateAssignmentDto.getId() + " does not exist.");
        }

        Assignments updatedAssignment = this.assignmentRepository.update(updateAssignmentDto);
        if (updatedAssignment == null) {
            throw new Exception("Assignment could not be updated.");
        }

        return updatedAssignment;
    }
}
