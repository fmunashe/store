package com.example.store.processor.impl;

import com.example.store.dto.CustomerDTO;
import com.example.store.entity.Customer;
import com.example.store.exceptions.RecordNotFoundException;
import com.example.store.mapper.CustomerMapper;
import com.example.store.service.api.CustomerService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class CustomerProcessorImplTests {
    @Mock
    private CustomerService customerService;

    @Mock
    private CustomerMapper mapper;

    private Customer customer;
    private CustomerDTO customerDTO;

    @InjectMocks
    private CustomerProcessorImpl customerProcessor;

    @BeforeEach
    void setUp() {
        customer = new Customer();
        customer.setName("John Doe");
        customer.setId(1L);

        customerDTO = new CustomerDTO();
        customerDTO.setName("John Doe");
        customerDTO.setId(1L);
    }

    @Test
    void findAll() {
        // Arrange
        List<Customer> customers = List.of(customer);
        List<CustomerDTO> customerDTOS = List.of(customerDTO);
        when(customerService.findAll()).thenReturn(customers);
        when(mapper.customersToCustomerDTOs(customers)).thenReturn(customerDTOS);

        // Act
        List<CustomerDTO> result = customerProcessor.findAll();

        // Assert
        assertNotNull(result);

        verify(customerService).findAll();
    }

    @Test
    void findById() {
        // Arrange
        when(customerService.findById(anyLong())).thenReturn(Optional.of(customer));
        when(mapper.customerToCustomerDTO(customer)).thenReturn(customerDTO);

        // Act
        CustomerDTO result = customerProcessor.findById(customer.getId());

        // Assert
        assertNotNull(result);
        assertEquals(customerDTO, result);
        verify(customerService).findById(customer.getId());
        verify(mapper).customerToCustomerDTO(customer);
    }

    @Test
    void findByIdNotFound() {
        // Arrange
        long id = 1L;
        when(customerService.findById(id)).thenReturn(Optional.empty());

        // When & Then
        RecordNotFoundException thrown = assertThrows(RecordNotFoundException.class, () -> {
            customerProcessor.findById(id);
        });

        assertEquals("Customer with id " + id + " not found", thrown.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatusCode());

        verify(customerService).findById(id);
        verifyNoInteractions(mapper); // Mapper should not be called when the record is not found
    }

    @Test
    void create() {
        // Arrange
        when(mapper.customerToCustomerDTO(customer)).thenReturn(customerDTO);
        when(customerService.create(customer)).thenReturn(customer);

        // Act
        CustomerDTO result = customerProcessor.create(customer);

        // Assert
        assertNotNull(result);

        verify(customerService).create(customer);
        verify(mapper).customerToCustomerDTO(customer);
    }

    @Test
    void findCustomersMatchingSearchQuery() {
        // Arrange
        when(customerService.findCustomersMatchingSearchQuery(anyString())).thenReturn(List.of(customer));
        when(mapper.customersToCustomerDTOs(List.of(customer))).thenReturn(List.of(customerDTO));

        // Act
        List<CustomerDTO> result = customerProcessor.findCustomersMatchingSearchQuery(customer.getName());

        // Assert
        assertNotNull(result);
        assertEquals(List.of(customerDTO), result);
        verify(customerService).findCustomersMatchingSearchQuery(customer.getName());
        verify(mapper).customersToCustomerDTOs(List.of(customer));
    }
}
