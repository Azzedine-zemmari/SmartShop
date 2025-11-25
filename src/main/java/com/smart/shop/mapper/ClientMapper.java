package com.smart.shop.mapper;

import com.smart.shop.dto.client.ClientDto;
import com.smart.shop.model.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClientMapper{
    Client ClientDtoToClient(ClientDto clientDto);
    ClientDto ClientToClientDto(Client client);
}
