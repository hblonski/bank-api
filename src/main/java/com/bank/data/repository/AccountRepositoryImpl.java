package com.bank.data.repository;

import com.bank.data.dao.BaseDao;
import com.bank.data.entity.Account;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.SQLDataException;
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
    public Account findByNumberAndBank(@NotNull String number, @NotNull Integer bank) throws SQLDataException {
        Map<String, Object> fieldValues = new HashMap<>();
        fieldValues.put("number", number);
        fieldValues.put("bank", bank);
        List<Account> accounts = baseDao.find(Account.class, fieldValues);
        if (accounts == null || accounts.isEmpty()) {
            return null;
        }
        if (accounts.size() > 1) {
            // This should not happen, since 'number' has a unique constraint, but I'll leave this check to prevent
            // invalid transactions in case it breaks accidentally in the future.
            throw new SQLDataException("Duplicate accounts found.");
        }
        return accounts.get(0);
    }
}
