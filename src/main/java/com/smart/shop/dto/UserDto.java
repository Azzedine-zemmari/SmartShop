package com.smart.shop.dto;

import com.smart.shop.enums.Role;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserDto {
    private int id;
    private String username;
    private Role role;
}
