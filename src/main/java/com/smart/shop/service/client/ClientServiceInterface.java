package com.smart.shop.service.client;

import com.smart.shop.dto.UserRegisterDto;
import com.smart.shop.dto.client.ClientDto;
import com.smart.shop.model.Client;
import java.util.List;

public interface ClientServiceInterface {
    ClientDto creeClient(UserRegisterDto userRegisterDto);
    ClientDto consulterInfoClient(int id);
    ClientDto updateClientInfo(int id, ClientDto clientDto);
    void deleteClient(int id);
    List<ClientDto> showAllClient();
}