package org.example.controller;

import org.example.model.User;
import org.example.service.UserService;
import org.example.view.RegistrationView;

public class UserController {
    private UserService userService;
    private RegistrationView registrationView;
    private User loggedInUser;

    public UserController(UserService userService, RegistrationView registrationView) {
        this.userService = userService;
        this.registrationView = registrationView;
    }

    // Registration of a new user
    public void registerUser() {
        String name = registrationView.getNameInput();
        String email = registrationView.getEmailInput();
        String password = registrationView.getPasswordInput();

        if (userService.isUserExists(email)) {
            registrationView.showError("A user with this email already exists. Please use a different email.");
            return;
        }

        userService.registerUser(name, email, password);
        registrationView.showSuccess("Registration successful!");
    }

    // Login of an existing user
    public void loginUser() {
        String email = registrationView.getEmailInput();
        String password = registrationView.getPasswordInput();

        User user = userService.authenticateUser(email, password);
        if (user != null) {
            loggedInUser = user;
            registrationView.showSuccess("Login successful! Welcome " + user.getName() + ".");
        } else {
            registrationView.showError("Invalid email or password. Please try again.");
        }
    }

    // Logout of the current user
    public void logoutUser() {
        loggedInUser = null;
        registrationView.showSuccess("Logout successful!");
    }

    public boolean isLoggedIn() {
        return loggedInUser != null;
    }

    public String getLoggedInUserName() {
        return loggedInUser != null ? loggedInUser.getName() : "";
    }
}