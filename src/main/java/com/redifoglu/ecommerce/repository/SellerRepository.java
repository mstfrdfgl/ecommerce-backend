package com.redifoglu.ecommerce.repository;

import com.redifoglu.ecommerce.entity.user.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SellerRepository extends JpaRepository<Seller, Long> {

    @Query("SELECT s FROM Seller s WHERE s.phone=:phone")
    Optional<Seller> findSellerByPhone(String phone);
}
