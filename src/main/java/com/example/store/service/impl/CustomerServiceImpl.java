package com.example.store.service.impl;

import com.example.store.entity.Customer;
import com.example.store.repository.CustomerRepository;
import com.example.store.service.api.CustomerService;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository repository;

    public CustomerServiceImpl(CustomerRepository repository) {
        this.repository = repository;
    }

    @Override
    public Customer create(Customer customer) {
        return repository.save(customer);
    }

    @Override
    @Cacheable(value = "customer", key = "#id")
    public Optional<Customer> findById(long id) {
        return repository.findById(id);
    }

    @Override
    public List<Customer> findAll() {
        return repository.findAll();
    }

    @Override
    @Cacheable(value = "customers", key = "#name")
    public List<Customer> findCustomersMatchingSearchQuery(String name) {
        return repository.findByNameContainingIgnoreCase(name);
    }
}
