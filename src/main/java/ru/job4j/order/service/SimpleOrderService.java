package ru.job4j.order.service;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.order.model.OrderDTO;
import ru.job4j.order.repository.DishRepository;
import ru.job4j.order.repository.OrderRepository;

import java.util.Optional;

@ThreadSafe
@Service
@AllArgsConstructor
public class SimpleOrderService implements OrderService {

    private final OrderRepository orderRepository;
    private final DishRepository dishRepository;

    @Override
    public Optional<OrderDTO> findById(int id) {
        var optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isEmpty()) {
            return Optional.empty();
        }
        var dish = dishRepository.findById(optionalOrder.get().getDishId());
        return Optional.of(new OrderDTO(optionalOrder.get(), dish.get()));
    }

    /*
    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public boolean deleteById(int id) {
        return orderRepository.deleteById(id);
    }

    @Override
    public boolean update(int id, Order order) {
        boolean result = false;
        var current = orderRepository.findById(id);
        if (current.isEmpty()) {
            return result;
        }
        orderRepository.save(order);
        result = true;
        return result;
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public List<Order> findByName(String name) {
        return orderRepository.findByName(name);
    }

     */
}
