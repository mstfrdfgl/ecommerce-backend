package com.redifoglu.ecommerce.mapper;

import com.redifoglu.ecommerce.dto.AdminDTO;
import com.redifoglu.ecommerce.entity.user.Admin;

public class AdminMapper {

    public static AdminDTO entityToDto(Admin admin) {
        if (admin == null) {
            return null;
        }
        return new AdminDTO(
                admin.getId(),
                admin.getUsername()
        );
    }

    public static Admin dtoToEntity(AdminDTO adminDTO) {
        if (adminDTO == null) {
            return null;
        }
        Admin admin = new Admin();
        admin.setId(adminDTO.id());
        admin.setUsername(adminDTO.username());
        return admin;
    }
}
