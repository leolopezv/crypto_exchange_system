package org.example.service;

import org.example.model.Exchange;
import org.example.model.OrderBook;
import org.example.repository.*;
import org.example.repository.iRepository.OrderRepository;
import org.example.repository.iRepository.UserRepository;
import org.example.repository.iRepository.WalletRepository;
import org.example.view.MenuViews;
import org.example.view.WalletView;

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
    public WalletService createWalletService(WalletRepository walletRepository, Exchange exchange) {
        return new WalletService(walletRepository, exchange, null);
    }

    @Override
    public BalanceService createBalanceService(WalletRepository walletRepository, OrderRepository orderRepository) {
        return new BalanceService(walletRepository, orderRepository);
    }

    @Override
    public TransferService createTransferService(WalletRepository walletRepository, Exchange exchange) {
        return new TransferService(walletRepository, exchange);
    }

    @Override
    public OrderService createOrderService(OrderRepository orderRepository, BalanceService balanceService, TransferService transferService) {
        return new OrderService(new OrderBook(orderRepository, balanceService, transferService), balanceService);
    }

    @Override
    public UserService createUserService(UserRepository userRepository, WalletRepository walletRepository) {
        return new UserService(userRepository, walletRepository);
    }

    @Override
    public WalletView createWalletView() {
        return new WalletView();
    }

    @Override
    public MenuViews createMenuViews() {
        return new MenuViews();
    }
}