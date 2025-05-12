package services;

import exceptions.AuthenticationException;
import models.Professors;
import models.User;
import models.dto.CreateProfessorDto;
import models.dto.UpdateProfessorDto;
import repository.ProfessorRepository;
import repository.UserRepository;
import utilities.PasswordHasher;

public class ProfessorService extends BaseService<Professors, CreateProfessorDto, UpdateProfessorDto> {

    private ProfessorRepository professorRepository;
    private UserRepository userRepository;

    public ProfessorService() {
        super(new ProfessorRepository());
        this.professorRepository = (ProfessorRepository) this.repository;
        this.userRepository = new UserRepository();
    }

    @Override
    public Professors getById(int id) throws Exception {
        return super.getById(id);
    }

    public Professors login(String professorNumber, String password) throws AuthenticationException {
        try {
            System.out.println("Attempting login for professor: " + professorNumber);

            Professors professor = professorRepository.findByProfessorNumber(professorNumber);
            System.out.println("Found professor: " + (professor != null ? professor.getProfessorNumber() : "null"));

            if (professor == null) {
                throw new AuthenticationException("Invalid credentials");
            }

            User user = userRepository.findById(professor.getUserId());
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

            return professor;
        } catch (Exception e) {
            e.printStackTrace();
            throw new AuthenticationException("Authentication failed");
        }
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