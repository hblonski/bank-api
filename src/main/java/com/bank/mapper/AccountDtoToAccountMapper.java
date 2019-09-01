package com.bank.mapper;

import com.bank.data.entity.Account;
import com.bank.dto.AccountDTO;

import javax.validation.constraints.NotNull;

public class AccountDtoToAccountMapper implements Mapper<AccountDTO, Account> {
    @Override
    public Account map(@NotNull AccountDTO from) {
        return new Account(from.getId(), from.getNumber(), from.getBalance(), from.getBank());
    }
}
