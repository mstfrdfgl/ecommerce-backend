package com.redifoglu.ecommerce.mapper;

import com.redifoglu.ecommerce.dto.OrderDTO;
import com.redifoglu.ecommerce.entity.Order;
import com.redifoglu.ecommerce.mapper.CustomerMapper;

import java.util.stream.Collectors;

public class OrderMapper {

    public static OrderDTO entityToDto(Order order) {
        if (order == null) {
            return null;
        }
        return new OrderDTO(
                order.getId(),
                order.getOrderDate(),
                order.getAmount(),
                order.getShippingDate(),
                CustomerMapper.entityToDto(order.getCustomer()),
                PaymentMapper.entityToDto(order.getPayment()),
                order.getOrderItems().stream()
                        .map(OrderItemMapper::entityToDto)
                        .collect(Collectors.toList())
        );
    }

    public static Order dtoToEntity(OrderDTO orderDTO) {
        if (orderDTO == null) {
            return null;
        }
        Order order = new Order();
        order.setId(orderDTO.id());
        order.setOrderDate(orderDTO.orderDate());
        order.setAmount(orderDTO.amount());
        order.setShippingDate(orderDTO.shippingDate());
        order.setCustomer(CustomerMapper.dtoToEntity(orderDTO.customer()));
        order.setPayment(PaymentMapper.dtoToEntity(orderDTO.payment()));
        order.setOrderItems(orderDTO.orderItems().stream()
                .map(OrderItemMapper::dtoToEntity)
                .collect(Collectors.toList()));
        return order;
    }
}
