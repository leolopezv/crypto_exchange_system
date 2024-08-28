package org.example.controller;

import org.example.model.Exchange;
import org.example.model.UserRepositoryInMemory;
import org.example.model.WalletRepositoryInMemory;
import org.example.service.UserService;
import org.example.service.WalletService;
import org.example.view.ConsoleView;
import org.example.view.MenuViews;
import org.example.view.RegistrationView;
import org.example.view.WalletView;

public class RootController {
    private final ConsoleView consoleView;
    private final MenuViews menuViews;
    private final UserController userController;
    private final WalletController walletController;

    public RootController(ConsoleView consoleView) {
        this.consoleView = consoleView;
        this.menuViews = new MenuViews();

        WalletRepositoryInMemory walletRepository = new WalletRepositoryInMemory();
        Exchange exchange = new Exchange();
        WalletService walletService = new WalletService(walletRepository, exchange);

        UserService userService = new UserService(new UserRepositoryInMemory(), walletRepository);
        this.userController = new UserController(userService, new RegistrationView());
        this.walletController = new WalletController(walletService, new WalletView());
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
                walletController.depositMoney(userController.getLoggedInUserId());
                break;
            case 2:
                walletController.viewWalletBalance(userController.getLoggedInUserId());
                break;
            case 3:
                walletController.buyReserveCrypto(userController.getLoggedInUserId());
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