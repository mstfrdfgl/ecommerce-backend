package com.redifoglu.ecommerce.mapper;

import com.redifoglu.ecommerce.dto.ReviewDTO;
import com.redifoglu.ecommerce.entity.Review;
import com.redifoglu.ecommerce.mapper.CustomerMapper;
import com.redifoglu.ecommerce.mapper.ProductMapper;

public class ReviewMapper {

    public static ReviewDTO entityToDto(Review review) {
        if (review == null) {
            return null;
        }
        return new ReviewDTO(
                review.getId(),
                review.getDescription(),
                review.getRating(),
                ProductMapper.entityToDto(review.getProduct()),
                CustomerMapper.entityToDto(review.getCustomer())
        );
    }

    public static Review dtoToEntity(ReviewDTO reviewDTO) {
        if (reviewDTO == null) {
            return null;
        }
        Review review = new Review();
        review.setId(reviewDTO.id());
        review.setDescription(reviewDTO.description());
        review.setRating(reviewDTO.rating());
        review.setProduct(ProductMapper.dtoToEntity(reviewDTO.product()));
        review.setCustomer(CustomerMapper.dtoToEntity(reviewDTO.customer()));
        return review;
    }
}
