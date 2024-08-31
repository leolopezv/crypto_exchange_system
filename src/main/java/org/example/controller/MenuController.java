package org.example.controller;

import org.example.view.ConsoleView;
import org.example.view.MenuView;

public class MenuController {
    private final ConsoleView consoleView;
    private final MenuView menuView;
    private final LoginPanelController loginPanelController;
    private final ExchangePanelController exchangePanelController;

    public MenuController(ConsoleView consoleView, MenuView menuView, UserController userController, WalletController walletController, OrderController orderController) {
        this.consoleView = consoleView;
        this.menuView = menuView;
        this.loginPanelController = new LoginPanelController(consoleView, menuView, userController);
        this.exchangePanelController = new ExchangePanelController(consoleView, menuView, userController, walletController, orderController);
    }

    public void displayMenu() {
        if (!loginPanelController.isUserLoggedIn()) {
            loginPanelController.showMenu();
        } else {
            exchangePanelController.showMenu();
        }
    }
}
