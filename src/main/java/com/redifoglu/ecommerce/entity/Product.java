package com.redifoglu.ecommerce.entity;

import com.redifoglu.ecommerce.entity.user.Seller;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "product",schema = "public")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @NotNull(message = "Product name cannot be null")
    @Size(min = 2, max = 100, message = "Product name must be between 2 and 100 characters")
    @Column(name = "name")
    private String name;

    @NotNull(message = "Price cannot be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    @Digits(integer = 10, fraction = 2, message = "Price must be a valid decimal with up to 10 integer digits and 2 decimal places")
    @Column(name = "price")
    private BigDecimal price;

    @NotNull(message = "Stock cannot be null")
    @Min(value = 0, message = "Stock cannot be negative")
    @Column(name = "stock")
    private Integer stock;

    @NotNull(message = "Brand cannot be null")
    @Size(min = 2, max = 50, message = "Brand name must be between 2 and 50 characters")
    @Column(name = "brand")
    private String brand;

    @NotNull(message = "Category cannot be null")
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @NotNull(message = "Seller cannot be null")
    @ManyToOne
    @JoinColumn(name = "seller_id")
    private Seller seller;

    @ManyToMany(mappedBy = "products")
    private List<Cart> carts;

    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL)
    private List<Review> reviews;

    @OneToMany(mappedBy = "product")
    private List<OrderItem> orderItems;
}
