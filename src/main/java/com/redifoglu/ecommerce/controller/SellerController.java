package com.redifoglu.ecommerce.controller;

import com.redifoglu.ecommerce.dto.ProductDTO;
import com.redifoglu.ecommerce.dto.SellerDTO;
import com.redifoglu.ecommerce.entity.Product;
import com.redifoglu.ecommerce.entity.user.Seller;
import com.redifoglu.ecommerce.mapper.ProductMapper;
import com.redifoglu.ecommerce.mapper.SellerMapper;
import com.redifoglu.ecommerce.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/seller")
public class SellerController extends BaseController{

    private SellerService sellerService;

    @Autowired
    public SellerController(SellerService sellerService) {
        this.sellerService = sellerService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<SellerDTO> getSellerById(@PathVariable Long id) {
        Seller seller = sellerService.findById(id);
        return ResponseEntity.ok(SellerMapper.entityToDto(seller));
    }

    @GetMapping("/phone/{phone}")
    public ResponseEntity<SellerDTO> getSellerByPhone(@PathVariable String phone) {
        Seller seller = sellerService.findSellerByPhone(phone);
        return ResponseEntity.ok(SellerMapper.entityToDto(seller));
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductDTO>> findProductsBySeller() {
        Long authenticatedSellerId = getAuthenticatedUserId();
        List<Product> products = sellerService.findProductsBySeller(authenticatedSellerId);
        List<ProductDTO> productDTOS = products.stream()
                .map(ProductMapper::entityToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(productDTOS);
    }

    @GetMapping("/{id}/products")
    public ResponseEntity<List<ProductDTO>> getProductsBySellerId(@PathVariable Long id) {
        List<Product> products = sellerService.findProductsBySeller(id);
        List<ProductDTO> productDTOS = products.stream()
                .map(ProductMapper::entityToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(productDTOS);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SellerDTO> updateSeller(@PathVariable Long id, @RequestBody Seller seller) {
        Seller updatedSeller = sellerService.updateSeller(id, seller);
        return ResponseEntity.ok(SellerMapper.entityToDto(updatedSeller));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSeller(@PathVariable Long id) {
        sellerService.deleteSeller(id);
        return ResponseEntity.noContent().build();
    }

}
