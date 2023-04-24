package ru.job4j.order.service;

import ru.job4j.order.model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    Order save(Order order);

    boolean deleteById(int id);

    boolean update(Order order);

    Optional<Order> findById(int id);

    List<Order> findAll();
}
