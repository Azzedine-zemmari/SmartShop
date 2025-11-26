package com.smart.shop.mapper;

import com.smart.shop.dto.client.ClientDto;
import com.smart.shop.model.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClientMapper{
    @Mapping(source = "username", target = "user.username")
    @Mapping(source = "role", target = "user.role")
    Client ClientDtoToClient(ClientDto clientDto);
    @Mapping(source = "user.username", target = "username")
    @Mapping(source = "user.role", target = "role")
    ClientDto ClientToClientDto(Client client);
}
