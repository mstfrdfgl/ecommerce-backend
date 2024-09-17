package com.redifoglu.ecommerce.controller;

import com.redifoglu.ecommerce.dto.ProductDTO;
import com.redifoglu.ecommerce.entity.Category;
import com.redifoglu.ecommerce.entity.Product;
import com.redifoglu.ecommerce.entity.user.Seller;
import com.redifoglu.ecommerce.exceptions.NotFoundException;
import com.redifoglu.ecommerce.mapper.ProductMapper;
import com.redifoglu.ecommerce.service.CategoryService;
import com.redifoglu.ecommerce.service.OrderItemService;
import com.redifoglu.ecommerce.service.ProductService;
import com.redifoglu.ecommerce.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController extends BaseController {

    private ProductService productService;
    private SellerService sellerService;
    private CategoryService categoryService;
    private OrderItemService orderItemService;

    @Autowired
    public ProductController(ProductService productService, SellerService sellerService, CategoryService categoryService,
                             OrderItemService orderItemService) {
        this.productService = productService;
        this.sellerService = sellerService;
        this.categoryService = categoryService;
        this.orderItemService = orderItemService;
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<Product> products = productService.findAllProducts();
        List<ProductDTO> productDTOS = new ArrayList<>();
        for (Product product : products) {
            ProductDTO productDTO = ProductMapper.entityToDto(product);
            productDTOS.add(productDTO);
        }
        return ResponseEntity.ok(productDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        Product product = productService.findProductById(id);
        return ResponseEntity.ok(ProductMapper.entityToDto(product));
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProductDTO>> getProductsByCategory(@PathVariable Long categoryId) {
        List<Product> products = productService.findProductsByCategoryId(categoryId);
        List<ProductDTO> productDTOS = new ArrayList<>();
        for (Product product : products) {
            ProductDTO productDTO = ProductMapper.entityToDto(product);
            productDTOS.add(productDTO);
        }
        return ResponseEntity.ok(productDTOS);
    }

    @GetMapping("/bestseller")
    public ResponseEntity<List<ProductDTO>> getBestSellingProducts() {
        List<Product> topSellingProducts = orderItemService.findTopSellingProducts(100);
        List<ProductDTO> productDTOS = new ArrayList<>();
        for (Product product : topSellingProducts) {
            ProductDTO productDTO = ProductMapper.entityToDto(product);
            productDTOS.add(productDTO);
        }
        return new ResponseEntity<>(productDTOS, HttpStatus.OK);
    }

    //SEÇİLEN İDDEKİ KATEGORİYE YENİ ÜRÜN EKLEME. SELLER OTOMATİK OLARAK BELİRLENİYOR
    @PostMapping("/add/{categoryId}")
    public ResponseEntity<ProductDTO> addProduct(@RequestBody Product product, @PathVariable Long categoryId) {
        Long sellerId = getAuthenticatedUserId();
        Seller seller = sellerService.findById(sellerId);
        Category category = categoryService.findCategoryById(categoryId);

        product.setSeller(seller);
        product.setCategory(category);

        Product savedProduct = productService.save(product);

        return new ResponseEntity<>(ProductMapper.entityToDto(savedProduct), HttpStatus.CREATED);
    }

    @PutMapping("/update/{productId}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long productId, @RequestBody Product updatedProduct) {
        Long sellerId = getAuthenticatedUserId();
        Product existingProduct = productService.findProductById(productId);
        if (!existingProduct.getSeller().getId().equals(sellerId)) {
            throw new RuntimeException("You are not authorized to update this product");
        }
        updatedProduct.setCategory(existingProduct.getCategory());
        updatedProduct.setSeller(existingProduct.getSeller());
        Product updated = productService.updateProduct(productId, updatedProduct);
        return new ResponseEntity<>(ProductMapper.entityToDto(updated), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        Long sellerId = getAuthenticatedUserId();
        Product product = productService.findProductById(productId);

        if (!product.getSeller().getId().equals(sellerId)) {
            throw new RuntimeException("You are not authorized to delete this product");
        }

        productService.deleteProductById(productId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}