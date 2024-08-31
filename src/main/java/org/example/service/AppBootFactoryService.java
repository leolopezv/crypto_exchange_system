package org.example.service;

import org.example.model.OrderBook;
import org.example.repository.*;
import org.example.repository.iRepository.OrderRepository;
import org.example.repository.iRepository.UserRepository;
import org.example.repository.iRepository.WalletRepository;
import org.example.service.iService.AppBootFactory;
import org.example.view.MenuViews;
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
    public WalletService createWalletService(WalletRepository walletRepository) {
        return new WalletService(walletRepository);
    }

    @Override
    public BalanceService createBalanceService(WalletRepository walletRepository, OrderRepository orderRepository) {
        return new BalanceService(walletRepository, orderRepository);
    }

    @Override
    public TransferService createTransferService(WalletRepository walletRepository) {
        return new TransferService(walletRepository);
    }

    @Override
    public OrderService createOrderService(OrderRepository orderRepository, BalanceService balanceService, TransferService transferService) {
        return new OrderService(balanceService, new OrderBook(orderRepository, balanceService, transferService));
    }

    @Override
    public UserService createUserService(UserRepository userRepository, WalletRepository walletRepository) {
        return new UserService(userRepository, walletRepository);
    }

    @Override
    public MoneyView createWalletView() {
        return new MoneyView();
    }

    @Override
    public MenuViews createMenuViews() {
        return new MenuViews();
    }
}