package com.bank.data.repository;

import com.bank.data.entity.Account;

import javax.validation.constraints.NotNull;

public interface AccountRepository {
    Account save(@NotNull Account account);
}
