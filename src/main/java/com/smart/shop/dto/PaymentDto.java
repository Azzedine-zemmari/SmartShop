package com.smart.shop.dto;

import com.smart.shop.enums.TypePayment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDto {
    private Integer id;
    private Integer numeroPaiement;
    private double montant;
    private TypePayment typePaiement;
    private LocalDate datePaiement;
    private LocalDate dateEncaissement;
    private Long commandeId;
}
