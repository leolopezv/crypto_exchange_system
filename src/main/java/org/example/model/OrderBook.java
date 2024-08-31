package org.example.model;

import org.example.repository.iRepository.OrderRepository;
import org.example.service.BalanceService;
import org.example.service.TransferService;

import java.util.List;

public class OrderBook {
    private final OrderRepository orderRepository;
    private final BalanceService balanceService;
    private final TransferService transferService;

    public OrderBook(OrderRepository orderRepository, BalanceService balanceService, TransferService transferService) {
        this.orderRepository = orderRepository;
        this.balanceService = balanceService;
        this.transferService = transferService;
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
        if (order instanceof SellOrder && !hasSufficientCrypto((SellOrder) order)) {
            System.out.println("Error: Insufficient crypto balance to place sell order.");
            return;
        }

        Order matchingOrder = findMatchingOrder(order);
        if (matchingOrder != null) {
            executeTransaction(order, matchingOrder);
        } else {
            addOrder(order);
        }
    }

    private boolean hasSufficientCrypto(SellOrder sellOrder) {
        return balanceService.hasSufficientCrypto(sellOrder.getUserId(), sellOrder.getCryptoSymbol(), sellOrder.getAmount());
    }

    private void executeTransaction(Order buyOrder, Order sellOrder) {
        if (buyOrder instanceof BuyOrder client && sellOrder instanceof SellOrder seller) {

            transferService.transferCrypto(seller.getUserId(), client.getUserId(), seller.getCryptoSymbol(), seller.getAmount());
            transferService.transferFiat(client.getUserId(), seller.getUserId(), seller.getMinPrice().multiply(seller.getAmount()));

            System.out.println("Transaction executed successfully:");
            System.out.println("Buyer: " + client);
            System.out.println("Seller: " + seller);

            removeOrder(buyOrder);
            removeOrder(sellOrder);
        } else if (buyOrder instanceof SellOrder && sellOrder instanceof BuyOrder) {
            executeTransaction(sellOrder, buyOrder);
        }
    }
}