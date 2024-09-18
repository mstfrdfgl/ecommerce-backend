package com.redifoglu.ecommerce.Service;

import com.redifoglu.ecommerce.entity.user.Admin;
import com.redifoglu.ecommerce.entity.user.Customer;
import com.redifoglu.ecommerce.entity.user.Seller;
import com.redifoglu.ecommerce.exceptions.NotFoundException;
import com.redifoglu.ecommerce.repository.AdminRepository;
import com.redifoglu.ecommerce.repository.CustomerRepository;
import com.redifoglu.ecommerce.repository.SellerRepository;
import com.redifoglu.ecommerce.service.LoginService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LoginServiceTest {

    @Mock
    private AdminRepository adminRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private SellerRepository sellerRepository;

    @InjectMocks
    private LoginService loginService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLoadUserByUsername_AdminFound() {
        // Test verilerini ayarlama
        Admin admin = new Admin();
        admin.setUsername("adminUser");
        when(adminRepository.findAdminByUsername("adminUser")).thenReturn(Optional.of(admin));

        // Metodu çağırma
        UserDetails userDetails = loginService.loadUserByUsername("adminUser");

        // Sonuçları doğrulama
        assertEquals(admin, userDetails);
    }

    @Test
    public void testLoadUserByUsername_SellerFound() {
        // Test verilerini ayarlama
        Seller seller = new Seller();
        seller.setPhone("1234567890");
        when(sellerRepository.findSellerByPhone("1234567890")).thenReturn(Optional.of(seller));

        // Metodu çağırma
        UserDetails userDetails = loginService.loadUserByUsername("1234567890");

        // Sonuçları doğrulama
        assertEquals(seller, userDetails);
    }

    @Test
    public void testLoadUserByUsername_CustomerFound() {
        // Test verilerini ayarlama
        Customer customer = new Customer();
        customer.setEmail("customer@example.com");
        when(customerRepository.findCustomerByEmail("customer@example.com")).thenReturn(Optional.of(customer));

        // Metodu çağırma
        UserDetails userDetails = loginService.loadUserByUsername("customer@example.com");

        // Sonuçları doğrulama
        assertEquals(customer, userDetails);
    }

    @Test
    public void testLoadUserByUsername_UserNotFound() {
        // Test verilerini ayarlama
        when(adminRepository.findAdminByUsername("unknownUser")).thenReturn(Optional.empty());
        when(sellerRepository.findSellerByPhone("unknownUser")).thenReturn(Optional.empty());
        when(customerRepository.findCustomerByEmail("unknownUser")).thenReturn(Optional.empty());

        // Metodu çağırma ve beklenen hatayı doğrulama
        assertThrows(UsernameNotFoundException.class, () -> {
            loginService.loadUserByUsername("unknownUser");
        });
    }
}
