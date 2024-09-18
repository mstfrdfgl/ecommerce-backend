package com.redifoglu.ecommerce.service;

import com.redifoglu.ecommerce.entity.user.Admin;
import com.redifoglu.ecommerce.entity.user.Customer;
import com.redifoglu.ecommerce.entity.user.Role;
import com.redifoglu.ecommerce.entity.user.Seller;
import com.redifoglu.ecommerce.enums.Genders;
import com.redifoglu.ecommerce.repository.AdminRepository;
import com.redifoglu.ecommerce.repository.CustomerRepository;
import com.redifoglu.ecommerce.repository.RoleRepository;
import com.redifoglu.ecommerce.repository.SellerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class AuthenticationServiceTest {

    @Mock
    private AdminRepository adminRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private SellerRepository sellerRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthenticationService authenticationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registerAdmin_ShouldReturnSavedAdmin() {
        // Arrange Test için gerekli veriler hazırlanır
        String username = "adminUser";
        String password = "adminPass";
        String encodedPassword = "encodedPass";
        Role role = new Role();
        role.setAuthority("ADMIN");

        Admin admin = new Admin();
        admin.setUsername(username);
        admin.setPassword(encodedPassword);
        admin.setAuthority(role);

        // Mock davranışları tanımlanır
        when(passwordEncoder.encode(anyString())).thenReturn(encodedPassword);
        when(roleRepository.findByAuthority(anyString())).thenReturn(Optional.of(role));
        when(adminRepository.save(any(Admin.class))).thenReturn(admin);

        // Act Gerçek işlev çağrılır
        Admin result = authenticationService.registerAdmin(username, password);

        // Assert Sonuç doğrulanır
        assertEquals(username, result.getUsername());
        assertEquals(encodedPassword, result.getPassword());
        assertEquals(role, result.getAuthority());
    }

    @Test
    void registerCustomer_ShouldReturnSavedCustomer() {
        // Arrange
        String firstName = "John";
        String lastName = "Doe";
        Genders gender = Genders.MALE;
        String phone = "1234567890";
        LocalDate dateOfBirth = LocalDate.of(1990, 1, 1);
        String email = "john.doe@example.com";
        String password = "password";
        String encodedPassword = "encodedPass";
        Role role = new Role();
        role.setAuthority("CUSTOMER");

        Customer customer = new Customer();
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setGender(gender);
        customer.setPhone(phone);
        customer.setDateOfBirth(dateOfBirth);
        customer.setEmail(email);
        customer.setPassword(encodedPassword);
        customer.setAuthority(role);
        customer.setAge(Period.between(dateOfBirth, LocalDate.now()).getYears());

        when(passwordEncoder.encode(anyString())).thenReturn(encodedPassword);
        when(roleRepository.findByAuthority(anyString())).thenReturn(Optional.of(role));
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        // Act
        Customer result = authenticationService.registerCustomer(firstName, lastName, gender, phone, dateOfBirth, email, password);

        // Assert
        assertEquals(firstName, result.getFirstName());
        assertEquals(lastName, result.getLastName());
        assertEquals(gender, result.getGender());
        assertEquals(phone, result.getPhone());
        assertEquals(dateOfBirth, result.getDateOfBirth());
        assertEquals(email, result.getEmail());
        assertEquals(encodedPassword, result.getPassword());
        assertEquals(role, result.getAuthority());
        assertEquals(Period.between(dateOfBirth, LocalDate.now()).getYears(), result.getAge());
    }

    @Test
    void registerSeller_ShouldReturnSavedSeller() {
        // Arrange
        String name = "SellerName";
        String phone = "0987654321";
        String password = "sellerPass";
        String encodedPassword = "encodedPass";
        Role role = new Role();
        role.setAuthority("SELLER");

        Seller seller = new Seller();
        seller.setName(name);
        seller.setPhone(phone);
        seller.setPassword(encodedPassword);
        seller.setAuthority(role);

        when(passwordEncoder.encode(anyString())).thenReturn(encodedPassword);
        when(roleRepository.findByAuthority(anyString())).thenReturn(Optional.of(role));
        when(sellerRepository.save(any(Seller.class))).thenReturn(seller);

        // Act
        Seller result = authenticationService.registerSeller(name, phone, password);

        // Assert
        assertEquals(name, result.getName());
        assertEquals(phone, result.getPhone());
        assertEquals(encodedPassword, result.getPassword());
        assertEquals(role, result.getAuthority());
    }
}
