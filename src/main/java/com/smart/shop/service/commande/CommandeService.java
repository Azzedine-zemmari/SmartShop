package com.smart.shop.service.commande;

import com.smart.shop.dto.CommandeRequestDto;
import com.smart.shop.dto.CommandeSummaryProjection;

import java.util.List;

public interface CommandeService {
    CommandeRequestDto createCommande(CommandeRequestDto dto);
    void ConfirmeCommande(Long commandeId);
    void CancelCommande(Long commandeId);
    List<CommandeSummaryProjection> getAllCommandeForUser(Integer userId);
}
