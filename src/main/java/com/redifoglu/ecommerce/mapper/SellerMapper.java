package com.redifoglu.ecommerce.mapper;

import com.redifoglu.ecommerce.dto.SellerDTO;
import com.redifoglu.ecommerce.entity.user.Seller;

import java.util.stream.Collectors;

public class SellerMapper {

    public static SellerDTO entityToDto(Seller seller) {
        if (seller == null) {
            return null;
        }
        return new SellerDTO(
                seller.getId(),
                seller.getName(),
                seller.getPhone()
//                seller.getProducts().stream()
//                        .map(ProductMapper::entityToDto)
//                        .collect(Collectors.toList())
        );
    }

    public static Seller dtoToEntity(SellerDTO sellerDTO) {
        if (sellerDTO == null) {
            return null;
        }
        Seller seller = new Seller();
        seller.setId(sellerDTO.id());
        seller.setName(sellerDTO.name());
        seller.setPhone(sellerDTO.phone());
//        seller.setProducts(sellerDTO.products().stream()
//                .map(ProductMapper::dtoToEntity)
//                .collect(Collectors.toList()));
        return seller;
    }
}
