package com.redifoglu.ecommerce.repository;

import com.redifoglu.ecommerce.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
