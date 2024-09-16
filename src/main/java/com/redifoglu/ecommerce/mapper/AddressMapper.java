package com.redifoglu.ecommerce.mapper;

import com.redifoglu.ecommerce.dto.AddressDTO;
import com.redifoglu.ecommerce.entity.Address;

public class AddressMapper {

    public static AddressDTO entityToDto(Address address) {
        if (address == null) {
            return null;
        }
        return new AddressDTO(
                address.getId(),
                address.getCity(),
                address.getState()
        );
    }

    public static Address dtoToEntity(AddressDTO addressDTO) {
        if (addressDTO == null) {
            return null;
        }
        Address address = new Address();
        address.setId(addressDTO.id());
        address.setCity(addressDTO.city());
        address.setState(addressDTO.state());
        return address;
    }
}
