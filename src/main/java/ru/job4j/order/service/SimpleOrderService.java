package ru.job4j.order.service;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.order.model.Order;
import ru.job4j.order.repository.OrderRepository;

import java.util.List;
import java.util.Optional;

@ThreadSafe
@Service
@AllArgsConstructor
public class SimpleOrderService implements OrderService{

    private final OrderRepository orderRepository;

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public boolean deleteById(int id) {
        return orderRepository.deleteById(id);
    }

    @Override
    public boolean update(Order order) {
        boolean result = false;
        var current = orderRepository.findById(order.getId());
        if (current.isEmpty()) {
            return result;
        }
        orderRepository.save(order);
        result = true;
        return result;
    }

    @Override
    public Optional<Order> findById(int id) {
        return orderRepository.findById(id);
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }
}
