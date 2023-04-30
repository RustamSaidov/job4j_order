package ru.job4j.order.service;

import ru.job4j.order.model.OrderDTO;

import java.util.Optional;

public interface OrderService {

    Optional<OrderDTO> findById(int id);

    /*
    Order save(Order order);

    boolean deleteById(int id);

    boolean update(int id, Order order);

    List<Order> findAll();

    List<Order> findByName(String name);

     */
}
