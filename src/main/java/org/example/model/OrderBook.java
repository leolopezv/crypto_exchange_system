package org.example.model;

import org.example.repository.iRepository.OrderRepository;
import org.example.repository.iRepository.TransactionRepository;
import org.example.service.WalletService;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderBook {
    private final OrderRepository orderRepository;
    private final WalletService walletService;
    private final TransactionRepository transactionRepository;

    public OrderBook(OrderRepository orderRepository, WalletService walletService, TransactionRepository transactionRepository) {
        this.orderRepository = orderRepository;
        this.walletService = walletService;
        this.transactionRepository = transactionRepository;
    }

    public void matchOrders(Order order) {
        Order match = findMatch(order);
        if (match != null) {
            executeDeal(order, match);
        } else {
            orderRepository.save(order);
        }
    }

    private Order findMatch(Order newOrder) {
        return orderRepository.findAll().stream()
                .filter(existingOrder -> existingOrder.matches(newOrder))
                .findFirst()
                .orElse(null);
    }

    private void executeDeal(Order buyOrder, Order sellOrder) {
        if (buyOrder instanceof BuyOrder && sellOrder instanceof SellOrder) {
            closeDeal((BuyOrder) buyOrder, (SellOrder) sellOrder);
        } else if (buyOrder instanceof SellOrder && sellOrder instanceof BuyOrder) {
            closeDeal((BuyOrder) sellOrder, (SellOrder) buyOrder);
        }
    }

    private void closeDeal(BuyOrder buyer, SellOrder seller) {
        walletService.transferCrypto(seller.getUserId(), buyer.getUserId(), seller.getCryptoSymbol(), seller.getAmount());
        walletService.transferFiat(buyer.getUserId(), seller.getUserId(), seller.getMinPrice().multiply(seller.getAmount()));

        System.out.println("The deal has been sealed:");
        System.out.println("Buyer: " + buyer);
        System.out.println("Seller: " + seller);

        registerDeal(buyer.getUserId(), seller.getCryptoSymbol(), seller.getAmount(), seller.getMinPrice(), TransactionType.BUY);
        registerDeal(seller.getUserId(), seller.getCryptoSymbol(), seller.getAmount(), seller.getMinPrice(), TransactionType.SELL);

        orderRepository.delete(buyer);
        orderRepository.delete(seller);
    }

    private void registerDeal(int userId, String cryptoSymbol, BigDecimal amount, BigDecimal price, TransactionType type) {
        Transaction transaction = new Transaction(
                userId,
                cryptoSymbol,
                amount,
                price,
                type,
                LocalDateTime.now()
        );
        transactionRepository.save(transaction);
    }
}