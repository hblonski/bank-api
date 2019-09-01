package com.bank.data.repository;

import com.bank.data.entity.Account;

import javax.validation.constraints.NotNull;
import java.sql.SQLDataException;

public interface AccountRepository {
    Account save(@NotNull Account account);
    Account findByNumberAndBank(@NotNull String number, @NotNull Integer bank) throws SQLDataException;
}
