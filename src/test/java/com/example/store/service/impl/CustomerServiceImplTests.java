package com.example.store.service.impl;

import com.example.store.entity.Customer;
import com.example.store.repository.CustomerRepository;

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
class CustomerServiceImplTests {
    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @BeforeEach
    void setUp() {
        customer = new Customer();
        customer.setName("John Doe");
        customer.setId(1L);
    }

    private Customer customer;

    @Test
    void create() {
        // Arrange
        when(customerRepository.save(customer)).thenReturn(customer);

        // Act
        Customer result = customerService.create(customer);

        // Assert
        assertNotNull(result);
        assertEquals(customer, result);

        verify(customerRepository).save(customer);
    }

    @Test
    void findById() {
        // Arrange
        Long id = 1L;
        when(customerRepository.findById(id)).thenReturn(Optional.of(customer));

        // Act
        Optional<Customer> result = customerService.findById(id);

        // Assert
        assertNotNull(result);
        assertTrue(result.isPresent());
        assertEquals(customer, result.get());

        verify(customerRepository).findById(id);
    }

    @Test
    void findAll() {
        // Arrange
        List<Customer> customers = List.of(customer);
        when(customerRepository.findAll()).thenReturn(customers);
        // Act
        List<Customer> result = customerService.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(customers, result);
        verify(customerRepository).findAll();
    }

    @Test
    void findCustomersMatchingSearchQuery() {
        // Arrange
        String name = "john";
        when(customerRepository.findByNameContainingIgnoreCase(name)).thenReturn(List.of(customer));

        // Act
        List<Customer> result = customerService.findCustomersMatchingSearchQuery(name);

        // Assert
        assertNotNull(result);

        assertEquals(List.of(customer), result);

        verify(customerRepository).findByNameContainingIgnoreCase(name);
    }
}
