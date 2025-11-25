package com.smart.shop.service.client;

import com.smart.shop.dto.client.ClientDto;
import com.smart.shop.exeception.UserNotFound;
import com.smart.shop.mapper.ClientMapper;
import com.smart.shop.model.Client;
import com.smart.shop.model.User;
import com.smart.shop.repository.ClientRepository;
import com.smart.shop.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientServiceInterface{
    private ClientRepository clientRepository;
    private UserRepository userRepository;
    private ClientMapper clientMapper;

    public ClientServiceImpl(ClientRepository clientRepository,UserRepository userRepository , ClientMapper clientMapper){
        this.clientRepository = clientRepository;
        this.userRepository = userRepository;
        this.clientMapper = clientMapper;
    }

    @Override
    public ClientDto creeUser(int userId,Client client){
        Optional<User> userOptional = userRepository.findById(userId);
        if(!userOptional.isPresent()){
            throw new UserNotFound("utilisateur avec cette id n'est pas existe");
        }
        User user = userOptional.get();
        Client newClient = new Client();
        newClient.setId(user.getId());
        newClient.setNom(client.getNom());
        newClient.setPassword(user.getPassword());
        newClient.setUsername(user.getUsername());
        newClient.setRole(user.getRole());

        clientRepository.save(newClient);
        return clientMapper.ClientToClientDto(newClient);
    }
}
