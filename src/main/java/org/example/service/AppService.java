package org.example.service;

import org.example.controller.OrderController;
import org.example.controller.RootController;
import org.example.controller.UserController;
import org.example.controller.WalletController;
import org.example.repository.iRepository.*;
import org.example.view.*;

public class AppService {
    private final RootController rootController;

    public AppService(ConsoleView consoleView, AppFactory factory) {
        WalletRepository walletRepository = factory.createWalletRepository();
        OrderRepository orderRepository = factory.createOrderRepository();
        UserRepository userRepository = factory.createUserRepository();
        TransactionRepository transactionRepository = factory.createTransactionRepository();

        WalletService walletService = factory.createWalletService(walletRepository);
        UserService userService = factory.createUserService(userRepository, walletRepository);
        BalanceService balanceService = factory.createBalanceService(walletRepository, orderRepository);
        OrderService orderService = factory.createOrderService(orderRepository, balanceService, walletService, transactionRepository);

        MoneyView moneyView = factory.createWalletView();
        MenuView menuView = factory.createMenuViews();

        this.rootController = new RootController(consoleView, menuView,
                new UserController(userService, consoleView),
                new WalletController(walletService, moneyView),
                new OrderController(orderService, moneyView));
    }

    public RootController getRootController() {
        return rootController;
    }
}