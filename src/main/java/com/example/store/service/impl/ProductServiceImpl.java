package com.example.store.service.impl;

import com.example.store.entity.Product;
import com.example.store.repository.ProductRepository;
import com.example.store.service.api.ProductService;

import jakarta.transaction.Transactional;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository repository;

    public ProductServiceImpl(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public Product create(Product product) {
        return repository.save(product);
    }

    @Override
    @Cacheable(value = "product", key = "#id")
    public Optional<Product> findById(long id) {
        return repository.findById(id);
    }

    @Override
    @Cacheable(value = "products")
    public List<Product> findAll() {
        return repository.findAll();
    }
}
