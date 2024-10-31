package com.practice.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private User user;
}
