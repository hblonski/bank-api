package com.bank.data.repository;

import com.bank.data.dao.BaseDao;
import com.bank.data.entity.Transaction;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class TransactionRepositoryImpl implements TransactionRepository {

    @Inject
    private BaseDao baseDao;

    @Override
    public Transaction save(@NotNull Transaction transaction) {
        Serializable id = baseDao.save(transaction);
        transaction.setId((Long) id);
        return transaction;
    }
}
