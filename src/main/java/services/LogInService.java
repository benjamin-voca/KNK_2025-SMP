package services;

import exceptions.AuthenticationException;
import models.Professors;
import models.Student;
import models.User;
import repository.ProfessorRepository;
import repository.StudentRepository;
import repository.UserRepository;
import utilities.PasswordHasher;

public class LogInService {
    private final StudentRepository studentRepository;
    private final ProfessorRepository professorRepository;
    private final UserRepository userRepository;

    private static final String ASSESSOR_ID = "ASSE001";

    public LogInService() {
        this.studentRepository = new StudentRepository();
        this.professorRepository = new ProfessorRepository();
        this.userRepository = new UserRepository();
    }

    public Student logInStudent(String studentNumber, String password) throws AuthenticationException {
        try {
            System.out.println("Attempting login for student: " + studentNumber);

            Student student = studentRepository.findByStudentNumber(studentNumber);
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
            throw new AuthenticationException("Student authentication failed");
        }
    }

    public Professors logInProfessor(String professorNumber, String password) throws AuthenticationException {
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
            throw new AuthenticationException("Professor authentication failed");
        }
    }

    public boolean logInAssessor(String assessorId, String password) throws AuthenticationException {
        try {
            System.out.println("Attempting login for assessor: " + assessorId);

            if (!assessorId.equals(ASSESSOR_ID)) {
                throw new AuthenticationException("Invalid assessor ID. Only ASSE001 is allowed.");
            }

            User user = userRepository.findAssessor();
            System.out.println("Found user: " + (user != null ? user.getEmail() : "null"));
            System.out.println("Stored hash: " + (user != null ? user.getPasswordHash() : "null"));

            if (user == null) {
                throw new AuthenticationException("Assessor not found in database");
            }

            boolean passwordValid = PasswordHasher.validatePassword(password, user.getPasswordHash());
            System.out.println("Password valid: " + passwordValid);

            if (!passwordValid) {
                throw new AuthenticationException("Invalid password");
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new AuthenticationException("Assessor authentication failed: " + e.getMessage());
        }
    }
}