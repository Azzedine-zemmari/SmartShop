package com.smart.shop.dto;

import lombok.Data;

@Data
public class ProductDto {
    private int id;
    private String nom;
    private double prixUnitaire;
    private int stockDisponible;
}
