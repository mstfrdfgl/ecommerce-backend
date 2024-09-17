package com.redifoglu.ecommerce.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record OrderDTO(Long id,
                       LocalDate orderDate,
                       BigDecimal amount,
                       LocalDate shippingDate,
                       CustomerDTO customer,
                       PaymentDTO payment,
                       List<OrderItemDTO> orderItems) {
}
