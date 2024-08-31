package org.example.controller;

import org.example.view.ConsoleView;
import org.example.view.MenuViews;

public class MenuController {
    private final ConsoleView consoleView;
    private final MenuViews menuViews;
    private final WelcomeMenuController welcomeMenuController;
    private final ExchangingMenuController exchangingMenuController;

    public MenuController(ConsoleView consoleView, MenuViews menuViews, UserController userController, WalletController walletController) {
        this.consoleView = consoleView;
        this.menuViews = menuViews;
        this.welcomeMenuController = new WelcomeMenuController(consoleView, menuViews, userController);
        this.exchangingMenuController = new ExchangingMenuController(consoleView, menuViews, userController, walletController);
    }

    public void displayMenu() {
        if (!welcomeMenuController.isUserLoggedIn()) {
            welcomeMenuController.showMenu();
        } else {
            exchangingMenuController.showMenu();
        }
    }
}
