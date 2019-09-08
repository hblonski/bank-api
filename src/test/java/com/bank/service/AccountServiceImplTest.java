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
import java.math.BigInteger;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceImplTest extends BaseTest {

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
        when(accountRepository.getNextId()).thenReturn(mock(BigInteger.class));
        AccountDTO saved = accountService.create(clientId);
        assertNotNull(saved);
        assertEquals(0.0, saved.getBalance(), 0.0);
    }

    @Test(expected = EntityNotFoundException.class)
    public void should_throwException_when_clientNotFound() {
        accountService.create(1L);
    }

    @Test(expected = EntityExistsException.class)
    public void should_throwException_when_clientAlreadyHasAccount() {
        Long clientId = 1L;
        Client client = mock(Client.class);
        when(clientRepository.findById(any())).thenReturn(client);
        when(client.getAccount()).thenReturn(mock(Account.class));
        accountService.create(clientId);
    }

    @Test(expected = ConstraintViolationException.class)
    public void should_throwException_when_constraintsViolated() {
        Long clientId = 1L;
        when(clientRepository.findById(any())).thenReturn(mock(Client.class));
        ConstraintViolationException exception = mockViolationException("violation");
        when(accountRepository.getNextId()).thenReturn(mock(BigInteger.class));
        when(accountRepository.save(any())).thenThrow(exception);
        accountService.create(clientId);
    }

    @Test
    public void should_returnAccountData_when_accountFound() {
        Account account = new AccountDtoToAccountMapper().map(mockAccountDTO());
        when(accountRepository.findByNumberAndBank(any(), any())).thenReturn(account);
        AccountDTO accountDTO = accountService.get("123");
        assertNotNull(accountDTO);
    }

    @Test
    public void should_returnNull_when_accountNotFound() {
        assertNull(accountService.get("123"));
    }
}
