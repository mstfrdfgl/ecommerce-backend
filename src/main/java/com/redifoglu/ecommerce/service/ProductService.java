package com.redifoglu.ecommerce.service;

import com.redifoglu.ecommerce.entity.Category;
import com.redifoglu.ecommerce.entity.Product;
import com.redifoglu.ecommerce.entity.user.Seller;
import com.redifoglu.ecommerce.exceptions.NotFoundException;
import com.redifoglu.ecommerce.repository.CategoryRepository;
import com.redifoglu.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

    public Product findProductById(Long id) throws NotFoundException {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            return product.get();
        }
        throw new NotFoundException("Product not found with id: " + id);
    }

    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    public List<Product> findProductsByCategoryId(Long categoryId){
        return productRepository.findByCategoryId(categoryId);
    }

    @Transactional
    public Product updateProduct(Long id, Product updatedProduct) {
        Product existingProduct = findProductById(id);

        existingProduct.setName(updatedProduct.getName());
        existingProduct.setPrice(updatedProduct.getPrice());
        existingProduct.setStock(updatedProduct.getStock());
        existingProduct.setBrand(updatedProduct.getBrand());
        existingProduct.setCategory(updatedProduct.getCategory());
        existingProduct.setSeller(updatedProduct.getSeller());

        return productRepository.save(existingProduct);
    }

    @Transactional
    public void deleteProductById(Long id) {
        Product product = findProductById(id);
        productRepository.delete(product);
    }

    @Transactional
    public void deleteAllProducts() {
        productRepository.deleteAll();
    }
}
