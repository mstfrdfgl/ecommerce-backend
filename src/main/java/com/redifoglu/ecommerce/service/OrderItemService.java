package com.redifoglu.ecommerce.service;

import com.redifoglu.ecommerce.entity.Cart;
import com.redifoglu.ecommerce.entity.Order;
import com.redifoglu.ecommerce.entity.OrderItem;
import com.redifoglu.ecommerce.entity.Product;
import com.redifoglu.ecommerce.repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    public List<Product> findTopSellingProducts(int limit) {
        List<OrderItem> orderItems = orderItemRepository.findAll();

        // Product'ları say ve en çok satanları bul
        Map<Product, Long> productCounts = orderItems.stream()
                .collect(Collectors.groupingBy(OrderItem::getProduct, Collectors.counting()));

        // En çok satan limit kadar ürünü dön
        return productCounts.entrySet().stream()
                .sorted(Map.Entry.<Product, Long>comparingByValue().reversed())
                .limit(limit)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

}
