package org.example.service;

import org.example.model.User;
import org.example.model.UserRepository;

public class UserService {
    /*
    UserService is helping me to check if a user already exists and to register and authenticate a user
     */
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Check if a user with the given email already exists
    public boolean isUserExists(String email) {
        return userRepository.findByEmail(email) != null;
    }

    // Register a new user
    public User registerUser(String name, String email, String password) {
        User newUser = new User(name, email, password);
        userRepository.save(newUser);
        return newUser;
    }

    // Authenticate a user
    public User authenticateUser(String email, String password) {
        User user = userRepository.findByEmail(email);
        return user != null && user.getPassword().equals(password) ? user : null;
    }
}