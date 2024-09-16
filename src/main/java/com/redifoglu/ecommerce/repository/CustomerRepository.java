package com.redifoglu.ecommerce.repository;

import com.redifoglu.ecommerce.entity.user.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("SELECT c FROM Customer c WHERE c.email=:email")
    Optional<Customer> findCustomerByEmail(String email);
}
