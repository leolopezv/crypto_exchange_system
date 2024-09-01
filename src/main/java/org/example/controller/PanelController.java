package org.example.controller;

import org.example.view.ConsoleView;
import org.example.view.MenuView;

public class PanelController {
    private final ConsoleView consoleView;
    private final MenuView menuView;
    private final UserController userController;
    private final WalletController walletController;
    private final OrderController orderController;

    public PanelController(ConsoleView consoleView, MenuView menuView, UserController userController, WalletController walletController, OrderController orderController) {
        this.consoleView = consoleView;
        this.menuView = menuView;
        this.userController = userController;
        this.walletController = walletController;
        this.orderController = orderController;
    }

    public void showMenu() {
        if (userController.isLoggedIn()) {
            showExchangeMenu();
        } else {
            showLoginMenu();
        }
    }

    private void showLoginMenu() {
        menuView.showLoginMenu(consoleView);
        int choice = consoleView.getUserChoice();
        loginMenu(choice);
    }

    private void loginMenu(int choice) {
        switch (choice) {
            case 1 -> userController.registerUser();
            case 2 -> userController.loginUser();
            case 3 -> quitApp();
            default -> showInvalidOption();
        }
    }

    private void showExchangeMenu() {
        menuView.showExMenu(consoleView, userController.getLoggedInUserName());
        int choice = consoleView.getUserChoice();
        exchangeMenu(choice);
    }

    private void exchangeMenu(int choice) {
        switch (choice) {
            case 1 -> walletController.depMoney(userController.getLoggedInUserId());
            case 2 -> walletController.viewWallet(userController.getLoggedInUserId());
            case 3 -> walletController.buyFromEx(userController.getLoggedInUserId());
            case 4 -> orderController.placeBuyOrder(userController.getLoggedInUserId());
            case 5 -> orderController.placeSellOrder(userController.getLoggedInUserId());
            case 6 -> orderController.showPastTr(userController.getLoggedInUserId());
            case 7 -> userController.logoutUser();
            default -> showInvalidOption();
        }
    }

    private void quitApp() {
        consoleView.showMessage("Exiting...");
        System.exit(0);
    }

    private void showInvalidOption() {
        consoleView.showError("Wrong choice, try again.");
    }
}