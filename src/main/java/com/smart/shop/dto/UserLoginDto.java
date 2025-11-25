package com.smart.shop.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserLoginDto {
    @NotBlank(message = "Veuillez saisir l'email ")
    private String username;
    @NotBlank(message="Veuillez saisir le mot de pass")
    private String password;
}
