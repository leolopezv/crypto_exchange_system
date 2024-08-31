package org.example.controller;

import org.example.view.ConsoleView;
import org.example.view.MenuView;

public class LoginPanelController {
    private final ConsoleView consoleView;
    private final MenuView menuView;
    private final UserController userController;

    public LoginPanelController(ConsoleView consoleView, MenuView menuView, UserController userController) {
        this.consoleView = consoleView;
        this.menuView = menuView;
        this.userController = userController;
    }

    public void showMenu() {
        menuView.showWelcomeMenu(consoleView);
        int choice = consoleView.getUserChoice();
        handleMenuChoice(choice);
    }

    private void handleMenuChoice(int choice) {
        switch (choice) {
            case 1 -> userController.registerUser();
            case 2 -> userController.loginUser();
            case 3 -> quitApplication();
            default -> showInvalidOption();
        }
    }

    public boolean isUserLoggedIn() {
        return userController.isLoggedIn();
    }

    private void quitApplication() {
        consoleView.showMessage("Exiting...");
        System.exit(0);
    }

    private void showInvalidOption() {
        consoleView.showError("Wrong choice, try again.");
    }
}