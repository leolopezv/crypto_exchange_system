package org.example.service;

import org.example.model.Exchange;
import org.example.repository.iRepository.OrderRepository;
import org.example.repository.iRepository.UserRepository;
import org.example.repository.iRepository.WalletRepository;
import org.example.view.*;
import org.example.controller.*;

public class AppBootService {
    private final UserController userController;
    private final WalletController walletController;
    private final MenuController menuController;

    public AppBootService(ConsoleView consoleView, AppBootFactoryService factory) {
        WalletRepository walletRepository = factory.createWalletRepository();
        OrderRepository orderRepository = factory.createOrderRepository();
        UserRepository userRepository = factory.createUserRepository();

        Exchange exchange = Exchange.getInstance();
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
