package com.redifoglu.ecommerce.mapper;

import com.redifoglu.ecommerce.dto.CustomerDTO;
import com.redifoglu.ecommerce.entity.user.Customer;

import java.util.stream.Collectors;

public class CustomerMapper {

    public static CustomerDTO entityToDto(Customer customer) {
        if (customer == null) {
            return null;
        }
        return new CustomerDTO(
                customer.getId(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getGender(),
                customer.getAge()
//                customer.getOrders().stream()
//                        .map(OrderMapper::entityToDto)
//                        .collect(Collectors.toList())
        );
    }

    public static Customer dtoToEntity(CustomerDTO customerDTO) {
        if (customerDTO == null) {
            return null;
        }
        Customer customer = new Customer();
        customer.setId(customerDTO.id());
        customer.setFirstName(customerDTO.firstName());
        customer.setLastName(customerDTO.lastName());
        customer.setGender(customerDTO.gender());
        customer.setAge(customerDTO.age());
//        customer.setOrders(customerDTO.orders().stream()
//                .map(OrderMapper::dtoToEntity)
//                .collect(Collectors.toList()));
        return customer;
    }
}
