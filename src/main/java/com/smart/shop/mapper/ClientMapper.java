package com.smart.shop.mapper;

import com.smart.shop.dto.client.ClientDto;
import com.smart.shop.model.Client;
import com.smart.shop.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClientMapper{
//    @Mapping(source = "username", target = "user.username")
//    @Mapping(source = "role", target = "user.role")
//    Client ClientDtoToClient(ClientDto clientDto);
//    @Mapping(source = "user.username", target = "username")
//    @Mapping(source = "user.role", target = "role")
//    ClientDto ClientToClientDto(Client client);
    default ClientDto clientToClientDto(Client client){
        if(client == null) return null;
        ClientDto clientDto = new ClientDto();
        clientDto.setId(client.getId());
        clientDto.setNom(client.getNom());
        clientDto.setEmail(client.getEmail());
        clientDto.setNiveau_fidelete(client.getNiveau_fidelete());
        if(client.getUser() != null){
            clientDto.setUsername(client.getUser().getUsername());
            clientDto.setRole(client.getUser().getRole());
        }
        return clientDto;
    }
    default Client clientDtoToClient(ClientDto clientDto){
        if(clientDto == null) return null;
        Client client = new Client();
        client.setId(clientDto.getId());
        client.setNom(clientDto.getNom());
        client.setEmail(clientDto.getEmail());
        client.setNiveau_fidelete(clientDto.getNiveau_fidelete());

        User user = new User();
        user.setUsername(clientDto.getUsername());
        user.setRole(clientDto.getRole());
        client.setUser(user);

        return client;
    }
}
