package com.smart.shop.controller;

import com.smart.shop.model.User;
import com.smart.shop.service.statistiques.StatistiqueService;
import jakarta.servlet.http.HttpSession;
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
    public ResponseEntity<Integer> totalCommande(HttpSession session){
        User user = (User) session.getAttribute("USER");
        Integer id = user.getId();
        Integer total = statistiqueService.totalCommandes(id);
        return ResponseEntity.ok(total);
    }
    @GetMapping("/totalCumule")
    public ResponseEntity<Double> totalCumule(HttpSession session){
        User user = (User) session.getAttribute("USER");
        Integer id = user.getId();
        Double total = statistiqueService.totalCumule(id);
        return ResponseEntity.ok(total);
    }
    @GetMapping("/dates")
    public ResponseEntity<String> firstAndLastCommandeDate(HttpSession session){
        User user = (User) session.getAttribute("USER");
        Integer id = user.getId();
        String dates = statistiqueService.firstAndLastDateCommande(id);
        return ResponseEntity.ok(dates);
    }
}
