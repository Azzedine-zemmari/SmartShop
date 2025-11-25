package com.smart.shop.service.user;

import com.smart.shop.dto.UserDto;
import com.smart.shop.dto.UserLoginDto;
import com.smart.shop.dto.UserRegisterDto;
import com.smart.shop.exeception.IncorrectPasswordException;
import com.smart.shop.exeception.UserAlreadyExiste;
import com.smart.shop.exeception.UserNotFound;
import com.smart.shop.mapper.UserMapper;
import com.smart.shop.model.User;
import com.smart.shop.repository.UserRepository;
import com.smart.shop.utils.PasswordUtils;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserServiceInterface{
    private UserRepository userRepository;
    private UserMapper userMapper;
    public UserServiceImpl(UserRepository userRepository , UserMapper userMapper){
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserDto register(UserRegisterDto userRegisterDto){
        User user = userMapper.UserRegisterDtoToUser(userRegisterDto);
        Optional<User> userisExists = userRepository.findByUsername(user.getUsername());
        if(userisExists.isPresent()){
            throw new UserAlreadyExiste("Utilisateur est deja exists");
        }
        String hashPassword = PasswordUtils.hashPassword(userRegisterDto.getPassword());
        user.setPassword(hashPassword);
        userRepository.save(user);
        return userMapper.userToUserDto(user);

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
