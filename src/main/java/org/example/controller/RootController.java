package org.example.controller;

import org.example.model.InMemoryUserRepository;
import org.example.view.ConsoleView;
import org.example.view.RegistrationView;

public class RootController {
    private final ConsoleView consoleView;
    private final UserController userController;

    public RootController(ConsoleView consoleView) {
        this.consoleView = consoleView;
        this.userController = new UserController(new InMemoryUserRepository(), new RegistrationView());
    }

    public void run() {
        while (true) {
            int choice = consoleView.getUserChoice();
            switch (choice) {
                case 1:
                    userController.registerUser();
                    break;
                case 2:
                    // Login to be implemented
                    break;
                case 3:
                    System.exit(0);
                default:
                    consoleView.showError("Invalid option. Please try again.");
            }
        }
    }
}
