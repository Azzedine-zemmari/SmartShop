package com.smart.shop.dto.client;

import com.smart.shop.enums.Niveau_fidelete;
import com.smart.shop.enums.Role;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ClientDto {
    private int id;
    @NotBlank(message="Veuillez saisir le nom ")
    private String nom;
    private Niveau_fidelete niveau_fidelete;
    private String username;
    private String email;
    private Role role;
}
