package services;

import models.User;
import models.dto.CreateUserDto;
import models.dto.UpdateUserDto;
import repository.UserRepository;
import utilities.PasswordHasher;

public class UserService {
    private UserRepository userRepository;

    public UserService() {
        this.userRepository = new UserRepository();
    }

    public User getById(int id) throws Exception {
        if (id <= 0) {
            throw new Exception("Id does not exist!");
        }
        User user = this.userRepository.getById(id);
        if (user == null) {
            throw new Exception("User with Id: " + id + " does not exist!");
        }
        return user;
    }

    public User create(CreateUserDto createUser) throws Exception {
        if (createUser.getFirst_name().isEmpty() || createUser.getLast_name().isEmpty() || createUser.getEmail().isEmpty() || createUser.getPassword_hash().isEmpty()) {
            throw new Exception("All fields are required!");
        }

        if (this.userRepository.getByEmail(createUser.getEmail()) != null) {
            throw new Exception("Email is already in use!");
        }

        String hashedPassword = PasswordHasher.hash(createUser.getPassword_hash());
        createUser.setPassword_hash(hashedPassword);

        User user = this.userRepository.create(createUser);
        if (user == null) {
            throw new Exception("User could not be created!");
        }
        return user;
    }

    public User update(UpdateUserDto updateUser) throws Exception {
        if (updateUser.getUser_id() <= 0) {
            throw new Exception("Invalid user ID!");
        }

        User existingUser = this.getById(updateUser.getUser_id());
        if (existingUser == null) {
            throw new Exception("User with Id: " + updateUser.getUser_id() + " does not exist!");
        }

        if (updateUser.getPassword_hash() != null && !updateUser.getPassword_hash().isEmpty()) {
            String hashedPassword = PasswordHasher.hash(updateUser.getPassword_hash());
            updateUser.setPassword_hash(hashedPassword);
        }

        User updatedUser = this.userRepository.update(updateUser);
        if (updatedUser == null) {
            throw new Exception("User could not be updated!");
        }
        return updatedUser;
    }
}
