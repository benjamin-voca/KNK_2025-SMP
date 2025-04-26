package services;

import models.Enrollments;
import models.dto.CreateEnrollmentDto;
import repository.EnrollmentRepository;

public class EnrollmentService extends BaseService<Enrollments, CreateEnrollmentDto, Void> {

    public EnrollmentService() {
        super(new EnrollmentRepository());
    }

    @Override
    public Enrollments getById(int id) throws Exception {
        return super.getById(id);
    }

    @Override
    public Enrollments create(CreateEnrollmentDto createEnrollmentDto) throws Exception {
        return super.create(createEnrollmentDto);
    }
}
