package com.smart.shop.controller;

import com.smart.shop.dto.PaymentDto;
import com.smart.shop.service.payment.PaymentService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping ("/payment")
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/creePayement")
    public ResponseEntity<PaymentDto> payerCommande(@RequestBody PaymentDto dto){
        PaymentDto paymentDto = paymentService.creePayment(dto);
        return ResponseEntity.ok(paymentDto);
    }

}
