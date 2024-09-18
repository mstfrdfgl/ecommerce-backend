package com.redifoglu.ecommerce.Controller;

import com.redifoglu.ecommerce.controller.AuthController;
import com.redifoglu.ecommerce.dto.AdminDTO;
import com.redifoglu.ecommerce.dto.CustomerDTO;
import com.redifoglu.ecommerce.dto.SellerDTO;
import com.redifoglu.ecommerce.entity.user.Admin;
import com.redifoglu.ecommerce.entity.user.Customer;
import com.redifoglu.ecommerce.entity.user.Seller;
import com.redifoglu.ecommerce.enums.Genders;
import com.redifoglu.ecommerce.mapper.AdminMapper;
import com.redifoglu.ecommerce.mapper.CustomerMapper;
import com.redifoglu.ecommerce.mapper.SellerMapper;
import com.redifoglu.ecommerce.service.AuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class AuthControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    @Test
    public void testRegisterAdmin() throws Exception {
        Admin admin = new Admin();
        admin.setUsername("adminUser");
        admin.setPassword("adminPass");

        Admin savedAdmin = new Admin();
        savedAdmin.setUsername("adminUser");
        savedAdmin.setPassword("encodedAdminPass");

        AdminDTO adminDTO = AdminMapper.entityToDto(savedAdmin);

        when(authenticationService.registerAdmin(any(String.class), any(String.class))).thenReturn(savedAdmin);

        mockMvc.perform(post("/auth/register/admin")
                        .contentType("application/json")
                        .content("{\"username\":\"adminUser\",\"password\":\"adminPass\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username", is("adminUser")));
    }

    @Test
    public void testRegisterCustomer() throws Exception {
        Customer customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setGender(Genders.MALE);
        customer.setPhone("1234567890");
        customer.setDateOfBirth(LocalDate.of(1990, 1, 1));
        customer.setEmail("john.doe@example.com");
        customer.setPassword("customerPass");

        Customer savedCustomer = new Customer();
        savedCustomer.setFirstName("John");
        savedCustomer.setLastName("Doe");
        savedCustomer.setGender(Genders.MALE);
        savedCustomer.setPhone("1234567890");
        savedCustomer.setDateOfBirth(LocalDate.of(1990, 1, 1));
        savedCustomer.setEmail("john.doe@example.com");
        savedCustomer.setPassword("encodedCustomerPass");

        CustomerDTO customerDTO = CustomerMapper.entityToDto(savedCustomer);

        when(authenticationService.registerCustomer(
                any(String.class), any(String.class), any(Genders.class), any(String.class),
                any(LocalDate.class), any(String.class), any(String.class)))
                .thenReturn(savedCustomer);

        mockMvc.perform(post("/auth/register/customer")
                        .contentType("application/json")
                        .content("{\"firstName\":\"John\",\"lastName\":\"Doe\",\"gender\":\"MALE\",\"phone\":\"1234567890\",\"dateOfBirth\":\"1990-01-01\",\"email\":\"john.doe@example.com\",\"password\":\"customerPass\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", is("John")));
    }

    @Test
    public void testRegisterSeller() throws Exception {
        Seller seller = new Seller();
        seller.setName("SellerName");
        seller.setPhone("9876543210");
        seller.setPassword("sellerPass");

        Seller savedSeller = new Seller();
        savedSeller.setName("SellerName");
        savedSeller.setPhone("9876543210");
        savedSeller.setPassword("encodedSellerPass");

        SellerDTO sellerDTO = SellerMapper.entityToDto(savedSeller);

        when(authenticationService.registerSeller(any(String.class), any(String.class), any(String.class)))
                .thenReturn(savedSeller);

        mockMvc.perform(post("/auth/register/seller")
                        .contentType("application/json")
                        .content("{\"name\":\"SellerName\",\"phone\":\"9876543210\",\"password\":\"sellerPass\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("SellerName")));
    }
}
