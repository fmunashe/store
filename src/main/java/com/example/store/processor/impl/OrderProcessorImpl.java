package com.example.store.processor.impl;

import com.example.store.dto.OrderDTO;
import com.example.store.entity.Order;
import com.example.store.exceptions.RecordNotFoundException;
import com.example.store.mapper.OrderMapper;
import com.example.store.processor.api.OrderProcessor;
import com.example.store.service.api.OrderService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class OrderProcessorImpl implements OrderProcessor {
    private final OrderService orderService;
    private final OrderMapper orderMapper;

    public OrderProcessorImpl(OrderService orderService, OrderMapper orderMapper) {
        this.orderService = orderService;
        this.orderMapper = orderMapper;
    }

    @Override
    public OrderDTO create(Order order) {
        Order savedOrder = orderService.create(order);
        return orderMapper.orderToOrderDTO(savedOrder);
    }

    @Override
    public OrderDTO findById(long id) {
        Optional<Order> optionalOrder = orderService.findById(id);
        if (optionalOrder.isPresent()) {
            return orderMapper.orderToOrderDTO(optionalOrder.get());
        }
        throw new RecordNotFoundException("Order with id " + id + " not found", HttpStatus.NOT_FOUND);
    }

    @Override
    public List<OrderDTO> findAll() {
        List<Order> allOrders = orderService.findAll();
        return orderMapper.ordersToOrderDTOs(allOrders);
    }
}
