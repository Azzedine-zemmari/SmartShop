package com.smart.shop.controller;

import com.smart.shop.dto.CommandeRequestDto;
import com.smart.shop.service.commande.CommandeService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/commande")
@AllArgsConstructor
public class CommandeController {
    private CommandeService commandeService;

    @PostMapping("/create")
    public ResponseEntity<CommandeRequestDto> createCommande(@RequestBody CommandeRequestDto dto ){
        CommandeRequestDto commandeRequestDto = commandeService.createCommande(dto);
        return ResponseEntity.ok(commandeRequestDto);
    }
    @PostMapping("/confirme/{id}")
    public ResponseEntity<String> confirmCommande(@PathVariable("id") Long commandeId){
        commandeService.ConfirmeCommande(commandeId);
        return ResponseEntity.ok("commande : " + commandeId + " confirme avec success");
    }
    @PostMapping("/cancel/{id}")
    public ResponseEntity<String> cancelCommande(@PathVariable("id") Long commandeId){
        commandeService.CancelCommande(commandeId);
        return ResponseEntity.ok("commande : " + commandeId + " annuller avec success");
    }
}
