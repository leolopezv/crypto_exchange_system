package org.example.service;

import org.example.model.OrderBook;
import org.example.repository.OrderMemoryRepo;
import org.example.repository.TransactionMemoryRepo;
import org.example.repository.UserMemoryRepo;
import org.example.repository.WalletMemoryRepo;
import org.example.repository.iRepository.*;
import org.example.view.MenuView;
import org.example.view.MoneyView;

public class AppFactory {

    public WalletRepository createWalletRepository() {
        return new WalletMemoryRepo();
    }

    public OrderRepository createOrderRepository() {
        return new OrderMemoryRepo();
    }

    public UserRepository createUserRepository() {
        return new UserMemoryRepo();
    }

    public TransactionRepository createTransactionRepository() {
        return new TransactionMemoryRepo();
    }

    public WalletService createWalletService(WalletRepository walletRepository) {
        return new WalletService(walletRepository);
    }

    public BalanceService createBalanceService(WalletRepository walletRepository, OrderRepository orderRepository) {
        return new BalanceService(walletRepository, orderRepository);
    }

    public OrderService createOrderService(OrderRepository orderRepository, BalanceService balanceService, WalletService walletService, TransactionRepository transactionRepository) {
        return new OrderService(balanceService, createOrderBook(orderRepository, balanceService, walletService, transactionRepository), transactionRepository);
    }

    public OrderBook createOrderBook(OrderRepository orderRepository, BalanceService balanceService, WalletService walletService, TransactionRepository transactionRepository) {
        return new OrderBook(orderRepository, balanceService, walletService, transactionRepository);
    }

    public UserService createUserService(UserRepository userRepository, WalletRepository walletRepository) {
        return new UserService(userRepository, walletRepository);
    }

    public MoneyView createWalletView() {
        return new MoneyView();
    }

    public MenuView createMenuViews() {
        return new MenuView();
    }
}
