package com.bank.service;

import com.bank.BaseTest;
import com.bank.data.entity.Account;
import com.bank.data.repository.AccountRepository;
import com.bank.data.repository.TransactionRepository;
import com.bank.data.value.BankProperties;
import com.bank.data.value.TransactionType;
import com.bank.dto.TransactionDTO;
import com.bank.exception.TransactionException;
import com.bank.mapper.AccountDtoToAccountMapper;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import javax.persistence.EntityNotFoundException;
import javax.ws.rs.core.Application;
import java.security.InvalidParameterException;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceTest extends BaseTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private AccountRepository accountRepository;

    private TransactionService transactionService;

    @Override
    protected Application configure() {
        MockitoAnnotations.initMocks(this);
        transactionService = new TransactionServiceImpl();
        return new ResourceConfig()
                .register(transactionService)
                .register(new AbstractBinder() {
                    @Override
                    protected void configure() {
                        bind(transactionRepository).to(TransactionRepository.class);
                        bind(accountRepository).to(AccountRepository.class);
                    }
                });
    }

    @Test
    public void should_returnTransaction_when_moneyTransferredFromForeignAccount() throws Exception {
        TransactionDTO transactionDTO = mockTransactionDTO();
        transactionDTO.setType(TransactionType.TRANSFER);
        transactionDTO.setOriginAccountBank(456);
        transactionDTO.setDestinationAccountBank(BankProperties.BANK_CODE);
        Account destination = new AccountDtoToAccountMapper().map(mockAccountDTO());
        destination.setBalance(0.0);
        destination.setBank(BankProperties.BANK_CODE);
        when(accountRepository.findByNumberAndBank(transactionDTO.getDestinationAccount(),
                                                   transactionDTO.getDestinationAccountBank()))
                .thenReturn(destination);
        when(accountRepository.save(any())).thenReturn(mock(Account.class));
        transactionService.transfer(transactionDTO);
        assertEquals(transactionDTO.getValue(), destination.getBalance());
    }

    @Test
    public void should_returnTransaction_when_moneyTransferredToForeignAccount() throws Exception {
        TransactionDTO transactionDTO = mockTransactionDTO();
        transactionDTO.setType(TransactionType.TRANSFER);
        transactionDTO.setOriginAccountBank(BankProperties.BANK_CODE);
        transactionDTO.setDestinationAccountBank(987);

        Account origin = new AccountDtoToAccountMapper().map(mockAccountDTO());
        origin.setBalance(123.0);
        origin.setBank(BankProperties.BANK_CODE);

        when(accountRepository.findByNumberAndBank(transactionDTO.getOriginAccount(),
                                                   transactionDTO.getOriginAccountBank()))
                .thenReturn(origin);
        when(accountRepository.save(any())).thenReturn(mock(Account.class));

        transactionService.transfer(transactionDTO);
        assertEquals(0.0, origin.getBalance(), 0.0);
    }

    @Test
    public void should_returnTransaction_when_moneyTransferredNoAccountForeign() throws Exception {
        TransactionDTO transactionDTO = mockTransactionDTO();
        transactionDTO.setType(TransactionType.TRANSFER);
        transactionDTO.setOriginAccountBank(456);
        transactionDTO.setDestinationAccountBank(BankProperties.BANK_CODE);

        Account destination = new AccountDtoToAccountMapper().map(mockAccountDTO());
        destination.setBalance(0.0);
        destination.setBank(BankProperties.BANK_CODE);

        Account origin = new AccountDtoToAccountMapper().map(mockAccountDTO());
        origin.setBalance(123.0);
        origin.setBank(BankProperties.BANK_CODE);

        when(accountRepository.findByNumberAndBank(transactionDTO.getDestinationAccount(),
                                                   transactionDTO.getDestinationAccountBank()))
                .thenReturn(destination);
        when(accountRepository.findByNumberAndBank(transactionDTO.getOriginAccount(),
                                                   transactionDTO.getOriginAccountBank()))
                .thenReturn(origin);
        transactionService.transfer(transactionDTO);

        assertEquals(transactionDTO.getValue(), destination.getBalance());
        assertEquals(0.0, origin.getBalance(), 0.0);
    }

    @Test(expected = InvalidParameterException.class)
    public void should_throwException_when_transferWrongTransactionType() throws Exception {
        TransactionDTO transactionDTO = mockTransactionDTO();
        transactionDTO.setType(TransactionType.WITHDRAWAL);
        transactionService.transfer(transactionDTO);
    }

    @Test(expected = TransactionException.class)
    public void should_throwException_when_transferAccountsDoNotBelongToTheBank() throws Exception {
        TransactionDTO transactionDTO = mockTransactionDTO();
        transactionDTO.setType(TransactionType.TRANSFER);
        transactionService.transfer(transactionDTO);
    }

    @Test(expected = EntityNotFoundException.class)
    public void should_throwException_when_transferAccountsNotFound() throws Exception {
        TransactionDTO transactionDTO = mockTransactionDTO();
        transactionDTO.setType(TransactionType.TRANSFER);
        transactionDTO.setOriginAccountBank(BankProperties.BANK_CODE);
        transactionService.transfer(transactionDTO);
    }

    @Test(expected = TransactionException.class)
    public void should_throwException_when_transferInvalidOriginAccount() throws Exception {
        TransactionDTO transactionDTO = mockTransactionDTO();
        transactionDTO.setType(TransactionType.TRANSFER);
        transactionDTO.setOriginAccountBank(BankProperties.BANK_CODE);
        when(accountRepository.findByNumberAndBank(transactionDTO.getDestinationAccount(),
                                                   transactionDTO.getDestinationAccountBank()))
                .thenReturn(mock(Account.class));
        transactionService.transfer(transactionDTO);
    }

    @Test(expected = TransactionException.class)
    public void should_throwException_when_transferInvalidDestinationAccount() throws Exception {
        TransactionDTO transactionDTO = mockTransactionDTO();
        transactionDTO.setType(TransactionType.TRANSFER);
        transactionDTO.setDestinationAccountBank(BankProperties.BANK_CODE);
        when(accountRepository.findByNumberAndBank(transactionDTO.getOriginAccount(),
                                                   transactionDTO.getOriginAccountBank()))
                .thenReturn(mock(Account.class));
        transactionService.transfer(transactionDTO);
    }

    @Test(expected = TransactionException.class)
    public void should_throwException_when_transferInsufficientBalance() throws Exception {
        TransactionDTO transactionDTO = mockTransactionDTO();
        transactionDTO.setType(TransactionType.TRANSFER);
        transactionDTO.setOriginAccountBank(BankProperties.BANK_CODE);
        Account origin = mock(Account.class);
        when(origin.getBalance()).thenReturn(0.0);
        when(origin.belongsToThisBank()).thenReturn(true);
        when(accountRepository.findByNumberAndBank(transactionDTO.getOriginAccount(),
                                                   transactionDTO.getOriginAccountBank()))
                .thenReturn(origin);
        when(accountRepository.save(any())).thenReturn(mock(Account.class));
        transactionService.transfer(transactionDTO);
    }
}
