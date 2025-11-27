package com.smart.shop.mapper;

import com.smart.shop.dto.UserDto;
import com.smart.shop.dto.UserRegisterDto;
import com.smart.shop.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserDto userToUserDto(User user){
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setRole(user.getRole());
        userDto.setUsername(user.getUsername());
        return userDto;
    };

}
