package com.smart.shop.controller;

import com.smart.shop.dto.client.ClientDto;
import com.smart.shop.model.Client;
import com.smart.shop.service.client.ClientServiceInterface;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/client")
public class ClientController {
    private ClientServiceInterface clientService;
    public ClientController(ClientServiceInterface clientServiceInterface){
        this.clientService = clientServiceInterface;
    }
    @PostMapping("/insert/{id}")
    public ResponseEntity<ClientDto> insert(@PathVariable("id") int userId, @RequestBody Client client){
        ClientDto newClient = clientService.creeUser(userId,client);
        return ResponseEntity.ok(newClient);
    }
}
