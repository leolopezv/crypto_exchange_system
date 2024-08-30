package org.example.model;

import org.example.repository.OrderRepository;
import org.example.service.WalletService;

import java.util.List;

public class OrderBook {
    private final OrderRepository orderRepository;
    private final WalletService walletService;

    public OrderBook(OrderRepository orderRepository, WalletService walletService) {
        this.orderRepository = orderRepository;
        this.walletService = walletService;
    }

    public void addOrder(Order order) {
        orderRepository.save(order);
    }

    public Order findMatchingOrder(Order newOrder) {
        List<Order> orders = orderRepository.findAll();

        for (Order existingOrder : orders) {
            if (existingOrder.matches(newOrder)) {
                return existingOrder;
            }
        }

        return null;
    }

    public void removeOrder(Order order) {
        orderRepository.delete(order);
    }

    public void matchOrders(Order order) {
        Order matchingOrder = findMatchingOrder(order);
        if (matchingOrder != null) {
            // Execute the transaction, update wallets, and print transaction details
            executeTransaction(order, matchingOrder);
        } else {
            addOrder(order);
        }
    }

    private void executeTransaction(Order buyOrder, Order sellOrder) {
        if (buyOrder instanceof BuyOrder && sellOrder instanceof SellOrder) {
            BuyOrder bOrder = (BuyOrder) buyOrder;
            SellOrder sOrder = (SellOrder) sellOrder;

            // Transfer the cryptocurrency from seller to buyer
            walletService.transferCrypto(sOrder.getUserId(), bOrder.getUserId(), sOrder.getCryptoSymbol(), sOrder.getAmount());

            // Transfer the fiat money from buyer to seller
            walletService.transferFiat(bOrder.getUserId(), sOrder.getUserId(), sOrder.getMinPrice().multiply(sOrder.getAmount()));

            // Print transaction details
            System.out.println("Transaction executed successfully:");
            System.out.println("Buyer: " + bOrder);
            System.out.println("Seller: " + sOrder);

            // Remove orders after execution
            removeOrder(buyOrder);
            removeOrder(sellOrder);
        } else if (buyOrder instanceof SellOrder && sellOrder instanceof BuyOrder) {
            executeTransaction(sellOrder, buyOrder);
        }
    }
}
