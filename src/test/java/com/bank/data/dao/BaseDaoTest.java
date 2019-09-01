package com.bank.data.dao;

import com.bank.BaseTest;
import com.bank.data.entity.Account;
import com.bank.data.entity.Client;
import com.bank.mapper.AccountDtoToAccountMapper;
import com.bank.mapper.ClientDtoToClientMapper;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import javax.ws.rs.core.Application;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class BaseDaoTest extends BaseTest {

    private BaseDao baseDao;

    @Override
    protected Application configure() {
        baseDao = new BaseDaoImpl();
        return new ResourceConfig()
                .register(baseDao);
    }

    @Test
    public void should_findById_when_objectSaved() {
        Client client = new ClientDtoToClientMapper().map(mockClientDTO());
        baseDao.save(client);
        assertNotNull(baseDao.findById(client.getClass(), client.getId()));
    }

    @Test
    public void should_findByFields_when_objectsSaved() {
        Account account = new AccountDtoToAccountMapper().map(mockAccountDTO());
        baseDao.save(account);
        Map<String, Object> fieldValues = new HashMap<>();
        fieldValues.put("number", account.getNumber());
        fieldValues.put("bank", account.getBank());
        List<Account> accounts = baseDao.find(Account.class, fieldValues);
        assertNotNull(accounts);
        assertEquals(1, accounts.size());
    }
}
