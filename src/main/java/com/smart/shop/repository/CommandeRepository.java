package com.smart.shop.repository;

import com.smart.shop.model.Commande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CommandeRepository extends JpaRepository<Commande,Long> {
    int countByClientId(Integer id);
    @Query("SELECT COALESCE(sum(c.total),0) FROM Commande c where c.client.id = :id")
    double sumTotalByClientId(Integer id);
}
