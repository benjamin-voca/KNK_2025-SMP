package services;

import models.Submissions;
import models.dto.CreateSubmissionDto;
import models.dto.UpdateSubmissionDto;
import repository.SubmissionsRepository;

public class SubmissionsService extends BaseService<Submissions, CreateSubmissionDto, UpdateSubmissionDto> {

    public SubmissionsService() {
        super(new SubmissionsRepository());
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
}