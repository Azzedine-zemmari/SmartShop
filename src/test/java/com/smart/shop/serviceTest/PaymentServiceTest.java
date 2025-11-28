package com.smart.shop.serviceTest;

import com.smart.shop.dto.PaymentDto;
import com.smart.shop.enums.TypePayment;
import com.smart.shop.mapper.PaymentMapper;
import com.smart.shop.model.Commande;
import com.smart.shop.model.Payment;
import com.smart.shop.repository.CommandeRepository;
import com.smart.shop.repository.PaymentRepository;
import com.smart.shop.service.payment.PaymentServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceTest {
    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private PaymentMapper paymentMapper;

    @Mock
    private CommandeRepository commandeRepository;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    @Test
    void creePayment_shouldSavePaymentSuccessfully() {
        Commande commande = new Commande();
        commande.setId(1L);
        commande.setMontant_restant(100.0);

        PaymentDto dto = new PaymentDto();
        dto.setCommandeId(1L);
        dto.setMontant(50.0);
        dto.setDatePaiement(LocalDate.now());
        dto.setDateEncaissement(LocalDate.now());
        dto.setTypePaiement(TypePayment.CHEQUE);

        Payment payment = new Payment();
        payment.setMontant(dto.getMontant());

        Payment savedPayment = new Payment();
        savedPayment.setMontant(dto.getMontant());

        PaymentDto resultDto = new PaymentDto();
        resultDto.setMontant(dto.getMontant());

        Mockito.when(commandeRepository.findById(1L)).thenReturn(Optional.of(commande));
        Mockito.when(paymentRepository.findTopByCommandeIdOrderByNumeroPaiementDesc(1L)).thenReturn(Optional.empty());
        Mockito.when(paymentRepository.save(Mockito.any(Payment.class))).thenReturn(savedPayment);
        Mockito.when(paymentMapper.toDto(savedPayment)).thenReturn(resultDto);

        // When
        PaymentDto result = paymentService.creePayment(dto);

        // Then
        Mockito.verify(paymentRepository).save(Mockito.argThat(p -> p.getMontant() == 50.0));
        Mockito.verify(commandeRepository).updateMontantRestant(50.0, 1L);
        assertEquals(50.0, result.getMontant());
    }

}
