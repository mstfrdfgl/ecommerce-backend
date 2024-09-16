package com.redifoglu.ecommerce.dto;


import java.util.List;

public record CartDTO(Long id,
                      Double itemTotal,
                      Double grandTotal,
                      CustomerDTO customer,
                      List<ProductDTO> products) {
}
