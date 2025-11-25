package com.smart.shop.service.user;

import com.smart.shop.dto.UserDto;
import com.smart.shop.dto.UserRegisterDto;

public interface UserServiceInterface {
    UserDto register(UserRegisterDto user);
}
