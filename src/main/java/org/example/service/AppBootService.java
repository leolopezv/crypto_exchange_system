package org.example.service;

import org.example.model.OrderBook;
import org.example.repository.iRepository.OrderRepository;
import org.example.repository.iRepository.TransactionRepository;
import org.example.repository.iRepository.UserRepository;
import org.example.repository.iRepository.WalletRepository;
import org.example.service.iService.*;
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
        TransactionRepository transactionRepository = factory.createTransactionRepository();

        IWalletService walletService = factory.createWalletService(walletRepository);
        IBalanceService balanceService = factory.createBalanceService(walletRepository, orderRepository);
        ITransferService transferService = factory.createTransferService(walletRepository);
        IOrderService orderService = factory.createOrderService(orderRepository, balanceService, transferService, transactionRepository);
        IUserService userService = factory.createUserService(userRepository, walletRepository);

        MoneyView moneyView = factory.createWalletView();
        MenuView menuView = factory.createMenuViews();

        this.userController = new UserController((UserService) userService, consoleView);
        this.walletController = new WalletController((WalletService) walletService, moneyView);
        this.orderController = new OrderController((OrderService) orderService, moneyView);
        this.menuController = new MenuController(consoleView, menuView, userController, walletController, orderController);
    }

    public MenuController getMenuController() {
        return menuController;
    }
}
