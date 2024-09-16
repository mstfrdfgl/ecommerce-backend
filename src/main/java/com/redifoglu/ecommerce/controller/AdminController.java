package com.redifoglu.ecommerce.controller;

import com.redifoglu.ecommerce.dto.AdminDTO;
import com.redifoglu.ecommerce.entity.user.Admin;
import com.redifoglu.ecommerce.mapper.AdminMapper;
import com.redifoglu.ecommerce.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/area")
    public String adminArea(){
        return "Admin Area";
    }

    @GetMapping
    public List<AdminDTO> findAll() {
        List<Admin> admins = adminService.findAllAdmin();
        List<AdminDTO> adminDTOS = new ArrayList<>();
        for (Admin admin : admins) {
            AdminDTO adminDTO = AdminMapper.entityToDto(admin);
            adminDTOS.add(adminDTO);
        }
        return adminDTOS;
    }

    @PostMapping
    public AdminDTO addAdmin(@RequestBody Admin admin) {
        Admin savedAdmin = adminService.addAdmin(admin);
        AdminDTO adminDTO = AdminMapper.entityToDto(savedAdmin);
        return adminDTO;
    }
}
