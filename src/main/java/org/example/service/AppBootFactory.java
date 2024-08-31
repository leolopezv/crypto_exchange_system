package org.example.service;

import org.example.model.Exchange;
import org.example.repository.*;
import org.example.service.*;
import org.example.view.MenuViews;
import org.example.view.WalletView;

public interface AppBootFactory {
    WalletRepository createWalletRepository();
    OrderRepository createOrderRepository();
    UserRepository createUserRepository();
    Exchange createExchange();
    WalletService createWalletService(WalletRepository walletRepository, Exchange exchange);
    BalanceService createBalanceService(WalletRepository walletRepository);
    TransferService createTransferService(WalletRepository walletRepository, Exchange exchange);
    OrderService createOrderService(OrderRepository orderRepository, BalanceService balanceService, TransferService transferService);
    UserService createUserService(UserRepository userRepository, WalletRepository walletRepository);
    WalletView createWalletView();
    MenuViews createMenuViews();
}
