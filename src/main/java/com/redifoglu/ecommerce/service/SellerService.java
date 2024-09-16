package com.redifoglu.ecommerce.service;

import com.redifoglu.ecommerce.entity.user.Seller;
import com.redifoglu.ecommerce.repository.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SellerService {

    private SellerRepository sellerRepository;

    @Autowired
    public SellerService(SellerRepository sellerRepository) {
        this.sellerRepository = sellerRepository;
    }

    public Seller findById(Long id){
        Optional<Seller> seller=sellerRepository.findById(id);
        if(seller.isPresent()){
            return seller.get();
        }
        throw new RuntimeException();
    }

    public Seller findSellerByPhone(String phone){
        Optional<Seller> seller=sellerRepository.findSellerByPhone(phone);
        if(seller.isPresent()){
            return seller.get();
        }
        throw new RuntimeException();
    }
}