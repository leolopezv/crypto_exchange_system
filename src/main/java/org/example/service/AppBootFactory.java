package org.example.service;

import org.example.model.Exchange;
import org.example.repository.iRepository.OrderRepository;
import org.example.repository.iRepository.UserRepository;
import org.example.repository.iRepository.WalletRepository;
import org.example.view.MenuViews;
import org.example.view.WalletView;

public interface AppBootFactory {
    WalletRepository createWalletRepository();
    OrderRepository createOrderRepository();
    UserRepository createUserRepository();
    WalletService createWalletService(WalletRepository walletRepository, Exchange exchange);
    BalanceService createBalanceService(WalletRepository walletRepository, OrderRepository orderRepository);
    TransferService createTransferService(WalletRepository walletRepository, Exchange exchange);
    OrderService createOrderService(OrderRepository orderRepository, BalanceService balanceService, TransferService transferService);
    UserService createUserService(UserRepository userRepository, WalletRepository walletRepository);
    WalletView createWalletView();
    MenuViews createMenuViews();
}

