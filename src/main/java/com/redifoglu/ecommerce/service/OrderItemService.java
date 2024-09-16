package com.redifoglu.ecommerce.service;

import com.redifoglu.ecommerce.entity.OrderItem;
import com.redifoglu.ecommerce.repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderItemService {

    private OrderItemRepository orderItemRepository;

    @Autowired
    public OrderItemService(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    public OrderItem save(OrderItem orderItem){
        return orderItemRepository.save(orderItem);
    }
}
