package org.example.service.iService;

import org.example.model.OrderBook;
import org.example.repository.iRepository.OrderRepository;
import org.example.repository.iRepository.TransactionRepository;
import org.example.repository.iRepository.UserRepository;
import org.example.repository.iRepository.WalletRepository;
import org.example.view.MenuView;
import org.example.view.MoneyView;

public interface AppBootFactory {
    WalletRepository createWalletRepository();
    OrderRepository createOrderRepository();
    UserRepository createUserRepository();
    TransactionRepository createTransactionRepository();
    IWalletService createWalletService(WalletRepository walletRepository);
    IBalanceService createBalanceService(WalletRepository walletRepository, OrderRepository orderRepository);
    ITransferService createTransferService(WalletRepository walletRepository);
    IOrderService createOrderService(OrderRepository orderRepository, IBalanceService balanceService, ITransferService transferService, TransactionRepository transactionRepository);
    OrderBook createOrderBook(OrderRepository orderRepository, IBalanceService balanceService, ITransferService transferService, TransactionRepository transactionRepository);
    IUserService createUserService(UserRepository userRepository, WalletRepository walletRepository);
    MoneyView createWalletView();
    MenuView createMenuViews();
}