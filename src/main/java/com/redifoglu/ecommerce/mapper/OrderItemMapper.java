package com.redifoglu.ecommerce.mapper;

import com.redifoglu.ecommerce.dto.OrderItemDTO;
import com.redifoglu.ecommerce.entity.OrderItem;

public class OrderItemMapper {

    public static OrderItemDTO entityToDto(OrderItem orderItem) {
        if (orderItem == null) {
            return null;
        }
        return new OrderItemDTO(
                orderItem.getId(),
                orderItem.getQuantity(),
                ProductMapper.entityToDto(orderItem.getProduct())
        );
    }

    public static OrderItem dtoToEntity(OrderItemDTO orderItemDTO) {
        if (orderItemDTO == null) {
            return null;
        }
        OrderItem orderItem = new OrderItem();
        orderItem.setId(orderItemDTO.id());
        orderItem.setQuantity(orderItemDTO.quantity());
        orderItem.setProduct(ProductMapper.dtoToEntity(orderItemDTO.product()));
        return orderItem;
    }
}
