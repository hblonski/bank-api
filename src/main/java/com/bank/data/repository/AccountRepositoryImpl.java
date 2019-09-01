package com.bank.data.repository;

import com.bank.data.dao.BaseDao;
import com.bank.data.entity.Account;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class AccountRepositoryImpl implements AccountRepository {

    @Inject
    private BaseDao baseDao;

    @Override
    public Account save(@NotNull Account account) {
        Serializable id = baseDao.save(account);
        account.setId((Long) id);
        return account;
    }
}
