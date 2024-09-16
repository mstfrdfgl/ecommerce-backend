package com.redifoglu.ecommerce.controller;

import com.redifoglu.ecommerce.dto.OrderDTO;
import com.redifoglu.ecommerce.entity.Order;
import com.redifoglu.ecommerce.entity.user.Customer;
import com.redifoglu.ecommerce.enums.PaymentMethods;
import com.redifoglu.ecommerce.exceptions.NotFoundException;
import com.redifoglu.ecommerce.mapper.OrderMapper;
import com.redifoglu.ecommerce.service.CustomerService;
import com.redifoglu.ecommerce.service.OrderService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController extends BaseController{

    private OrderService orderService;
    private CustomerService customerService;

    @Autowired
    public OrderController(OrderService orderService, CustomerService customerService) {
        this.orderService = orderService;
        this.customerService = customerService;
    }


    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@RequestParam PaymentMethods paymentMethod) throws Exception {
        Long authenticatedUserId = getAuthenticatedUserId(); // KİMLİĞİ DOĞRULA

        Customer customer = customerService.findById(authenticatedUserId);
        if (customer == null) {
            throw new NotFoundException("Customer not found with ID: " + authenticatedUserId);
        }

        Order order = orderService.createOrder(customer.getId(), paymentMethod);
        return new ResponseEntity<>(OrderMapper.entityToDto(order), HttpStatus.CREATED);
    }
}
