package com.smart.shop.repository;

import com.smart.shop.enums.Niveau_fidelete;
import com.smart.shop.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ClientRepository extends JpaRepository<Client,Integer> {
    @Modifying
    @Query("UPDATE Client c set c.niveau_fidelete = :niveau where c.id = :clientId")
    void updateNiveauFidelete(@Param("clientId") Integer clientId , @Param("niveau")Niveau_fidelete niveau);
}
