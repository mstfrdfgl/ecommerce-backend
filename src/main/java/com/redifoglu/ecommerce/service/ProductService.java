package com.redifoglu.ecommerce.service;

import com.redifoglu.ecommerce.entity.Category;
import com.redifoglu.ecommerce.entity.Product;
import com.redifoglu.ecommerce.entity.user.Seller;
import com.redifoglu.ecommerce.repository.CategoryRepository;
import com.redifoglu.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ProductService {

    private ProductRepository productRepository;
    private CategoryService categoryService;
    private SellerService sellerService;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public Product save(Product product) {
        return productRepository.save(product);
    }

    public Product findProductById(Long id){
        Optional<Product> product=productRepository.findById(id);
        if(product.isPresent()){
            return product.get();
        }
        throw new RuntimeException();
    }
}
