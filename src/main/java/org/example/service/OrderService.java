package org.example.service;

import org.example.model.*;
import org.example.repository.iRepository.TransactionRepository;
import org.example.service.exception.FailedTransactionEx;

import java.math.BigDecimal;
import java.util.List;

public class OrderService {
    private final OrderBook orderBook;
    private final BalanceService balanceService;
    private final TransactionRepository transactionRepository;

    public OrderService(BalanceService balanceService, OrderBook orderBook, TransactionRepository transactionRepository) {
        this.balanceService = balanceService;
        this.orderBook = orderBook;
        this.transactionRepository = transactionRepository;
    }

    public void placeBuyOrder(int userId, String cryptoSymbol, BigDecimal amount, BigDecimal maxPrice) {
        BigDecimal newOrderCost = amount.multiply(maxPrice);

        if (!balanceService.hasEnoughFiat(userId, newOrderCost)) {
            throw new FailedTransactionEx("You dont have enough fiat to place buy order.");
        }
        BuyOrder order = new BuyOrder(userId, cryptoSymbol, amount, maxPrice);
        orderBook.matchOrders(order);
    }

    public void placeSellOrder(int userId, String cryptoSymbol, BigDecimal amount, BigDecimal minPrice) {
        if (!balanceService.hasEnoughCrypto(userId, cryptoSymbol, amount)) {
            throw new FailedTransactionEx("You may need more crypto to place sell order.");
        }
        SellOrder order = new SellOrder(userId, cryptoSymbol, amount, minPrice);
        orderBook.matchOrders(order);
    }

    public void showHistory(int userId) {
        List<Transaction> transactions = transactionRepository.findByUserId(userId);
        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
        } else {
            System.out.println("Your past transactions:");
            transactions.forEach(System.out::println);
        }
    }
}