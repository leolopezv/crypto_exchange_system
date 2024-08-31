package org.example.controller;

import org.example.view.ConsoleView;
import org.example.view.MenuViews;

public class LoginPanelController {
    private final ConsoleView consoleView;
    private final MenuViews menuViews;
    private final UserController userController;

    public LoginPanelController(ConsoleView consoleView, MenuViews menuViews, UserController userController) {
        this.consoleView = consoleView;
        this.menuViews = menuViews;
        this.userController = userController;
    }

    public void showMenu() {
        menuViews.showWelcomeMenu(consoleView);
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
