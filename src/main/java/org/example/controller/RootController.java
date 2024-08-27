package org.example.controller;

import org.example.model.UserRepositoryInMemory;
import org.example.service.UserService;
import org.example.view.ConsoleView;
import org.example.view.MenuViews;
import org.example.view.RegistrationView;

public class RootController {
    private final ConsoleView consoleView;
    private final MenuViews menuViews;
    private final UserController userController;

    public RootController(ConsoleView consoleView) {
        this.consoleView = consoleView;
        this.menuViews = new MenuViews();
        UserService userService = new UserService(new UserRepositoryInMemory());
        this.userController = new UserController(userService, new RegistrationView());
    }

    public void run() {
        while (true) {
            if (!userController.isLoggedIn()) {
                showWelcomeMenu();
            } else {
                showExchangingMenu();
            }
        }
    }

    private void showWelcomeMenu() {
        menuViews.showWelcomeMenu(consoleView);
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

    private void showExchangingMenu() {
        menuViews.showExchangingMenu(consoleView, userController.getLoggedInUserName());
        int choice = consoleView.getUserChoice();
        switch (choice) {
            case 1:
                // Deposit money to wallet
                break;
            case 2:
                // View wallet balance
                break;
            case 3:
                // Buy crypto
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