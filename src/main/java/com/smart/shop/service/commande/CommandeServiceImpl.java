package com.smart.shop.service.commande;

import com.smart.shop.dto.CommandeRequestDto;
import com.smart.shop.dto.OrderItemRequestDto;
import com.smart.shop.enums.Niveau_fidelete;
import com.smart.shop.enums.OrderStatus;
import com.smart.shop.exeception.NotEnoughStockException;
import com.smart.shop.exeception.ProductNotFoundException;
import com.smart.shop.exeception.UserNotFound;
import com.smart.shop.mapper.CommandeMapper;
import com.smart.shop.mapper.ProductMapper;
import com.smart.shop.model.Client;
import com.smart.shop.model.Commande;
import com.smart.shop.model.OrderItem;
import com.smart.shop.model.Product;
import com.smart.shop.repository.ClientRepository;
import com.smart.shop.repository.CommandeRepository;
import com.smart.shop.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CommandeServiceImpl implements CommandeService{
    private final CommandeRepository commandeRepository;
    private final ClientRepository clientRepository;
    private final ProductRepository productRepository;
    private final CommandeMapper commandeMapper;

    @Override
    @Transactional
    public CommandeRequestDto createCommande(CommandeRequestDto dto){
        Client client = clientRepository.findById(dto.getClientId()).orElseThrow(()-> new UserNotFound("Veuillez saisir un client "));

        // fetch product
        List<Product> products = new ArrayList<>();
        for(OrderItemRequestDto item : dto.getItems()){
            Product product = productRepository.findById(item.getProductId()).orElseThrow(()-> new ProductNotFoundException("Veuillez saisir un produit deja exist"));
            if(item.getQuantity() > product.getStock_disponible()){
                throw new NotEnoughStockException("Le produit " + product.getNom()  + " na pas assez de stock "+ "Demande: " + item.getQuantity()
                        + ", Disponible: " + product.getStock_disponible());
            }
            products.add(product);
        }

        Commande commande = commandeMapper.toEntity(dto);

        commande.setClient(client);

        double sous_total = 0.0;

        for(int i=0;i<commande.getOrderItems().size();i++){
            // Get the empty OrderItem created by the mapper
            OrderItem orderItem = commande.getOrderItems().get(i);
            Product product = products.get(i);
            // Get the quantity and productId sent by the client
            OrderItemRequestDto itemDto = dto.getItems().get(i);

            orderItem.setProduct(product);
            orderItem.setCommande(commande);
            orderItem.setPrice(product.getPrix_unitaire());
            orderItem.setQuantity(itemDto.getQuantity());
            orderItem.setTotal_ligne(orderItem.getTotal());
            sous_total += orderItem.getTotal();
        }

        // set discount
        double discout = calculteDiscount(sous_total,client.getNiveau_fidelete());
        commande.setDiscount(discout);
        // set total
        commande.setSous_total(sous_total);
        double total = sous_total - discout + dto.getTva() ;
        commande.setTotal(total);
        commande.setMontant_restant(total);
        commande.setStatus(OrderStatus.PENDING);

        // save

        Commande savedCommande = commandeRepository.save(commande);

        int totalOrders = commandeRepository.countByClientIdAndStatus(client.getId() , OrderStatus.CONFIRMED);
        double totalSpent= commandeRepository.sumTotalByClientId(client.getId() ,OrderStatus.CONFIRMED);
        Niveau_fidelete newLvl = calculateNiveauFidelete(totalOrders,totalSpent);

        if(client.getNiveau_fidelete() != newLvl){
            clientRepository.updateNiveauFidelete(client.getId() , newLvl);
        }

        return commandeMapper.toRequestDto(savedCommande);
    }
    private Niveau_fidelete calculateNiveauFidelete(int totalOrders, double totalSpent){
        if (totalOrders >= 20 || totalSpent >= 15000) {
            return Niveau_fidelete.PLATINUM;
        }

        if (totalOrders >= 10 || totalSpent >= 5000) {
            return Niveau_fidelete.GOLD;
        }

        if (totalOrders >= 3 || totalSpent >= 1000) {
            return Niveau_fidelete.SILVER;
        }

        return Niveau_fidelete.BASIC;
    }
    private Double calculteDiscount(double sous_total , Niveau_fidelete niveauFidelete){
        switch(niveauFidelete){
            case SILVER:
                return (sous_total >= 500) ? sous_total * 0.05 : 0.0;
            case GOLD:
                return (sous_total >= 800) ? sous_total * 0.10 : 0.0;
            case PLATINUM:
                return (sous_total >= 1200) ? sous_total * 0.15 : 0.0;
            default:
                return 0.0;
        }
    }
}
