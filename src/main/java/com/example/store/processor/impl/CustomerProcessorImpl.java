package com.example.store.processor.impl;

import com.example.store.dto.CustomerDTO;
import com.example.store.entity.Customer;
import com.example.store.exceptions.RecordNotFoundException;
import com.example.store.mapper.CustomerMapper;
import com.example.store.processor.api.CustomerProcessor;
import com.example.store.service.api.CustomerService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CustomerProcessorImpl implements CustomerProcessor {
    private final CustomerService customerService;
    private final CustomerMapper customerMapper;

    public CustomerProcessorImpl(CustomerService customerService, CustomerMapper customerMapper) {
        this.customerService = customerService;
        this.customerMapper = customerMapper;
    }

    @Override
    public CustomerDTO create(Customer customer) {
        Customer savedCustomer = customerService.create(customer);
        return customerMapper.customerToCustomerDTO(savedCustomer);
    }

    @Override
    public CustomerDTO findById(long id) {
        Optional<Customer> optionalCustomer = customerService.findById(id);
        if (optionalCustomer.isPresent()) {
            return customerMapper.customerToCustomerDTO(optionalCustomer.get());
        }
        throw new RecordNotFoundException("Customer with id " + id + " not found", HttpStatus.NOT_FOUND);
    }

    @Override
    public List<CustomerDTO> findAll() {
        List<Customer> customers = customerService.findAll();
        return customerMapper.customersToCustomerDTOs(customers);
    }

    @Override
    public List<CustomerDTO> findCustomersMatchingSearchQuery(String name) {
        List<Customer> customers = customerService.findCustomersMatchingSearchQuery(name);
        return customerMapper.customersToCustomerDTOs(customers);
    }
}
