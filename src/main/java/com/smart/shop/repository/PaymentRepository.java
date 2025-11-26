package com.smart.shop.repository;

import com.smart.shop.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment,Integer> {
    Optional<Payment> findTopByCommandeIdOrderByNumeroPaiementDesc(Long commandeId);
}
