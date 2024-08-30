package org.example.controller;

import org.example.model.Exchange;
import org.example.repository.UserRepositoryInMemory;
import org.example.repository.WalletRepositoryInMemory;
import org.example.repository.OrderRepositoryInMemory;
import org.example.service.UserService;
import org.example.service.WalletService;
import org.example.service.OrderService;
import org.example.view.ConsoleView;
import org.example.view.MenuViews;
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
        OrderRepositoryInMemory orderRepository = new OrderRepositoryInMemory();
        Exchange exchange = new Exchange();

        WalletService walletService = new WalletService(walletRepository, exchange, null);

        OrderService orderService = new OrderService(orderRepository, walletService);

        walletService.setOrderService(orderService);

        UserService userService = new UserService(new UserRepositoryInMemory(), walletRepository);

        this.userController = new UserController(userService, consoleView);
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
            case 1 -> userController.registerUser();
            case 2 -> userController.loginUser();
            case 3 -> quitApplication();
            default -> showInvalidOption();
        }
    }

    private void showExchangingMenu() {
        menuViews.showExchangingMenu(consoleView, userController.getLoggedInUserName());
        int choice = consoleView.getUserChoice();
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
