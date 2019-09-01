package com.bank.mapper;

import com.bank.data.entity.Client;
import com.bank.dto.ClientDTO;

import javax.validation.constraints.NotNull;

public class ClientDtoToClientMapper implements Mapper<ClientDTO, Client> {
    @Override
    public Client map(@NotNull ClientDTO from) {
        return new Client(from.getId(),
                from.getName(),
                from.getDocumentNumber(),
                from.getAddress(),
                from.getCity(),
                from.getState(),
                from.getCountry(),
                from.getProfession(),
                from.getPhone(),
                from.getAccount());
    }
}