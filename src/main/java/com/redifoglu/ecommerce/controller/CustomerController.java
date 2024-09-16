package com.redifoglu.ecommerce.controller;

import com.redifoglu.ecommerce.dto.AddressDTO;
import com.redifoglu.ecommerce.dto.CustomerDTO;
import com.redifoglu.ecommerce.dto.OrderDTO;
import com.redifoglu.ecommerce.entity.Address;
import com.redifoglu.ecommerce.entity.Order;
import com.redifoglu.ecommerce.entity.user.Customer;
import com.redifoglu.ecommerce.mapper.AddressMapper;
import com.redifoglu.ecommerce.mapper.CustomerMapper;
import com.redifoglu.ecommerce.mapper.OrderMapper;
import com.redifoglu.ecommerce.service.AddressService;
import com.redifoglu.ecommerce.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private CustomerService customerService;
    private AddressService addressService;

    @Autowired
    public CustomerController(CustomerService customerService, AddressService addressService) {
        this.customerService = customerService;
        this.addressService = addressService;
    }

    //VERİLEN İDDEKİ CUSTOMERİ GETİR
    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> findById(@PathVariable Long id) {
        Customer customer = customerService.findById(id);
        CustomerDTO customerDTO = CustomerMapper.entityToDto(customer);
        return ResponseEntity.ok(customerDTO);
    }

    //TÜM CUSTOMERLARI GETİR
    @GetMapping
    public List<CustomerDTO> findAll() {
        List<Customer> customers = customerService.findAll();
        List<CustomerDTO> customerDTOS = new ArrayList<>();
        for (Customer customer : customers) {
            CustomerDTO customerDTO = CustomerMapper.entityToDto(customer);
            customerDTOS.add(customerDTO);
        }
        return customerDTOS;
    }

    @GetMapping("/{customerId}/orders")
    public ResponseEntity<List<OrderDTO>> findOrdersByCustomerId(@PathVariable Long customerId) {
        List<Order> orders = customerService.findOrdersByCustomerId(customerId);
        List<OrderDTO> orderDTOS = new ArrayList<>();
        for (Order order : orders) {
            OrderDTO orderDTO = OrderMapper.entityToDto(order);
            orderDTOS.add(orderDTO);
        }
        return ResponseEntity.ok(orderDTOS);
    }

    //ADDRESS EKLE
    @PostMapping("/addAddress")
    public AddressDTO addAddress(@RequestBody Address address) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();

            if (principal instanceof UserDetails) {
                String username = ((UserDetails) principal).getUsername();
                Customer customer = customerService.findCustomerByEmail(username);

                address.setCustomer(customer);
                Address savedAddress = addressService.addAddress(address);
                return AddressMapper.entityToDto(savedAddress);
            }
        }
        throw new RuntimeException();
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        customerService.deleteCustomerById(id);
    }

}
