package ru.job4j.order.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.order.model.Order;

import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Integer> {
    List<Order> findAll();

    boolean deleteById(int id);

    List<Order> findByName(String name);

}
