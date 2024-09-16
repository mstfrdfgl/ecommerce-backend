package com.redifoglu.ecommerce.service;

import com.redifoglu.ecommerce.entity.Product;
import com.redifoglu.ecommerce.entity.user.Seller;
import com.redifoglu.ecommerce.exceptions.NotFoundException;
import com.redifoglu.ecommerce.repository.SellerRepository;
import jakarta.transaction.Transactional;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SellerService {

    private SellerRepository sellerRepository;

    @Autowired
    public SellerService(SellerRepository sellerRepository) {
        this.sellerRepository = sellerRepository;
    }

    public Seller findById(Long id) throws NotFoundException {
        Optional<Seller> seller = sellerRepository.findById(id);
        if (seller.isPresent()) {
            return seller.get();
        }
        throw new NotFoundException("Seller not found with ID: " + id);
    }

    public Seller findSellerByPhone(String phone) throws NotFoundException {
        Optional<Seller> seller = sellerRepository.findSellerByPhone(phone);
        if (seller.isPresent()) {
            return seller.get();
        }
        throw new NotFoundException("Seller not found with phone: " + phone);
    }

    @Transactional
    public Seller updateSeller(Long id, Seller seller) {
        Seller existingSeller = findById(id);
        existingSeller.setName(seller.getName());
        existingSeller.setPhone(seller.getPhone());
        existingSeller.setPassword(seller.getPassword());

        return sellerRepository.save(existingSeller);
    }

    @Transactional
    public void deleteSeller(Long id) {
        Seller seller = findById(id);
        sellerRepository.delete(seller);
    }

    public List<Product> findProductsBySeller(Long id) {
        Seller seller = findById(id);
        return seller.getProducts();
    }
}