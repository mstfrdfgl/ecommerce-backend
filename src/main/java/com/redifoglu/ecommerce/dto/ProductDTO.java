package com.redifoglu.ecommerce.dto;

public record ProductDTO(Long id,
                         String name,
                         Double price,
                         Integer stock,
                         String brand,
                         Long categoryId,
                         String categoryName,
                         SellerDTO seller) {
}