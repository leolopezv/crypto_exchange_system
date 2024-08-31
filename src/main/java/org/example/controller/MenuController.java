package org.example.controller;

import org.example.view.ConsoleView;
import org.example.view.MenuViews;

public class MenuController {
    private final ConsoleView consoleView;
    private final MenuViews menuViews;
    private final LoginPanelController loginPanelController;
    private final ExchangePanelController exchangePanelController;

    public MenuController(ConsoleView consoleView, MenuViews menuViews, UserController userController, WalletController walletController) {
        this.consoleView = consoleView;
        this.menuViews = menuViews;
        this.loginPanelController = new LoginPanelController(consoleView, menuViews, userController);
        this.exchangePanelController = new ExchangePanelController(consoleView, menuViews, userController, walletController);
    }

    public void displayMenu() {
        if (!loginPanelController.isUserLoggedIn()) {
            loginPanelController.showMenu();
        } else {
            exchangePanelController.showMenu();
        }
    }
}
