package org.example.controller;

import org.example.model.User;
import org.example.service.UserService;
import org.example.view.RegistrationView;

public class UserController {
    /*
    UserController may be responsible for registering new users, logging in and out old users, and checking who is logged in
     */
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
            registrationView.showError("This email has been taken. Please input another one.");
            return;
        }

        User newUser = userService.registerUser(name, email, password);
        registrationView.showSuccess("Successful registration! Hello: " + newUser.getName() + "! This is your ID: " + newUser.getUserId() + ".");
    }

    // Login of an existing user
    public void loginUser() {
        String email = registrationView.getEmailInput();
        String password = registrationView.getPasswordInput();

        User user = userService.authenticateUser(email, password);
        if (user != null) {
            loggedInUser = user;
            registrationView.showSuccess("You have logged in! Hi " + user.getName() + ".");
        } else {
            registrationView.showError("Invalid! Check your email and password.");
        }
    }

    // Logout of the current user
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
}