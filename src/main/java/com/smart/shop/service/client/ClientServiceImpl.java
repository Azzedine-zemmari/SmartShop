package com.smart.shop.service.client;

import com.smart.shop.dto.UserDto;
import com.smart.shop.dto.UserRegisterDto;
import com.smart.shop.dto.client.ClientDto;
import com.smart.shop.enums.Niveau_fidelete;
import com.smart.shop.enums.Role;
import com.smart.shop.exeception.UserAlreadyExiste;
import com.smart.shop.exeception.UserNotFound;
import com.smart.shop.mapper.ClientMapper;
import com.smart.shop.model.Client;
import com.smart.shop.model.User;
import com.smart.shop.repository.ClientRepository;
import com.smart.shop.repository.UserRepository;
import com.smart.shop.utils.PasswordUtils;
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
    public ClientDto creeClient(UserRegisterDto userRegisterDto){
        Optional<User> userisExists = userRepository.findByUsername(userRegisterDto.getUsername());
        if(userisExists.isPresent()){
            throw new UserAlreadyExiste("Utilisateur est deja exists");
        }
        User user = new User();
        user.setUsername(userRegisterDto.getUsername());
        String hashPassword = PasswordUtils.hashPassword(userRegisterDto.getPassword());
        user.setPassword(hashPassword);
        user.setRole(userRegisterDto.getRole());

        User savedUser = userRepository.save(user);

        Client client = new Client();
        client.setNom(userRegisterDto.getNom());
        client.setEmail(userRegisterDto.getEmail());
        client.setNiveau_fidelete(Niveau_fidelete.BASIC);
        client.setUser(savedUser);

        Client savedClient = clientRepository.save(client);
        return clientMapper.clientToClientDto(savedClient);
    }

    @Override
    public ClientDto consulterInfoClient(int id) {
        Optional<Client> client = clientRepository.findById(id);
        if(!client.isPresent()){
            throw new UserNotFound("client n'est pas trouve");
        }
        return clientMapper.clientToClientDto(client.get());
    }
    @Override
    public ClientDto updateClientInfo(int id , ClientDto clientDto){
        Client client = clientRepository.findById(id).orElseThrow(() -> new UserNotFound("client n'est pas trouve"));

        if (clientDto.getEmail() != null) {
            client.setEmail(clientDto.getEmail());
        }
        if (clientDto.getNom() != null) {
            client.setNom(clientDto.getNom());
        }
        if (clientDto.getNiveau_fidelete() != null) {
            client.setNiveau_fidelete(clientDto.getNiveau_fidelete());
        }
        if (client.getUser() != null) {
            if (clientDto.getUsername() != null) {
                client.getUser().setUsername(clientDto.getUsername());
            }
            if (clientDto.getRole() != null) {
                client.getUser().setRole(clientDto.getRole());
            }
        }

        clientRepository.save(client);

        ClientDto updateDto = clientMapper.clientToClientDto(client);

        return updateDto;
    }
}
