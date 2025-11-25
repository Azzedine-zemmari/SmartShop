package com.smart.shop.service.user;

import com.smart.shop.dto.UserDto;
import com.smart.shop.dto.UserLoginDto;
import com.smart.shop.dto.UserRegisterDto;
import jakarta.servlet.http.HttpSession;

public interface UserServiceInterface {
    UserDto register(UserRegisterDto user);
    UserDto login(UserLoginDto user, HttpSession session);
    void logout(HttpSession session);
}
