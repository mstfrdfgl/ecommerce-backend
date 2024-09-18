package com.redifoglu.ecommerce.service;

import com.redifoglu.ecommerce.entity.Cart;
import com.redifoglu.ecommerce.entity.Product;
import com.redifoglu.ecommerce.entity.user.Customer;
import com.redifoglu.ecommerce.exceptions.InsufficientStockException;
import com.redifoglu.ecommerce.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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

    public Cart save(Cart cart) {
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
    public Cart findOrCreateCartForCustomer(Long customerId) {
        Customer customer = customerService.findById(customerId);

        return cartRepository.findByCustomer(customer)
                .orElseGet(() -> {
                    Cart cart = new Cart();
                    cart.setCustomer(customer);
                    cart.setItemTotal(BigDecimal.valueOf(0.0));
                    cart.setGrandTotal(BigDecimal.valueOf(0.0));
                    return cartRepository.save(cart);
                });
    }

    //MÜŞTERİ İDSİNE GÖRE SEPETE ÜRÜN EKLE
    @Transactional
    public Cart addProductToCart(Long customerId, Long productId) throws InsufficientStockException {
        Cart cart = findOrCreateCartForCustomer(customerId);

        Product product = productService.findProductById(productId);
        //STOK KONTROLÜ
        if (product.getStock() < 1) {
            throw new InsufficientStockException("This products is out of stock " + product.getName());
        }

        cart.getProducts().add(product);

        BigDecimal itemTotal = cart.getProducts().stream()
                .map(Product::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        cart.setItemTotal(itemTotal);
        cart.setGrandTotal(itemTotal);

        return cartRepository.save(cart);
    }

    @Transactional
    public void clearCart(Cart cart) {
        cart.getProducts().clear();
        cart.setItemTotal(BigDecimal.valueOf(0.0));
        cart.setGrandTotal(BigDecimal.valueOf(0.0));
        cartRepository.save(cart);
    }


}
