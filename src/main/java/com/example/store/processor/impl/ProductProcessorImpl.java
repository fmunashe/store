package com.example.store.processor.impl;

import com.example.store.dto.ProductDTO;
import com.example.store.entity.Product;
import com.example.store.exceptions.RecordNotFoundException;
import com.example.store.mapper.ProductMapper;
import com.example.store.processor.api.ProductProcessor;
import com.example.store.service.api.ProductService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ProductProcessorImpl implements ProductProcessor {
    private final ProductService productService;
    private final ProductMapper productMapper;

    public ProductProcessorImpl(ProductService productService, ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
    }

    @Override
    public ProductDTO create(Product product) {
        Product savedProduct = productService.create(product);
        return productMapper.productToProductDTO(savedProduct);
    }

    @Override
    public ProductDTO findById(long id) {
        Optional<Product> optionalProduct = productService.findById(id);
        if (optionalProduct.isPresent()) {
            return productMapper.productToProductDTO(optionalProduct.get());
        }
        throw new RecordNotFoundException("Product with id " + id + " not found", HttpStatus.NOT_FOUND);
    }

    @Override
    public List<ProductDTO> findAll() {
        List<Product> products = productService.findAll();
        return productMapper.productsToProductDTOs(products);
    }
}
