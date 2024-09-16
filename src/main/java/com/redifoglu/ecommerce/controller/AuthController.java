package com.redifoglu.ecommerce.controller;

import com.redifoglu.ecommerce.dto.AdminDTO;
import com.redifoglu.ecommerce.dto.CustomerDTO;
import com.redifoglu.ecommerce.dto.SellerDTO;
import com.redifoglu.ecommerce.entity.user.Admin;
import com.redifoglu.ecommerce.entity.user.Customer;
import com.redifoglu.ecommerce.entity.user.Seller;
import com.redifoglu.ecommerce.mapper.AdminMapper;
import com.redifoglu.ecommerce.mapper.CustomerMapper;
import com.redifoglu.ecommerce.mapper.SellerMapper;
import com.redifoglu.ecommerce.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private AuthenticationService authenticationService;

    @Autowired
    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register/admin")
    public AdminDTO register(@RequestBody Admin admin) {
        Admin savedAdmin = authenticationService.registerAdmin(
                admin.getUsername(),
                admin.getPassword());
        return AdminMapper.entityToDto(savedAdmin);
    }

    @PostMapping("/register/customer")
    public CustomerDTO register(@RequestBody Customer customer) {
        Customer savedCustomer = authenticationService.registerCustomer(
                customer.getFirstName(),
                customer.getLastName(),
                customer.getGender(),
                customer.getPhone(),
                customer.getDateOfBirth(),
                customer.getUsername(),
                customer.getPassword());
        return CustomerMapper.entityToDto(savedCustomer);
    }

    @PostMapping("/register/seller")
    public SellerDTO register(@RequestBody Seller seller) {
        Seller savedSeller = authenticationService.registerSeller(
                seller.getName(),
                seller.getUsername(),
                seller.getPassword());
        return SellerMapper.entityToDto(savedSeller);
    }
}
