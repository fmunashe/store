package com.example.store.service.api;

import com.example.store.commons.AppService;
import com.example.store.entity.Customer;

import java.util.List;

public interface CustomerService extends AppService<Customer> {
    List<Customer> findCustomersMatchingSearchQuery(String name);
}
