package com.example.store.processor.impl;

import com.example.store.dto.OrderDTO;
import com.example.store.entity.Order;
import com.example.store.exceptions.RecordNotFoundException;
import com.example.store.mapper.OrderMapper;
import com.example.store.service.api.OrderService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@SpringBootTest
class OrderProcessorImplTests {
    @Mock
    private OrderService orderService;

    @Mock
    private OrderMapper mapper;

    private Order order;
    private OrderDTO orderDTO;

    @InjectMocks
    private OrderProcessorImpl orderProcessor;

    @BeforeEach
    void setUp() {
        order = new Order();
        order.setDescription("Test Order");
        order.setId(1L);

        orderDTO = new OrderDTO();
        order.setDescription("Test Order");
        orderDTO.setId(1L);
    }

    @Test
    void findAll() {
        // Arrange
        List<Order> orders = List.of(order);
        List<OrderDTO> orderDTOS = List.of(orderDTO);
        when(orderService.findAll()).thenReturn(orders);
        when(mapper.ordersToOrderDTOs(orders)).thenReturn(orderDTOS);

        // Act
        List<OrderDTO> result = orderProcessor.findAll();

        // Assert
        assertNotNull(result);

        verify(orderService).findAll();
    }

    @Test
    void findById() {
        // Arrange
        when(orderService.findById(anyLong())).thenReturn(Optional.of(order));
        when(mapper.orderToOrderDTO(order)).thenReturn(orderDTO);

        // Act
        OrderDTO result = orderProcessor.findById(order.getId());

        // Assert
        assertNotNull(result);
        assertEquals(orderDTO, result);
        verify(orderService).findById(order.getId());
        verify(mapper).orderToOrderDTO(order);
    }

    @Test
    void findByIdNotFound() {
        // Arrange
        long id = 1L;
        when(orderService.findById(id)).thenReturn(Optional.empty());

        // When & Then
        RecordNotFoundException thrown = assertThrows(RecordNotFoundException.class, () -> {
            orderProcessor.findById(id);
        });

        assertEquals("Order with id " + id + " not found", thrown.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatusCode());

        verify(orderService).findById(id);
        verifyNoInteractions(mapper); // Mapper should not be called when the record is not found
    }

    @Test
    void create() {
        // Arrange
        when(mapper.orderToOrderDTO(order)).thenReturn(orderDTO);
        when(orderService.create(order)).thenReturn(order);

        // Act
        OrderDTO result = orderProcessor.create(order);

        // Assert
        assertNotNull(result);

        verify(orderService).create(order);
        verify(mapper).orderToOrderDTO(order);
    }
}
