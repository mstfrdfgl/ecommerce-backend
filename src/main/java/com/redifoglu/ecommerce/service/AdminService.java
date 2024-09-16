package com.redifoglu.ecommerce.service;

import com.redifoglu.ecommerce.entity.user.Admin;
import com.redifoglu.ecommerce.exceptions.NotFoundException;
import com.redifoglu.ecommerce.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    private AdminRepository adminRepository;

    @Autowired
    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public List<Admin> findAllAdmin() {
        return adminRepository.findAll();
    }

    public Admin findById(Long id) throws NotFoundException {
        Optional<Admin> admin = adminRepository.findById(id);
        if (admin.isPresent()) {
            return admin.get();
        }
        throw new NotFoundException("Admin not found with ID: " + id);
    }

    @Transactional
    public Admin save(Admin admin) {
        Admin savedAdmin = adminRepository.save(admin);
        return savedAdmin;
    }

    @Transactional
    public void deleteById(Long id) throws NotFoundException {
        if (!adminRepository.existsById(id)) {
            throw new NotFoundException("Admin with ID: " + id + " does not exist.");
        }
        adminRepository.deleteById(id);
    }

}
