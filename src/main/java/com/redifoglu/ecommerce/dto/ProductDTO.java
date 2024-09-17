package com.redifoglu.ecommerce.dto;

import java.math.BigDecimal;

public record ProductDTO(Long id,
                         String name,
                         BigDecimal price,
                         Integer stock,
                         String brand,
                         Long categoryId,
                         String categoryName,
                         SellerDTO seller) {
}