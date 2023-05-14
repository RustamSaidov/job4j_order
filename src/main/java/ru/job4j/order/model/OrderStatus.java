package ru.job4j.order.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderStatus {
    private int id;
    private String orderStatus;
}
