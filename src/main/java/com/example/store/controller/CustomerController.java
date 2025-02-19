package com.example.store.controller;

import com.example.store.dto.CustomerDTO;
import com.example.store.entity.Customer;
import com.example.store.processor.api.CustomerProcessor;

import jakarta.websocket.server.PathParam;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerProcessor customerProcessor;

    public CustomerController(CustomerProcessor customerProcessor) {
        this.customerProcessor = customerProcessor;
    }

    @GetMapping
    public List<CustomerDTO> getAllCustomers() {
        return customerProcessor.findAll();
    }

    @GetMapping("/search")
    public List<CustomerDTO> getAllCustomersMatchingSearchQuery(@PathParam("name") String name) {
        return customerProcessor.findCustomersMatchingSearchQuery(name);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDTO createCustomer(@RequestBody Customer customer) {
        return customerProcessor.create(customer);
    }
}
