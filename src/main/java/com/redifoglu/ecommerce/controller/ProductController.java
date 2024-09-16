package com.redifoglu.ecommerce.controller;

import com.redifoglu.ecommerce.dto.ProductDTO;
import com.redifoglu.ecommerce.entity.Category;
import com.redifoglu.ecommerce.entity.Product;
import com.redifoglu.ecommerce.entity.user.Seller;
import com.redifoglu.ecommerce.mapper.ProductMapper;
import com.redifoglu.ecommerce.service.CategoryService;
import com.redifoglu.ecommerce.service.ProductService;
import com.redifoglu.ecommerce.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {

    private ProductService productService;
    private SellerService sellerService;
    private CategoryService categoryService;

    @Autowired
    public ProductController(ProductService productService, SellerService sellerService, CategoryService categoryService) {
        this.productService = productService;
        this.sellerService = sellerService;
        this.categoryService = categoryService;
    }

    //SEÇİLEN İDDEKİ KATEGORİYE YENİ ÜRÜN EKLEME. SELLER OTOMATİK OLARAK BELİRLENİYOR
    @PostMapping("/{categoryId}")
    public ProductDTO save(@RequestBody Product product, @PathVariable Long categoryId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();

            if (principal instanceof UserDetails) {
                String username = ((UserDetails) principal).getUsername();
                Seller seller = sellerService.findSellerByPhone(username);
                Category category = categoryService.findCategoryById(categoryId);

                product.setSeller(seller);
                product.setCategory(category);

                Product savedProduct = productService.save(product);
                ProductDTO productDTO = ProductMapper.entityToDto(savedProduct);
                return productDTO;
            }
        }
        throw new RuntimeException();
    }
}
