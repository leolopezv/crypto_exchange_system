package org.example.repository;

import org.example.model.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderRepositoryInMemory implements OrderRepository {
    private List<Order> orders = new ArrayList<>();
    private int currentId = 1;

    @Override
    public void save(Order order) {
        order.setOrderId(currentId++);
        orders.add(order);
    }

    @Override
    public List<Order> findAll() {
        return new ArrayList<>(orders);
    }

    @Override
    public Order findById(int orderId) {
        return orders.stream().filter(order -> order.getOrderId() == orderId).findFirst().orElse(null);
    }

    @Override
    public void delete(Order order) {
        orders.remove(order);
    }
}
