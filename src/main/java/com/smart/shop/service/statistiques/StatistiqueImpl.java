package com.smart.shop.service.statistiques;

import com.smart.shop.enums.OrderStatus;
import com.smart.shop.repository.CommandeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StatistiqueImpl implements StatistiqueService{
    private final CommandeRepository commandeRepository;

    public StatistiqueImpl(CommandeRepository commandeRepository){
        this.commandeRepository = commandeRepository;
    }
    @Override
    public Integer totalCommandes(Integer id){
        return commandeRepository.countByUserIdAndStatus(id,OrderStatus.CONFIRMED);
    }

    @Override
    public Double totalCumule(Integer id){
        double total = commandeRepository.sumTotalByUserId(id,OrderStatus.CONFIRMED);
        return Math.round(total * 100.0) / 100.0;
    }

    @Override
    public String firstAndLastDateCommande(Integer id){
        List<Object[]> result = commandeRepository.findFirstAndLastCommandeDateForUser(id);
        Object[] firstRow = result.get(0);
        LocalDateTime first = (LocalDateTime) firstRow[0];
        LocalDateTime last = (LocalDateTime) firstRow[1];
        if(first == null || last == null){
            return "Aucune commande trouvee pour cet utilisateur.";
        }
        return first + " : " + last;

    }
}
