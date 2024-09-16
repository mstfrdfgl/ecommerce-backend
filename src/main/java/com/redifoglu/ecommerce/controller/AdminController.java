package com.redifoglu.ecommerce.controller;

import com.redifoglu.ecommerce.dto.AdminDTO;
import com.redifoglu.ecommerce.entity.user.Admin;
import com.redifoglu.ecommerce.exceptions.NotFoundException;
import com.redifoglu.ecommerce.exceptions.UnathenticatedException;
import com.redifoglu.ecommerce.mapper.AdminMapper;
import com.redifoglu.ecommerce.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController extends BaseController {

    private AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/area")
    public String adminArea() {
        return "Admin Area";
    }


    @GetMapping
    public ResponseEntity<List<AdminDTO>> findAll() throws UnathenticatedException {
        Long adminId = getAuthenticatedUserId();
        if (adminId != 5) {
            throw new UnathenticatedException("Only admin with ID 5 is allowed to delete an admin.");
        }
        List<Admin> admins = adminService.findAllAdmin();
        List<AdminDTO> adminDTOS = new ArrayList<>();
        for (Admin admin : admins) {
            AdminDTO adminDTO = AdminMapper.entityToDto(admin);
            adminDTOS.add(adminDTO);
        }
        return ResponseEntity.ok(adminDTOS);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteAdminById(@PathVariable Long id) throws UnathenticatedException {
        // ADMİNİN İDSİNİ AL
        Long adminId = getAuthenticatedUserId();

        // YALNIZCA 5 İDLİ ADMİN BU İŞLEMİ YAPABİLİR
        if (adminId != 5L) {
            throw new UnathenticatedException("Only admin with ID 5 is allowed to delete an admin.");
        }

        adminService.deleteById(id);
        return ResponseEntity.ok("Admin with ID: " + id + " has been deleted.");
    }


}
