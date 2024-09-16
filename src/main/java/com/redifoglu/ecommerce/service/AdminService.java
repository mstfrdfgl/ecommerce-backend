package com.redifoglu.ecommerce.service;

import com.redifoglu.ecommerce.entity.user.Admin;
import com.redifoglu.ecommerce.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    private AdminRepository adminRepository;

    @Autowired
    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public List<Admin> findAllAdmin() {
//        List<Admin> admins = adminRepository.findAll();
//        List<AdminDTO> adminDTOS=new ArrayList<>();
//        for(Admin admin:admins){
//            AdminDTO adminDTO=AdminMapper.entityToDto(admin);
//            adminDTOS.add(adminDTO);
//        }
//        return adminDTOS;
        return adminRepository.findAll();
    }

    public Admin addAdmin(Admin admin) {
        Admin savedAdmin=adminRepository.save(admin);
        return savedAdmin;
    }

}
