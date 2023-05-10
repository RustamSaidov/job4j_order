package ru.job4j.order.service;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.job4j.order.model.Order;
import ru.job4j.order.model.OrderDTO;
import ru.job4j.order.repository.DishRepository;
import ru.job4j.order.repository.OrderRepository;

import java.util.Optional;

@ThreadSafe
@Service
@AllArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final DishRepository dishRepository;

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public Order save(Order order) {
        var savedOrder = orderRepository.save(order);
        kafkaTemplate.send("job4j_orders", savedOrder);
        return savedOrder;
    }

    public Optional<OrderDTO> findById(int id) {
        var optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isEmpty()) {
            return Optional.empty();
        }
        var dish = dishRepository.findById(optionalOrder.get().getDishId());
        return Optional.of(new OrderDTO(optionalOrder.get(), dish.get()));
    }
}
