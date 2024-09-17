package com.redifoglu.ecommerce.controller;

import com.redifoglu.ecommerce.dto.OrderDTO;
import com.redifoglu.ecommerce.dto.OrderUpdateDTO;
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

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController extends BaseController {

    private OrderService orderService;
    private CustomerService customerService;

    @Autowired
    public OrderController(OrderService orderService, CustomerService customerService) {
        this.orderService = orderService;
        this.customerService = customerService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long id) {
        Order order = orderService.findById(id);
        return new ResponseEntity<>(OrderMapper.entityToDto(order), HttpStatus.OK);
    }

    @GetMapping("/my-orders/{orderId}")
    public ResponseEntity<OrderDTO> getOrderByIdForCustomer(@PathVariable Long orderId) {
        Long authenticatedUserId = getAuthenticatedUserId();

        Customer customer = customerService.findById(authenticatedUserId);

        Order order = orderService.findOrderByIdAndCustomerId(orderId, authenticatedUserId);
        OrderDTO orderDTO = OrderMapper.entityToDto(order);
        return new ResponseEntity<>(orderDTO, HttpStatus.OK);
    }

    @GetMapping("/orders")
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        List<Order> orders = orderService.findAllOrder();
        List<OrderDTO> orderDTOs = new ArrayList<>();
        for (Order order : orders) {
            OrderDTO orderDTO = OrderMapper.entityToDto(order);
            orderDTOs.add(orderDTO);
        }
        return new ResponseEntity<>(orderDTOs, HttpStatus.OK);
    }

    @GetMapping("/my-orders")
    public ResponseEntity<List<OrderDTO>> getMyOrders() {
        Long authenticatedUserId = getAuthenticatedUserId();
        Customer customer = customerService.findById(authenticatedUserId);

        List<Order> orders = orderService.findOrdersByCustomerId(authenticatedUserId);
        List<OrderDTO> orderDTOs = new ArrayList<>();
        for (Order order : orders) {
            OrderDTO orderDTO = OrderMapper.entityToDto(order);
            orderDTOs.add(orderDTO);
        }
        return new ResponseEntity<>(orderDTOs, HttpStatus.OK);
    }


    @PostMapping("/create")
    public ResponseEntity<OrderDTO> createOrder(@RequestParam PaymentMethods paymentMethod) throws Exception {
        Long authenticatedUserId = getAuthenticatedUserId(); // KİMLİĞİ DOĞRULA

        Customer customer = customerService.findById(authenticatedUserId);
        if (customer == null) {
            throw new NotFoundException("Customer not found with ID: " + authenticatedUserId);
        }

        Order order = orderService.createOrder(customer.getId(), paymentMethod);
        return new ResponseEntity<>(OrderMapper.entityToDto(order), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Void> updateOrder(@PathVariable Long id, @RequestBody OrderUpdateDTO orderUpdateDTO) {
        Order existingOrder = orderService.findById(id);


        existingOrder.setStatus(orderUpdateDTO.status());
        existingOrder.setShippingDate(orderUpdateDTO.shippingDate());

        orderService.updateOrder(id, existingOrder);
        return ResponseEntity.ok().build();
    }

//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
//        Order order = orderService.findById(id);
//
//        orderService.deleteOrder(id);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }

}
