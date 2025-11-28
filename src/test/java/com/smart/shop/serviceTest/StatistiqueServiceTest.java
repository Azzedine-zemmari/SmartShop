package com.smart.shop.serviceTest;

import com.smart.shop.enums.OrderStatus;
import com.smart.shop.repository.CommandeRepository;
import com.smart.shop.service.statistiques.StatistiqueImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class StatistiqueServiceTest {
    @Mock
    private CommandeRepository commandeRepository;

    @InjectMocks
    private StatistiqueImpl statistique;

    @Test
    public void totalCommandes(){
        Mockito.when(commandeRepository.countByUserIdAndStatus(1,OrderStatus.CONFIRMED)).thenReturn(13);

        Integer result = statistique.totalCommandes(1);

        Mockito.verify(commandeRepository).countByUserIdAndStatus(1,OrderStatus.CONFIRMED);
        assertEquals(13,result);
    }

    @Test
    public void TotalCumule(){
        Mockito.when(commandeRepository.sumTotalByUserId(1,OrderStatus.CONFIRMED))
                .thenReturn(12.345);

        Double result = statistique.totalCumule(1);

        Mockito.verify(commandeRepository).sumTotalByUserId(1,OrderStatus.CONFIRMED);
        assertEquals(12.35,result);
    }

    @Test
    void firstAndLastDateCommande_success() {
        LocalDateTime firstDate = LocalDateTime.of(2024, 1, 10, 12, 0);
        LocalDateTime lastDate  = LocalDateTime.of(2024, 3, 20, 18, 30);

        List<Object[]> mockResult = new ArrayList<>();
        mockResult.add(new Object[]{firstDate, lastDate});

        Mockito.when(commandeRepository.findFirstAndLastCommandeDateForUser(1)).thenReturn(mockResult);

        String result = statistique.firstAndLastDateCommande(1);

        assertEquals(firstDate + " : " + lastDate , result);
    }
}
