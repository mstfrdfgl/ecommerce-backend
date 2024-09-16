package com.redifoglu.ecommerce.dto;

import com.redifoglu.ecommerce.entity.Order;
import com.redifoglu.ecommerce.enums.Genders;

import java.util.List;

public record CustomerDTO(Long id,
                          String firstName,
                          String lastName,
                          Genders gender,
                          Integer age) {
}
