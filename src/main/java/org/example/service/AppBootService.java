package org.example.service;

import org.example.repository.iRepository.OrderRepository;
import org.example.repository.iRepository.UserRepository;
import org.example.repository.iRepository.WalletRepository;
import org.example.view.*;
import org.example.controller.*;

public class AppBootService {
    private final UserController userController;
    private final WalletController walletController;
    private final MenuController menuController;
    private final OrderController orderController;

    public AppBootService(ConsoleView consoleView, AppBootFactoryService factory) {
        WalletRepository walletRepository = factory.createWalletRepository();
        OrderRepository orderRepository = factory.createOrderRepository();
        UserRepository userRepository = factory.createUserRepository();

        WalletService walletService = factory.createWalletService(walletRepository);
        BalanceService balanceService = factory.createBalanceService(walletRepository, orderRepository);
        TransferService transferService = factory.createTransferService(walletRepository);
        OrderService orderService = factory.createOrderService(orderRepository, balanceService, transferService);
        UserService userService = factory.createUserService(userRepository, walletRepository);

        MoneyView moneyView = factory.createWalletView();
        MenuViews menuViews = factory.createMenuViews();

        this.userController = new UserController(userService, consoleView);
        this.walletController = new WalletController(walletService, moneyView);
        this.orderController = new OrderController(orderService, moneyView);
        this.menuController = new MenuController(consoleView, menuViews, userController, walletController, orderController);
    }

    public MenuController getMenuController() {
        return menuController;
    }
}
