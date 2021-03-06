package com.bank.data.repository;

import com.bank.data.dao.BaseDao;
import com.bank.data.entity.Account;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountRepositoryImpl implements AccountRepository {

    @Inject
    private BaseDao baseDao;

    @Override
    public Account save(@NotNull Account account) {
        Serializable id = baseDao.save(account);
        account.setId((Long) id);
        return account;
    }

    @Override
    public Account findByNumberAndBank(@NotNull String number, @NotNull Integer bank) {
        Map<String, Object> fieldValues = new HashMap<>();
        fieldValues.put("number", number);
        fieldValues.put("bank", bank);
        List<Account> accounts = baseDao.find(Account.class, fieldValues);
        if (accounts == null || accounts.isEmpty()) {
            return null;
        }
        return accounts.get(0);
    }

    @Override
    public BigInteger getNextId() {
        return baseDao.getNextSequenceValue(Account.ACCOUNT_ID_SEQUENCE);
    }
}
