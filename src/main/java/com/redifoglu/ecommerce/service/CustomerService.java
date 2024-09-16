package com.redifoglu.ecommerce.service;

import com.redifoglu.ecommerce.entity.Order;
import com.redifoglu.ecommerce.entity.user.Customer;
import com.redifoglu.ecommerce.exceptions.NotFoundException;
import com.redifoglu.ecommerce.repository.AddressRepository;
import com.redifoglu.ecommerce.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private CustomerRepository customerRepository;
    private AddressRepository addressRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, AddressRepository addressRepository) {
        this.customerRepository = customerRepository;
        this.addressRepository = addressRepository;
    }

    public Customer findById(Long id) throws NotFoundException {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isPresent()) {
            return customer.get();
        }
        throw new NotFoundException("Customer with ID: " + id + " not found.");
    }

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }


    public Customer findCustomerByEmail(String email) throws NotFoundException {
        Optional<Customer> customer = customerRepository.findCustomerByEmail(email);
        if (customer.isPresent()) {
            return customer.get();
        }
        throw new NotFoundException("Customer with email: " + email + " not found.");
    }

    @Transactional
    public void deleteCustomerById(Long id) throws NotFoundException {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isPresent()) {
            customerRepository.deleteById(id);
        }
        throw new NotFoundException("Customer with ID: " + id + " not found.");
    }

    public List<Order> findOrdersByCustomerId(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new NotFoundException("Customer with ID: " + customerId + " not found."));
        return customer.getOrders();
    }
}
