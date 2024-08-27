package org.example.service;

import org.example.model.User;
import org.example.model.UserRepository;

public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean isUserExists(String email) {
        return userRepository.findByEmail(email) != null;
    }

    public User registerUser(String name, String email, String password) {
        User newUser = new User(name, email, password);
        userRepository.save(newUser);
        return newUser;
    }

    public User authenticateUser(String email, String password) {
        User user = userRepository.findByEmail(email);
        return user != null && user.getPassword().equals(password) ? user : null;
    }
}