package org.example.controller;

import org.example.view.ConsoleView;
import org.example.view.MenuView;

public class RootController {
    private final PanelController panelController;

    public RootController(ConsoleView consoleView, MenuView menuView, UserController userController, WalletController walletController, OrderController orderController) {
        this.panelController = new PanelController(consoleView, menuView, userController, walletController, orderController);
    }

    public void run() {
        while (true) {
            panelController.showMenu();
        }
    }
}
