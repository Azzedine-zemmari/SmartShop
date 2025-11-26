package com.smart.shop.dto;

import lombok.Data;

@Data
public class ProductDto {
    private int id;
    private String nom;
    private Double prixUnitaire;
    private Integer stockDisponible;
}
