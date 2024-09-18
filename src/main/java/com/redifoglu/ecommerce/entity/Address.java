package com.redifoglu.ecommerce.entity;

import com.redifoglu.ecommerce.entity.user.Customer;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "address",schema = "public")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @NotBlank(message = "City cannot be blank")
    @Size(min = 3, max = 50, message = "City must be between 3 and 50 characters")
    @Column(name = "city")
    private String city;

    @NotBlank(message = "State cannot be blank")
    @Size(min = 3, max = 50, message = "State must be between 3 and 50 characters")
    @Column(name = "state")
    private String state;

    @NotBlank(message = "Description cannot be blank")
    @Column(name = "description")
    private String description;

    @NotBlank(message = "Postal code cannot be blank")
    @Pattern(regexp = "\\d{5}", message = "Postal code must be 5 digits")
    @Column(name = "postal_code")
    private String postalCode;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    public Address(String city, String state, String description, String postalCode, Customer customer) {
        this.city = city;
        this.state = state;
        this.description = description;
        this.postalCode = postalCode;
        this.customer = customer;
    }
}
