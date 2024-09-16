package com.redifoglu.ecommerce.controller;

import com.redifoglu.ecommerce.dto.CartDTO;
import com.redifoglu.ecommerce.entity.Cart;
import com.redifoglu.ecommerce.entity.user.Customer;
import com.redifoglu.ecommerce.mapper.CartMapper;
import com.redifoglu.ecommerce.service.CartService;
import com.redifoglu.ecommerce.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
public class CartController {

    private CartService cartService;
    private CustomerService customerService;

    @Autowired
    public CartController(CartService cartService, CustomerService customerService) {
        this.cartService = cartService;
        this.customerService = customerService;
    }

    @PostMapping("/{productId}")
    public CartDTO addProductToCart(@PathVariable Long productId) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();

            if (principal instanceof UserDetails) {
                String username = ((UserDetails) principal).getUsername();
                Customer customer = customerService.findCustomerByEmail(username);

                Cart cart = cartService.addProductToCart(customer.getId(), productId);
                return CartMapper.entityToDto(cart);
            }
        }
        throw new RuntimeException();
    }
}
