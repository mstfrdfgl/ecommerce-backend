package com.redifoglu.ecommerce.service;

import com.redifoglu.ecommerce.entity.*;
import com.redifoglu.ecommerce.entity.user.Customer;
import com.redifoglu.ecommerce.enums.PaymentMethods;
import com.redifoglu.ecommerce.enums.Status;
import com.redifoglu.ecommerce.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private OrderRepository orderRepository;
    private CartService cartService;
    private CustomerService customerService;
    private PaymentService paymentService;
    private OrderItemService orderItemService;

    @Autowired
    public OrderService(OrderRepository orderRepository, CartService cartService, CustomerService customerService, PaymentService paymentService, OrderItemService orderItemService) {
        this.orderRepository = orderRepository;
        this.cartService = cartService;
        this.customerService = customerService;
        this.paymentService = paymentService;
        this.orderItemService = orderItemService;
    }

    public Order findById(Long id) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isPresent()) {
            return order.get();
        }
        throw new RuntimeException();
    }

    public List<Order> findAllOrder(){
        return orderRepository.findAll();
    }

    @Transactional
    public Order createOrder(Long customerId, PaymentMethods paymentMethod) throws Exception {
        //MÜŞTERİNİN SEPETİNİ BUL
        Customer customer = customerService.findById(customerId);
        Cart cart = cartService.findCartByCustomer(customer);

        //ADRES KONTROLÜ
        if (customer.getAddresses() == null || customer.getAddresses().isEmpty()) {
            throw new Exception("Sipariş vermek için adres gerekli");
        }

        //SİPARİŞ OLUŞTUR
        Order order = new Order();
        order.setOrderDate(LocalDate.now());
        order.setAmount(cart.getGrandTotal());
        order.setStatus(Status.PENDING);
        order.setCart(cart);
        order.setCustomer(cart.getCustomer());
        Order savedOrder = orderRepository.save(order);
        List<OrderItem> orderItems = new ArrayList<>();
        //ORDER İTEMLARI OLUŞTUR
        for (Product product : cart.getProducts()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setQuantity(1);
            orderItem.setOrder(savedOrder);
            orderItem.setProduct(product);
            orderItems.add(orderItem);
            orderItemService.save(orderItem);
        }
        order.setOrderItems(orderItems);
        orderRepository.save(order);

        //ÖDEMEYİ YAP, PAYMENT OLUŞTUR VE SEPETİ TEMİZLE
        paymentService.processPayment(savedOrder, paymentMethod);
        cartService.clearCart(cart);
//        cartService.deleteCart(cart);

//        Cart newCart = cartService.createNewCartForCustomer(customer);
//        cartService.save(newCart);

        return savedOrder;
    }
}
