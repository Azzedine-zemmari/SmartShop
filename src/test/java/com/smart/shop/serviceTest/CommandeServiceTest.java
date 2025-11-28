package com.smart.shop.serviceTest;

import com.smart.shop.dto.CommandeRequestDto;
import com.smart.shop.dto.OrderItemRequestDto;
import com.smart.shop.enums.Niveau_fidelete;
import com.smart.shop.enums.OrderStatus;
import com.smart.shop.exeception.CannotCancelOrderException;
import com.smart.shop.exeception.ProductNotFoundException;
import com.smart.shop.exeception.UserNotFound;
import com.smart.shop.mapper.CommandeMapper;
import com.smart.shop.model.Client;
import com.smart.shop.model.Commande;
import com.smart.shop.model.OrderItem;
import com.smart.shop.model.Product;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    @Test
    void shouldCreateRejectedCommande_whenStockInsufficient() {
        CommandeRequestDto dto = new CommandeRequestDto();
        dto.setClientId(1);

        OrderItemRequestDto item = new OrderItemRequestDto();
        item.setProductId(5);
        item.setQuantity(10);

        dto.setItems(List.of(item));

        Client client = new Client();
        client.setId(1);

        Product product = new Product();
        product.setId(5);
        product.setStock_disponible(3);
        product.setPrix_unitaire(100.0);


        Commande commandeMapped = new Commande();
        commandeMapped.setOrderItems(List.of(new OrderItem()));

        Mockito.when(commandeMapper.toEntity(dto)).thenReturn(commandeMapped);
        Mockito.when(clientRepository.findById(1)).thenReturn(Optional.of(client));
        Mockito.when(productRepository.findById(5)).thenReturn(Optional.of(product));

        Mockito.when(commandeRepository.save(Mockito.any())).thenAnswer(inv -> inv.getArgument(0));

        CommandeRequestDto result = commandeService.createCommande(dto);

        Mockito.verify(commandeRepository).save(Mockito.any());

        Mockito.verify(commandeMapper).toRequestDto(
                Mockito.argThat(cmd ->
                        cmd.getStatus() == OrderStatus.REJECTED &&
                                cmd.getTotal() == 0
                )
        );

    }

    @Test
    void shouldCreateSuccessCommande(){
        CommandeRequestDto dto = new CommandeRequestDto();
        dto.setClientId(1);

        OrderItemRequestDto item = new OrderItemRequestDto();
        item.setProductId(1);
        item.setQuantity(5);

        dto.setItems(List.of(item));

        Product product = new Product();
        product.setId(1);
        product.setNom("jean");
        product.setStock_disponible(40);
        product.setPrix_unitaire(50.22);

        Client client = new Client();
        client.setId(1);
        client.setNiveau_fidelete(Niveau_fidelete.BASIC);

        Commande commandeMapped = new Commande();
        commandeMapped.setOrderItems(List.of(new OrderItem()));

        Mockito.when(commandeMapper.toEntity(dto)).thenReturn(commandeMapped);
        Mockito.when(clientRepository.findById(1)).thenReturn(Optional.of(client));
        Mockito.when(productRepository.findById(1)).thenReturn(Optional.of(product));

        Mockito.when(commandeRepository.save(Mockito.any())).thenAnswer(inv -> inv.getArgument(0));

        Mockito.when(commandeMapper.toRequestDto(Mockito.any()))
                .thenReturn(new CommandeRequestDto());

        Mockito.when(commandeRepository.countByClientIdAndStatus(1, OrderStatus.CONFIRMED))
                .thenReturn(5);
        Mockito.when(commandeRepository.sumTotalByClientId(1, OrderStatus.CONFIRMED))
                .thenReturn(2000.0);

        CommandeRequestDto result = commandeService.createCommande(dto);

        Mockito.verify(commandeRepository).save(Mockito.any());
    }
    @Test
    void confirmeCommande_shouldThrowIfCommandeNotFound() {
        Mockito.when(commandeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> commandeService.ConfirmeCommande(1L));
    }

    @Test
    void confirmeCommande_shouldThrowIfMontantRestantIsNull() {
        Commande commande = new Commande();
        commande.setId(1L);
        commande.setMontant_restant(null);

        Mockito.when(commandeRepository.findById(1L)).thenReturn(Optional.of(commande));

        assertThrows(RuntimeException.class, () -> commandeService.ConfirmeCommande(1L));
    }

    @Test
    void confirmeCommande_shouldThrowIfMontantRestantNotPaid() {
        Commande commande = new Commande();
        commande.setId(1L);
        commande.setMontant_restant(100.0);

        Mockito.when(commandeRepository.findById(1L)).thenReturn(Optional.of(commande));

        assertThrows(RuntimeException.class, () -> commandeService.ConfirmeCommande(1L));
    }


    @Test
    void confirmeCommande_shouldUpdateStockAndStatus() {
        // Arrange
        Commande commande = new Commande();
        commande.setId(1L);
        commande.setMontant_restant(0.0);

        Product product = new Product();
        product.setId(10);
        product.setStock_disponible(50);

        OrderItem item = new OrderItem();
        item.setProduct(product);
        item.setQuantity(5);

        Mockito.when(commandeRepository.findById(1L)).thenReturn(Optional.of(commande));
        Mockito.when(orderItemsRepository.findByCommandeId(1L))
                .thenReturn(List.of(item));

        commandeService.ConfirmeCommande(1L);

        Mockito.verify(productRepository).updateStockDisponible(45, 10);
        Mockito.verify(commandeRepository).updateStatus(1L, OrderStatus.CONFIRMED);
    }

    @Test
    void cancelCommande_shouldThrowIfCommandeNotFound() {
        Mockito.when(commandeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> commandeService.CancelCommande(1L));
    }

    @Test
    void cancelCommande_shouldThrowIfCommandeConfirmedOrRejected() {
        Commande commande = new Commande();
        commande.setId(1L);
        commande.setStatus(OrderStatus.CONFIRMED);

        Mockito.when(commandeRepository.findById(1L)).thenReturn(Optional.of(commande));

        assertThrows(CannotCancelOrderException.class, () -> commandeService.CancelCommande(1L));
    }

    @Test
    void cancelCommande_shouldUpdateOrerStatusToCanceled() {
        Commande commande = new Commande();
        commande.setId(1L);
        commande.setStatus(OrderStatus.PENDING);

        Mockito.when(commandeRepository.findById(1L)).thenReturn(Optional.of(commande));

        commandeService.CancelCommande(1L);

        Mockito.verify(commandeRepository).updateStatus(1L,OrderStatus.CANCELED);
    }
    


}
