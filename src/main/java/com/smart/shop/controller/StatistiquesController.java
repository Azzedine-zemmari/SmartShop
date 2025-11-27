package com.smart.shop.controller;

import com.smart.shop.service.statistiques.StatistiqueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/statistique")
@RequiredArgsConstructor
public class StatistiquesController {
    private final StatistiqueService statistiqueService;

    @GetMapping("/totalCommande")
    public ResponseEntity<Long> totalCommande(){
        Long total = statistiqueService.totalCommandes();
        return ResponseEntity.ok(total);
    }
    @GetMapping("/totalCumule")
    public ResponseEntity<Double> totalCumule(){
        Double total = statistiqueService.totalCumule();
        return ResponseEntity.ok(total);
    }
}
