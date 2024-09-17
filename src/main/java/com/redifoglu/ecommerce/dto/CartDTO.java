package com.redifoglu.ecommerce.dto;


import java.math.BigDecimal;
import java.util.List;

public record CartDTO(Long id,
                      BigDecimal itemTotal,
                      BigDecimal grandTotal,
                      CustomerDTO customer,
                      List<ProductDTO> products) {
}
