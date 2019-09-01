package com.bank.mapper;

import com.bank.data.entity.Client;
import com.bank.dto.ClientDTO;

import javax.validation.constraints.NotNull;

public class ClientToClientDtoMapper implements Mapper<Client, ClientDTO> {
    @Override
    public ClientDTO map(@NotNull Client from) {
        return new ClientDTO(
                from.getId(),
                from.getName(),
                from.getDocumentNumber(),
                from.getAddress(),
                from.getCity(),
                from.getState(),
                from.getCountry(),
                from.getProfession(),
                from.getPhone(),
                from.getAccount()
        );
    }
}
