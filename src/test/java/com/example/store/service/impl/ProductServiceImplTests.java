package com.example.store.service.impl;

import com.example.store.entity.Product;
import com.example.store.repository.ProductRepository;

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
class ProductServiceImplTests {
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setDescription("Test Product");
        product.setId(1L);
    }

    @Test
    void create() {
        // Arrange
        when(productRepository.save(product)).thenReturn(product);

        // Act
        Product result = productService.create(product);

        // Assert
        assertNotNull(result);
        assertEquals(product, result);

        verify(productRepository).save(product);
    }

    @Test
    void findById() {
        // Arrange
        Long id = 1L;
        when(productRepository.findById(id)).thenReturn(Optional.of(product));

        // Act
        Optional<Product> result = productService.findById(id);

        // Assert
        assertNotNull(result);
        assertTrue(result.isPresent());
        assertEquals(product, result.get());

        verify(productRepository).findById(id);
    }

    @Test
    void findAll() {
        // Arrange
        List<Product> products = List.of(product);
        when(productRepository.findAll()).thenReturn(products);
        // Act
        List<Product> result = productService.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(products, result);
        verify(productRepository).findAll();
    }
}
