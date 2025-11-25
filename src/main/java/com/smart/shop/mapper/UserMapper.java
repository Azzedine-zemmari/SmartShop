package com.smart.shop.mapper;

import com.smart.shop.dto.UserDto;
import com.smart.shop.dto.UserRegisterDto;
import com.smart.shop.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel="spring")
public interface UserMapper {
    @Mapping(target="password" , ignore = true)
    @Mapping(target="id" , ignore=true)
    User UserRegisterDtoToUser(UserRegisterDto userRegisterDto);
    UserDto userToUserDto(User user);

}
