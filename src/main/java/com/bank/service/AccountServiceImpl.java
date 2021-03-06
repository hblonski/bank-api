package com.bank.service;

import com.bank.data.entity.Account;
import com.bank.data.entity.Client;
import com.bank.data.repository.AccountRepository;
import com.bank.data.repository.ClientRepository;
import com.bank.data.value.BankProperties;
import com.bank.dto.AccountDTO;
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
    public AccountDTO create(@NotNull Long clientId) throws EntityNotFoundException, EntityExistsException {
        Client client = clientRepository.findById(clientId);
        if (client == null) {
            throw new EntityNotFoundException("Client not found");
        }
        if (client.getAccount() != null) {
            throw new EntityExistsException("Client already has an account");
        }
        Account account = new Account();
        account.zeroBalance();
        account.setBank(BankProperties.BANK_CODE);
        account.setNumber(accountRepository.getNextId().toString());
        Account saved = accountRepository.save(account);
        client.setAccount(saved);
        clientRepository.save(client);
        return new AccountToAccountDtoMapper().map(saved);
    }

    @Override
    public AccountDTO get(@NotNull String number) {
        Account account = accountRepository.findByNumberAndBank(number, BankProperties.BANK_CODE);
        if (account != null) {
            return new AccountToAccountDtoMapper().map(account);
        }
        return null;
    }
}
