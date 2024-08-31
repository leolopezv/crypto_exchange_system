package org.example.controller;

import org.example.view.ConsoleView;
import org.example.view.MenuViews;

public class ExchangePanelController {
    private final ConsoleView consoleView;
    private final MenuViews menuViews;
    private final UserController userController;
    private final WalletController walletController;
    private final OrderController orderController;

    public ExchangePanelController(ConsoleView consoleView, MenuViews menuViews, UserController userController, WalletController walletController, OrderController orderController) {
        this.consoleView = consoleView;
        this.menuViews = menuViews;
        this.userController = userController;
        this.walletController = walletController;
        this.orderController = orderController;
    }

    public void showMenu() {
        menuViews.showExchangingMenu(consoleView, userController.getLoggedInUserName());
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
            case 6 -> userController.logoutUser();
            default -> showInvalidOption();
        }
    }

    private void showInvalidOption() {
        consoleView.showError("Wrong choice, try again.");
    }
}
