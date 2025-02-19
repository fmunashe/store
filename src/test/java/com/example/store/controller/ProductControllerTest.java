package com.example.store.controller;

import com.example.store.dto.ProductDTO;
import com.example.store.entity.Product;
import com.example.store.processor.api.ProductProcessor;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
@ExtendWith(SpringExtension.class)
class ProductControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    private ProductProcessor productProcessor;

    @Autowired
    private ObjectMapper objectMapper;

    private Product product;
    private ProductDTO productDTO;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setId(1L);
        product.setDescription("Vanilla Creams");

        productDTO = new ProductDTO();
        productDTO.setId(1L);
        productDTO.setDescription("Vanilla Creams");
    }

    @Test
    void getAllProducts() throws Exception {
        when(productProcessor.findAll()).thenReturn(List.of(productDTO));
        mockMvc.perform(get("/product"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$..id").value(1))
                .andExpect(jsonPath("$..description").value("Vanilla Creams"));
    }

    @Test
    void getProductById() throws Exception {
        when(productProcessor.findById(anyLong())).thenReturn(productDTO);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/product/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(productDTO));
    }

    @Test
    void createProduct() throws Exception {
        // Mocking the service behavior
        when(productProcessor.create(any())).thenReturn(productDTO);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(productDTO))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(productDTO.getDescription()));
    }
}
