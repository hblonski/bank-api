package com.bank.data.repository;

import com.bank.BaseTest;
import com.bank.data.dao.BaseDao;
import com.bank.data.entity.Account;
import com.bank.mapper.AccountDtoToAccountMapper;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Application;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AccountRepositoryTest extends BaseTest {

    @Mock
    private BaseDao baseDao;

    private AccountRepository accountRepository;

    @Override
    protected Application configure() {
        MockitoAnnotations.initMocks(this);
        accountRepository = new AccountRepositoryImpl();
        return new ResourceConfig()
                .register(accountRepository)
                .register(new AbstractBinder() {
                    @Override
                    protected void configure() {
                        bind(baseDao).to(BaseDao.class);
                    }
                });
    }

    @Test
    public void should_returnAccount_when_accountCreated() {
        Long id = 2L;
        Account account = new AccountDtoToAccountMapper().map(mockAccountDTO());
        when(baseDao.save(any())).thenReturn(id);
        Account saved = accountRepository.save(account);
        assertNotNull(saved);
        assertEquals(id, saved.getId());
    }

    @Test(expected = ConstraintViolationException.class)
    public void should_throwException_when_constraintsViolated() {
        ConstraintViolationException exception = mockViolationException("violated");
        when(baseDao.save(any())).thenThrow(exception);
        accountRepository.save(mock(Account.class));
    }
}
