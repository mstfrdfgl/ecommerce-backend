package com.redifoglu.ecommerce.service;

import com.redifoglu.ecommerce.entity.Order;
import com.redifoglu.ecommerce.entity.Payment;
import com.redifoglu.ecommerce.enums.PaymentMethods;
import com.redifoglu.ecommerce.repository.OrderRepository;
import com.redifoglu.ecommerce.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class PaymentService {

    private PaymentRepository paymentRepository;
    private OrderRepository orderRepository;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository,OrderRepository orderRepository) {
        this.paymentRepository = paymentRepository;
        this.orderRepository=orderRepository;
    }

    public Payment findById(Long id) {
        Optional<Payment> payment = paymentRepository.findById(id);
        if (payment.isPresent()) {
            return payment.get();
        }
        throw new RuntimeException();
    }

    public Payment processPayment(Order order, PaymentMethods paymentMethod) {
        Payment payment = new Payment();
        payment.setMethod(paymentMethod);
        payment.setPaymentDate(LocalDate.now());
        payment.setOrder(order);
        payment.setCustomer(order.getCustomer());
        order.setPayment(payment);
        orderRepository.save(order);
        return paymentRepository.save(payment);
    }
}
