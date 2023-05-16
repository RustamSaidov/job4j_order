package ru.job4j.order.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.jcip.annotations.ThreadSafe;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.job4j.order.model.Order;
import ru.job4j.order.model.OrderDTO;
import ru.job4j.order.model.OrderStatus;
import ru.job4j.order.repository.DishRepository;
import ru.job4j.order.repository.OrderRepository;

import java.util.Optional;

@ThreadSafe
@Service
@AllArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final DishRepository dishRepository;

    private final KafkaTemplate<Order, Object> kafkaTemplateForOrder;
    private final KafkaTemplate<OrderStatus, Object> kafkaTemplateForOrderStatus;

    public Order save(Order order) {
        order.setOrderStatus("received");
        order.setUserId(1);
        var savedOrder = orderRepository.save(order);
        OrderStatus orderStatus = new OrderStatus(savedOrder.getId(), savedOrder.getOrderStatus());
        kafkaTemplateForOrderStatus.send("notification", orderStatus);
        kafkaTemplateForOrder.send("preorder", savedOrder);
        return savedOrder;
    }

    @KafkaListener(topics = "cooked_order")
    public void receiveCookedStatus(Order order) {
        log.debug(order.toString());
        OrderStatus orderStatus = new OrderStatus(order.getId(), order.getOrderStatus());
        orderRepository.save(order);
        kafkaTemplateForOrderStatus.send("notification", orderStatus);

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
