package com.bank.service;

import com.bank.data.entity.Account;
import com.bank.data.entity.Client;
import com.bank.data.repository.AccountRepository;
import com.bank.data.repository.ClientRepository;
import com.bank.dto.AccountDTO;
import com.bank.mapper.AccountDtoToAccountMapper;
import com.bank.mapper.AccountToAccountDtoMapper;

import javax.inject.Inject;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.NotNull;

public class AccountServiceImpl implements AccountService {

    @Inject
    private AccountRepository accountRepository;

    @Inject
    private ClientRepository clientRepository;

    @Override
    public AccountDTO save(
            @NotNull Long clientId,
            @NotNull AccountDTO accountDTO
    ) throws EntityNotFoundException, EntityExistsException {
        Account account = new AccountDtoToAccountMapper().map(accountDTO);
        account.zeroBalance();
        Client client = clientRepository.findById(clientId);
        if (client == null) {
            throw new EntityNotFoundException("Client not found");
        }
        if (client.getAccount() != null) {
            throw new EntityExistsException("Client already has an account");
        }
        Account saved = accountRepository.save(account);
        client.setAccount(saved);
        clientRepository.save(client);
        return new AccountToAccountDtoMapper().map(saved);
    }
}
