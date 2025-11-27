package com.smart.shop.model;

import com.smart.shop.enums.TypePayment;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne()
    @JoinColumn(name="id_commande")
    private Commande commande;
    private double montant;
    @Enumerated(EnumType.STRING)
    private TypePayment typePaiement;
    private Integer numeroPaiement;
    private LocalDate datePaiement;
    private LocalDate dateEncaissement;

    @Override
    public int hashCode(){
        return id == null ? 0 : id.hashCode();
    }

    @Override
    public boolean equals(Object obj){
        if(this == obj) return true;
        if(!(obj instanceof  Payment)) return false;

        Payment autre = (Payment) obj;
        return this.id != null && this.id.equals(autre.id);
    }
}
