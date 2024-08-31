package org.example.model;

import org.example.repository.iRepository.OrderRepository;
import org.example.repository.iRepository.TransactionRepository;
import org.example.service.BalanceService;
import org.example.service.TransferService;

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

    public Order findMatchingOrder(Order newOrder) {
        List<Order> orders = orderRepository.findAll();
        for (Order existingOrder : orders) if (existingOrder.matches(newOrder)) return existingOrder;
        return null;
    }

    public void matchOrders(Order order) {
        if (order instanceof SellOrder && !hasSufficientCrypto((SellOrder) order)) {
            System.out.println("Insufficient crypto balance to place sell order.");
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
        if (buyOrder instanceof BuyOrder buyer && sellOrder instanceof SellOrder seller) {

            transferService.transferCrypto(seller.getUserId(), buyer.getUserId(), seller.getCryptoSymbol(), seller.getAmount());
            transferService.transferFiat(buyer.getUserId(), seller.getUserId(), seller.getMinPrice().multiply(seller.getAmount()));

            System.out.println("Transaction executed successfully:");
            System.out.println("Buyer: " + buyer);
            System.out.println("Seller: " + seller);

            // Create and save transaction for buyer
            Transaction buyTransaction = new Transaction(
                    buyer.getUserId(),
                    seller.getCryptoSymbol(),
                    seller.getAmount(),
                    seller.getMinPrice(),
                    "buy",
                    LocalDateTime.now()
            );
            transactionRepository.save(buyTransaction);

            // Create and save transaction for seller
            Transaction sellTransaction = new Transaction(
                    seller.getUserId(),
                    seller.getCryptoSymbol(),
                    seller.getAmount(),
                    seller.getMinPrice(),
                    "sell",
                    LocalDateTime.now()
            );
            transactionRepository.save(sellTransaction);

            removeOrder(buyOrder);
            removeOrder(sellOrder);
        } else if (buyOrder instanceof SellOrder && sellOrder instanceof BuyOrder) {
            executeTransaction(sellOrder, buyOrder);
        }
    }

    private void addOrder(Order order) {
        orderRepository.save(order);
    }

    private void removeOrder(Order order) {
        orderRepository.delete(order);
    }
}