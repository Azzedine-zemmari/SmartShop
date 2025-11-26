package com.smart.shop.service.commande;

import com.smart.shop.dto.CommandeRequestDto;
import com.smart.shop.dto.OrderItemRequestDto;
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
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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

            sous_total += orderItem.getTotal();
        }

        // set total
        commande.setSous_total(sous_total);
        commande.setTotal(sous_total - dto.getDiscount() + dto.getTva());

        // save

        Commande savedCommande = commandeRepository.save(commande);

        return commandeMapper.toRequestDto(savedCommande);
    }
}
