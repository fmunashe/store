package com.example.store.service.impl;

import com.example.store.entity.Order;
import com.example.store.repository.OrderRepository;
import com.example.store.service.api.OrderService;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository repository;

    public OrderServiceImpl(OrderRepository repository) {
        this.repository = repository;
    }

    @Override
    public Order create(Order order) {
        return repository.save(order);
    }

    @Override
    @Cacheable(value = "order", key = "#id")
    public Optional<Order> findById(long id) {
        return repository.findById(id);
    }

    @Override
    @Cacheable(value = "orders")
    public List<Order> findAll() {
        return repository.findAll();
    }
}
