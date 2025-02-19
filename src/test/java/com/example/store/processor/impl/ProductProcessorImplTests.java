package com.example.store.processor.impl;

import com.example.store.dto.ProductDTO;
import com.example.store.entity.Product;
import com.example.store.exceptions.RecordNotFoundException;
import com.example.store.mapper.ProductMapper;
import com.example.store.service.api.ProductService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@SpringBootTest
class ProductProcessorImplTests {

    @Mock
    private ProductService productService;

    @Mock
    private ProductMapper mapper;

    private Product product;
    private ProductDTO productDTO;

    @InjectMocks
    private ProductProcessorImpl productProcessor;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setDescription("Test Product");
        product.setId(1L);

        productDTO = new ProductDTO();
        product.setDescription("Test Product");
        productDTO.setId(1L);
    }

    @Test
    void findAll() {
        // Arrange
        List<Product> products = List.of(product);
        List<ProductDTO> productDTOS = List.of(productDTO);
        when(productService.findAll()).thenReturn(products);
        when(mapper.productsToProductDTOs(products)).thenReturn(productDTOS);

        // Act
        List<ProductDTO> result = productProcessor.findAll();

        // Assert
        assertNotNull(result);

        verify(productService).findAll();
    }

    @Test
    void findById() {
        // Arrange
        when(productService.findById(anyLong())).thenReturn(Optional.of(product));
        when(mapper.productToProductDTO(product)).thenReturn(productDTO);

        // Act
        ProductDTO result = productProcessor.findById(product.getId());

        // Assert
        assertNotNull(result);
        assertEquals(productDTO, result);
        verify(productService).findById(product.getId());
        verify(mapper).productToProductDTO(product);
    }

    @Test
    void findByIdNotFound() {
        // Arrange
        long id = 1L;
        when(productService.findById(id)).thenReturn(Optional.empty());

        // When & Then
        RecordNotFoundException thrown = assertThrows(RecordNotFoundException.class, () -> {
            productProcessor.findById(id);
        });

        assertEquals("Product with id " + id + " not found", thrown.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatusCode());

        verify(productService).findById(id);
        verifyNoInteractions(mapper); // Mapper should not be called when the record is not found
    }

    @Test
    void create() {
        // Arrange
        when(mapper.productToProductDTO(product)).thenReturn(productDTO);
        when(productService.create(product)).thenReturn(product);

        // Act
        ProductDTO result = productProcessor.create(product);

        // Assert
        assertNotNull(result);

        verify(productService).create(product);
        verify(mapper).productToProductDTO(product);
    }
}
