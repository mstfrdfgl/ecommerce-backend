package com.redifoglu.ecommerce.dto;

public record OrderItemDTO(Long id,
                           Integer quantity,
                           ProductDTO product) {
}
