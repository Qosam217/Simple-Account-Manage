package com.demo.simple_account.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name="orders")
@EqualsAndHashCode(callSuper=true)
@Data
public class Order extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    @JsonBackReference
    private Product product;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "quantity")
    @NotNull(message = "Quantity must be filled")
    @Pattern(
        regexp = "^\\d+$",
        message = "Quantity must be only number"
    )
    private int quantity;

}
