package org.example.controller;

import org.example.model.Exchange;
import org.example.repository.OrderRepositoryInMemory;
import org.example.repository.UserRepositoryInMemory;
import org.example.repository.WalletRepositoryInMemory;
import org.example.service.NavigationService;
import org.example.service.OrderService;
import org.example.service.UserService;
import org.example.service.WalletService;
import org.example.view.ConsoleView;
import org.example.view.MenuViews;
import org.example.view.WalletView;

public class RootController {
    private final ConsoleView consoleView;
    private final NavigationService navigationService;
    private final UserController userController;

    public RootController(ConsoleView consoleView) {
        this.consoleView = consoleView;
        this.userController = initializeUserController();
        this.navigationService = initializeNavigationService();
    }

    private UserController initializeUserController() {
        WalletRepositoryInMemory walletRepository = new WalletRepositoryInMemory();
        UserService userService = new UserService(new UserRepositoryInMemory(), walletRepository);
        return new UserController(userService, consoleView);
    }

    private NavigationService initializeNavigationService() {
        WalletRepositoryInMemory walletRepository = new WalletRepositoryInMemory();
        OrderRepositoryInMemory orderRepository = new OrderRepositoryInMemory();
        Exchange exchange = new Exchange();

        WalletService walletService = new WalletService(walletRepository, exchange, null);
        OrderService orderService = new OrderService(orderRepository, walletService);
        walletService.setOrderService(orderService);

        WalletController walletController = new WalletController(walletService, new WalletView());
        return new NavigationService(consoleView, new MenuViews(), userController, walletController);
    }

    public void run() {
        while (true) {
            if (!userController.isLoggedIn()) {
                navigationService.showWelcomeMenu();
            } else {
                navigationService.showExchangingMenu();
            }
        }
    }
}