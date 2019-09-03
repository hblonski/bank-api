package com.bank.data.repository;

import com.bank.data.entity.Client;

import javax.validation.constraints.NotNull;

public interface ClientRepository {
    Client save(@NotNull Client client);
    Client findById(@NotNull Long Id);
    Client findByDocumentNumber(@NotNull String documentNumber);
}
