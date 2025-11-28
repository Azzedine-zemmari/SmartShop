package com.smart.shop.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserLoginDto {
    private String username;
    private String password;
}
