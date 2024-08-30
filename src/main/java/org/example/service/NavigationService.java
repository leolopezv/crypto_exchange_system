package org.example.service;

import org.example.controller.UserController;
import org.example.controller.WalletController;
import org.example.view.ConsoleView;
import org.example.view.MenuViews;

public class NavigationService {
    private final ConsoleView consoleView;
    private final MenuViews menuViews;
    private final UserController userController;
    private final WalletController walletController;

    public NavigationService(ConsoleView consoleView, MenuViews menuViews, UserController userController, WalletController walletController) {
        this.consoleView = consoleView;
        this.menuViews = menuViews;
        this.userController = userController;
        this.walletController = walletController;
    }

    public void showWelcomeMenu() {
        menuViews.showWelcomeMenu(consoleView);
        int choice = consoleView.getUserChoice();
        handleWelcomeMenu(choice);
    }

    public void showExchangingMenu() {
        menuViews.showExchangingMenu(consoleView, userController.getLoggedInUserName());
        int choice = consoleView.getUserChoice();
        handleExchangingMenu(choice);
    }

    private void handleWelcomeMenu(int choice) {
        switch (choice) {
            case 1 -> userController.registerUser();
            case 2 -> userController.loginUser();
            case 3 -> quitApplication();
            default -> showInvalidOption();
        }
    }

    private void handleExchangingMenu(int choice) {
        switch (choice) {
            case 1 -> walletController.depositMoney(userController.getLoggedInUserId());
            case 2 -> walletController.viewWalletBalance(userController.getLoggedInUserId());
            case 3 -> walletController.buyReserveCrypto(userController.getLoggedInUserId());
            case 4 -> walletController.placeBuyOrder(userController.getLoggedInUserId());
            case 5 -> walletController.placeSellOrder(userController.getLoggedInUserId());
            case 6 -> userController.logoutUser();
            default -> showInvalidOption();
        }
    }

    private void quitApplication() {
        consoleView.showMessage("Exiting...");
        System.exit(0);
    }

    private void showInvalidOption() {
        consoleView.showError("Wrong choice, try again.");
    }
}