package com.redifoglu.ecommerce.controller;

import com.redifoglu.ecommerce.dto.CartDTO;
import com.redifoglu.ecommerce.entity.Cart;
import com.redifoglu.ecommerce.entity.user.Customer;
import com.redifoglu.ecommerce.mapper.CartMapper;
import com.redifoglu.ecommerce.service.CartService;
import com.redifoglu.ecommerce.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
public class CartController extends BaseController {

    private CartService cartService;
    private CustomerService customerService;

    @Autowired
    public CartController(CartService cartService, CustomerService customerService) {
        this.cartService = cartService;
        this.customerService = customerService;
    }

    @PostMapping("/{productId}")
    public ResponseEntity<CartDTO> addProductToCart(@PathVariable Long productId) {
        Long customerId = getAuthenticatedUserId();
        Customer customer = customerService.findById(customerId);

        Cart cart = cartService.addProductToCart(customer.getId(), productId);
        return ResponseEntity.ok(CartMapper.entityToDto(cart));
    }

    @PostMapping("/remove/{productId}")
    public ResponseEntity<CartDTO> removeProductFromCart(@PathVariable Long productId) {
        Long customerId = getAuthenticatedUserId();
        Customer customer = customerService.findById(customerId);

        Cart cart = cartService.removeProductFromCart(customer.getId(), productId);
        return ResponseEntity.ok(CartMapper.entityToDto(cart));

    }

}
