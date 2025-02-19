package com.example.store.service.impl;

import com.example.store.entity.Order;
import com.example.store.repository.OrderRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class OrderServiceImplTests {
    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    private Order order;

    @BeforeEach
    void setUp() {
        order = new Order();
        order.setDescription("Test Order");
        order.setId(1L);
    }

    @Test
    void create() {
        // Arrange
        when(orderRepository.save(order)).thenReturn(order);

        // Act
        Order result = orderService.create(order);

        // Assert
        assertNotNull(result);
        assertEquals(order, result);

        verify(orderRepository).save(order);
    }

    @Test
    void findById() {
        // Arrange
        Long id = 1L;
        when(orderRepository.findById(id)).thenReturn(Optional.of(order));

        // Act
        Optional<Order> result = orderService.findById(id);

        // Assert
        assertNotNull(result);
        assertTrue(result.isPresent());
        assertEquals(order, result.get());

        verify(orderRepository).findById(id);
    }

    @Test
    void findAll() {
        // Arrange
        List<Order> orders = List.of(order);
        when(orderRepository.findAll()).thenReturn(orders);
        // Act
        List<Order> result = orderService.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(orders, result);
        verify(orderRepository).findAll();
    }
}
