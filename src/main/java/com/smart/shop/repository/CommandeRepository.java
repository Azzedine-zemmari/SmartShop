package com.smart.shop.repository;

import com.smart.shop.dto.CommandeSummaryProjection;
import com.smart.shop.enums.OrderStatus;
import com.smart.shop.model.Commande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommandeRepository extends JpaRepository<Commande,Long> {
    @Query("SELECT COUNT(c) FROM Commande c WHERE c.client.id = :clientId AND c.status = :status")
    int countByClientIdAndStatus(@Param("clientId") Integer clientId, @Param("status") OrderStatus status);

    @Query("SELECT COALESCE(sum(c.total),0) FROM Commande c where c.client.id = :id and c.status = :status")
    double sumTotalByClientId(Integer id , @Param("status") OrderStatus orderStatus);

    @Modifying
    @Query("UPDATE Commande c set c.montant_restant = :montant where c.id = :commandId ")
    void updateMontantRestant(@Param("montant") double montant , @Param("commandId") Long commandId);

    @Modifying
    @Query("UPDATE Commande c set c.status = :status where c.id = :commandeId")
    void updateStatus(@Param("commandeId") Long commandeId , @Param("status") OrderStatus status);

    @Query("Select c.montant_restant FROM Commande c where c.id = :commandeId")
    Double getMontantRestant(Long commandeId);

    @Query("SELECT COUNT(c) FROM Commande c WHERE c.client.user.id = :id AND c.status = :status")
    int countByUserIdAndStatus(@Param("id") Integer id, @Param("status") OrderStatus status);

    @Query("SELECT COALESCE(sum(c.total),0) FROM Commande c where c.client.user.id = :id and c.status = :status")
    double sumTotalByUserId(@Param("id") Integer id , @Param("status") OrderStatus orderStatus);

    @Query("SELECT MIN(c.date) , MAX(c.date) FROM Commande c where c.client.user.id = :id")
    List<Object[]> findFirstAndLastCommandeDateForUser(@Param("id") Integer id);

    @Query("SELECT c FROM Commande c WHERE c.client.user.id = :userId")
    List<CommandeSummaryProjection> findAllByUserId(@Param("userId") Integer userId);

}
