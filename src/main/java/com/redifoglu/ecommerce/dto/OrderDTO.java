package com.redifoglu.ecommerce.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record OrderDTO(Long id,
                       LocalDate orderDate,
                       Double amount,
                       LocalDate shippingDate,
                       CustomerDTO customer,
                       PaymentDTO payment,
                       List<OrderItemDTO> orderItems) {
}
