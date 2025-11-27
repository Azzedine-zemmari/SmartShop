package com.smart.shop.service.statistiques;

public interface StatistiqueService {
    Integer totalCommandes(Integer id);
    Double totalCumule(Integer id);
    String firstAndLastDateCommande(Integer id);
}
