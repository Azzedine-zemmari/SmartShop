package com.smart.shop.model;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Product product;

    @ManyToOne
    private Commande commande;

    private int quantity;

    private double price; // price per unit at the time of order

    private double total_ligne;

    public double getTotal() {
        return price * quantity;
    }
}
