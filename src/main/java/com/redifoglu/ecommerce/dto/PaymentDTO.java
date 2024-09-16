package com.redifoglu.ecommerce.dto;

import com.redifoglu.ecommerce.enums.PaymentMethods;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record PaymentDTO(Long id,
                         PaymentMethods method,
                         LocalDate paymentDate) {
}
