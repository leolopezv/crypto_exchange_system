package org.example.controller;

import org.example.model.User;
import org.example.model.UserRepository;
import org.example.view.RegistrationView;

public class UserController {
    private UserRepository userRepository;
    private RegistrationView registrationView;

    public UserController(UserRepository userRepository, RegistrationView registrationView) {
        this.userRepository = userRepository;
        this.registrationView = registrationView;
    }

    public void registerUser() {
        String name = registrationView.getNameInput();
        String email = registrationView.getEmailInput();
        String password = registrationView.getPasswordInput();

        // Check if the user already exists
        if (userRepository.findByName(name) != null) {
            registrationView.showError("A user with this name already exists. Please choose a different name.");
            return;
        }

        if (userRepository.findByEmail(email) != null) {
            registrationView.showError("A user with this email already exists. Please use a different email.");
            return;
        }

        // User creation and saving
        User newUser = new User(name, email, password);
        userRepository.save(newUser);
        registrationView.showSuccess(String.valueOf(newUser.getUserId()));
    }
}
