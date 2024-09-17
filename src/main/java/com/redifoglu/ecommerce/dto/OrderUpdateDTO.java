package com.redifoglu.ecommerce.dto;

import com.redifoglu.ecommerce.enums.Status;

import java.time.LocalDate;

public record OrderUpdateDTO(Status status,
                             LocalDate shippingDate) {
}
