package com.bank.servlet;

import com.bank.data.dao.BaseDao;
import com.bank.data.dao.BaseDaoImpl;
import com.bank.data.repository.AccountRepository;
import com.bank.data.repository.AccountRepositoryImpl;
import com.bank.data.repository.ClientRepository;
import com.bank.data.repository.ClientRepositoryImpl;
import com.bank.data.repository.TransactionRepository;
import com.bank.data.repository.TransactionRepositoryImpl;
import com.bank.service.AccountService;
import com.bank.service.AccountServiceImpl;
import com.bank.service.ClientService;
import com.bank.service.ClientServiceImpl;
import com.bank.service.TransactionService;
import com.bank.service.TransactionServiceImpl;
import org.glassfish.jersey.internal.inject.AbstractBinder;

public class ApplicationBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bind(BaseDaoImpl.class).to(BaseDao.class);

        // Clients
        bind(ClientRepositoryImpl.class).to(ClientRepository.class);
        bind(ClientServiceImpl.class).to(ClientService.class);

        // Accounts
        bind(AccountRepositoryImpl.class).to(AccountRepository.class);
        bind(AccountServiceImpl.class).to(AccountService.class);

        // Transactions
        bind(TransactionRepositoryImpl.class).to(TransactionRepository.class);
        bind(TransactionServiceImpl.class).to(TransactionService.class);
    }
}
