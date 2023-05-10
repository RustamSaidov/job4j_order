package ru.job4j.order.controller;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.order.model.Order;
import ru.job4j.order.model.OrderDTO;
import ru.job4j.order.service.OrderService;

import java.net.MalformedURLException;
import java.util.Optional;

@RestController
@RequestMapping("/order")
@Data
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> findById(@PathVariable int id) {
        var orderDTO = this.orderService.findById(id);
        return new ResponseEntity<OrderDTO>(
                orderDTO.orElse(new OrderDTO()),
                orderDTO.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND
        );
    }

    @PostMapping("/")
    public ResponseEntity<Order> save(@RequestBody Order order) throws MalformedURLException {
        Optional<Order> registeredOrder = Optional.ofNullable(orderService.save(order));
        return ResponseEntity.of(registeredOrder);
    }
}