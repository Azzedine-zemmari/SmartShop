package com.smart.shop.controller;

import com.smart.shop.dto.UserDto;
import com.smart.shop.dto.UserLoginDto;
import com.smart.shop.dto.UserRegisterDto;
import com.smart.shop.service.user.UserServiceInterface;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserServiceInterface userServiceInterface;
    public UserController(UserServiceInterface userServiceInterface){
        this.userServiceInterface = userServiceInterface;
    }
    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@RequestBody UserRegisterDto userRegisterDto){
        UserDto userDto = userServiceInterface.register(userRegisterDto);
        return ResponseEntity.ok(userDto);
    }
    @PostMapping("/login")
    public ResponseEntity<UserDto> loginUser(@RequestBody UserLoginDto userLoginDto , HttpSession session){
        UserDto userDto = userServiceInterface.login(userLoginDto,session);
        return ResponseEntity.ok(userDto);
    }
}
