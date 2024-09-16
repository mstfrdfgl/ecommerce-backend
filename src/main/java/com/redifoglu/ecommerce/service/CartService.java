package com.redifoglu.ecommerce.service;

import com.redifoglu.ecommerce.entity.Cart;
import com.redifoglu.ecommerce.entity.Product;
import com.redifoglu.ecommerce.entity.user.Customer;
import com.redifoglu.ecommerce.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CartService {

    private CartRepository cartRepository;
    private CustomerService customerService;
    private ProductService productService;

    @Autowired
    public CartService(CartRepository cartRepository, CustomerService customerService, ProductService productService) {
        this.cartRepository = cartRepository;
        this.customerService = customerService;
        this.productService = productService;
    }

    public Cart save(Cart cart){
        return cartRepository.save(cart);
    }

    public Cart findById(Long id) {
        Optional<Cart> cart = cartRepository.findById(id);
        if (cart.isPresent()) {
            return cart.get();
        }
        throw new RuntimeException();
    }

    public Cart findCartByCustomer(Customer customer) {
        Optional<Cart> cart = cartRepository.findByCustomer(customer);
        if (cart.isPresent()) {
            return cart.get();
        }
        throw new RuntimeException();
    }

    //MÜŞTERİNİN SEPETİ YOKSA OLUŞTUR VARSA VAR OLANI DÖN
    @Transactional
    public Cart findOrCreateCartForCustomer(Long customerId) throws Exception {
        Customer customer = customerService.findById(customerId);

        return cartRepository.findByCustomer(customer)
                .orElseGet(() -> {
                    Cart cart = new Cart();
                    cart.setCustomer(customer);
                    cart.setItemTotal(0.0);
                    cart.setGrandTotal(0.0);
                    return cartRepository.save(cart);
                });
    }

    //MÜŞTERİ İDSİNE GÖRE SEPETE ÜRÜN EKLE
    @Transactional
    public Cart addProductToCart(Long customerId, Long productId) throws Exception {
        Cart cart = findOrCreateCartForCustomer(customerId);

        Product product = productService.findProductById(productId);

        cart.getProducts().add(product);

        double itemTotal = cart.getProducts().stream()
                .mapToDouble(Product::getPrice)
                .sum();

        cart.setItemTotal(itemTotal);
        cart.setGrandTotal(itemTotal);

        return cartRepository.save(cart);
    }

    @Transactional
    public void clearCart(Cart cart) {
        cart.getProducts().clear();
        cart.setItemTotal(0.0);
        cart.setGrandTotal(0.0);
        cartRepository.save(cart);
    }

    @Transactional
    public void deleteCart(Cart cart) {
        cartRepository.save(cart);
        cartRepository.delete(cart);
    }

    @Transactional
    public Cart createNewCartForCustomer(Customer customer) {
        Cart newCart = new Cart();
        newCart.setCustomer(customer);
        newCart.setItemTotal(0.0);
        newCart.setGrandTotal(0.0);
        return cartRepository.save(newCart);
    }

}
