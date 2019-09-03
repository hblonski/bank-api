package com.bank.service;

import com.bank.dto.TransactionDTO;
import com.bank.exception.TransactionException;

import javax.validation.constraints.NotNull;

public interface TransactionService {
    TransactionDTO transfer(@NotNull TransactionDTO transactionDTO) throws TransactionException;
    TransactionDTO deposit(@NotNull TransactionDTO transactionDTO) throws TransactionException;
}
