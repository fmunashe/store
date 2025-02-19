package com.example.store.mapper;

import com.example.store.dto.ProductDTO;
import com.example.store.entity.Order;
import com.example.store.entity.Product;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(target = "orderIds", expression = "java(getOrderIds(product))")
    ProductDTO productToProductDTO(Product product);

    List<ProductDTO> productsToProductDTOs(List<Product> products);

    // Custom method to extract order IDs for a given product
    default List<Long> getOrderIds(Product product) {
        // Assuming the Product entity has a list of orders associated with it
        return product.getOrders().stream()
                .map(Order::getId) // Extracting the order IDs
                .collect(Collectors.toList());
    }
}
