package com.redifoglu.ecommerce.service;

import com.redifoglu.ecommerce.entity.*;
import com.redifoglu.ecommerce.entity.user.Customer;
import com.redifoglu.ecommerce.enums.PaymentMethods;
import com.redifoglu.ecommerce.enums.Status;
import com.redifoglu.ecommerce.exceptions.NotFoundException;
import com.redifoglu.ecommerce.exceptions.UnauthorizedException;
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

    public Order findById(Long id) throws NotFoundException {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isPresent()) {
            return order.get();
        }
        throw new NotFoundException("Order not found with id: " + id);
    }

    public List<Order> findAllOrder() {
        return orderRepository.findAll();
    }

    public List<Order> findOrdersByCustomerId(Long customerId) {
        return orderRepository.findByCustomerId(customerId);
    }

    public Order findOrderByIdAndCustomerId(Long orderId, Long customerId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Order not found with ID: " + orderId));

        if (!order.getCustomer().getId().equals(customerId)) {
            throw new UnauthorizedException("You are not authorized to view this order");
        }

        return order;
    }

    @Transactional
    public Order createOrder(Long customerId, PaymentMethods paymentMethod) throws NotFoundException {
        // Get customer's cart
        Customer customer = customerService.findById(customerId);
        Cart cart = cartService.findCartByCustomer(customer);

        // Check address
        if (customer.getAddresses() == null || customer.getAddresses().isEmpty()) {
            throw new NotFoundException("Address is required to place an order");
        }

        // Create new order
        Order order = new Order();
        order.setOrderDate(LocalDate.now());
        order.setAmount(cart.getGrandTotal());
        order.setStatus(Status.PENDING);
        order.setCart(cart);
        order.setCustomer(cart.getCustomer());

        // Save order
        Order savedOrder = orderRepository.save(order);

        // Create order items
        createOrderItems(cart, savedOrder);

        // Process payment and clear cart
        paymentService.processPayment(savedOrder, paymentMethod);
        cartService.clearCart(cart);

        return savedOrder;
    }

    @Transactional
    private void createOrderItems(Cart cart, Order order) {
        List<OrderItem> orderItems = new ArrayList<>();
        for (Product product : cart.getProducts()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setQuantity(1);  // Assuming quantity is 1 for simplicity
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItems.add(orderItem);
            orderItemService.save(orderItem);
        }
        order.setOrderItems(orderItems);
        orderRepository.save(order);
    }

    @Transactional
    public Order updateOrder(Long id, Order updatedOrder) {
        Order existingOrder = findById(id);
        existingOrder.setStatus(updatedOrder.getStatus());
        existingOrder.setShippingDate(updatedOrder.getShippingDate());

        return orderRepository.save(existingOrder);
    }

    @Transactional
    public void deleteOrder(Long id) {
        Order order = findById(id);
        orderRepository.delete(order);
    }
}
