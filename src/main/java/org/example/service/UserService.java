package org.example.service;

import org.example.model.User;
import org.example.model.UserRepository;

public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Check if a user with the given email already exists
    public boolean isUserExists(String email) {
        return userRepository.findByEmail(email) != null;
    }

    // Register a new user
    public void registerUser(String name, String email, String password) {
        User newUser = new User(name, email, password);
        userRepository.save(newUser);
    }

    // Authenticate a user
    public User authenticateUser(String email, String password) {
        User user = userRepository.findByEmail(email);
        return user != null && user.getPassword().equals(password) ? user : null;
    }
}