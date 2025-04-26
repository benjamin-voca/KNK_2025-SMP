package services;

import models.User;
import models.dto.CreateUserDto;
import models.dto.UpdateUserDto;
import repository.UserRepository;
import utilities.PasswordHasher;

public class UserService extends BaseService<User, CreateUserDto, UpdateUserDto> {

    private UserRepository userRepository;

    public UserService() {
        super(new UserRepository());
        this.userRepository = (UserRepository) this.repository;
    }

    @Override
    public User getById(int id) throws Exception {
        return super.getById(id);
    }

    @Override
    public User create(CreateUserDto createUserDto) throws Exception {
        if (createUserDto.getFirst_name().isEmpty() || createUserDto.getLast_name().isEmpty() || createUserDto.getEmail().isEmpty() || createUserDto.getPassword_hash().isEmpty()) {
            throw new Exception("All fields are required!");
        }

        if (this.userRepository.getByEmail(createUserDto.getEmail()) != null) {
            throw new Exception("Email is already in use!");
        }

        String hashedPassword = PasswordHasher.hash(createUserDto.getPassword_hash());
        createUserDto.setPassword_hash(hashedPassword);

        User user = this.repository.create(createUserDto);
        if (user == null) {
            throw new Exception("User could not be created!");
        }
        return user;
    }

    @Override
    public User update(UpdateUserDto updateUserDto) throws Exception {
        if (updateUserDto.getUser_id() <= 0) {
            throw new Exception("Invalid user ID!");
        }

        User existingUser = this.getById(updateUserDto.getUser_id());
        if (existingUser == null) {
            throw new Exception("User with Id: " + updateUserDto.getUser_id() + " does not exist!");
        }

        if (updateUserDto.getPassword_hash() != null && !updateUserDto.getPassword_hash().isEmpty()) {
            String hashedPassword = PasswordHasher.hash(updateUserDto.getPassword_hash());
            updateUserDto.setPassword_hash(hashedPassword);
        }

        User updatedUser = this.repository.update(updateUserDto);
        if (updatedUser == null) {
            throw new Exception("User could not be updated!");
        }
        return updatedUser;
    }
}
