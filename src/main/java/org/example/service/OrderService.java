package org.example.service;

import org.example.model.*;
import org.example.repository.iRepository.TransactionRepository;

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
            System.out.println("Not enough fiat to place buy order.");
            return;
        }
        BuyOrder order = new BuyOrder(userId, cryptoSymbol, amount, maxPrice);
        System.out.println("Buy order placed.");
        orderBook.matchOrders(order);
    }

    public void placeSellOrder(int userId, String cryptoSymbol, BigDecimal amount, BigDecimal minPrice) {
        if (!balanceService.hasEnoughCrypto(userId, cryptoSymbol, amount)) {
            System.out.println("Not enough crypto to place sell order.");
            return;
        }
        SellOrder order = new SellOrder(userId, cryptoSymbol, amount, minPrice);
        System.out.println("Sell order placed.");
        orderBook.matchOrders(order);
    }

    public void showHistory(int userId) {
        List<Transaction> transactions = transactionRepository.findByUserId(userId);
        for (Transaction transaction : transactions) {
            System.out.println(transaction);
        }
    }
}