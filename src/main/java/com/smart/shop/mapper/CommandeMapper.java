package com.smart.shop.mapper;

import com.smart.shop.dto.CommandeRequestDto;
import com.smart.shop.dto.OrderItemRequestDto;
import com.smart.shop.model.Commande;
import com.smart.shop.model.OrderItem;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class CommandeMapper {
    public Commande toEntity(CommandeRequestDto commandeRequestDto){
        Commande commande = new Commande();
        commande.setDate(LocalDateTime.now());
        commande.setTva(commandeRequestDto.getTva());
        List<OrderItem> orderItems = new ArrayList<>();
        for (OrderItemRequestDto item : commandeRequestDto.getItems()){
            OrderItem orderItem = new OrderItem();
            orderItem.setQuantity(item.getQuantity());
            orderItems.add(orderItem);
        }
        commande.setOrderItems(orderItems);

        return commande;
        
    }
    public CommandeRequestDto toRequestDto(Commande commande){
        CommandeRequestDto commandeRequestDto = new CommandeRequestDto();
        commandeRequestDto.setClientId(commande.getClient().getId());
        commandeRequestDto.setTva(commande.getTva());

        List<OrderItemRequestDto> items = commande.getOrderItems().stream().map(orderItem -> {
            OrderItemRequestDto itemDto = new OrderItemRequestDto();
            itemDto.setProductId(orderItem.getProduct().getId());
            itemDto.setQuantity(orderItem.getQuantity());
            return itemDto;
        }).toList();

        commandeRequestDto.setItems(items);
        return commandeRequestDto;
    }
}
