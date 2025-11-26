package com.smart.shop.service.user;

import com.smart.shop.dto.UserDto;
import com.smart.shop.dto.UserLoginDto;
import com.smart.shop.dto.UserRegisterDto;
import com.smart.shop.enums.Role;
import com.smart.shop.exeception.IncorrectPasswordException;
import com.smart.shop.exeception.UserAlreadyExiste;
import com.smart.shop.exeception.UserNotFound;
import com.smart.shop.mapper.UserMapper;
import com.smart.shop.model.Client;
import com.smart.shop.model.User;
import com.smart.shop.repository.ClientRepository;
import com.smart.shop.repository.UserRepository;
import com.smart.shop.utils.PasswordUtils;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserServiceInterface{
    private final ClientRepository clientRepository;
    private UserRepository userRepository;
    private UserMapper userMapper;
    public UserServiceImpl(UserRepository userRepository , UserMapper userMapper, ClientRepository clientRepository){
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.clientRepository = clientRepository;
    }
    @Override
    public UserDto login(UserLoginDto userLoginDto , HttpSession session){
        User user = userRepository.findByUsername(userLoginDto.getUsername()).orElseThrow(()-> new UserNotFound("utilisateur n'est pas exsiste"));
        boolean isValid = PasswordUtils.checkPassword(userLoginDto.getPassword(),user.getPassword());
        if(!isValid){
            throw new IncorrectPasswordException("mot de pass incorrect");
        }
        session.setAttribute("USER",user);

        return userMapper.userToUserDto(user);

    }
    @Override
    public void logout(HttpSession session){
        session.invalidate();
    }
}
