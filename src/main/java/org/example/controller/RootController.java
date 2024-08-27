package org.example.controller;

import org.example.model.UserRepositoryInMemory;
import org.example.service.UserService;
import org.example.view.ConsoleView;
import org.example.view.RegistrationView;

public class RootController {
    /*
    RootController will ensure that the application is up and running, ensuring users can register, login, and exchange.
     */
    private final ConsoleView consoleView;
    private final UserController userController;

    public RootController(ConsoleView consoleView) {
        this.consoleView = consoleView;
        UserService userService = new UserService(new UserRepositoryInMemory());
        this.userController = new UserController(userService, new RegistrationView());
    }

    // Main loop
    public void run() {
        while (true) {
            if (!userController.isLoggedIn()) {
                showWelcomeMenu();
            } else {
                showExchangingMenu();
            }
        }
    }

    // Menu for not logged in users
    private void showWelcomeMenu() {
        consoleView.showMessage("Welcome to Leonardo's Crypto Exchange System:");
        consoleView.showMessage("1. Register");
        consoleView.showMessage("2. Login");
        consoleView.showMessage("3. Quit");
        consoleView.showMessage("Enter an option to get into the club: ");

        int choice = consoleView.getUserChoice();
        switch (choice) {
            case 1:
                userController.registerUser();
                break;
            case 2:
                userController.loginUser();
                break;
            case 3:
                System.exit(0);
            default:
                consoleView.showError("Oops! Look out for errors.");
        }
    }

    // Menu for logged in users
    private void showExchangingMenu() {
        consoleView.showMessage("Welcome back " + userController.getLoggedInUserName() + "!");
        consoleView.showMessage("1. Soon");
        consoleView.showMessage("2. Soon");
        consoleView.showMessage("3. Soon");
        consoleView.showMessage("4. Soon");
        consoleView.showMessage("5. Log out");
        consoleView.showMessage("Enter an option to start exchanging: ");

        int choice = consoleView.getUserChoice();
        switch (choice) {
            case 1:
                // Soon
                break;
            case 2:
                // Soon
                break;
            case 3:
                // Soon
                break;
            case 4:
                // Soon
                break;
            case 5:
                userController.logoutUser();
                break;
            default:
                consoleView.showError("That was not supposed to happen! Try again.");
        }
    }
}