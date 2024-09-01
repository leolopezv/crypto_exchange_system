package org.example.repository;

import org.example.model.Order;
import org.example.repository.iRepository.OrderRepository;

import java.util.ArrayList;
import java.util.List;

public class OrderMemoryRepo implements OrderRepository {
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
    public void delete(Order order) {
        orders.remove(order);
    }
}
