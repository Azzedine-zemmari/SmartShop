package com.smart.shop.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nom;

    private double prix_unitaire;

    private int stock_disponible;

    @OneToMany(mappedBy="product")
    private List<OrderItem> orderItems = new ArrayList<>();

    @Column(name= "deleted_at" , nullable = true)
    private LocalDateTime deletedAt;
}
