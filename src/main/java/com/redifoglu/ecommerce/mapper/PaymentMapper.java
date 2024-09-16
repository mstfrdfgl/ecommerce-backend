package com.redifoglu.ecommerce.mapper;

import com.redifoglu.ecommerce.dto.PaymentDTO;
import com.redifoglu.ecommerce.entity.Payment;
import com.redifoglu.ecommerce.mapper.CustomerMapper;
import com.redifoglu.ecommerce.mapper.OrderMapper;

public class PaymentMapper {

    public static PaymentDTO entityToDto(Payment payment) {
        if (payment == null) {
            return null;
        }
        return new PaymentDTO(
                payment.getId(),
                payment.getMethod(),
                payment.getPaymentDate()
//                OrderMapper.entityToDto(payment.getOrder()),

        );
    }

    public static Payment dtoToEntity(PaymentDTO paymentDTO) {
        if (paymentDTO == null) {
            return null;
        }
        Payment payment = new Payment();
        payment.setId(paymentDTO.id());
        payment.setMethod(paymentDTO.method());
        payment.setPaymentDate(paymentDTO.paymentDate());
//        payment.setOrder(OrderMapper.dtoToEntity(paymentDTO.order()));
        return payment;
    }
}
