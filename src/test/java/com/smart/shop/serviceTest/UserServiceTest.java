package com.smart.shop.serviceTest;

import com.smart.shop.dto.UserDto;
import com.smart.shop.dto.UserLoginDto;
import com.smart.shop.exeception.IncorrectPasswordException;
import com.smart.shop.exeception.UserNotFound;
import com.smart.shop.mapper.UserMapper;
import com.smart.shop.model.User;
import com.smart.shop.repository.ClientRepository;
import com.smart.shop.repository.UserRepository;
import com.smart.shop.service.user.UserServiceImpl;
import com.smart.shop.utils.PasswordUtils;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private ClientRepository clientRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;


    @Test
    public void loginThrowUserNotFoundException(){

        UserLoginDto userDto = new UserLoginDto();
        userDto.setUsername("amine");
        userDto.setPassword("1245");

        HttpSession session = Mockito.mock(HttpSession.class);

        Mockito.when(userRepository.findByUsername("amine")).thenReturn(Optional.empty());

        assertThrows(UserNotFound.class,()-> userService.login(userDto,session));
    }

    @Test
    public void loginWrongPassword(){
        UserLoginDto userDto = new UserLoginDto();
        userDto.setUsername("amine");
        userDto.setPassword("1245");

        HttpSession session = Mockito.mock(HttpSession.class);

        User user = new User();
        user.setUsername("amine");
        user.setPassword("real_encode_pass");

        Mockito.when(userRepository.findByUsername("amine")).thenReturn(Optional.of(user));

        Mockito.mockStatic(PasswordUtils.class)
                .when(()-> PasswordUtils.checkPassword("12345","real_encode_pass"))
                .thenReturn(false);

        assertThrows(IncorrectPasswordException.class , ()-> userService.login(userDto,session));

    }
    @Test
    public void loginSucess(){
        UserLoginDto userLoginDto = new UserLoginDto();
        userLoginDto.setUsername("amine");
        userLoginDto.setPassword("1245");

        HttpSession session = Mockito.mock(HttpSession.class);

        User user = new User();
        user.setUsername("amine");
        user.setPassword("real_encode_pass");

        UserDto userDto = new UserDto();
        userDto.setId(1);
        userDto.setUsername("amine");

        Mockito.when(userRepository.findByUsername("amine")).thenReturn(Optional.of(user));

        Mockito.mockStatic(PasswordUtils.class)
                .when(()-> PasswordUtils.checkPassword("1245","real_encode_pass"))
                .thenReturn(true);

        Mockito.when(userMapper.userToUserDto(user)).thenReturn(userDto);

        UserDto result = userService.login(userLoginDto,session);


        Mockito.verify(session).setAttribute("USER",user);
        Mockito.verify(userMapper).userToUserDto(user);

        assertEquals("amine",result.getUsername());

    }

    @Test
    public void logoutSuccessfully(){

       HttpSession SESSION =  Mockito.mock(HttpSession.class);

        userService.logout(SESSION);

        Mockito.verify(SESSION).invalidate();
    }

}
