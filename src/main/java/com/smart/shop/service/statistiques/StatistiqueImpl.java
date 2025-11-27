package com.smart.shop.service.statistiques;

import com.smart.shop.enums.OrderStatus;
import com.smart.shop.repository.CommandeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
public class StatistiqueImpl implements StatistiqueService{
    private final CommandeRepository commandeRepository;

    public StatistiqueImpl(CommandeRepository commandeRepository){
        this.commandeRepository = commandeRepository;
    }
    @Override
    public Long totalCommandes(){
        return commandeRepository.countAllCommande();
    }

    @Override
    public Double totalCumule(){
        Double total = commandeRepository.sumTotalCommandeConfirmed(OrderStatus.CONFIRMED);
        return Math.round(total * 100.0) / 100.0;
    }
}
