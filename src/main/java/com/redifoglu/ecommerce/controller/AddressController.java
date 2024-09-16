package com.redifoglu.ecommerce.controller;

import com.redifoglu.ecommerce.dto.AddressDTO;
import com.redifoglu.ecommerce.entity.Address;
import com.redifoglu.ecommerce.mapper.AddressMapper;
import com.redifoglu.ecommerce.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/address")
public class AddressController extends BaseController {

    private AddressService addressService;

    @Autowired
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }


    @GetMapping
    public ResponseEntity<List<AddressDTO>> getCustomerAddresses() {
        Long authenticatedUserId = getAuthenticatedUserId(); // Giriş yapan kullanıcının kimliğini al

        List<Address> addresses = addressService.findAddressesByCustomerId(authenticatedUserId);
        List<AddressDTO> addressDTOS = new ArrayList<>();
        for (Address address : addresses) {
            AddressDTO addressDTO = AddressMapper.entityToDto(address);
            addressDTOS.add(addressDTO);
        }
        return new ResponseEntity<>(addressDTOS, HttpStatus.OK);
    }

    @PostMapping
    public AddressDTO addAddress(@RequestBody Address address) {
        AddressDTO addressDTO = AddressMapper.entityToDto(address);
        addressService.addAddress(address);
        return addressDTO;
    }

    @DeleteMapping("/{addressId}")
    public ResponseEntity<Void> deleteCustomerAddress(@PathVariable Long addressId) {
        Long authenticatedUserId = getAuthenticatedUserId(); // GİRİŞ YAPAN KULLANICININ KİMLİĞİNİ DOĞRULA

        // Adresin kullanıcıya ait olup olmadığını kontrol et
        Address address = addressService.findAddressById(addressId);
        if (address == null || !address.getCustomer().getId().equals(authenticatedUserId)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN); // ADDRESS HATALARI
        }

        addressService.deleteAddressByCustomer(authenticatedUserId, addressId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
