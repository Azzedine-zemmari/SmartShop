package com.smart.shop.repository;

import com.smart.shop.enums.Niveau_fidelete;
import com.smart.shop.model.Commande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommandeRepository extends JpaRepository<Commande,Long> {
    int countByClientId(Integer id);
    @Query("SELECT COALESCE(sum(c.total),0) FROM Commande c where c.client.id = :id")
    double sumTotalByClientId(Integer id);
    @Modifying
    @Query("UPDATE Commande c set c.montant_restant = :montant where c.id = :commandId")
    void updateMontantRestant(@Param("montant") double montant , @Param("commandId") Long commandId);
}
