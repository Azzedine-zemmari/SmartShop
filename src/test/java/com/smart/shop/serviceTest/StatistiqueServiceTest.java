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


}
