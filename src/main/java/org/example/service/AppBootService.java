package org.example.service;

import org.example.model.Exchange;
import org.example.view.*;
import org.example.controller.*;
import org.example.repository.*;

public class AppBootService {
    private final UserController userController;
    private final WalletController walletController;
    private final MenuController menuController;

    public AppBootService(ConsoleView consoleView, AppBootFactoryService factory) {
        // Use the factory to create services and repositories
        WalletRepository walletRepository = factory.createWalletRepository();
        OrderRepository orderRepository = factory.createOrderRepository();
        UserRepository userRepository = factory.createUserRepository();

        Exchange exchange = factory.createExchange();
        WalletService walletService = factory.createWalletService(walletRepository, exchange);
        BalanceService balanceService = factory.createBalanceService(walletRepository);
        TransferService transferService = factory.createTransferService(walletRepository, exchange);
        OrderService orderService = factory.createOrderService(orderRepository, balanceService, transferService);
        walletService.setOrderService(orderService);
        UserService userService = factory.createUserService(userRepository, walletRepository);

        WalletView walletView = factory.createWalletView();
        MenuViews menuViews = factory.createMenuViews();

        this.userController = new UserController(userService, consoleView);
        this.walletController = new WalletController(walletService, walletView);

        this.menuController = new MenuController(consoleView, menuViews, userController, walletController);
    }

    public MenuController getMenuController() {
        return menuController;
    }
}
