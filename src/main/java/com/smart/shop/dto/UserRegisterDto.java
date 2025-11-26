package com.smart.shop.dto;

import com.smart.shop.enums.Niveau_fidelete;
import com.smart.shop.enums.Role;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRegisterDto {
    @NotBlank(message = "Veuillez saisir l'email")
    private String username;

    @NotBlank(message="Veuillez saisir le mot de pass")
    private String password;

    @NotBlank(message="Veuillez saisr le role")
    private Role role;

    private String nom;  // only for client
    private Niveau_fidelete niveau_fidelete; // only for client
    private String email;

}
