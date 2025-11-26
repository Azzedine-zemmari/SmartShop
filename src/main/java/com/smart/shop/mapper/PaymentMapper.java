package com.smart.shop.mapper;

import com.smart.shop.dto.PaymentDto;
import com.smart.shop.model.Commande;
import com.smart.shop.model.Payment;
import com.smart.shop.repository.CommandeRepository;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {
    private final CommandeRepository commandeRepository;

    public PaymentMapper(CommandeRepository commandeRepository) {
        this.commandeRepository = commandeRepository;
    }
    public Payment toEntity(PaymentDto dto){
        Payment payment = new Payment();
        payment.setMontant(dto.getMontant());
        payment.setDatePaiement(dto.getDatePaiement());
        payment.setDateEncaissement(dto.getDateEncaissement());
        payment.setTypePaiement(dto.getTypePaiement());
        if(dto.getCommandeId() != null){
            Commande commande = commandeRepository.findById(dto.getCommandeId())
                    .orElseThrow(() -> new RuntimeException("Commande n 'est pas trouver avec cet id " + dto.getCommandeId()));

            payment.setCommande(commande);
        }
        return payment;
    }
    public PaymentDto toDto(Payment entity) {
        PaymentDto dto = new PaymentDto();

        dto.setId(entity.getId());
        dto.setMontant(entity.getMontant());
        dto.setDatePaiement(entity.getDatePaiement());
        dto.setDateEncaissement(entity.getDateEncaissement());
        dto.setTypePaiement(entity.getTypePaiement());
        dto.setNumeroPaiement(entity.getNumeroPaiement());

        if (entity.getCommande() != null) {
            dto.setCommandeId(entity.getCommande().getId());
        }

        return dto;
    }

}
