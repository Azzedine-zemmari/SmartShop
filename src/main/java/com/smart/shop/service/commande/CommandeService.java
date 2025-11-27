package com.smart.shop.service.commande;

import com.smart.shop.dto.CommandeRequestDto;
import com.smart.shop.enums.OrderStatus;

public interface CommandeService {
    CommandeRequestDto createCommande(CommandeRequestDto dto);
    void ConfirmeCommande(Long commandeId);
}
