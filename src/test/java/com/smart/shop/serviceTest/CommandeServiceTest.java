package com.smart.shop.serviceTest;

import com.smart.shop.dto.CommandeRequestDto;
import com.smart.shop.dto.OrderItemRequestDto;
import com.smart.shop.exeception.ProductNotFoundException;
import com.smart.shop.exeception.UserNotFound;
import com.smart.shop.mapper.CommandeMapper;
import com.smart.shop.model.Client;
import com.smart.shop.repository.ClientRepository;
import com.smart.shop.repository.CommandeRepository;
import com.smart.shop.repository.OrderItemsRepository;
import com.smart.shop.repository.ProductRepository;
import com.smart.shop.service.commande.CommandeServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class CommandeServiceTest {
    @Mock
    private CommandeRepository commandeRepository;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CommandeMapper commandeMapper;

    @Mock
    private OrderItemsRepository orderItemsRepository;

    @InjectMocks
    private CommandeServiceImpl commandeService;


    @Test
    void creeCommandeshouldThrowUserNotFoundException(){
        CommandeRequestDto dto = new CommandeRequestDto();
        dto.setClientId(1);

        Mockito.when(clientRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(UserNotFound.class , ()-> commandeService.createCommande(dto));
    }
    @Test
    void creeCommandeshouldThrowProductNotFoundException(){
        CommandeRequestDto dto = new CommandeRequestDto();
        dto.setClientId(1);

        OrderItemRequestDto item = new OrderItemRequestDto();
        item.setProductId(10);
        item.setQuantity(1);
        dto.setItems(List.of(item));

        Mockito.when(clientRepository.findById(1)).thenReturn(Optional.of(new Client()));
        Mockito.when(productRepository.findById(10)).thenReturn(Optional.empty());


        assertThrows(ProductNotFoundException.class,()-> commandeService.createCommande(dto));

    }
}
