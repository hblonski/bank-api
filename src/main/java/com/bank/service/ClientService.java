package com.bank.service;

import com.bank.dto.ClientDTO;

import javax.validation.constraints.NotNull;

public interface ClientService {
    ClientDTO save(@NotNull ClientDTO clientDTO);
}
