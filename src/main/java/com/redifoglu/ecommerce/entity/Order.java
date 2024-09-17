package com.redifoglu.ecommerce.entity;

import com.redifoglu.ecommerce.entity.user.Customer;
import com.redifoglu.ecommerce.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "order", schema = "public")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull(message = "Order date cannot be null")
    @Column(name = "order_date")
    private LocalDate orderDate;

    @NotNull(message = "Amount cannot be null")
    @Min(value = 0, message = "Amount must be a positive number")
    @Column(name = "amount")
    private BigDecimal amount;

    @NotNull(message = "Status cannot be null")
    @Enumerated(value = EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @FutureOrPresent(message = "Shipping date must be in the future or present")
    @Column(name = "shipping_date")
    private LocalDate shippingDate;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToOne(mappedBy = "order")
    private Payment payment;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems=new ArrayList<>();

    @Transient
    public boolean isValid() {
        if (shippingDate != null && orderDate != null) {
            return shippingDate.isAfter(orderDate) || shippingDate.isEqual(orderDate);
        }
        return true;
    }
}
