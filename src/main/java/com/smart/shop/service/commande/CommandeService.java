package com.smart.shop.service.commande;

import com.smart.shop.dto.CommandeRequestDto;

public interface CommandeService {
    CommandeRequestDto createCommande(CommandeRequestDto dto);
}
