package com.practice.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(catalog = "spring_data_jpa", schema = "public", name = "shopping_carts")
public class ShoppingCart implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "shopping_carts_seq")
    @SequenceGenerator(name = "shopping_carts_seq", sequenceName = "shopping_carts_id_seq", allocationSize = 1)
    private Long id;
    private Short quantity;
    private BigDecimal amount;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            catalog = "spring_data_jpa",
            schema = "public",
            name = "cart_items",
            joinColumns = @JoinColumn(name = "cart_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> items = new ArrayList<>();
}
