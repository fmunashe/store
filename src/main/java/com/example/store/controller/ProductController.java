package com.example.store.controller;

import com.example.store.dto.ProductDTO;
import com.example.store.entity.Product;
import com.example.store.processor.api.ProductProcessor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    private final ProductProcessor productProcessor;

    public ProductController(ProductProcessor productProcessor) {
        this.productProcessor = productProcessor;
    }

    @GetMapping
    public List<ProductDTO> getAllProducts() {
        return productProcessor.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDTO getProductById(@PathVariable("id") long id) {
        return productProcessor.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDTO createProduct(@RequestBody Product product) {
        return productProcessor.create(product);
    }
}
