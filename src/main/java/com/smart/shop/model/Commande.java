package com.smart.shop.model;

import com.smart.shop.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString(onlyExplicitlyIncluded = true) // to avoid StackOverflow erreur
public class Commande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Include
    private Long id;

    @ToString.Exclude // to fix stackoverflow erreur
    @ManyToOne
    private Client client;

    @ToString.Include
    private LocalDateTime date;

    @ToString.Include
    private double sous_total;

    @ToString.Include
    private double discount;

    @ToString.Include
    private double tva;

    @ToString.Include
    private double total;

    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<OrderItem> orderItems = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @ToString.Include
    private OrderStatus status;

    // todo add code promo model

    @ToString.Include
    private Double montant_restant;


    @OneToMany(mappedBy = "commande",cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Payment> paiements;
}
