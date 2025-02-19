package com.example.store.controller;

import com.example.store.dto.OrderDTO;
import com.example.store.entity.Order;
import com.example.store.processor.api.OrderProcessor;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderProcessor orderProcessor;

    @GetMapping
    public List<OrderDTO> getAllOrders() {
        return orderProcessor.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDTO createOrder(@RequestBody Order order) {
        return orderProcessor.create(order);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderDTO getOrderById(@PathVariable("id") long id) {
        return orderProcessor.findById(id);
    }
}
