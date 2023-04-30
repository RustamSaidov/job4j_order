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
    /*
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public Order save(@RequestBody Order order) {
        return orderService.save(order);
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestParam int id, @RequestBody Order order) {
        boolean status = orderService.update(id, order);
        return ResponseEntity
                .status(status ? HttpStatus.OK : HttpStatus.NOT_FOUND)
                .build();
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestParam int id) {
        boolean status = orderService.deleteById(id);
        return ResponseEntity
                .status(status ? HttpStatus.OK : HttpStatus.NOT_FOUND)
                .build();
    }

    @GetMapping("/getById")
    public Optional<Order> getById(@RequestParam int id) {
        return orderService.findById(id);
    }

    @GetMapping("/getByName")
    public List<Order> getByName(@RequestParam String name) {
        return orderService.findByName(name);
    }

    @GetMapping("/getAll")
    public List<Order> getAll() {
        return orderService.findAll();
    }
     */
}