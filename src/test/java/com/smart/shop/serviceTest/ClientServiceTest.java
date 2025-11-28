package com.smart.shop.serviceTest;

import com.smart.shop.dto.UserRegisterDto;
import com.smart.shop.dto.client.ClientDto;
import com.smart.shop.enums.Niveau_fidelete;
import com.smart.shop.enums.Role;
import com.smart.shop.exeception.UserAlreadyExiste;
import com.smart.shop.mapper.ClientMapper;
import com.smart.shop.model.Client;
import com.smart.shop.model.User;
import com.smart.shop.repository.ClientRepository;
import com.smart.shop.repository.UserRepository;
import com.smart.shop.service.client.ClientServiceImpl;
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
public class ClientServiceTest {
    @Mock
    private ClientRepository clientRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ClientMapper clientMapper;

    @InjectMocks
    private ClientServiceImpl clientService;

    @Test
    public void ShouldThrowUserAlreadyException(){
        UserRegisterDto userRegisterDto = new UserRegisterDto();
        userRegisterDto.setUsername("azzedine");

        Mockito.when(userRepository.findByUsername("azzedine")).thenReturn(Optional.of(new User()));

        assertThrows(UserAlreadyExiste.class,()-> clientService.creeClient(userRegisterDto));
    }

    @Test
    public void InsertClientSuccessfully(){
        UserRegisterDto userRegisterDto = new UserRegisterDto();
        userRegisterDto.setUsername("azzedine");
        userRegisterDto.setPassword("1234");
        userRegisterDto.setEmail("azzedine@gmail.com");
        userRegisterDto.setRole(Role.CLIENT);

        Mockito.when(userRepository.findByUsername("azzedine")).thenReturn(Optional.empty());

        User savedUser = new User();
        savedUser.setId(1);
        savedUser.setUsername("azzedine");
        savedUser.setPassword("hashed");
        savedUser.setRole(Role.CLIENT);

        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(savedUser);

        Client savedClient = new Client();
        savedClient.setId(10);
        savedClient.setNom("azzedine");
        savedClient.setEmail("azzedine@gmail.com");
        savedClient.setUser(savedUser);
        savedClient.setNiveau_fidelete(Niveau_fidelete.BASIC);

        Mockito.when(clientRepository.save(Mockito.any(Client.class))).thenReturn(savedClient);

        ClientDto clientDto = new ClientDto();
        clientDto.setId(10);
        clientDto.setNom("azzedine");
        clientDto.setEmail("azzedine@gmail.com");

        Mockito.when(clientMapper.clientToClientDto(savedClient)).thenReturn(clientDto);

        ClientDto result = clientService.creeClient(userRegisterDto);

        assertEquals(10,result.getId());
        assertEquals("azzedine",result.getNom());
        assertEquals("azzedine@gmail.com",result.getEmail());

    }
}
