package com.redifoglu.ecommerce.dto;

import com.redifoglu.ecommerce.enums.Ratings;

public record ReviewDTO(Long id,
                        String description,
                        Ratings rating,
                        ProductDTO product,
                        CustomerDTO customer) {
}
