package com.bank.service;

import com.bank.dto.AccountDTO;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.NotNull;

public interface AccountService {
    AccountDTO create(@NotNull Long clientId) throws EntityNotFoundException, EntityExistsException;
    AccountDTO get(@NotNull String number);
}
