package com.bank.service;

import com.bank.BaseTest;
import com.bank.data.entity.Account;
import com.bank.data.entity.Client;
import com.bank.data.repository.AccountRepository;
import com.bank.data.repository.ClientRepository;
import com.bank.dto.AccountDTO;
import com.bank.mapper.AccountDtoToAccountMapper;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Application;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest extends BaseTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private ClientRepository clientRepository;

    private AccountService accountService;

    @Override
    protected Application configure() {
        MockitoAnnotations.initMocks(this);
        accountService = new AccountServiceImpl();
        return new ResourceConfig()
                .register(accountService)
                .register(new AbstractBinder() {
                    @Override
                    protected void configure() {
                        bind(accountRepository).to(AccountRepository.class);
                        bind(clientRepository).to(ClientRepository.class);
                    }
                });
    }

    @Test
    public void should_returnAccountWithZeroBalance_when_accountCreated() {
        Long clientId = 1L;
        AccountDTO accountDTO = mockAccountDTO();
        when(clientRepository.findById(any())).thenReturn(mock(Client.class));
        when(accountRepository.save(any())).thenReturn(new AccountDtoToAccountMapper().map(accountDTO));
        AccountDTO saved = accountService.save(clientId, accountDTO);
        assertNotNull(saved);
        assertEquals(0.0, saved.getBalance(), 0.0);
    }

    @Test(expected = EntityNotFoundException.class)
    public void should_throwException_when_clientNotFound() {
        Long clientId = 1L;
        AccountDTO accountDTO = mockAccountDTO();
        accountService.save(clientId, accountDTO);
    }

    @Test(expected = EntityExistsException.class)
    public void should_throwException_when_clientAlreadyHasAccount() {
        Long clientId = 1L;
        AccountDTO accountDTO = mockAccountDTO();
        Client client = mock(Client.class);
        when(clientRepository.findById(any())).thenReturn(client);
        when(client.getAccount()).thenReturn(mock(Account.class));
        accountService.save(clientId, accountDTO);
    }

    @Test(expected = ConstraintViolationException.class)
    public void should_throwException_when_constraintsViolated() {
        Long clientId = 1L;
        AccountDTO accountDTO = mockAccountDTO();
        when(clientRepository.findById(any())).thenReturn(mock(Client.class));
        ConstraintViolationException exception = mockViolationException("violation");
        when(accountRepository.save(any())).thenThrow(exception);
        accountService.save(clientId, accountDTO);
    }
}