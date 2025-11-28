package com.smart.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private int id;
    private String nom;
    private Double prixUnitaire;
    private Integer stockDisponible;
}
