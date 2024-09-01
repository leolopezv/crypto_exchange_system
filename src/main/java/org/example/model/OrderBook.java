package org.example.model;

import org.example.repository.iRepository.OrderRepository;
import org.example.repository.iRepository.TransactionRepository;
import org.example.service.BalanceService;
import org.example.service.TransferService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OrderBook {
    private final OrderRepository orderRepository;
    private final BalanceService balanceService;
    private final TransferService transferService;
    private final TransactionRepository transactionRepository;

    public OrderBook(OrderRepository orderRepository, BalanceService balanceService, TransferService transferService, TransactionRepository transactionRepository) {
        this.orderRepository = orderRepository;
        this.balanceService = balanceService;
        this.transferService = transferService;
        this.transactionRepository = transactionRepository;
    }

    public void matchOrders(Order order) {
        if (order instanceof SellOrder sellOrder && !balanceService.hasSufficientCrypto(sellOrder.getUserId(), sellOrder.getCryptoSymbol(), sellOrder.getAmount())) {
            System.out.println("Insufficient crypto balance to place sell order.");
            return;
        }

        Order matchingOrder = findMatchingOrder(order);
        if (matchingOrder != null) {
            executeTransaction(order, matchingOrder);
        } else {
            orderRepository.save(order);
        }
    }

    private Order findMatchingOrder(Order newOrder) {
        return orderRepository.findAll().stream()
                .filter(existingOrder -> existingOrder.matches(newOrder))
                .findFirst()
                .orElse(null);
    }

    private void executeTransaction(Order buyOrder, Order sellOrder) {
        if (buyOrder instanceof BuyOrder && sellOrder instanceof SellOrder) {
            performTransaction((BuyOrder) buyOrder, (SellOrder) sellOrder);
        } else if (buyOrder instanceof SellOrder && sellOrder instanceof BuyOrder) {
            performTransaction((BuyOrder) sellOrder, (SellOrder) buyOrder);
        }
    }

    private void performTransaction(BuyOrder buyer, SellOrder seller) {
        transferService.transferCrypto(seller.getUserId(), buyer.getUserId(), seller.getCryptoSymbol(), seller.getAmount());
        transferService.transferFiat(buyer.getUserId(), seller.getUserId(), seller.getMinPrice().multiply(seller.getAmount()));

        System.out.println("Transaction executed successfully:");
        System.out.println("Buyer: " + buyer);
        System.out.println("Seller: " + seller);

        saveTransaction(buyer.getUserId(), seller.getCryptoSymbol(), seller.getAmount(), seller.getMinPrice(), "buy");
        saveTransaction(seller.getUserId(), seller.getCryptoSymbol(), seller.getAmount(), seller.getMinPrice(), "sell");

        orderRepository.delete(buyer);
        orderRepository.delete(seller);
    }

    private void saveTransaction(int userId, String cryptoSymbol, BigDecimal amount, BigDecimal price, String type) {
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