package org.example.service;

import org.example.model.OrderBook;
import org.example.repository.OrderRepositoryInMemory;
import org.example.repository.TransactionRepositoryInMemory;
import org.example.repository.UserRepositoryInMemory;
import org.example.repository.WalletRepositoryInMemory;
import org.example.repository.iRepository.*;
import org.example.service.iService.*;
import org.example.view.MenuView;
import org.example.view.MoneyView;

public class AppBootFactoryService implements AppBootFactory {

    @Override
    public WalletRepository createWalletRepository() {
        return new WalletRepositoryInMemory();
    }

    @Override
    public OrderRepository createOrderRepository() {
        return new OrderRepositoryInMemory();
    }

    @Override
    public UserRepository createUserRepository() {
        return new UserRepositoryInMemory();
    }

    @Override
    public TransactionRepository createTransactionRepository() {
        return new TransactionRepositoryInMemory();
    }

    @Override
    public IWalletService createWalletService(WalletRepository walletRepository) {
        return new WalletService(walletRepository);
    }

    @Override
    public IBalanceService createBalanceService(WalletRepository walletRepository, OrderRepository orderRepository) {
        return new BalanceService(walletRepository, orderRepository);
    }

    @Override
    public ITransferService createTransferService(WalletRepository walletRepository) {
        return new TransferService(walletRepository);
    }

    @Override
    public IOrderService createOrderService(OrderRepository orderRepository, IBalanceService balanceService, ITransferService transferService, TransactionRepository transactionRepository) {
        return new OrderService(balanceService, createOrderBook(orderRepository, balanceService, transferService, transactionRepository), transactionRepository);
    }

    @Override
    public OrderBook createOrderBook(OrderRepository orderRepository, IBalanceService balanceService, ITransferService transferService, TransactionRepository transactionRepository) {
        return new OrderBook(orderRepository, (BalanceService) balanceService, (TransferService) transferService, transactionRepository);
    }

    @Override
    public IUserService createUserService(UserRepository userRepository, WalletRepository walletRepository) {
        return new UserService(userRepository, walletRepository);
    }

    @Override
    public MoneyView createWalletView() {
        return new MoneyView();
    }

    @Override
    public MenuView createMenuViews() {
        return new MenuView();
    }
}
