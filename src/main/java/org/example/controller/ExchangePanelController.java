package org.example.controller;

import org.example.view.ConsoleView;
import org.example.view.MenuView;

public class ExchangePanelController {
    private final ConsoleView consoleView;
    private final MenuView menuView;
    private final UserController userController;
    private final WalletController walletController;
    private final OrderController orderController;

    public ExchangePanelController(ConsoleView consoleView, MenuView menuView, UserController userController, WalletController walletController, OrderController orderController) {
        this.consoleView = consoleView;
        this.menuView = menuView;
        this.userController = userController;
        this.walletController = walletController;
        this.orderController = orderController;
    }

    public void showMenu() {
        menuView.showExchangingMenu(consoleView, userController.getLoggedInUserName());
        int choice = consoleView.getUserChoice();
        handleMenuChoice(choice);
    }

    private void handleMenuChoice(int choice) {
        switch (choice) {
            case 1 -> walletController.depositMoney(userController.getLoggedInUserId());
            case 2 -> walletController.viewWalletBalance(userController.getLoggedInUserId());
            case 3 -> walletController.buyReserveCrypto(userController.getLoggedInUserId());
            case 4 -> orderController.placeBuyOrder(userController.getLoggedInUserId());
            case 5 -> orderController.placeSellOrder(userController.getLoggedInUserId());
            case 6 -> orderController.showPastTransactions(userController.getLoggedInUserId());
            case 7 -> userController.logoutUser();
            default -> showInvalidOption();
        }
    }

    private void showInvalidOption() {
        consoleView.showError("Wrong choice, try again.");
    }
}