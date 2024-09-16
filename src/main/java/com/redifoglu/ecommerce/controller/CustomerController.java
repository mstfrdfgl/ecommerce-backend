package com.redifoglu.ecommerce.controller;

import com.redifoglu.ecommerce.dto.AddressDTO;
import com.redifoglu.ecommerce.dto.CustomerDTO;
import com.redifoglu.ecommerce.dto.OrderDTO;
import com.redifoglu.ecommerce.entity.Address;
import com.redifoglu.ecommerce.entity.Order;
import com.redifoglu.ecommerce.entity.user.Customer;
import com.redifoglu.ecommerce.exceptions.NotFoundException;
import com.redifoglu.ecommerce.mapper.AddressMapper;
import com.redifoglu.ecommerce.mapper.CustomerMapper;
import com.redifoglu.ecommerce.mapper.OrderMapper;
import com.redifoglu.ecommerce.service.AddressService;
import com.redifoglu.ecommerce.service.CustomerService;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController extends BaseController{

    private CustomerService customerService;
    private AddressService addressService;

    @Autowired
    public CustomerController(CustomerService customerService, AddressService addressService) {
        this.customerService = customerService;
        this.addressService = addressService;
    }

    //VERİLEN İDDEKİ CUSTOMERİ GETİR
    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> findById(@PathVariable Long id){
        Customer customer = customerService.findById(id);
        CustomerDTO customerDTO = CustomerMapper.entityToDto(customer);
        return new ResponseEntity<>(customerDTO, HttpStatus.OK);
    }

    //TÜM CUSTOMERLARI GETİR
    @GetMapping("/customers")
    public ResponseEntity<List<CustomerDTO>> findAll() {
        List<Customer> customers = customerService.findAll();
        List<CustomerDTO> customerDTOS = new ArrayList<>();
        for (Customer customer : customers) {
            customerDTOS.add(CustomerMapper.entityToDto(customer));
        }
        return new ResponseEntity<>(customerDTOS, HttpStatus.OK);
    }

    @GetMapping("/orders/{customerId}")
    public ResponseEntity<List<OrderDTO>> findOrdersByCustomerId(@PathVariable Long customerId) {
        List<Order> orders = customerService.findOrdersByCustomerId(customerId);
        List<OrderDTO> orderDTOS = new ArrayList<>();
        for (Order order : orders) {
            OrderDTO orderDTO = OrderMapper.entityToDto(order);
            orderDTOS.add(orderDTO);
        }
        return ResponseEntity.ok(orderDTOS);
    }

    @GetMapping("/orders")
    public ResponseEntity<List<OrderDTO>> findOrdersForAuthenticatedCustomer() {
        Long authenticatedUserId = getAuthenticatedUserId();
        try {
            Customer customer = customerService.findById(authenticatedUserId);

            List<Order> orders = customerService.findOrdersByCustomerId(customer.getId());
            if (orders.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            List<OrderDTO> orderDTOS = new ArrayList<>();
            for (Order order : orders) {
                OrderDTO orderDTO = OrderMapper.entityToDto(order);
                orderDTOS.add(orderDTO);
            }
            return ResponseEntity.ok(orderDTOS);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //ADDRESS EKLE
    @PostMapping("/address/addAddress")
    public ResponseEntity<AddressDTO> addAddress(@RequestBody Address address) {
        Long authenticatedUserId = getAuthenticatedUserId(); // KİMLİĞİ DOĞRULA

        // Kullanıcıya ait müşteri bilgilerini al
        Customer customer = customerService.findById(authenticatedUserId);
        if (customer == null) {
            throw new NotFoundException("Customer not found with ID: " + authenticatedUserId);
        }

        address.setCustomer(customer);
        Address savedAddress = addressService.addAddress(address);
        return new ResponseEntity<>(AddressMapper.entityToDto(savedAddress), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        Customer customer = customerService.findById(id);
        customerService.deleteCustomerById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
