package ru.job4j.order.controller;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.job4j.order.model.OrderDTO;
import ru.job4j.order.service.OrderService;

@RestController
@RequestMapping("/order")
@Data
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/order/{id}")
    public ResponseEntity<OrderDTO> findById(@PathVariable int id) {
        var orderDTO = this.orderService.findById(id);
        return new ResponseEntity<OrderDTO>(
                orderDTO.orElse(new OrderDTO()),
                orderDTO.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND
        );
    }
}