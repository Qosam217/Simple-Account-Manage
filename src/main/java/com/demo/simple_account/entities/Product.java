package com.demo.simple_account.entities;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "products")
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product extends BaseEntity{
    
    @Column(name = "name")
    @NotBlank(message = "Product name must be filled")
    private String name;

    @Column(name = "category")
    private String category;

    @Column(name = "price")
    @NotEmpty(message = "Price must be filled")
    @Pattern(
        regexp = "^\\d+$",
        message = "Price must be only number"
    )
    private Double price;

    @Column(name = "stock")
    @NotEmpty(message = "Stock must be filled")
    @Pattern(
        regexp = "^\\d+$",
        message = "Stock must be only number"
    )
    private Integer stock;

    @Column(name = "expired")
    private LocalDate expired;

    @OneToMany(mappedBy = "product")
    @JsonBackReference
    private List<Order> orders;

}