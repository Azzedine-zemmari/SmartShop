package com.smart.shop.service.payment;

import com.smart.shop.dto.PaymentDto;
import com.smart.shop.mapper.PaymentMapper;
import com.smart.shop.model.Commande;
import com.smart.shop.model.Payment;
import com.smart.shop.repository.CommandeRepository;
import com.smart.shop.repository.PaymentRepository;

import java.util.Optional;

public class PaymentServiceImpl implements PaymentService{
    private PaymentRepository paymentRepository;
    private PaymentMapper paymentMapper;
    private CommandeRepository commandeRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository , PaymentMapper paymentMapper,CommandeRepository commandeRepository){
        this.paymentMapper = paymentMapper;
        this.paymentRepository = paymentRepository;
        this.commandeRepository = commandeRepository;
    }

    @Override
    public PaymentDto creePayment(PaymentDto dto) {
        Commande commande = commandeRepository.findById(dto.getCommandeId())
                .orElseThrow(() -> new RuntimeException("Commande introuvable"));

        Optional<Payment> lastPayment = paymentRepository.findTopByCommandeIdOrderByNumeroPaiementDesc(commande.getId());
        int nextNumber = lastPayment.map(p-> p.getNumeroPaiement() + 1).orElse(0);
        Payment payment = new Payment();
        payment.setCommande(commande);
        payment.setMontant(dto.getMontant());
        payment.setDatePaiement(dto.getDatePaiement());
        payment.setDateEncaissement(dto.getDateEncaissement());
        payment.setTypePaiement(dto.getTypePaiement());
        payment.setNumeroPaiement(nextNumber);

        Payment saved = paymentRepository.save(payment);

        return paymentMapper.toDto(saved);
    }

}
