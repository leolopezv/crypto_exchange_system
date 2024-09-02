package org.example.controller;

import org.example.model.User;
import org.example.service.UserService;
import org.example.service.exception.FailedSessionEx;
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
        try {
            userService.registerUser(name, email, password);
            consoleView.showSuccess("Successful registration.");
        } catch (FailedSessionEx e) {
            consoleView.showError(e.getMessage());
        }
    }

    public void loginUser() {
        String email = consoleView.getUserInput("Enter your email: ");
        String password = consoleView.getUserInput("Enter your password: ");
        try {
            loggedInUser = userService.authenticateUser(email, password);
            consoleView.showSuccess("Hi " + loggedInUser.getName() + "!");
        } catch (FailedSessionEx e) {
            consoleView.showError(e.getMessage());
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