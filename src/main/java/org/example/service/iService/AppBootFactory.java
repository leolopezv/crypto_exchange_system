package org.example.service.iService;

import org.example.repository.iRepository.OrderRepository;
import org.example.repository.iRepository.UserRepository;
import org.example.repository.iRepository.WalletRepository;
import org.example.service.*;
import org.example.view.MenuViews;
import org.example.view.MoneyView;

public interface AppBootFactory {
    WalletRepository createWalletRepository();
    OrderRepository createOrderRepository();
    UserRepository createUserRepository();
    WalletService createWalletService(WalletRepository walletRepository);
    BalanceService createBalanceService(WalletRepository walletRepository, OrderRepository orderRepository);
    TransferService createTransferService(WalletRepository walletRepository);
    OrderService createOrderService(OrderRepository orderRepository, BalanceService balanceService, TransferService transferService);
    UserService createUserService(UserRepository userRepository, WalletRepository walletRepository);
    MoneyView createWalletView();
    MenuViews createMenuViews();
}

