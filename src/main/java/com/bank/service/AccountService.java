package com.bank.service;

import com.bank.dto.AccountDTO;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.NotNull;

public interface AccountService {
    AccountDTO save(
            @NotNull Long clientId,
            @NotNull AccountDTO accountDTO
    ) throws EntityNotFoundException, EntityExistsException;
}
