package com.smart.shop.repository;

import com.smart.shop.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment,Integer> {
    Optional<Payment> findTopByCommandeIdOrderByNumeroPaiementDesc(Long commandeId);
    @Query("SELECT coalesce(sum(p.montant) , 0) FROM Payment p  where p.commande.id = :commandeId")
    Optional<Double> sumMontantByCommandeId(@Param("commandeId") Long commandeId);
}
