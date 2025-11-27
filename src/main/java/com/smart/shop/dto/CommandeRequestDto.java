package com.smart.shop.dto;

import lombok.Data;

import java.util.List;

@Data
public class CommandeRequestDto {
    private Integer clientId;
    private List<OrderItemRequestDto> items;
    private double tva;
    private String code_promo;
    private double total;
    private double discount;
    private double sous_total;

}
