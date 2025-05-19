package services;

import models.Submissions;
import models.dto.CreateSubmissionDto;
import models.dto.SubmissionViewDto;
import models.dto.UpdateSubmissionDto;
import repository.SubmissionsRepository;

import java.util.List;

public class SubmissionsService extends BaseService<Submissions, CreateSubmissionDto, UpdateSubmissionDto> {

    private final SubmissionsRepository submissionsRepository;

    public SubmissionsService() {
        super(new SubmissionsRepository());
        this.submissionsRepository = (SubmissionsRepository) super.repository;
    }

    @Override
    public Submissions getById(int id) throws Exception {
        return super.getById(id);
    }

    @Override
    public Submissions create(CreateSubmissionDto createSubmissionDto) throws Exception {
        return super.create(createSubmissionDto);
    }

    @Override
    public Submissions update(UpdateSubmissionDto updateSubmissionDto) throws Exception {
        return super.update(updateSubmissionDto);
    }

    public List<SubmissionViewDto> fetchSubmissionsForStudent(int studentId) {
        return submissionsRepository.fetchSubmissionsForStudent(studentId);
    }
}