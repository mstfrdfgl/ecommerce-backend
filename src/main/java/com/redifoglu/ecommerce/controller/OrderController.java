package com.redifoglu.ecommerce.controller;

import com.redifoglu.ecommerce.dto.OrderDTO;
import com.redifoglu.ecommerce.entity.Order;
import com.redifoglu.ecommerce.entity.user.Customer;
import com.redifoglu.ecommerce.enums.PaymentMethods;
import com.redifoglu.ecommerce.mapper.OrderMapper;
import com.redifoglu.ecommerce.service.CustomerService;
import com.redifoglu.ecommerce.service.OrderService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {

    private OrderService orderService;
    private CustomerService customerService;

    @Autowired
    public OrderController(OrderService orderService, CustomerService customerService) {
        this.orderService = orderService;
        this.customerService = customerService;
    }


    @PostMapping
    public OrderDTO createOrder(@RequestParam PaymentMethods paymentMethod) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();

            if (principal instanceof UserDetails) {
                String username = ((UserDetails) principal).getUsername();
                Customer customer = customerService.findCustomerByEmail(username);

                Order order=orderService.createOrder(customer.getId(),paymentMethod);
                return OrderMapper.entityToDto(order);
                
            }
        }
        throw new RuntimeException();
    }
}
