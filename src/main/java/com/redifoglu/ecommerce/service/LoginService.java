package com.redifoglu.ecommerce.service;

import com.redifoglu.ecommerce.entity.user.Admin;
import com.redifoglu.ecommerce.entity.user.Customer;
import com.redifoglu.ecommerce.entity.user.Seller;
import com.redifoglu.ecommerce.repository.AdminRepository;
import com.redifoglu.ecommerce.repository.CustomerRepository;
import com.redifoglu.ecommerce.repository.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class LoginService implements UserDetailsService {

    private AdminRepository adminRepository;
    private CustomerRepository customerRepository;
    private SellerRepository sellerRepository;

    @Autowired
    public LoginService(AdminRepository adminRepository, CustomerRepository customerRepository, SellerRepository sellerRepository) {
        this.adminRepository = adminRepository;
        this.customerRepository = customerRepository;
        this.sellerRepository = sellerRepository;
    }

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return adminRepository.findAdminByUsername(username)
//                .orElseThrow(() -> {
//                    System.out.println("admin not valid");
//                    throw new UsernameNotFoundException("admin not valid");
//                });
//    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Admin admin = adminRepository.findAdminByUsername(username).orElse(null);
        if (admin != null) {
            return admin;
        }

        Seller seller = sellerRepository.findSellerByPhone(username).orElse(null);
        if (seller != null) {
            return seller;
        }

        Customer customer = customerRepository.findCustomerByEmail(username).orElse(null);
        if (customer != null) {
            return customer;
        }

        throw new UsernameNotFoundException("Kullanıcı bulunamadı: " + username);
    }
}
