package com.bank.servlet;

import com.bank.data.dao.BaseDao;
import com.bank.data.dao.BaseDaoImpl;
import com.bank.data.repository.AccountRepository;
import com.bank.data.repository.AccountRepositoryImpl;
import com.bank.data.repository.ClientRepository;
import com.bank.data.repository.ClientRepositoryImpl;
import com.bank.service.AccountService;
import com.bank.service.AccountServiceImpl;
import com.bank.service.ClientService;
import com.bank.service.ClientServiceImpl;
import org.glassfish.jersey.internal.inject.AbstractBinder;

public class ApplicationBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bind(BaseDaoImpl.class).to(BaseDao.class);

        // Client
        bind(ClientRepositoryImpl.class).to(ClientRepository.class);
        bind(ClientServiceImpl.class).to(ClientService.class);

        // Account
        bind(AccountRepositoryImpl.class).to(AccountRepository.class);
        bind(AccountServiceImpl.class).to(AccountService.class);
    }
}
