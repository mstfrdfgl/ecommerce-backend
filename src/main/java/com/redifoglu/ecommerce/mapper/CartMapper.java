package com.redifoglu.ecommerce.mapper;

import com.redifoglu.ecommerce.dto.CartDTO;
import com.redifoglu.ecommerce.entity.Cart;

import java.util.stream.Collectors;

public class CartMapper {

    public static CartDTO entityToDto(Cart cart) {
        if (cart == null) {
            return null;
        }
        return new CartDTO(
                cart.getId(),
                cart.getItemTotal(),
                cart.getGrandTotal(),
                CustomerMapper.entityToDto(cart.getCustomer()),
                cart.getProducts().stream()
                        .map(ProductMapper::entityToDto)
                        .collect(Collectors.toList())
        );
    }

    public static Cart dtoToEntity(CartDTO cartDTO) {
        if (cartDTO == null) {
            return null;
        }
        Cart cart = new Cart();
        cart.setId(cartDTO.id());
        cart.setItemTotal(cartDTO.itemTotal());
        cart.setGrandTotal(cartDTO.grandTotal());
        cart.setCustomer(CustomerMapper.dtoToEntity(cartDTO.customer()));
        cart.setProducts(cartDTO.products().stream()
                .map(ProductMapper::dtoToEntity)
                .collect(Collectors.toList()));
        return cart;
    }
}
