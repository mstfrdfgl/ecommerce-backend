package com.redifoglu.ecommerce.service;

import com.redifoglu.ecommerce.dto.AddressDTO;
import com.redifoglu.ecommerce.entity.Address;
import com.redifoglu.ecommerce.entity.user.Customer;
import com.redifoglu.ecommerce.exceptions.NotFoundException;
import com.redifoglu.ecommerce.mapper.AddressMapper;
import com.redifoglu.ecommerce.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AddressService {

    private AddressRepository addressRepository;
    private CustomerService customerService;

    @Autowired
    public AddressService(AddressRepository addressRepository, CustomerService customerService) {
        this.addressRepository = addressRepository;
        this.customerService = customerService;
    }

    public Address findAddressById(Long addressId) throws NotFoundException {
        Optional<Address> address = addressRepository.findById(addressId);
        if (address.isPresent()) {
            return address.get();
        }
        throw new NotFoundException("Customer not found with ID: " + addressId);
    }

    public List<Address> findAddressesByCustomerId(Long customerId) throws NotFoundException {
        Customer customer = customerService.findById(customerId);
        List<Address> addresses = customer.getAddresses();
        return addresses;
    }

    @Transactional
    public void deleteAddressByCustomer(Long customerId, Long addressId) throws NotFoundException {
        Customer customer = customerService.findById(customerId);

        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new NotFoundException("Address with ID: " + addressId + " not found."));

        if (!address.getCustomer().getId().equals(customerId)) {
            throw new NotFoundException("Address does not belong to the customer.");
        }

        customer.getAddresses().remove(address);
        addressRepository.delete(address);
    }

    public List<Address> findAllAddress() {
        return addressRepository.findAll();
    }

    @Transactional
    public Address addAddress(Address address) {
        return addressRepository.save(address);
    }

    @Transactional
    public void deleteAddressById(Long id) throws NotFoundException {
        if (!addressRepository.existsById(id)) {
            throw new NotFoundException("Address with ID: " + id + " does not exist.");
        }
        addressRepository.deleteById(id);
    }

}
