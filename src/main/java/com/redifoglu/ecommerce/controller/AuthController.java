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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<AdminDTO> register(@RequestBody Admin admin) {
        try {
            Admin savedAdmin = authenticationService.registerAdmin(
                    admin.getUsername(),
                    admin.getPassword());
            AdminDTO adminDTO = AdminMapper.entityToDto(savedAdmin);

            return ResponseEntity.status(HttpStatus.CREATED).body(adminDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping("/register/customer")
    public ResponseEntity<CustomerDTO> registerCustomer(@RequestBody Customer customer) {
        try {
            Customer savedCustomer = authenticationService.registerCustomer(
                    customer.getFirstName(),
                    customer.getLastName(),
                    customer.getGender(),
                    customer.getPhone(),
                    customer.getDateOfBirth(),
                    customer.getUsername(),
                    customer.getPassword());
            CustomerDTO customerDTO = CustomerMapper.entityToDto(savedCustomer);
            return ResponseEntity.status(HttpStatus.CREATED).body(customerDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping("/register/seller")
    public ResponseEntity<SellerDTO> registerSeller(@RequestBody Seller seller) {
        try {
            Seller savedSeller = authenticationService.registerSeller(
                    seller.getName(),
                    seller.getUsername(),
                    seller.getPassword());
            SellerDTO sellerDTO = SellerMapper.entityToDto(savedSeller);
            return ResponseEntity.status(HttpStatus.CREATED).body(sellerDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
