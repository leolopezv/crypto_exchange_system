package org.example.repository.iRepository;

import org.example.model.Order;

import java.util.List;

public interface OrderRepository {
    void save(Order order);
    List<Order> findAll();
    void delete(Order order);
}
