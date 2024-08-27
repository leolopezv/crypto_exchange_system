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

    public void registerUser() {
        String name = registrationView.getNameInput();
        String email = registrationView.getEmailInput();
        String password = registrationView.getPasswordInput();
        User newUser = userService.registerUser(name, email, password);

        if (newUser != null) {
            registrationView.showSuccess("User registered successfully.");
        } else {
            registrationView.showError("Credentials already taken.");
        }
    }

    public void loginUser() {
        String email = registrationView.getEmailInput();
        String password = registrationView.getPasswordInput();

        User user = userService.authenticateUser(email, password);
        if (user != null) {
            loggedInUser = user;
            registrationView.showSuccess("You have logged in! Hi " + user.getName());
        } else {
            registrationView.showError("Invalid! Check your email and password.");
        }
    }

    public void logoutUser() {
        loggedInUser = null;
        registrationView.showSuccess("You have logged out successfully.");
    }

    public boolean isLoggedIn() {
        return loggedInUser != null;
    }

    public String getLoggedInUserName() {
        return loggedInUser != null ? loggedInUser.getName() : "";
    }

    public int getLoggedInUserId() {
        return loggedInUser != null ? loggedInUser.getUserId() : -1;
    }
}