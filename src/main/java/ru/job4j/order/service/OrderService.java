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

import java.util.Objects;
import java.util.Optional;

@ThreadSafe
@Service
@AllArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final DishRepository dishRepository;

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public Order save(Order order) {
        order.setOrderStatus("received");
        order.setUserId(1);
        var savedOrder = orderRepository.save(order);
        OrderStatus orderStatus = new OrderStatus(savedOrder.getId(), savedOrder.getOrderStatus());
        //------------------------------------
        System.out.println("!!!!!!!!!!!!!!!!!!!!!! order_status_notification: " + orderStatus);
        kafkaTemplate.send("order_status_notification", orderStatus);
        //------------------------------------

        kafkaTemplate.send("preorder", savedOrder);
        return savedOrder;
    }

    @KafkaListener(topics = "cooked_order")
    public void receiveCookedStatus(Order order) {
        log.debug(order.toString());
        if(Objects.equals(order.getOrderStatus(), "impossible to complete")){
            orderRepository.deleteById(order.getId());
        }
        OrderStatus orderStatus = new OrderStatus(order.getId(), order.getOrderStatus());
        //------------------------------------
        System.out.println("!!!!!!!!!!!!!!!!!!!!!! order_status_notification: " + orderStatus);
        kafkaTemplate.send("order_status_notification", orderStatus);
        //------------------------------------

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
