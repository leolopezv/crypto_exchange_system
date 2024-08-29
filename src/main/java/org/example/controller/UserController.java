package org.example.controller;

import org.example.model.User;
import org.example.service.UserService;
import org.example.view.ConsoleView;

public class UserController {
    private UserService userService;
    private ConsoleView consoleView;
    private User loggedInUser;

    public UserController(UserService userService, ConsoleView consoleView) {
        this.userService = userService;
        this.consoleView = consoleView;
    }

    public void registerUser() {
        String name = consoleView.getUserInput("Enter your name: ");
        String email = consoleView.getUserInput("Enter your email: ");
        String password = consoleView.getUserInput("Enter your password: ");
        User newUser = userService.registerUser(name, email, password);
        if (newUser != null) {
            consoleView.showSuccess("Successful registration.");
        } else {
            consoleView.showError("Email already in use.");
        }
    }

    public void loginUser() {
        String email = consoleView.getUserInput("Enter your email: ");
        String password = consoleView.getUserInput("Enter your password: ");
        loggedInUser = userService.authenticateUser(email, password);
        if (loggedInUser != null) {
            consoleView.showSuccess("Hi " + loggedInUser.getName() + "!");
        } else {
            consoleView.showError("Invalid credentials.");
        }
    }

    public void logoutUser() {
        loggedInUser = null;
        consoleView.showSuccess("You have logged out.");
    }

    public boolean isLoggedIn() {
        return loggedInUser != null;
    }

    public String getLoggedInUserName() {
        return isLoggedIn() ? loggedInUser.getName() : "";
    }

    public int getLoggedInUserId() {
        return isLoggedIn() ? loggedInUser.getUserId() : -1;
    }
}