package com.redifoglu.ecommerce.repository;

import com.redifoglu.ecommerce.entity.Cart;
import com.redifoglu.ecommerce.entity.user.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findByCustomer(Customer customer);
}
