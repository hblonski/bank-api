package com.bank.data.repository;

import com.bank.data.entity.Transaction;

import javax.validation.constraints.NotNull;

public interface TransactionRepository {
    Transaction save(@NotNull Transaction transaction);
}