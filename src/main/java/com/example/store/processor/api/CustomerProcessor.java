package com.example.store.processor.api;

import com.example.store.commons.AppProcessor;
import com.example.store.dto.CustomerDTO;
import com.example.store.entity.Customer;

import java.util.List;

public interface CustomerProcessor extends AppProcessor<CustomerDTO, Customer> {
    List<CustomerDTO> findCustomersMatchingSearchQuery(String name);
}
