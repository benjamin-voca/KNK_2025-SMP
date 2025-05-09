package services;

import models.Student;
import models.User;
import models.dto.CreateStudentDto;
import models.dto.UpdateStudentDto;
import repository.StudentRepository;
import repository.UserRepository;
import utilities.PasswordHasher;
import exceptions.AuthenticationException;

public class StudentService extends BaseService<Student, CreateStudentDto, UpdateStudentDto> {

    private final UserRepository userRepository;

    public StudentService() {
        super(new StudentRepository());
        this.userRepository = new UserRepository();
    }

    @Override
    public Student getById(int id) throws Exception {
        return super.getById(id);
    }

    public Student login(String studentNumber, String password) throws AuthenticationException {
        try {
            System.out.println("Attempting login for: " + studentNumber);

            Student student = ((StudentRepository)this.repository).findByStudentNumber(studentNumber);
            System.out.println("Found student: " + (student != null ? student.getStudentNumber() : "null"));

            if (student == null) {
                throw new AuthenticationException("Invalid credentials");
            }

            User user = userRepository.findById(student.getUserId());
            System.out.println("Found user: " + (user != null ? user.getEmail() : "null"));
            System.out.println("Stored hash: " + (user != null ? user.getPasswordHash() : "null"));

            if (user == null) {
                throw new AuthenticationException("Invalid credentials");
            }

            boolean passwordValid = PasswordHasher.validatePassword(password, user.getPasswordHash());
            System.out.println("Password valid: " + passwordValid);

            if (!passwordValid) {
                throw new AuthenticationException("Invalid credentials");
            }

            return student;
        } catch (Exception e) {
            e.printStackTrace();
            throw new AuthenticationException("Authentication failed");
        }
    }

    @Override
    public Student create(CreateStudentDto createStudentDto) throws Exception {
        return super.create(createStudentDto);
    }

    @Override
    public Student update(UpdateStudentDto updateStudentDto) throws Exception {
        return super.update(updateStudentDto);
    }
}